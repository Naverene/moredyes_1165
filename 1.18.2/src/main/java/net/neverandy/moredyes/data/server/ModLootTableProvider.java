package net.neverandy.moredyes.data.server;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
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
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables()
    {
        return ImmutableList.of(Pair.of(ModBlockLootTables::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker)
    {
        map.forEach((name, table) -> LootTables.validate(validationtracker, name, table));
    }

    public static class ModBlockLootTables extends BlockLoot
    {
        private static final float[] SAPLING_CHANCES = {0.05F, 0.0625F, 0.083333336F, 0.1F};

        @Override
        protected void addTables()
        {
            for (Block block : getKnownBlocks())
            {
                dropSelf(block);
            }
            for (int i = 0; i < ColorStrings.ALL.length; i++)
            {
                Block cobble = MDBlock.cobbleArray[i];
                add(MDBlock.stoneArray[i], stone -> createSingleItemTableWithSilkTouch(stone, cobble));
                add(MDBlock.glassArray[i], BlockLoot::createSilkTouchOnlyTable);
                add(MDBlock.glassFoggyArray[i], BlockLoot::createSilkTouchOnlyTable);
                add(MDBlock.glassPaneArray[i], BlockLoot::createSilkTouchOnlyTable);
                add(MDBlock.bookshelfArray[i], shelf -> createSingleItemTableWithSilkTouch(shelf, Items.BOOK, ConstantValue.exactly(3)));
                add(MDBlock.glassFoggyPaneArray[i], BlockLoot::createSilkTouchOnlyTable);
                add(MDBlock.iceArray[i], BlockLoot::createSilkTouchOnlyTable);
                add(MDBlock.packedIceArray[i], BlockLoot::createSilkTouchOnlyTable);
                for (TallFlowerBlock[] flowers : MDBlock.tallFlowerArrays)
                {
                    add(flowers[i], flower -> createSinglePropConditionTable(flower, TallFlowerBlock.HALF, DoubleBlockHalf.LOWER));
                }
                for (DyedShapes shapes : DyedShapes.ALL)
                {
                    add(shapes.slabs[i], BlockLoot::createSlabItemTable);
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
            return MDBlock.BLOCKS.getEntries().stream().map(RegistryObject::get)
                    .filter(block -> !(block instanceof BlockPistonHead) && !(block instanceof DyedWallBlock)).collect(Collectors.toList());
        }
    }
}
