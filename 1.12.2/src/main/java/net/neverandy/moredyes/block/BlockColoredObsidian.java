package net.neverandy.moredyes.block;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.neverandy.moredyes.utility.BlockInfo;

/** Dyed obsidian cannot be moved by pistons, like vanilla obsidian. */
public class BlockColoredObsidian extends BlockColored
{
	public BlockColoredObsidian(BlockInfo info,int group)
	{
		super(info,group);
	}
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state)
	{
		return EnumPushReaction.BLOCK;
	}
}
