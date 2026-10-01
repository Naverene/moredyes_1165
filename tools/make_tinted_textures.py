#!/usr/bin/env python3
"""Builds the grey textures that dyed blocks are tinted from.

Each dyed block is drawn from one grey texture multiplied by its dye color (see
client/ColorHandlers.java), the same way 1.7.10 MoreDyes and vanilla grass work.
The grey textures are made from the vanilla 1.16.5 textures: the pixel keeps its
brightness and loses its color.

Run after `./gradlew build` has downloaded Minecraft (needs Pillow):
    python3 tools/make_tinted_textures.py
"""
import colorsys
import glob
import io
import os
import sys
import zipfile

from PIL import Image

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
OUT = os.path.join(ROOT, 'src/main/resources/assets/moredyes/textures/block/tinted')
BASE = os.path.join(ROOT, 'tools/base')
WOODS = ['oak', 'birch', 'spruce', 'jungle', 'acacia', 'dark_oak']


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


GREY = grey
GREY_IF_GREEN = lambda p: grey(p) if is_green(p) else (0, 0, 0, 0)
GREY_IF_NOT_GREEN = lambda p: (0, 0, 0, 0) if is_green(p) else grey(p)
KEEP_IF_GREEN = lambda p: p if is_green(p) else (0, 0, 0, 0)
KEEP_IF_NOT_GREEN = lambda p: (0, 0, 0, 0) if is_green(p) else p

# output name -> (vanilla texture under textures/, or base: file in tools/base, transform)
SOURCES = {
    'glass': ('block/glass', GREY),
    'glass_foggy': ('base:glass_foggy', GREY),
    'sand': ('block/sand', GREY),
    'brick': ('block/bricks', GREY),
    'clay': ('block/clay', GREY),
    'hardened_clay': ('block/terracotta', GREY),
    'wool': ('block/white_wool', GREY),
    'cobble': ('block/cobblestone', GREY),
    'stonebrick': ('block/stone_bricks', GREY),
    'stonebrick_cracked': ('block/cracked_stone_bricks', GREY),
    'stonebrick_carved': ('block/chiseled_stone_bricks', GREY),
    'stone': ('block/stone', GREY),
    'obsidian': ('block/obsidian', GREY),
    'lapis': ('block/lapis_block', GREY),
    'glowstone': ('block/glowstone', GREY),
    'coal': ('block/coal_block', GREY),
    'soulsand': ('block/soul_sand', GREY),
    'redstone': ('block/redstone_block', GREY),
    'quartz': ('block/quartz_block_side', GREY),
    'andesite': ('block/andesite', GREY),
    'diorite': ('block/diorite', GREY),
    'concrete': ('block/white_concrete', GREY),
    'concrete_powder': ('block/white_concrete_powder', GREY),
    'sandstone_top': ('block/sandstone_top', GREY),
    'sandstone_side': ('block/sandstone', GREY),
    'sandstone_bottom': ('block/sandstone_bottom', GREY),
    'sandstone_carved': ('block/chiseled_sandstone', GREY),
    'sandstone_smooth': ('block/cut_sandstone', GREY),
    'workbench_top': ('block/crafting_table_top', GREY),
    'workbench_side': ('block/crafting_table_side', GREY),
    'workbench_front': ('block/crafting_table_front', GREY),
    'tulip_petals': ('block/white_tulip', GREY_IF_NOT_GREEN),
    'tulip_stem': ('block/white_tulip', KEEP_IF_GREEN),
    'dye': ('item/white_dye', GREY),
    'glass_pane_top': ('block/glass_pane_top', GREY),
    'bookshelf': ('block/bookshelf', GREY),
    'piston_top': ('block/piston_top', GREY),
    'piston_side': ('block/piston_side', GREY),
    'piston_bottom': ('block/piston_bottom', GREY),
    'piston_inner': ('block/piston_inner', GREY),
    'granite': ('block/granite', GREY),
    'polished_andesite': ('block/polished_andesite', GREY),
    'polished_diorite': ('block/polished_diorite', GREY),
    'polished_granite': ('block/polished_granite', GREY),
    'endstone': ('block/end_stone', GREY),
    'mossy_cobble': ('block/mossy_cobblestone', GREY),
    'mossy_stonebrick': ('block/mossy_stone_bricks', GREY),
    'quartz_bricks': ('block/quartz_bricks', GREY),
    'quartz_chiseled': ('block/chiseled_quartz_block', GREY),
    'quartz_chiseled_top': ('block/chiseled_quartz_block_top', GREY),
    'quartz_pillar': ('block/quartz_pillar', GREY),
    'quartz_pillar_top': ('block/quartz_pillar_top', GREY),
    'quartz_smooth': ('block/quartz_block_bottom', GREY),
    'bone_block_side': ('block/bone_block_side', GREY),
    'bone_block_top': ('block/bone_block_top', GREY),
    'gravel': ('block/gravel', GREY),
    'ice': ('block/ice', GREY),
    'packed_ice': ('block/packed_ice', GREY),
    'snow': ('block/snow', GREY),
}
# Dyed flowers: the petals take the dye color and the green stem and leaves keep theirs, like the tulip.
FLOWERS = {'allium': 'allium', 'azurebluet': 'azure_bluet', 'cornflower': 'cornflower', 'dandelion': 'dandelion',
           'lilyofthevalley': 'lily_of_the_valley', 'orchid': 'blue_orchid', 'oxeyedaisy': 'oxeye_daisy', 'poppy': 'poppy'}
