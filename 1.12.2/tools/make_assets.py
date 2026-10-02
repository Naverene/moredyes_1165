#!/usr/bin/env python3
"""Builds the textures, models and blockstates of the dyed blocks.

Each dyed block is drawn from one grey texture multiplied by its dye color (see
client/ClientHandler.java), the same way 1.7.10 MoreDyes and vanilla grass work. So every
color of a block shares one blockstate file and one model, all written by this script.

The grey textures are made from the vanilla 1.12.2 textures: each pixel keeps its
brightness and loses its color.

Run after `./gradlew build` has downloaded Minecraft (needs Pillow):
    python3 tools/make_assets.py
"""
import colorsys
import io
import json
import os
import sys
import zipfile

from PIL import Image

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
ASSETS = os.path.join(ROOT, 'src/main/resources/assets/moredyes')
BASE = os.path.join(ROOT, 'tools/base')
CLIENT_JAR = os.path.expanduser('~/.gradle/caches/forge_gradle/minecraft_repo/versions/1.12.2/client.jar')


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


CLEAR = (0, 0, 0, 0)
GREY = grey
GREY_IF_GREEN = lambda p: grey(p) if is_green(p) else CLEAR
GREY_IF_NOT_GREEN = lambda p: CLEAR if is_green(p) else grey(p)
KEEP_IF_GREEN = lambda p: p if is_green(p) else CLEAR
KEEP_IF_NOT_GREEN = lambda p: CLEAR if is_green(p) else p

# output texture -> (vanilla texture under textures/, or base: file in tools/base, transform)
TEXTURES = {
    'blocks/tinted/wool': ('blocks/wool_colored_white', GREY),
    'blocks/tinted/stone': ('blocks/stone', GREY),
    'blocks/tinted/cobble': ('blocks/cobblestone', GREY),
    'blocks/tinted/stonebrick': ('blocks/stonebrick', GREY),
    'blocks/tinted/stonebrick_carved': ('blocks/stonebrick_carved', GREY),
    'blocks/tinted/stonebrick_cracked': ('blocks/stonebrick_cracked', GREY),
    'blocks/tinted/obsidian': ('blocks/obsidian', GREY),
    'blocks/tinted/soulsand': ('blocks/soul_sand', GREY),
    'blocks/tinted/quartz_top': ('blocks/quartz_block_top', GREY),
    'blocks/tinted/quartz_side': ('blocks/quartz_block_side', GREY),
    'blocks/tinted/quartz_bottom': ('blocks/quartz_block_bottom', GREY),
    'blocks/tinted/clay': ('blocks/hardened_clay_stained_white', GREY),
    'blocks/tinted/hardened_clay': ('blocks/hardened_clay', GREY),
    'blocks/tinted/coal': ('blocks/coal_block', GREY),
    'blocks/tinted/glowstone': ('blocks/glowstone', GREY),
    'blocks/tinted/lapis': ('blocks/lapis_block', GREY),
    'blocks/tinted/redstone': ('blocks/redstone_block', GREY),
    'blocks/tinted/plank': ('blocks/planks_oak', GREY),
    'blocks/tinted/brick': ('blocks/brick', GREY),
    'blocks/tinted/sand': ('blocks/sand', GREY),
    'blocks/tinted/sandstone_top': ('blocks/sandstone_top', GREY),
    'blocks/tinted/sandstone_side': ('blocks/sandstone_normal', GREY),
    'blocks/tinted/sandstone_bottom': ('blocks/sandstone_bottom', GREY),
    'blocks/tinted/diorite': ('blocks/stone_diorite', GREY),
    'blocks/tinted/bookshelf': ('blocks/bookshelf', GREY),
    'blocks/tinted/workbench_top': ('blocks/crafting_table_top', GREY),
    'blocks/tinted/workbench_side': ('blocks/crafting_table_side', GREY),
    'blocks/tinted/workbench_front': ('blocks/crafting_table_front', GREY),
    'blocks/tinted/log_top': ('blocks/log_oak_top', GREY),
    'blocks/tinted/glass': ('blocks/glass', GREY),
    'blocks/tinted/glass_foggy': ('base:glass_foggy', GREY),
    'blocks/tinted/glass_pane_top': ('blocks/glass_pane_top', GREY),
    'blocks/tinted/tulip_petals': ('blocks/flower_tulip_white', GREY_IF_NOT_GREEN),
    'blocks/tinted/tulip_stem': ('blocks/flower_tulip_white', KEEP_IF_GREEN),
    'blocks/tinted/sapling_leaves': ('blocks/sapling_oak', GREY_IF_GREEN),
    'blocks/tinted/sapling_trunk': ('blocks/sapling_oak', KEEP_IF_NOT_GREEN),
    'items/dye': ('items/dye_powder_white', GREY),
}

# Chest textures are used whole by the chest renderer. The latch keeps its natural color: its
# pixels sit in the top-left corner of the texture.
CHESTS = ['normal', 'normal_double']
LATCH_WIDTH, LATCH_HEIGHT = 6, 5

