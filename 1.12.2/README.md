# More Dyes (Minecraft 1.12.2)

More Dyes adds 118 dyes to Minecraft, each a mix of two of the 16 vanilla dyes, and a set of blocks in every one of those colors. This is the Forge 1.12.2 version.

## What it adds

- **Dyes.** Craft two different vanilla dyes together to get two of the mixed dye. Six pairs already make a vanilla dye (for example red and yellow make orange); for those, use two of each to get four of the mixed dye.
- **Dyed blocks.** Put eight blocks around a dye to get eight dyed blocks. This works for wool, stone, cobblestone, the three kinds of stone bricks, diorite, bricks, terracotta and stained terracotta, sand, sandstone, glass, glass panes, quartz, obsidian, soul sand, glowstone, bookshelves, planks, and the blocks of coal, lapis and redstone.
- **Dyed chests and crafting tables.** Craft a chest or crafting table with a dye, or build one from dyed planks of a single color. Two dyed chests of the same color join into a double chest.
- **Dye trees and tulips.** Both generate in the overworld in every color. Craft a sapling with a dye to get a dyed sapling. The leaves drop their sapling and their dye, and a tulip crafts into one dye.
- **Dyed sheep.** Right-click a sheep with one of the dyes to color it. Shearing or killing it gives wool of that color, the wool grows back in it, and lambs take after a parent. A few sheep (5% by default) get a random color when they first appear in the world.
- **Washing.** Craft a dyed block with a water bucket to get the vanilla block back, or right-click a cauldron holding water with a stack of dyed blocks to wash the whole stack for one level of water.

Dyed blocks behave like the vanilla blocks they are made from: dyed stone drops dyed cobblestone, dyed cobblestone smelts into dyed stone, dyed sand falls and smelts into dyed glass, dyed bookshelves power an enchanting table, and so on. They are also in the ore dictionary under the same names as the vanilla blocks, so recipes from other mods accept them.

If Thermal Foundation is installed, rockwool can be dyed too.

The config file has switches for the tree and tulip generation and for whether mobs can spawn on the dyed blocks, and sets how often sheep get a random color.

## Building

The mod builds with Java 8:

    ./gradlew build

The jar is written to `build/libs`.

To run the game from the project, import it into your IDE first (or run `./gradlew eclipse` once). That makes ForgeGradle decompile Minecraft, which `runClient` and `runServer` need on 1.12.2; without it they crash while Forge loads.

Every color of a block shares one grey texture, which the game tints with the dye color. The textures, models and blockstate files are written by `tools/make_assets.py`; run it again after changing it.

## Builds and releases

GitHub Actions builds every push and pull request, and starts a server with the mod to check that it loads. Every push to `main` updates the "Development build" pre-release with the newest jar. To publish a release, push a version tag such as `v1.0.0`; the build attaches the jar to a release with that name.
