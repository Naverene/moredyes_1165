package net.neverandy.moredyes.data.server;

import net.minecraft.world.level.block.Block;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;
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
    protected void addTags()
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
            boolean wood = shapes.full[0].defaultBlockState().getMaterial() == Material.WOOD;
            tag(wood ? BlockTags.WOODEN_SLABS : BlockTags.SLABS).add(shapes.slabs);
            tag(wood ? BlockTags.WOODEN_STAIRS : BlockTags.STAIRS).add(shapes.stairs);
            // Optional, so the tag still loads when walls are turned off and these blocks don't exist.
            for (Block wall : shapes.walls)
            {
                tag(BlockTags.WALLS).addOptional(wall.getRegistryName());
            }
        }
        tag(BlockTags.WOOL).add(MDBlock.woolArray);
        tag(Tags.Blocks.CHESTS_WOODEN).add(MDBlock.chestArray);
        tag(Tags.Blocks.GLASS_PANES).add(concat(MDBlock.glassPaneArray, MDBlock.glassFoggyPaneArray));
        addToolTags();
    }

    /**
     * Since 1.17 the tool that breaks a block quickly, and the tier a block needs to drop, come from these tags.
     * The tool follows the material, like vanilla's own blocks; walls are optional for the same reason as above.
     */
    private void addToolTags()
    {
        for (RegistryObject<Block> entry : MDBlock.BLOCKS.getEntries())
        {
            Block block = entry.get();
            TagKey<Block> tool = mineable(block.defaultBlockState().getMaterial());
            if (tool == null)
            {
                continue;
            }
            if (block instanceof WallBlock)
            {
                tag(tool).addOptional(entry.getId());
            }
            else
            {
                tag(tool).add(block);
            }
        }
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(MDBlock.obsidianArray);
        tag(BlockTags.NEEDS_STONE_TOOL).add(MDBlock.lapisArray);
    }

    private static TagKey<Block> mineable(Material material)
    {
        if (material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL || material == Material.PISTON
                || material == Material.ICE || material == Material.ICE_SOLID)
        {
            return BlockTags.MINEABLE_WITH_PICKAXE;
        }
        if (material == Material.WOOD || material == Material.NETHER_WOOD || material == Material.VEGETABLE)
        {
            return BlockTags.MINEABLE_WITH_AXE;
        }
        if (material == Material.SAND || material == Material.DIRT || material == Material.CLAY || material == Material.GRASS
                || material == Material.SNOW || material == Material.TOP_SNOW)
        {
            return BlockTags.MINEABLE_WITH_SHOVEL;
        }
        if (material == Material.LEAVES)
        {
            return BlockTags.MINEABLE_WITH_HOE;
        }
        return null;
    }

    private static Block[] concat(Block[]... arrays)
    {
        return java.util.Arrays.stream(arrays).flatMap(java.util.Arrays::stream).toArray(Block[]::new);
    }
}