T = 'moredyes:blocks/tinted/'

# Blocks drawn as a cube with one tinted texture on every side: block type -> texture.
CUBE_ALL = {
    'wool': T + 'wool',
    'rockwool': T + 'wool',
    'stone': T + 'stone',
    'cobble': T + 'cobble',
    'stonebrick': T + 'stonebrick',
    'stonebrick_carved': T + 'stonebrick_carved',
    'stonebrick_cracked': T + 'stonebrick_cracked',
    'obsidian': T + 'obsidian',
    'soulsand': T + 'soulsand',
    'clay': T + 'clay',
    'hardened_clay': T + 'hardened_clay',
    'coal': T + 'coal',
    'glowstone': T + 'glowstone',
    'lapis': T + 'lapis',
    'redstone': T + 'redstone',
    'plank': T + 'plank',
    'brick': T + 'brick',
    'sand': T + 'sand',
    'diorite': T + 'diorite',
    'glass': T + 'glass',
    'glass_foggy': T + 'glass_foggy',
    # The vanilla oak leaves texture is already grey; the game tints it with the biome color.
    'leaf': 'minecraft:blocks/leaves_oak',
}

# Blocks drawn as a cube with different tinted textures per side: block type -> textures.
CUBE = {
    'quartz': dict(particle=T + 'quartz_side', down=T + 'quartz_bottom', up=T + 'quartz_top',
                   north=T + 'quartz_side', south=T + 'quartz_side', west=T + 'quartz_side', east=T + 'quartz_side'),
    'sandstone': dict(particle=T + 'sandstone_side', down=T + 'sandstone_bottom', up=T + 'sandstone_top',
                      north=T + 'sandstone_side', south=T + 'sandstone_side', west=T + 'sandstone_side',
                      east=T + 'sandstone_side'),
    'bookshelf': dict(particle=T + 'bookshelf', down=T + 'plank', up=T + 'plank',
                      north=T + 'bookshelf', south=T + 'bookshelf', west=T + 'bookshelf', east=T + 'bookshelf'),
    'workbench': dict(particle=T + 'workbench_front', down=T + 'plank', up=T + 'workbench_top',
                      north=T + 'workbench_front', south=T + 'workbench_side', west=T + 'workbench_front',
                      east=T + 'workbench_side'),
}

# Plants drawn as two crossed squares in two layers: block type -> (tinted texture, natural texture).
CROSS = {
    'sapling': (T + 'sapling_leaves', T + 'sapling_trunk'),
    'tulip': (T + 'tulip_petals', T + 'tulip_stem'),
}

# Glass panes: block type -> texture of the pane's face.
PANES = {
    'glass_pane': T + 'glass',
    'glass_foggy_pane': T + 'glass_foggy',
}

SIDES = ['down', 'up', 'north', 'south', 'west', 'east']


def write_json(path, data):
    path = os.path.join(ASSETS, path)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w') as f:
        json.dump(data, f, indent=2)
        f.write('\n')


def block_and_item(name, model, variants=None):
    """One model shared by the placed block (in every color) and its item."""
    write_json('models/block/%s.json' % name, model)
    write_json('blockstates/%s.json' % name, {'variants': variants or {'normal': {'model': 'moredyes:' + name}}})
    write_json('models/item/%s.json' % name, {'parent': 'moredyes:block/' + name})


def flat_item(name, *layers):
    textures = {'layer%d' % i: texture for i, texture in enumerate(layers)}
    write_json('models/item/%s.json' % name, {'parent': 'item/generated', 'textures': textures})


def cross_planes(texture, tinted):
    face = {'uv': [0, 0, 16, 16], 'texture': texture}
    if tinted:
        face['tintindex'] = 0
    rotation = {'origin': [8, 8, 8], 'axis': 'y', 'angle': 45, 'rescale': True}
    return [
        {'from': [0.8, 0, 8], 'to': [15.2, 16, 8], 'rotation': rotation, 'shade': False,
         'faces': {'north': face, 'south': face}},
        {'from': [8, 0, 0.8], 'to': [8, 16, 15.2], 'rotation': rotation, 'shade': False,
         'faces': {'west': face, 'east': face}},
    ]


