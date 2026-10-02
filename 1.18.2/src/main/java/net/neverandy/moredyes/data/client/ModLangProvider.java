package net.neverandy.moredyes.data.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.reference.ColorStrings;

public class ModLangProvider extends LanguageProvider
{
    /** English names in the order of MDBlock.SMALL_FLOWERS and MDBlock.TALL_FLOWERS. */
    private static final String[] FLOWER_NAMES = {"Allium", "Azure Bluet", "Cornflower", "Dandelion", "Lily of the Valley",
            "Orchid", "Oxeye Daisy", "Poppy"};
    private static final String[] TALL_FLOWER_NAMES = {"Lilac", "Peony", "Rose Bush"};

    public ModLangProvider(DataGenerator gen, String modid, String locale)
    {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations()
    {
        add("itemGroup.trees", "MoreDyes Trees");
        add("itemGroup.plants", "MoreDyes Plants");
        add("itemGroup.dyes", "MoreDyes Dyes");
        add("itemGroup.blocks", "MoreDyes Blocks");
        add("itemGroup.shapes", "MoreDyes Slabs, Stairs and Walls");

        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String c = ColorStrings.ALL[i].toUpperCase() + " ";
            add(MDItem.dye[i], c + "Dye");

            add(MDBlock.stoneArray[i], c + "Stone");
            add(MDBlock.cobbleArray[i], c + "Cobblestone");
            add(MDBlock.stonebrickArray[i], c + "Stone Bricks");
            add(MDBlock.stonebrickCarvedArray[i], c + "Chiseled Stone Bricks");
            add(MDBlock.stonebrickCrackedArray[i], c + "Cracked Stone Bricks");
            add(MDBlock.brickArray[i], c + "Bricks");
            add(MDBlock.clayArray[i], c + "Clay");
            add(MDBlock.hardenedClayArray[i], c + "Terracotta");
            add(MDBlock.coalArray[i], c + "Block of Coal");
            add(MDBlock.lapisArray[i], c + "Block of Lapis Lazuli");
            add(MDBlock.redstoneArray[i], c + "Block of Redstone");
            add(MDBlock.quartzArray[i], c + "Block of Quartz");
            add(MDBlock.obsidianArray[i], c + "Obsidian");
            add(MDBlock.glowstoneArray[i], c + "Glowstone");
            add(MDBlock.glassArray[i], c + "Glass");
            add(MDBlock.glassFoggyArray[i], c + "Foggy Glass");
            add(MDBlock.sandArray[i], c + "Sand");
            add(MDBlock.sandstoneArray[i], c + "Sandstone");
            add(MDBlock.sandstoneCarvedArray[i], c + "Chiseled Sandstone");
            add(MDBlock.sandstoneSmoothArray[i], c + "Cut Sandstone");
            add(MDBlock.soulsandArray[i], c + "Soul Sand");
            add(MDBlock.woolArray[i], c + "Wool");
            add(MDBlock.andesiteArray[i], c + "Andesite");
            add(MDBlock.dioriteArray[i], c + "Diorite");
            add(MDBlock.concreteArray[i], c + "Concrete");
            add(MDBlock.concretePowderArray[i], c + "Concrete Powder");
            add(MDBlock.workbenchArray[i], c + "Crafting Table");
            add(MDBlock.chestArray[i], c + "Chest");
            add(MDBlock.bookshelfArray[i], c + "Bookshelf");
            add(MDBlock.pistonArray[i], c + "Piston");
            add(MDBlock.stickyPistonArray[i], c + "Sticky Piston");
            add(MDBlock.pistonHeadArray[i], c + "Piston Head");
            add(MDBlock.glassPaneArray[i], c + "Glass Pane");
            add(MDBlock.glassFoggyPaneArray[i], c + "Foggy Glass Pane");
            add(MDBlock.tulipArray[i], c + "Tulip");
            add(MDBlock.graniteArray[i], c + "Granite");
            add(MDBlock.polishedAndesiteArray[i], c + "Polished Andesite");
            add(MDBlock.polishedDioriteArray[i], c + "Polished Diorite");
            add(MDBlock.polishedGraniteArray[i], c + "Polished Granite");
            add(MDBlock.endstoneArray[i], c + "End Stone");
            add(MDBlock.mossyCobbleArray[i], c + "Mossy Cobblestone");
            add(MDBlock.mossyStonebrickArray[i], c + "Mossy Stone Bricks");
            add(MDBlock.quartzBricksArray[i], c + "Quartz Bricks");
            add(MDBlock.quartzChiseledArray[i], c + "Chiseled Quartz Block");
            add(MDBlock.quartzPillarArray[i], c + "Quartz Pillar");
            add(MDBlock.quartzSmoothArray[i], c + "Smooth Quartz Block");
            add(MDBlock.boneBlockArray[i], c + "Bone Block");
            add(MDBlock.gravelArray[i], c + "Gravel");
            add(MDBlock.iceArray[i], c + "Ice");
            add(MDBlock.packedIceArray[i], c + "Packed Ice");
            add(MDBlock.snowArray[i], c + "Snow Block");
            for (int f = 0; f < FLOWER_NAMES.length; f++)
            {
                add(MDBlock.smallFlowerArrays[f][i], c + FLOWER_NAMES[f]);
            }
            for (int f = 0; f < TALL_FLOWER_NAMES.length; f++)
            {
                add(MDBlock.tallFlowerArrays[f][i], c + TALL_FLOWER_NAMES[f]);
            }
            for (DyedShapes shapes : DyedShapes.ALL)
            {
                add(shapes.slabs[i], c + shapes.displayName + " Slab");
                add(shapes.stairs[i], c + shapes.displayName + " Stairs");
                if (shapes.walls.length > 0)
                {
                    add(shapes.walls[i], c + shapes.displayName + " Wall");
                }
            }

            wood(c + "Oak", MDBlock.oakLogArray[i], MDBlock.oakPlankArray[i], MDBlock.oakLeafArray[i], MDBlock.oakSaplingArray[i], MDBlock.oakFenceArray[i]);
            wood(c + "Birch", MDBlock.birchLogArray[i], MDBlock.birchPlankArray[i], MDBlock.birchLeafArray[i], MDBlock.birchSaplingArray[i], MDBlock.birchFenceArray[i]);
            wood(c + "Spruce", MDBlock.spruceLogArray[i], MDBlock.sprucePlankArray[i], MDBlock.spruceLafArray[i], MDBlock.spruceSaplingArray[i], MDBlock.spruceFenceArray[i]);
            wood(c + "Jungle", MDBlock.jungleLogArray[i], MDBlock.junglePlankArray[i], MDBlock.jungleLeafArray[i], MDBlock.jungleSaplingArray[i], MDBlock.jungleFenceArray[i]);
            wood(c + "Acacia", MDBlock.acaciaLogArray[i], MDBlock.acaciaPlankArray[i], MDBlock.acaciaLeafArray[i], MDBlock.acaciaSaplingArray[i], MDBlock.acaciaFenceArray[i]);
            wood(c + "Dark Oak", MDBlock.darkOakLogArray[i], MDBlock.darkOakPlankArray[i], MDBlock.darkOakLeafArray[i], MDBlock.darkOakSaplingArray[i], MDBlock.darkOakFenceArray[i]);
        }
    }

    private void wood(String prefix, Block log, Block planks, Block leaves, Block sapling, Block fence)
    {
        add(log, prefix + " Log");
        add(planks, prefix + " Planks");
        add(leaves, prefix + " Leaves");
        add(sapling, prefix + " Sapling");
        add(fence, prefix + " Fence");
    }
}
