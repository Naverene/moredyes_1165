package net.neverandy.moredyes.data.server;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neverandy.moredyes.block.BlockPistonHead;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.block.DyedWallBlock;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.world.DyeTrees;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** What each dyed block drops: itself, except where the vanilla block drops something else. */
public class ModLootTableProvider
{
    private ModLootTableProvider() {}

    public static LootTableProvider create(PackOutput output)
    {
        return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)));
    }

    public static class ModBlockLootTables extends BlockLootSubProvider
    {
        private static final float[] SAPLING_CHANCES = {0.05F, 0.0625F, 0.083333336F, 0.1F};

        protected ModBlockLootTables()
        {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate()
        {
            for (Block block : getKnownBlocks())
            {
                dropSelf(block);
            }
            for (int i = 0; i < ColorStrings.ALL.length; i++)
            {
                Block cobble = MDBlock.cobbleArray[i];
                add(MDBlock.stoneArray[i], stone -> createSingleItemTableWithSilkTouch(stone, cobble));
                dropWhenSilkTouch(MDBlock.glassArray[i]);
                dropWhenSilkTouch(MDBlock.glassFoggyArray[i]);
                dropWhenSilkTouch(MDBlock.glassPaneArray[i]);
                dropWhenSilkTouch(MDBlock.glassFoggyPaneArray[i]);
                add(MDBlock.bookshelfArray[i], shelf -> createSingleItemTableWithSilkTouch(shelf, Items.BOOK, ConstantValue.exactly(3)));
                dropWhenSilkTouch(MDBlock.iceArray[i]);
                dropWhenSilkTouch(MDBlock.packedIceArray[i]);
                for (TallFlowerBlock[] flowers : MDBlock.tallFlowerArrays)
                {
                    add(flowers[i], flower -> createSinglePropConditionTable(flower, TallFlowerBlock.HALF, DoubleBlockHalf.LOWER));
                }
                for (DyedShapes shapes : DyedShapes.ALL)
                {
                    add(shapes.slabs[i], this::createSlabItemTable);
                }
                for (String wood : DyeTrees.WOODS)
                {
                    Block sapling = DyeTrees.saplings(wood)[i];
                    add(DyeTrees.leaves(wood)[i], leaves -> createLeavesDrops(leaves, sapling, SAPLING_CHANCES));
                }
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            // Piston heads drop nothing, like the vanilla head, and walls drop themselves in code (DyedWallBlock).
            return MDBlock.all().stream()
                    .filter(block -> !(block instanceof BlockPistonHead) && !(block instanceof DyedWallBlock)).collect(Collectors.toList());
        }
    }
}
