package net.neverandy.moredyes.data.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.world.DyeTrees;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Puts the dyed wood, wool and flowers in the vanilla tags, so leaves stay alive next to dyed logs, dyed planks
 * work in vanilla recipes, and wood burns in a furnace. The mineable tags say which tool breaks each block fastest.
 */
public class ModBlockTagsProvider extends BlockTagsProvider
{
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper existingFileHelper)
    {
        super(output, lookup, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        for (String wood : DyeTrees.WOODS)
        {
            tag(BlockTags.LOGS_THAT_BURN).add(DyeTrees.logs(wood));
            tag(BlockTags.LEAVES).add(DyeTrees.leaves(wood));
        }
        tag(BlockTags.PLANKS).add(concat(MDBlock.oakPlankArray, MDBlock.birchPlankArray, MDBlock.sprucePlankArray,
                MDBlock.junglePlankArray, MDBlock.acaciaPlankArray, MDBlock.darkOakPlankArray));
        tag(BlockTags.SAPLINGS).add(concat(MDBlock.oakSaplingArray, MDBlock.birchSaplingArray, MDBlock.spruceSaplingArray,
                MDBlock.jungleSaplingArray, MDBlock.acaciaSaplingArray, MDBlock.darkOakSaplingArray));
        tag(BlockTags.WOODEN_FENCES).add(concat(MDBlock.oakFenceArray, MDBlock.birchFenceArray, MDBlock.spruceFenceArray,
                MDBlock.jungleFenceArray, MDBlock.acaciaFenceArray, MDBlock.darkOakFenceArray));
        tag(BlockTags.SMALL_FLOWERS).add(MDBlock.tulipArray);
        tag(BlockTags.SMALL_FLOWERS).add(concat(MDBlock.smallFlowerArrays));
        tag(BlockTags.TALL_FLOWERS).add(concat(MDBlock.tallFlowerArrays));
        tag(BlockTags.ICE).add(concat(MDBlock.iceArray, MDBlock.packedIceArray));
        for (DyedShapes shapes : DyedShapes.ALL)
        {
            // Wooden slabs and stairs are in the wooden tags, which are part of the plain ones.
            boolean wood = MDBlock.MINEABLE.get(shapes.full) == BlockTags.MINEABLE_WITH_AXE;
            tag(wood ? BlockTags.WOODEN_SLABS : BlockTags.SLABS).add(shapes.slabs);
            tag(wood ? BlockTags.WOODEN_STAIRS : BlockTags.STAIRS).add(shapes.stairs);
        }
        tag(BlockTags.WOOL).add(MDBlock.woolArray);
        tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(MDBlock.bookshelfArray);
        tag(BlockTags.GUARDED_BY_PIGLINS).add(MDBlock.chestArray);
        tag(Tags.Blocks.CHESTS_WOODEN).add(MDBlock.chestArray);
        tag(Tags.Blocks.GLASS_PANES).add(concat(MDBlock.glassPaneArray, MDBlock.glassFoggyPaneArray));
        tag(Tags.Blocks.GLASS).add(concat(MDBlock.glassArray, MDBlock.glassFoggyArray));

        addTool(MDBlock.MINEABLE);
        addTool(MDBlock.NEEDS_TOOL);
    }

    /**
     * Mineable and needs-tool tags. Walls are added as optional entries, so the tags still load when walls are turned
     * off and those blocks don't exist.
     */
    private void addTool(Map<Block[], TagKey<Block>> tools)
    {
        for (Map.Entry<Block[], TagKey<Block>> entry : tools.entrySet())
        {
            for (Block block : entry.getKey())
            {
                if (block instanceof net.neverandy.moredyes.block.DyedWallBlock)
                {
                    tag(entry.getValue()).addOptional(ForgeRegistries.BLOCKS.getKey(block));
                }
                else
                {
                    tag(entry.getValue()).add(block);
                }
            }
        }
        for (DyedShapes shapes : DyedShapes.ALL)
        {
            for (Block wall : shapes.walls)
            {
                tag(BlockTags.WALLS).addOptional(ForgeRegistries.BLOCKS.getKey(wall));
            }
        }
    }

    private static Block[] concat(Block[]... arrays)
    {
        return Arrays.stream(arrays).flatMap(Arrays::stream).toArray(Block[]::new);
    }
}
