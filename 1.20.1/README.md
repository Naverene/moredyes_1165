# More Dyes for Minecraft Forge 1.20.1

This mod adds more than 100 new dyes to Minecraft. For now they are all combinations of the 16 vanilla dye colors. Each color also has a variety of blocks that can be colored with the dye. These include stone, stonebrick(in all varieties), wool, glowstone, even redstone and lapis blocks.

Blocks can be cleaned and returned to their vanilla color by crafting them with a bucket of water, or by dipping them in a cauldron of water.

If Rechiseled is installed, the dyed blocks can be chiseled into each other and into their vanilla block.

I'm open to suggestions for naming the colors. Right now they are all labeled with their hex code.

Dyed saplings can now be crafted from an oak sapling and the appropriate colored dye. Thats right, dye trees are now in.

Tulips in all the colors are available from the creative menu. Worldgen now exists for both the dye trees and the tulips.

Sheep can be dyed any MoreDyes color, and a few spawn in the world already dyed.

## Building

Needs Java 17.

- `./gradlew build` builds the mod jar into `build/libs`.
- `./gradlew runClient` and `./gradlew runServer` start the game with the mod.
- `./gradlew runData` writes the generated blockstates, models, recipes, loot tables, tags and language file into
  `src/generated/resources`. It needs about 8 GB of memory: `_JAVA_OPTIONS=-Xmx8g ./gradlew runData`.
- `python3 tools/make_tinted_textures.py` rebuilds the grey block textures from the vanilla ones (needs Pillow and a
  finished `./gradlew build`). Run it after adding a dyed block type.

The wall blocks take about 1 GB of memory. They can be turned off with `wall_blocks` in `config/moredyes-client.toml`;
a server and its players must use the same setting.
