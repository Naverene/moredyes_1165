# More Dyes for Hytale

A Hytale port of More Dyes, starting as an asset pack (no Java plugin yet).

Status: **prototype.** The pack adds three dyed stone blocks (E57F6C, 4D73C5 and B2D926). It checks that one grey texture plus the BlockType `Tint` field gives correctly colored blocks, which is how all 118 colors would be made.

## Trying it

1. Copy the `pack/MoreDyes` folder into Hytale's `UserData/Mods` folder.
   - Windows: `%APPDATA%\Hytale\UserData\Mods\MoreDyes`
   - Some older guides say `UserData/Packs`. Use that if `Mods` doesn't exist.
2. Start Hytale, open **Worlds**, right-click a creative world and turn on **MoreDyes**.
3. Join the world and search the creative inventory for "Stone", or use the block IDs `MoreDyes_Stone_E57F6C`, `MoreDyes_Stone_4D73C5` and `MoreDyes_Stone_B2D926`.

**What to check:**
- The placed blocks show the same three colors as their inventory icons (orange-red, blue, lime).
- Breaking a block drops that same block.

If the placed blocks show up grey while the icons are colored, `Tint` isn't being applied. If the pack fails to load, the log names the field it rejected.

## Regenerating

```
pip install pillow
python3 tools/generate.py          # the three prototype colors
python3 tools/generate.py --all    # all 118 colors
```

`tools/colors.json` holds the 118 colors, taken from `ColorStrings.ALL` in [moredyes_1165](https://github.com/Naverene/moredyes_1165).
