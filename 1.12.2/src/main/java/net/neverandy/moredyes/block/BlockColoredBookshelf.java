package net.neverandy.moredyes.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.neverandy.moredyes.utility.BlockInfo;

/** A dyed bookshelf powers an enchanting table and drops three books, like the vanilla one. Silk touch keeps it. */
public class BlockColoredBookshelf extends BlockColored
{
	public BlockColoredBookshelf(BlockInfo info,int group)
	{
		super(info,group);
	}
	@Override
	public float getEnchantPowerBonus(World world,BlockPos pos)
	{
		return 1.0F;
	}
	@Override
	public int quantityDropped(Random random)
	{
		return 3;
	}
	@Override
	public Item getItemDropped(IBlockState state,Random rand,int fortune)
	{
		return Items.BOOK;
	}
	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}
}
