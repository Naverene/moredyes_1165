package net.neverandy.moredyes.data.server;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        getOrCreateBuilder(BlockTags.WOOL).add(MDBlock.woolArray);
        getOrCreateBuilder(Tags.Blocks.CHESTS_WOODEN).add(MDBlock.chestArray);
        getOrCreateBuilder(Tags.Blocks.GLASS_PANES).add(concat(MDBlock.glassPaneArray, MDBlock.glassFoggyPaneArray));
    }

    private static Block[] concat(Block[]... arrays)
    {
        return java.util.Arrays.stream(arrays).flatMap(java.util.Arrays::stream).toArray(Block[]::new);
    }
}
