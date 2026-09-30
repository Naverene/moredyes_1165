package net.neverandy.moredyes.data.client;


import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;

/**
 * Every dyed block uses one grey texture from textures/block/tinted, multiplied by the block's dye color when it
 * is drawn (see client/ColorHandlers). The parent models in models/block/tinted mark which faces take the color.
 * Parts that keep their natural color, such as log bark and a sapling's trunk, use an untinted texture instead.
 */
public class ModBlockStateProvider extends BlockStateProvider
{

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, Reference.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        MoreDyes.LOGGER.info("Starting for loop");
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            cubeAll(MDBlock.glassArray[i], "glass");
            cubeAll(MDBlock.glassFoggyArray[i], "glass_foggy");
            cubeAll(MDBlock.sandArray[i], "sand");
            cubeAll(MDBlock.brickArray[i], "brick");
            cubeAll(MDBlock.clayArray[i], "clay");
            cubeAll(MDBlock.hardenedClayArray[i], "hardened_clay");
            cubeAll(MDBlock.woolArray[i], "wool");
            cubeAll(MDBlock.cobbleArray[i], "cobble");
            cubeAll(MDBlock.stonebrickArray[i], "stonebrick");
            cubeAll(MDBlock.stonebrickCrackedArray[i], "stonebrick_cracked");
            cubeAll(MDBlock.stonebrickCarvedArray[i], "stonebrick_carved");
            cubeAll(MDBlock.stoneArray[i], "stone");
            cubeAll(MDBlock.obsidianArray[i], "obsidian");
            cubeAll(MDBlock.lapisArray[i], "lapis");
            cubeAll(MDBlock.glowstoneArray[i], "glowstone");
            cubeAll(MDBlock.coalArray[i], "coal");
            cubeAll(MDBlock.soulsandArray[i], "soulsand");
            cubeAll(MDBlock.redstoneArray[i], "redstone");
            cubeAll(MDBlock.quartzArray[i], "quartz");
            cubeAll(MDBlock.andesiteArray[i], "andesite");
            cubeAll(MDBlock.dioriteArray[i], "diorite");
            cubeAll(MDBlock.concreteArray[i], "concrete");
            cubeAll(MDBlock.concretePowderArray[i], "concrete_powder");

            sandstone(MDBlock.sandstoneArray[i], "sandstone_side");
            sandstone(MDBlock.sandstoneCarvedArray[i], "sandstone_carved");
            sandstone(MDBlock.sandstoneSmoothArray[i], "sandstone_smooth");

            Block workbench = MDBlock.workbenchArray[i];
            simpleBlock(workbench, tinted(workbench, "orientable_with_bottom")
                    .texture("top", tex("workbench_top"))
                    .texture("side", tex("workbench_side"))
                    .texture("front", tex("workbench_front"))
                    .texture("bottom", tex("oak_planks")));

            // The chest itself is drawn by client/ChestRenderer; its block model only gives the breaking particles.
            simpleBlock(MDBlock.chestArray[i], models().getExistingFile(modLoc("block/tinted/chest")));

            Block shelf = MDBlock.bookshelfArray[i];
            simpleBlock(shelf, tinted(shelf, "cube_column")
                    .texture("side", tex("bookshelf"))
                    .texture("end", tex("oak_planks")));

            pane(MDBlock.glassPaneArray[i], "glass");
            pane(MDBlock.glassFoggyPaneArray[i], "glass_foggy");

            Block tulip = MDBlock.tulipArray[i];
            simpleBlock(tulip, tinted(tulip, "cross_layered")
                    .texture("cross", tex("tulip_petals"))
                    .texture("overlay", tex("tulip_stem")));

            wood(i, "oak", MDBlock.oakPlankArray, MDBlock.oakLogArray, MDBlock.oakLeafArray, MDBlock.oakSaplingArray, MDBlock.oakFenceArray);
            wood(i, "birch", MDBlock.birchPlankArray, MDBlock.birchLogArray, MDBlock.birchLeafArray, MDBlock.birchSaplingArray, MDBlock.birchFenceArray);
            wood(i, "spruce", MDBlock.sprucePlankArray, MDBlock.spruceLogArray, MDBlock.spruceLafArray, MDBlock.spruceSaplingArray, MDBlock.spruceFenceArray);
            wood(i, "jungle", MDBlock.junglePlankArray, MDBlock.jungleLogArray, MDBlock.jungleLeafArray, MDBlock.jungleSaplingArray, MDBlock.jungleFenceArray);
            wood(i, "acacia", MDBlock.acaciaPlankArray, MDBlock.acaciaLogArray, MDBlock.acaciaLeafArray, MDBlock.acaciaSaplingArray, MDBlock.acaciaFenceArray);
            wood(i, "dark_oak", MDBlock.darkOakPlankArray, MDBlock.darkOakLogArray, MDBlock.darkOakLeafArray, MDBlock.darkOakSaplingArray, MDBlock.darkOakFenceArray);
        }
    }

    private void wood(int i, String wood, Block[] planks, Block[] logs, Block[] leaves, Block[] saplings, Block[] fences)
    {
        ResourceLocation plankTexture = tex(wood + "_planks");
        cubeAll(planks[i], wood + "_planks");
        simpleBlock(logs[i], tinted(logs[i], "log")
                .texture("end", tex(wood + "_log_top"))
                .texture("side", mcLoc("block/" + wood + "_log")));
        // Vanilla leaf textures are already grey (the game tints them by biome), so they are used as they are.
        simpleBlock(leaves[i], tinted(leaves[i], "cube_all").texture("all", mcLoc("block/" + wood + "_leaves")));
        simpleBlock(saplings[i], tinted(saplings[i], "cross_layered")
                .texture("cross", tex(wood + "_sapling_leaves"))
                .texture("overlay", tex(wood + "_sapling_trunk")));

        String fence = name(fences[i]);
        ModelFile post = models().withExistingParent(fence + "_post", modLoc("block/tinted/fence_post")).texture("texture", plankTexture);
        ModelFile side = models().withExistingParent(fence + "_side", modLoc("block/tinted/fence_side")).texture("texture", plankTexture);
        fourWayBlock((FenceBlock) fences[i], post, side);
        models().withExistingParent(fence + "_inventory", modLoc("block/tinted/fence_inventory")).texture("texture", plankTexture);
    }

    private void pane(PaneBlock pane, String texture)
    {
        String name = name(pane);
        ModelFile[] parts = new ModelFile[5];
        String[] suffixes = {"post", "side", "side_alt", "noside", "noside_alt"};
        for (int p = 0; p < parts.length; p++)
        {
            parts[p] = models().withExistingParent(name + "_" + suffixes[p], modLoc("block/tinted/glass_pane_" + suffixes[p]))
                    .texture("pane", tex(texture))
                    .texture("edge", tex("glass_pane_top"));
        }
        paneBlock(pane, parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    private void cubeAll(Block block, String texture)
    {
        simpleBlock(block, tinted(block, "cube_all").texture("all", tex(texture)));
    }

    private void sandstone(Block block, String side)
    {
        simpleBlock(block, tinted(block, "cube_bottom_top")
                .texture("side", tex(side))
                .texture("top", tex("sandstone_top"))
                .texture("bottom", tex("sandstone_bottom")));
    }

    private BlockModelBuilder tinted(Block block, String parent)
    {
        return models().withExistingParent(name(block), modLoc("block/tinted/" + parent));
    }

    private ResourceLocation tex(String name)
    {
        return modLoc("block/tinted/" + name);
    }

    private static String name(Block block)
    {
        return block.getRegistryName().getPath();
    }
}
