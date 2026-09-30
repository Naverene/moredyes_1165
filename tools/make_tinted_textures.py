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
    'clay': ('block/white_terracotta', GREY),
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
}
for w in WOODS:
    SOURCES[w + '_planks'] = ('block/%s_planks' % w, GREY)
    SOURCES[w + '_log_top'] = ('block/%s_log_top' % w, GREY)
    SOURCES[w + '_sapling_leaves'] = ('block/%s_sapling' % w, GREY_IF_GREEN)
    SOURCES[w + '_sapling_trunk'] = ('block/%s_sapling' % w, KEEP_IF_NOT_GREEN)


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


if __name__ == '__main__':
    main()
