#!/usr/bin/env python3
"""Builds the grey textures that dyed blocks are tinted from.

Each dyed block is drawn from one grey texture multiplied by its dye color (see
client/Tints.java), the same way 1.7.10 More Dyes and vanilla grass work. The
grey textures are made from the vanilla textures: each pixel keeps its
brightness and loses its color, and the texture is then brightened so that dark
materials like stone still show their dye clearly.

Some vanilla textures are split into two layers: a grey part that takes the dye
color, and a part that keeps its own color and is drawn over it untinted (a
tulip's stem, a sapling's trunk, the slime on a sticky piston).

TEXTURES below is also read by generate_resources.py, which uses it to point
the models at these textures.

Run after `./gradlew build` has downloaded Minecraft (needs Pillow):
    python3 tools/textures.py
"""
import colorsys
import glob
import io
import os
import sys
import zipfile

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
ASSETS = os.path.join(ROOT, 'src/main/resources/assets/moredyes/textures')
BASE = os.path.join(ROOT, 'tools/base')
WOODS = ['oak', 'birch', 'spruce', 'jungle', 'acacia', 'dark_oak']

# A dye color only shows as bright as the grey it multiplies, so a mid-grey texture like stone would draw every color
# at half brightness. Each grey texture is shifted up until its average is this bright, keeping its pattern.
BRIGHTNESS = 200
# Materials that are meant to stay dark whatever their dye.
STAY_DARK = {'block/obsidian', 'block/coal_block'}

GREY, GREY_IF_GREEN, GREY_IF_NOT_GREEN, KEEP_IF_GREEN, KEEP_IF_NOT_GREEN, SLIME = (
    'grey', 'grey_if_green', 'grey_if_not_green', 'keep_if_green', 'keep_if_not_green', 'slime')

# Vanilla texture -> the layers it becomes, as (texture in this mod, how it is made, whether it takes the dye color).
# The first layer replaces the vanilla texture in the models; a second layer is drawn over it, untinted.
# A vanilla texture that is not listed is used as it is (for example log bark and leaves, which are already grey).
TEXTURES = {
    'block/glass': [('block/glass', GREY, True)],
    'block/glass_pane_top': [('block/glass_pane_top', GREY, True)],
    'block/sand': [('block/sand', GREY, True)],
    'block/bricks': [('block/bricks', GREY, True)],
    'block/clay': [('block/clay', GREY, True)],
    'block/terracotta': [('block/terracotta', GREY, True)],
    'block/white_wool': [('block/wool', GREY, True)],
    'block/cobblestone': [('block/cobblestone', GREY, True)],
    'block/stone_bricks': [('block/stone_bricks', GREY, True)],
    'block/cracked_stone_bricks': [('block/cracked_stone_bricks', GREY, True)],
    'block/chiseled_stone_bricks': [('block/chiseled_stone_bricks', GREY, True)],
    'block/stone': [('block/stone', GREY, True)],
    'block/andesite': [('block/andesite', GREY, True)],
    'block/diorite': [('block/diorite', GREY, True)],
    'block/obsidian': [('block/obsidian', GREY, True)],
    'block/lapis_block': [('block/lapis_block', GREY, True)],
    'block/glowstone': [('block/glowstone', GREY, True)],
    'block/coal_block': [('block/coal_block', GREY, True)],
    'block/soul_sand': [('block/soul_sand', GREY, True)],
    'block/redstone_block': [('block/redstone_block', GREY, True)],
    'block/quartz_block_side': [('block/quartz_block_side', GREY, True)],
    'block/quartz_block_top': [('block/quartz_block_top', GREY, True)],
    'block/quartz_block_bottom': [('block/quartz_block_bottom', GREY, True)],
    'block/white_concrete': [('block/concrete', GREY, True)],
    'block/white_concrete_powder': [('block/concrete_powder', GREY, True)],
    'block/sandstone': [('block/sandstone', GREY, True)],
    'block/sandstone_top': [('block/sandstone_top', GREY, True)],
    'block/sandstone_bottom': [('block/sandstone_bottom', GREY, True)],
    'block/chiseled_sandstone': [('block/chiseled_sandstone', GREY, True)],
    'block/cut_sandstone': [('block/cut_sandstone', GREY, True)],
    'block/crafting_table_top': [('block/crafting_table_top', GREY, True)],
    'block/crafting_table_side': [('block/crafting_table_side', GREY, True)],
    'block/crafting_table_front': [('block/crafting_table_front', GREY, True)],
    'block/bookshelf': [('block/bookshelf', GREY, True)],
    'block/piston_top': [('block/piston_top', GREY, True)],
    'block/piston_side': [('block/piston_side', GREY, True)],
    'block/piston_bottom': [('block/piston_bottom', GREY, True)],
    'block/piston_inner': [('block/piston_inner', GREY, True)],
    'block/piston_top_sticky': [('block/piston_top_sticky', SLIME, True), ('block/piston_slime', SLIME, False)],
    'block/white_tulip': [('block/tulip', GREY_IF_NOT_GREEN, True), ('block/tulip_stem', KEEP_IF_GREEN, False)],
    'item/white_dye': [('item/dye', GREY, True)],
}
for _wood in WOODS:
    TEXTURES['block/%s_planks' % _wood] = [('block/%s_planks' % _wood, GREY, True)]
    TEXTURES['block/%s_log_top' % _wood] = [('block/%s_log_top' % _wood, GREY, True)]
    TEXTURES['block/%s_sapling' % _wood] = [('block/%s_sapling' % _wood, GREY_IF_GREEN, True),
                                           ('block/%s_sapling_trunk' % _wood, KEEP_IF_NOT_GREEN, False)]

