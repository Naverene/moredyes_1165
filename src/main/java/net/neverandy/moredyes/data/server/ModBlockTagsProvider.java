package net.neverandy.moredyes.data.server;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.world.DyeTrees;

/**
 * Puts the dyed wood, wool and flowers in the vanilla tags, so leaves stay alive next to dyed logs, dyed planks
 * work in vanilla recipes, and wood burns in a furnace.
 */
public class ModBlockTagsProvider extends BlockTagsProvider
{
    public ModBlockTagsProvider(DataGenerator gen, ExistingFileHelper existingFileHelper)
    {
        super(gen, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags()
    {
        for (String wood : DyeTrees.WOODS)
        {
            getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).add(DyeTrees.logs(wood));
            getOrCreateBuilder(BlockTags.LEAVES).add(DyeTrees.leaves(wood));
        }
        getOrCreateBuilder(BlockTags.PLANKS).add(concat(MDBlock.oakPlankArray, MDBlock.birchPlankArray, MDBlock.sprucePlankArray,
                MDBlock.junglePlankArray, MDBlock.acaciaPlankArray, MDBlock.darkOakPlankArray));
        getOrCreateBuilder(BlockTags.SAPLINGS).add(concat(MDBlock.oakSaplingArray, MDBlock.birchSaplingArray, MDBlock.spruceSaplingArray,
                MDBlock.jungleSaplingArray, MDBlock.acaciaSaplingArray, MDBlock.darkOakSaplingArray));
        getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(concat(MDBlock.oakFenceArray, MDBlock.birchFenceArray, MDBlock.spruceFenceArray,
                MDBlock.jungleFenceArray, MDBlock.acaciaFenceArray, MDBlock.darkOakFenceArray));
        getOrCreateBuilder(BlockTags.SMALL_FLOWERS).add(MDBlock.tulipArray);
        getOrCreateBuilder(BlockTags.SMALL_FLOWERS).add(concat(MDBlock.smallFlowerArrays));
        getOrCreateBuilder(BlockTags.TALL_FLOWERS).add(concat(MDBlock.tallFlowerArrays));
        getOrCreateBuilder(BlockTags.ICE).add(concat(MDBlock.iceArray, MDBlock.packedIceArray));
        for (DyedShapes shapes : DyedShapes.ALL)
        {
            // Wooden slabs and stairs are in the wooden tags, which are part of the plain ones.
            boolean wood = shapes.full[0].getDefaultState().getMaterial() == net.minecraft.block.material.Material.WOOD;
            getOrCreateBuilder(wood ? BlockTags.WOODEN_SLABS : BlockTags.SLABS).add(shapes.slabs);
            getOrCreateBuilder(wood ? BlockTags.WOODEN_STAIRS : BlockTags.STAIRS).add(shapes.stairs);
            // Optional, so the tag still loads when walls are turned off and these blocks don't exist.
            for (Block wall : shapes.walls)
            {
                getOrCreateBuilder(BlockTags.WALLS).addOptional(wall.getRegistryName());
            }
        }
        getOrCreateBuilder(BlockTags.WOOL).add(MDBlock.woolArray);
        getOrCreateBuilder(Tags.Blocks.CHESTS_WOODEN).add(MDBlock.chestArray);
        getOrCreateBuilder(Tags.Blocks.GLASS_PANES).add(concat(MDBlock.glassPaneArray, MDBlock.glassFoggyPaneArray));
    }

    private static Block[] concat(Block[]... arrays)
    {
        return java.util.Arrays.stream(arrays).flatMap(java.util.Arrays::stream).toArray(Block[]::new);
    }
}
