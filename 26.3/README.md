# More Dyes for Minecraft 26.3

This mod adds 118 new dyes to Minecraft, one for every mix of two vanilla dye colors. Each color also comes with
blocks dyed in it: wool, stone and stone bricks (in all varieties), cobblestone, andesite, diorite, bricks, clay,
terracotta, concrete and concrete powder, sand and sandstone, soul sand, obsidian, glowstone, blocks of coal, lapis,
redstone and quartz, clear and foggy glass and panes, bookshelves, crafting tables, chests, pistons and sticky
pistons, and logs, planks, leaves, saplings and fences in all six woods. The colors are named by their hex code.

This is the NeoForge port for Minecraft 26.3, made from the 1.16.5 version.

## Playing

* Dyes are crafted from two vanilla dyes. Six colors take two of each, because the plain pair already makes a vanilla
  dye. A dyed tulip also makes one dye of its color.
* Eight vanilla blocks around a dye make eight dyed blocks. A sapling, a crafting table or a chest takes one dye.
* A dyed block crafted with a water bucket, or used on a water cauldron, turns back into the vanilla block.
* Dyed blocks turn into each other like the vanilla ones: cobblestone smelts to stone, logs make planks, dyed planks
  make crafting tables, chests, bookshelves and pistons of their color, and so on.
* A dye used on a sheep dyes its wool, and some sheep spawn already dyed (the chance is `sheep_spawn_chance` in the
  server config, `serverconfig/moredyes-server.toml`). Lambs take a color from a parent.
* Dye trees grow in the overworld about every four chunks, and patches of dyed tulips about every other chunk.
* Dyed blocks behave like their vanilla block: dyed obsidian makes nether portals and holds end crystals, dyed redstone
  blocks power repeaters and comparators, dyed bookshelves power enchanting tables, and dyed soul sand makes bubble
  columns.

## Building

The mod needs Java 25. `./gradlew build` builds the jar into `build/libs`, `./gradlew runClient` and
`./gradlew runServer` start the game with the mod.

Every block is drawn from one grey texture that the game tints with the block's color, so the textures don't need a
file per color. The resources that come once per color (blockstates, item models, drops, recipes, tags, names and
trees) are generated from the vanilla ones and kept in `src/generated/resources`. After changing the kinds of block
(`block/Kind.java`) or the colors (`color/ColorGroup.java`), run `./gradlew build` once so Minecraft is downloaded,
then:

```
python3 tools/textures.py            # the grey textures, made from the vanilla ones (needs Pillow)
python3 tools/generate_resources.py  # everything in src/generated/resources
```

## Releases

Every push to `main` updates the "Development build" pre-release on GitHub. Pushing a version tag such as `v3.0.0`
publishes a release with the jar and uploads it to [CurseForge](https://www.curseforge.com/minecraft/mc-mods/moredyes)
when the repository has a `CURSEFORGE_TOKEN` secret.