# Foggy glass replaces the vanilla glass texture with its own, made from tools/base/foggy_glass.png.
FOGGY_GLASS = 'block/foggy_glass'

# Chest textures go on the chest atlas (textures/entity/chest). The latch keeps its natural color: its pixels sit in the
# top-left corner of the texture, (width, height) given here.
CHESTS = {'normal': (6, 5), 'normal_left': (4, 5), 'normal_right': (4, 5)}


def grey(p):
    r, g, b, a = p
    lum = min(255, round(0.299 * r + 0.587 * g + 0.114 * b))
    return (lum, lum, lum, a)


def is_green(p):
    r, g, b, a = p
    if a == 0:
        return False
    h, s, _ = colorsys.rgb_to_hsv(r / 255, g / 255, b / 255)
    return s > 0.15 and 65 <= h * 360 <= 170


def stats_of(pixels):
    lums = [p[0] for p in pixels if p[3] > 0]
    return (sum(lums) / len(lums), max(lums)) if lums else (BRIGHTNESS, BRIGHTNESS)


def brighten(pixels, stats=None):
    """Shifts grey pixels so their average is BRIGHTNESS. Highlights are compressed rather than clipped to white.
    stats is the (mean, max) to shift by, so a texture can match another one; by default it is the pixels' own."""
    mean, top = stats or stats_of(pixels)
    if mean >= BRIGHTNESS:
        return pixels
    squeeze = min(1.0, (250 - BRIGHTNESS) / max(1.0, top - mean))
    out = []
    for r, g, b, a in pixels:
        d = r - mean
        lum = max(0, min(255, round(BRIGHTNESS + (d * squeeze if d > 0 else d))))
        out.append((lum, lum, lum, a))
    return out


def client_jar():
    jars = glob.glob(os.path.expanduser('~/.gradle/caches/neoformruntime/artifacts/minecraft_*_client.jar'))
    if not jars:
        sys.exit('The Minecraft client jar was not found; run ./gradlew build first.')
    return zipfile.ZipFile(sorted(jars)[-1])


def first_frame(image):
    """Animated textures are a vertical strip of frames; keeps the first frame."""
    return image.crop((0, 0, image.width, min(image.width, image.height)))


def save(image, name):
    path = os.path.join(ASSETS, name + '.png')
    os.makedirs(os.path.dirname(path), exist_ok=True)
    image.save(path)


def main():
    from PIL import Image

    jar = client_jar()
    vanilla = lambda path: first_frame(Image.open(io.BytesIO(jar.read('assets/minecraft/textures/%s.png' % path)))
                                       .convert('RGBA'))
    count = 0
    for source, layers in sorted(TEXTURES.items()):
        image = vanilla(source)
        if layers[0][1] == SLIME:
            # The sticky piston face is the plain piston face with slime on it: the slime is every pixel that
            # differs. The rim is brightened by the same amount as the plain face, so the two match.
            plain = vanilla('block/piston_top')
            pairs = list(zip(plain.get_flattened_data(), image.get_flattened_data()))
            rim = [grey(s) if s == p else (0, 0, 0, 0) for p, s in pairs]
            layer_pixels = [brighten(rim, stats_of([grey(p) for p in plain.get_flattened_data()])),
                            [(0, 0, 0, 0) if s == p else s for p, s in pairs]]
        else:
            layer_pixels = []
            for name, how, tinted in layers:
                pixels = list(image.get_flattened_data())
                if how == GREY:
                    pixels = [grey(p) for p in pixels]
                elif how == GREY_IF_GREEN:
                    pixels = [grey(p) if is_green(p) else (0, 0, 0, 0) for p in pixels]
                elif how == GREY_IF_NOT_GREEN:
                    pixels = [(0, 0, 0, 0) if is_green(p) else grey(p) for p in pixels]
                elif how == KEEP_IF_GREEN:
                    pixels = [p if is_green(p) else (0, 0, 0, 0) for p in pixels]
                elif how == KEEP_IF_NOT_GREEN:
                    pixels = [(0, 0, 0, 0) if is_green(p) else p for p in pixels]
                if tinted and source not in STAY_DARK:
                    pixels = brighten(pixels)
                layer_pixels.append(pixels)
        for (name, _, _), pixels in zip(layers, layer_pixels):
            out = Image.new('RGBA', image.size)
            out.putdata(pixels)
            save(out, name)
            count += 1

    foggy = first_frame(Image.open(os.path.join(BASE, 'foggy_glass.png')).convert('RGBA'))
    foggy.putdata(brighten([grey(p) for p in foggy.get_flattened_data()]))
    save(foggy, FOGGY_GLASS)
    count += 1

    for name, (latch_w, latch_h) in sorted(CHESTS.items()):
        image = Image.open(io.BytesIO(jar.read('assets/minecraft/textures/entity/chest/%s.png' % name))).convert('RGBA')
        pixels = image.load()
        body = [(x, y) for y in range(image.height) for x in range(image.width) if not (x < latch_w and y < latch_h)]
        lifted = brighten([grey(pixels[x, y]) for x, y in body])
        for (x, y), p in zip(body, lifted):
            pixels[x, y] = p
        save(image, 'entity/chest/' + name)
        count += 1
    print('wrote %d textures to %s' % (count, os.path.relpath(ASSETS, ROOT)))


if __name__ == '__main__':
    main()
