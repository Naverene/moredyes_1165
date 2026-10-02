package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Collections;
import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

/**
 * A dyed wall drops itself without a loot table. Walls can be turned off in the config, and a loot table for a wall
 * that isn't registered fails to load, so the walls carry their drop in code instead.
 */
public class DyedWallBlock extends WallBlock
{
    public DyedWallBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return Collections.singletonList(new ItemStack(this));
    }
}
