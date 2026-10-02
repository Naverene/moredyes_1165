This mod adds more than 100 new dyes to Minecraft. For now they are all combinations of the 16 vanilla dye colors. Each color also has a variety of blocks that can be colored with the dye. These include stone, stonebrick(in all varieties), wool, glowstone, even redstone and lapis blocks.

Blocks can be cleaned and returned to their vanilla color by crafting them with a bucket of water.

If Thermal Expansion is detected, rockwool will be available in the mod colors.

If Chisel is detected, all colors of all blocks will be chiselable.

I'm open to suggestions for naming the colors. Right now they are all labeled with their hex code.

Dyed saplings can now be crafted from an oak sapling and the appropriate colored dye. Thats right, dye trees are now in.

Tulips in all the colors are available from the creative menu. Worldgen now exists for both the dye trees and the tulips.


Dyed crafting tables and chests are available in every color. Craft them from four (crafting table) or eight (chest) dyed planks of one color, or combine a vanilla crafting table or chest with a dye. Two chests of the same color placed side by side join into a double chest. Craft either with a bucket of water to get the vanilla block back.

## Blocks from newer Minecraft versions

Many blocks from Minecraft 1.8 to 1.16 are available in every color: granite, andesite and their polished versions (and polished diorite), concrete and concrete powder, soul soil, basalt and polished basalt, smooth stone, smooth sandstone, smooth quartz, cut sandstone, end stone bricks, cracked and chiseled nether bricks, bone blocks, glazed terracotta, blocks of netherite, crying obsidian, chains and cornflowers. Mossy cobblestone, mossy stone bricks and nether bricks come in every color too, because their stairs and walls are made from them.

Plain granite and andesite generate underground and are crafted as in 1.8, unless another mod already provides them (the same as diorite). Concrete powder turns into concrete when it touches water.

Stairs, slabs and walls come in every color for stone, granite, diorite, andesite, the polished stones, mossy cobblestone, mossy stone bricks, end stone bricks, smooth sandstone, smooth quartz, nether bricks, bricks, stone bricks, sandstone and cut sandstone (not every material has all three shapes, the same as in vanilla). They are crafted from dyed blocks of one shade with the vanilla recipes and are in their own creative tab. Iron trapdoors come in every color as well and, like iron doors, only open with redstone. Pistons and sticky pistons come in every color too and work like the vanilla ones; craft a piston or sticky piston with a dye, or put a slimeball on top of a dyed piston to make it sticky. The rod of an extended piston keeps its wood color, and other pistons cannot push a dyed piston (in 1.7.10 pistons cannot move blocks that store extra data, the same as chests).

Most of these use their vanilla recipe with dyed blocks of one shade, or eight blocks around a dye. Blocks that have no recipe in 1.7:

- Soul soil: dyed soul sand and dirt make two.
- Basalt: smelt dyed soul soil.
- Crying obsidian: eight dyed obsidian around a ghast tear.
- Block of netherite: obsidian in the corners, gold ingots on the sides and a dye in the middle make four.
- Chain: an iron ingot above and below a dye make four.
- Bone block: eight bone meal around a dye.
- Iron trapdoor: four iron ingots and a dye.

Only the newer blocks that have a plain or vanilla version (granite, andesite, cut sandstone, mossy cobblestone, mossy stone bricks and nether bricks) can be washed back. Stairs, slabs and walls cannot be washed.

Workstations from newer versions (loom, smoker, barrel and so on) are left to other mods.
Sheep can be dyed any of the mod's colors: right-click a sheep with a dye, and shearing or killing it gives wool in that color. A vanilla dye puts it back on a vanilla color. Some new sheep also spawn in a random mod color; the `sheepSpawnChance` config option sets how often (5% by default, 0 to turn it off).

## How the colors are drawn

Blocks are not stored as one texture file per color. Each block type has one grey texture, and the game multiplies it by the dye color when it draws the block, the same way it colors grass and leaves.

The grey textures are made from the vanilla textures when the game loads, so they follow whatever resource pack the player uses. The list of which vanilla texture each block uses is in `client/TintSources.java`. Blocks that do not exist in vanilla 1.7.10 use a grey file in `textures/blocks/base/` instead (diorite, granite, concrete and the other newer blocks, and foggy glass).

To give a new block a texture, add a line to `TintSources` and call `TintedTextures.register(iconRegister, "<key>")` from the block's `registerBlockIcons`. Parts that should keep their natural color, such as a flower's stem or a log's bark, are drawn as a separate untinted layer (see `ILayeredBlock`).

Stairs, slabs, walls, trapdoors and pistons need their metadata for their direction or shape, so they keep their color in a small tile entity instead. Each is a single block for all colors, with the color's number (see `ColorIndex`) as the item damage.

## Downloads and releases

GitHub Actions builds the mod on every push, then starts a server with it and a fresh world; the build fails if the mod crashes or throws an error while loading. Pushing or merging to `main` updates the **Development build** pre-release on the Releases page with the newest jar. These builds are for testing.

To publish a proper release, tag the commit with a version number starting with `v` and push the tag:

```
git tag v1.2.0
git push origin v1.2.0
```

The workflow builds that commit and creates a release named after the tag, with the jar attached and release notes generated from the merged pull requests and commits.
