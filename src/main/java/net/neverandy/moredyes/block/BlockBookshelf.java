package net.neverandy.moredyes.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.neverandy.moredyes.utility.BlockInfo;

/** A dyed bookshelf. Like a vanilla bookshelf it powers enchanting tables and burns. */
public class BlockBookshelf extends BasicBlock
{
    public BlockBookshelf(BlockInfo info)
    {
        super(info);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos)
    {
        return 1.0F;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 30;
    }
}