TALL_FLOWERS = {'lilac': 'lilac', 'peony': 'peony', 'rosebush': 'rose_bush'}
for name, vanilla in FLOWERS.items():
    SOURCES[name + '_petals'] = ('block/' + vanilla, GREY_IF_NOT_GREEN)
    SOURCES[name + '_stem'] = ('block/' + vanilla, KEEP_IF_GREEN)
for name, vanilla in TALL_FLOWERS.items():
    for half in ('bottom', 'top'):
        SOURCES['%s_%s_petals' % (name, half)] = ('block/%s_%s' % (vanilla, half), GREY_IF_NOT_GREEN)
        SOURCES['%s_%s_stem' % (name, half)] = ('block/%s_%s' % (vanilla, half), KEEP_IF_GREEN)
for w in WOODS:
    SOURCES[w + '_planks'] = ('block/%s_planks' % w, GREY)
    SOURCES[w + '_log_top'] = ('block/%s_log_top' % w, GREY)
    SOURCES[w + '_sapling_leaves'] = ('block/%s_sapling' % w, GREY_IF_GREEN)
    SOURCES[w + '_sapling_trunk'] = ('block/%s_sapling' % w, KEEP_IF_NOT_GREEN)


# Chest textures go on the chest atlas (textures/entity/chest), not the block atlas. The latch keeps its natural
# color: its pixels sit in the top-left corner of the texture, (width, height) given here.
CHEST_OUT = os.path.join(ROOT, 'src/main/resources/assets/moredyes/textures/entity/chest')
CHESTS = {'normal': (6, 5), 'normal_left': (4, 5), 'normal_right': (4, 5)}


def client_jar():
    jars = glob.glob(os.path.expanduser('~/.gradle/caches/forge_gradle/minecraft_repo/versions/1.16.5/client.jar'))
    if not jars:
        sys.exit('Minecraft 1.16.5 client.jar not found; run ./gradlew build first.')
    return zipfile.ZipFile(jars[0])


def main():
    jar = client_jar()
    os.makedirs(OUT, exist_ok=True)
    for name, (src, transform) in sorted(SOURCES.items()):
        if src.startswith('base:'):
            image = Image.open(os.path.join(BASE, src[5:] + '.png'))
        else:
            image = Image.open(io.BytesIO(jar.read('assets/minecraft/textures/%s.png' % src)))
        image = image.convert('RGBA')
        # Animated textures are a vertical strip of frames; keep the first frame.
        size = image.width
        image = image.crop((0, 0, size, min(size, image.height)))
        image.putdata([transform(p) for p in image.getdata()])
        image.save(os.path.join(OUT, name + '.png'))
    print('wrote %d textures to %s' % (len(SOURCES), os.path.relpath(OUT, ROOT)))

    # The sticky piston face is drawn twice: the tinted wooden rim, then the slime in its own green. The slime is
    # every pixel that differs from the plain piston face.
    plain = Image.open(io.BytesIO(jar.read('assets/minecraft/textures/block/piston_top.png'))).convert('RGBA')
    sticky = Image.open(io.BytesIO(jar.read('assets/minecraft/textures/block/piston_top_sticky.png'))).convert('RGBA')
    pairs = list(zip(plain.getdata(), sticky.getdata()))
    rim = Image.new('RGBA', sticky.size)
    rim.putdata([grey(s) if s == p else (0, 0, 0, 0) for p, s in pairs])
    rim.save(os.path.join(OUT, 'piston_top_sticky.png'))
    slime = Image.new('RGBA', sticky.size)
    slime.putdata([(0, 0, 0, 0) if s == p else s for p, s in pairs])
    slime.save(os.path.join(OUT, 'piston_slime.png'))

    os.makedirs(CHEST_OUT, exist_ok=True)
    for name, (latch_w, latch_h) in sorted(CHESTS.items()):
        image = Image.open(io.BytesIO(jar.read('assets/minecraft/textures/entity/chest/%s.png' % name))).convert('RGBA')
        pixels = image.load()
        for y in range(image.height):
            for x in range(image.width):
                if not (x < latch_w and y < latch_h):
                    pixels[x, y] = grey(pixels[x, y])
        image.save(os.path.join(CHEST_OUT, name + '.png'))
    print('wrote %d chest textures to %s' % (len(CHESTS), os.path.relpath(CHEST_OUT, ROOT)))


if __name__ == '__main__':
    main()
