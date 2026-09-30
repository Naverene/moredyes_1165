package net.neverandy.moredyes.data.server;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.neverandy.moredyes.reference.Reference;

/** The item side of ModBlockTagsProvider; these tags give the furnace burn times and vanilla recipe inputs. */
public class ModItemTagsProvider extends ItemTagsProvider
{
    public ModItemTagsProvider(DataGenerator gen, BlockTagsProvider blockTags, ExistingFileHelper existingFileHelper)
    {
        super(gen, blockTags, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags()
    {
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copy(BlockTags.WOOL, ItemTags.WOOL);
    }
}
