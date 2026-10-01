package net.neverandy.moredyes.data.server;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.neverandy.moredyes.block.BlockPistonHead;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.block.DyedWallBlock;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.world.DyeTrees;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** What each dyed block drops: itself, except where the vanilla block drops something else. */
public class ModLootTableProvider extends LootTableProvider
{
    public ModLootTableProvider(DataGenerator dataGeneratorIn)
    {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        return ImmutableList.of(Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker)
    {
        map.forEach((name, table) -> LootTableManager.validateLootTable(validationtracker, name, table));
    }

    public static class ModBlockLootTables extends BlockLootTables
    {
        private static final float[] SAPLING_CHANCES = {0.05F, 0.0625F, 0.083333336F, 0.1F};

        @Override
        protected void addTables()
        {
            for (Block block : getKnownBlocks())
            {
                registerDropSelfLootTable(block);
            }
            for (int i = 0; i < ColorStrings.ALL.length; i++)
            {
                Block cobble = MDBlock.cobbleArray[i];
                registerLootTable(MDBlock.stoneArray[i], stone -> droppingWithSilkTouch(stone, cobble));
                registerLootTable(MDBlock.glassArray[i], BlockLootTables::onlyWithSilkTouch);
                registerLootTable(MDBlock.glassFoggyArray[i], BlockLootTables::onlyWithSilkTouch);
                registerLootTable(MDBlock.glassPaneArray[i], BlockLootTables::onlyWithSilkTouch);
                registerLootTable(MDBlock.bookshelfArray[i], shelf -> droppingWithSilkTouchOrRandomly(shelf, Items.BOOK, ConstantRange.of(3)));
                registerLootTable(MDBlock.glassFoggyPaneArray[i], BlockLootTables::onlyWithSilkTouch);
                registerLootTable(MDBlock.iceArray[i], BlockLootTables::onlyWithSilkTouch);
                registerLootTable(MDBlock.packedIceArray[i], BlockLootTables::onlyWithSilkTouch);
                for (TallFlowerBlock[] flowers : MDBlock.tallFlowerArrays)
                {
                    registerLootTable(flowers[i], flower -> droppingWhen(flower, TallFlowerBlock.HALF, DoubleBlockHalf.LOWER));
                }
                for (DyedShapes shapes : DyedShapes.ALL)
                {
                    registerLootTable(shapes.slabs[i], BlockLootTables::droppingSlab);
                }
                for (String wood : DyeTrees.WOODS)
                {
                    Block sapling = DyeTrees.saplings(wood)[i];
                    registerLootTable(DyeTrees.leaves(wood)[i], leaves -> droppingWithChancesAndSticks(leaves, sapling, SAPLING_CHANCES));
                }
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks()
        {
            // Piston heads drop nothing, like the vanilla head, and walls drop themselves in code (DyedWallBlock).
            return MDBlock.BLOCKS.getEntries().stream().map(RegistryObject::get)
                    .filter(block -> !(block instanceof BlockPistonHead) && !(block instanceof DyedWallBlock)).collect(Collectors.toList());
        }
    }
}