def make_textures(jar):
    for name, (src, transform) in sorted(TEXTURES.items()):
        if src.startswith('base:'):
            image = Image.open(os.path.join(BASE, src[5:] + '.png'))
        else:
            image = Image.open(io.BytesIO(jar.read('assets/minecraft/textures/%s.png' % src)))
        image = image.convert('RGBA')
        pixels = image.load()
        for y in range(image.height):
            for x in range(image.width):
                pixels[x, y] = transform(pixels[x, y])
        path = os.path.join(ASSETS, 'textures', name + '.png')
        os.makedirs(os.path.dirname(path), exist_ok=True)
        image.save(path)
    for name in CHESTS:
        image = Image.open(io.BytesIO(jar.read('assets/minecraft/textures/entity/chest/%s.png' % name)))
        image = image.convert('RGBA')
        pixels = image.load()
        for y in range(image.height):
            for x in range(image.width):
                if not (x < LATCH_WIDTH and y < LATCH_HEIGHT):
                    pixels[x, y] = grey(pixels[x, y])
        path = os.path.join(ASSETS, 'textures/entity/chest', name + '.png')
        os.makedirs(os.path.dirname(path), exist_ok=True)
        image.save(path)
    print('wrote %d textures' % (len(TEXTURES) + len(CHESTS)))


def make_models(jar):
    # Parents: the vanilla cube with every face tinted.
    write_json('models/block/tinted_cube.json', {
        'parent': 'block/block',
        'elements': [{
            'from': [0, 0, 0], 'to': [16, 16, 16],
            'faces': {side: {'texture': '#' + side, 'cullface': side, 'tintindex': 0} for side in SIDES},
        }],
    })
    write_json('models/block/tinted_cube_all.json', {
        'parent': 'moredyes:block/tinted_cube',
        'textures': dict({side: '#all' for side in SIDES}, particle='#all'),
    })

    for name, texture in sorted(CUBE_ALL.items()):
        block_and_item(name, {'parent': 'moredyes:block/tinted_cube_all', 'textures': {'all': texture}})
    for name, textures in sorted(CUBE.items()):
        block_and_item(name, {'parent': 'moredyes:block/tinted_cube', 'textures': textures})

    # Logs: the cut ends take the dye color, the bark stays oak.
    bark = 'minecraft:blocks/log_oak'
    ends = {side: {'texture': '#end', 'cullface': side, 'tintindex': 0} for side in ['down', 'up']}
    sides = {side: {'texture': '#side', 'cullface': side} for side in ['north', 'south', 'west', 'east']}
    write_json('models/block/log_bark.json', {'parent': 'block/cube_all', 'textures': {'all': bark}})
    block_and_item('log', {
        'parent': 'block/block',
        'textures': {'particle': bark, 'end': T + 'log_top', 'side': bark},
        'elements': [{'from': [0, 0, 0], 'to': [16, 16, 16], 'faces': dict(ends, **sides)}],
    }, {
        'axis=y': {'model': 'moredyes:log'},
        'axis=z': {'model': 'moredyes:log', 'x': 90},
        'axis=x': {'model': 'moredyes:log', 'x': 90, 'y': 90},
        'axis=none': {'model': 'moredyes:log_bark'},
    })

    for name, (tinted, natural) in sorted(CROSS.items()):
        write_json('models/block/%s.json' % name, {
            'ambientocclusion': False,
            'textures': {'particle': tinted, 'tinted': tinted, 'natural': natural},
            'elements': cross_planes('#natural', False) + cross_planes('#tinted', True),
        })
        write_json('blockstates/%s.json' % name, {'variants': {'normal': {'model': 'moredyes:' + name}}})
        flat_item(name, tinted, natural)

    # Glass panes: the vanilla pane models with every face tinted.
    parts = ['post', 'side', 'side_alt', 'noside', 'noside_alt']
    for part in parts:
        model = json.loads(jar.read('assets/minecraft/models/block/pane_%s.json' % part).decode('utf-8'))
        for element in model['elements']:
            for face in element['faces'].values():
                face['tintindex'] = 0
        write_json('models/block/tinted_pane_%s.json' % part, model)
    vanilla_state = json.loads(jar.read('assets/minecraft/blockstates/glass_pane.json').decode('utf-8'))
    for name, pane in sorted(PANES.items()):
        for part in parts:
            write_json('models/block/%s_%s.json' % (name, part), {
                'parent': 'moredyes:block/tinted_pane_' + part,
                'textures': {'pane': pane, 'edge': T + 'glass_pane_top'},
            })
        state = json.loads(json.dumps(vanilla_state))
        for case in state['multipart']:
            case['apply']['model'] = 'moredyes:' + case['apply']['model'].replace('glass_pane', name)
        write_json('blockstates/%s.json' % name, state)
        flat_item(name, pane)

    # Chests are drawn by the chest renderer; the model only gives the breaking particles.
    write_json('models/block/chest.json', {'textures': {'particle': T + 'plank'}})
    write_json('blockstates/chest.json', {'variants': {'normal': {'model': 'moredyes:chest'}}})
    write_json('models/item/chest.json', {'parent': 'minecraft:item/chest'})

    flat_item('dye', 'moredyes:items/dye')
    print('wrote the models and blockstates')


def main():
    if not os.path.exists(CLIENT_JAR):
        sys.exit('Minecraft 1.12.2 client.jar not found; run ./gradlew build first.')
    jar = zipfile.ZipFile(CLIENT_JAR)
    make_textures(jar)
    make_models(jar)


if __name__ == '__main__':
    main()
