package net.neverandy.moredyes.data.client;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.reference.ColorStrings;

public class ModLangProvider extends LanguageProvider
{
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
