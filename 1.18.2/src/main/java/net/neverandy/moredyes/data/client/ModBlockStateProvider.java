package net.neverandy.moredyes.data.client;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.DyedShapes;
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

            cubeAll(MDBlock.graniteArray[i], "granite");
            cubeAll(MDBlock.polishedAndesiteArray[i], "polished_andesite");
            cubeAll(MDBlock.polishedDioriteArray[i], "polished_diorite");
            cubeAll(MDBlock.polishedGraniteArray[i], "polished_granite");
            cubeAll(MDBlock.endstoneArray[i], "endstone");
            cubeAll(MDBlock.mossyCobbleArray[i], "mossy_cobble");
            cubeAll(MDBlock.mossyStonebrickArray[i], "mossy_stonebrick");
            cubeAll(MDBlock.quartzBricksArray[i], "quartz_bricks");
            cubeAll(MDBlock.quartzSmoothArray[i], "quartz_smooth");
            cubeAll(MDBlock.gravelArray[i], "gravel");
            cubeAll(MDBlock.iceArray[i], "ice");
            cubeAll(MDBlock.packedIceArray[i], "packed_ice");
            cubeAll(MDBlock.snowArray[i], "snow");
            Block chiseledQuartz = MDBlock.quartzChiseledArray[i];
            simpleBlock(chiseledQuartz, tinted(chiseledQuartz, "cube_column")
                    .texture("side", tex("quartz_chiseled"))
                    .texture("end", tex("quartz_chiseled_top")));
            pillar(MDBlock.quartzPillarArray[i], "quartz_pillar", "quartz_pillar_top");
            pillar(MDBlock.boneBlockArray[i], "bone_block_side", "bone_block_top");
            for (int f = 0; f < MDBlock.SMALL_FLOWERS.length; f++)
            {
                Block flower = MDBlock.smallFlowerArrays[f][i];
                String type = MDBlock.SMALL_FLOWERS[f][0];
                simpleBlock(flower, tinted(flower, "cross_layered")
                        .texture("cross", tex(type + "_petals"))
                        .texture("overlay", tex(type + "_stem")));
            }
            for (int f = 0; f < MDBlock.TALL_FLOWERS.length; f++)
            {
                tallFlower(MDBlock.tallFlowerArrays[f][i], MDBlock.TALL_FLOWERS[f][0]);
            }

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

            piston(MDBlock.pistonArray[i], "piston");
            piston(MDBlock.stickyPistonArray[i], "sticky_piston");
            pistonHead(MDBlock.pistonHeadArray[i]);

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
        for (DyedShapes shapes : DyedShapes.ALL)
        {
            shapes(shapes);
        }
    }

    /** One set of models per type, shared by every color of its slabs, stairs and walls. */
    private void shapes(DyedShapes shapes)
    {
        String base = "block/shape/" + shapes.type;
        ModelFile slab = shape(base + "_slab", "slab", shapes);
        ModelFile slabTop = shape(base + "_slab_top", "slab_top", shapes);
        ModelFile full = shape(base + "_double", "cube_bottom_top", shapes);
        ModelFile stairs = shape(base + "_stairs", "stairs", shapes);
        ModelFile inner = shape(base + "_stairs_inner", "inner_stairs", shapes);
        ModelFile outer = shape(base + "_stairs_outer", "outer_stairs", shapes);
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            slabBlock(shapes.slabs[i], slab, slabTop, full);
            stairsBlock(shapes.stairs[i], stairs, inner, outer);
        }
        if (shapes.walls.length == 0)
        {
            return;
        }
        ModelFile post = models().withExistingParent(base + "_wall_post", modLoc("block/tinted/template_wall_post")).texture("wall", tex(shapes.side));
        ModelFile side = models().withExistingParent(base + "_wall_side", modLoc("block/tinted/template_wall_side")).texture("wall", tex(shapes.side));
        ModelFile tall = models().withExistingParent(base + "_wall_side_tall", modLoc("block/tinted/template_wall_side_tall")).texture("wall", tex(shapes.side));
        models().withExistingParent(base + "_wall_inventory", modLoc("block/tinted/wall_inventory")).texture("wall", tex(shapes.side));
        for (WallBlock wall : shapes.walls)
        {
            wallBlock(wall, post, side, tall);
        }
    }

    private ModelFile shape(String name, String parent, DyedShapes shapes)
    {
        return models().withExistingParent(name, modLoc("block/tinted/" + parent))
                .texture("side", tex(shapes.side))
                .texture("top", tex(shapes.top))
                .texture("bottom", tex(shapes.bottom));
    }

    private void pillar(RotatedPillarBlock block, String side, String end)
    {
        ModelFile vertical = tinted(block, "cube_column").texture("side", tex(side)).texture("end", tex(end));
        ModelFile horizontal = models().withExistingParent(name(block) + "_horizontal", modLoc("block/tinted/cube_column_horizontal"))
                .texture("side", tex(side)).texture("end", tex(end));
        axisBlock(block, vertical, horizontal);
    }

    /** Like the vanilla tall flowers: each half is a cross of tinted petals over a stem in its own color. */
    private void tallFlower(TallFlowerBlock flower, String type)
    {
        String name = name(flower);
        ModelFile bottom = models().withExistingParent(name + "_bottom", modLoc("block/tinted/cross_layered"))
                .texture("cross", tex(type + "_bottom_petals")).texture("overlay", tex(type + "_bottom_stem"));
        ModelFile top = models().withExistingParent(name + "_top", modLoc("block/tinted/cross_layered"))
                .texture("cross", tex(type + "_top_petals")).texture("overlay", tex(type + "_top_stem"));
        getVariantBuilder(flower).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(TallFlowerBlock.HALF) == DoubleBlockHalf.LOWER ? bottom : top).build());
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

    /** Every color shares the tinted piston models; the facing turns them like the vanilla piston's blockstate. */
    private void piston(Block piston, String model)
    {
        ModelFile retracted = models().getExistingFile(modLoc("block/tinted/" + model));
        ModelFile extended = models().getExistingFile(modLoc("block/tinted/piston_base"));
        getVariantBuilder(piston).forAllStates(state -> facing(state.getValue(PistonBaseBlock.EXTENDED) ? extended : retracted,
                state.getValue(PistonBaseBlock.FACING)));
    }

    private void pistonHead(Block head)
    {
        getVariantBuilder(head).forAllStates(state ->
        {
            String model = "block/tinted/piston_head" + (state.getValue(PistonHeadBlock.SHORT) ? "_short" : "")
                    + (state.getValue(PistonHeadBlock.TYPE) == PistonType.STICKY ? "_sticky" : "");
            return facing(models().getExistingFile(modLoc(model)), state.getValue(PistonHeadBlock.FACING));
        });
    }

    private static ConfiguredModel[] facing(ModelFile model, Direction facing)
    {
        int x = facing == Direction.DOWN ? 90 : facing == Direction.UP ? 270 : 0;
        int y = facing.getAxis().isVertical() ? 0 : ((int) facing.toYRot() + 180) % 360;
        return ConfiguredModel.builder().modelFile(model).rotationX(x).rotationY(y).build();
    }

    private void pane(IronBarsBlock pane, String texture)
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
