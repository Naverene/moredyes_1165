#!/usr/bin/env python3
"""Generate the More Dyes Hytale asset pack.

Every dyed block shares one light grey texture and gets its color from the
BlockType "Tint" field, the same idea as the tinted blocks in the 1.16.5 mod.

    python3 tools/generate.py           # the three prototype colors
    python3 tools/generate.py --all     # all 118 colors

Needs Pillow (pip install pillow). Output goes to pack/MoreDyes/.
"""
import json
import random
import shutil
import sys
from pathlib import Path

from PIL import Image

ROOT = Path(__file__).resolve().parent.parent
PACK = ROOT / "pack" / "MoreDyes"
COLORS = json.loads((ROOT / "tools" / "colors.json").read_text())
PROTOTYPE_COLORS = ["e57f6c", "4d73c5", "b2d926"]
SIZE = 32

MANIFEST = {
    "Group": "Naverene",
    "Name": "MoreDyes",
    "Version": "0.1.0",
    "Description": "118 extra dye colors. Prototype: tinted stone.",
    "Authors": [{"Name": "Naverene"}],
    "Website": "https://github.com/Naverene/moredyes_hytale",
    "ServerVersion": "*",
    "Dependencies": {},
    "OptionalDependencies": {},
    "DisabledByDefault": False,
}


def block_id(color):
    return "MoreDyes_Stone_" + color.upper()


def base_texture():
    """A light grey stone tile; light so the multiplied tint stays bright."""
    rng = random.Random(1165)
    img = Image.new("L", (SIZE, SIZE))
    for y in range(SIZE):
        for x in range(SIZE):
            v = rng.randint(212, 244)
            if x in (0, SIZE - 1) or y in (0, SIZE - 1):
                v -= 28
            img.putpixel((x, y), v)
    return img.convert("RGBA")


def tinted(img, color):
    r, g, b = (int(color[i:i + 2], 16) for i in (0, 2, 4))
    out = img.copy()
    px = out.load()
    for y in range(out.height):
        for x in range(out.width):
            v, _, _, a = px[x, y]
            px[x, y] = (v * r // 255, v * g // 255, v * b // 255, a)
    return out


def item(color):
    bid = block_id(color)
    return {
        "TranslationProperties": {"Name": "server." + bid + ".name"},
        "MaxStack": 100,
        "Icon": "Icons/ItemsGenerated/" + bid + ".png",
        "Categories": ["Blocks.Rocks"],
        "PlayerAnimationsId": "Block",
        "Set": "Rock_Stone",
        "BlockType": {
            "Material": "Solid",
            "DrawType": "Cube",
            "Group": "Stone",
            "Flags": {},
            "Gathering": {"Breaking": {"GatherType": "Rocks", "ItemId": bid}},
            "BlockParticleSetId": "Stone",
            "Textures": [{"All": "BlockTextures/MoreDyes_Stone.png"}],
            "Tint": ["#" + color],
            "ParticleColor": "#" + color,
            "BlockSoundSetId": "Stone",
            "BlockBreakingDecalId": "Breaking_Decals_Rock",
        },
        "ResourceTypes": [{"Id": "Rock"}],
    }


def main():
    colors = COLORS if "--all" in sys.argv else PROTOTYPE_COLORS
    if PACK.exists():
        shutil.rmtree(PACK)
    textures = PACK / "Common" / "BlockTextures"
    icons = PACK / "Common" / "Icons" / "ItemsGenerated"
    items = PACK / "Server" / "Item" / "Items"
    lang = PACK / "Server" / "Languages" / "en-US"
    for d in (textures, icons, items, lang):
        d.mkdir(parents=True)

    (PACK / "manifest.json").write_text(json.dumps(MANIFEST, indent=2) + "\n")
    base = base_texture()
    base.save(textures / "MoreDyes_Stone.png")

    lines = []
    for color in colors:
        bid = block_id(color)
        (items / (bid + ".json")).write_text(json.dumps(item(color), indent=2) + "\n")
        tinted(base, color).save(icons / (bid + ".png"))
        lines.append(bid + ".name = " + color.upper() + " Stone")
    (lang / "server.lang").write_text("\n".join(lines) + "\n")
    print("Wrote %d blocks to %s" % (len(colors), PACK.relative_to(ROOT)))


if __name__ == "__main__":
    main()
