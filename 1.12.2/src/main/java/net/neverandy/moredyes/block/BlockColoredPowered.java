package net.neverandy.moredyes.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.neverandy.moredyes.utility.BlockInfo;

/** A dyed block of redstone gives off a full redstone signal, like the vanilla block. */
public class BlockColoredPowered extends BlockColored
{
	public BlockColoredPowered(BlockInfo info,int group)
	{
		super(info,group);
	}
	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return true;
	}
	@Override
	public int getWeakPower(IBlockState blockState,IBlockAccess blockAccess,BlockPos pos,EnumFacing side)
	{
		return 15;
	}
}
