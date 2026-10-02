package net.neverandy.moredyes.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.neverandy.moredyes.utility.BlockInfo;

/** Dyed stone drops dyed cobblestone of the same shade, like vanilla stone. Silk touch gives the stone itself. */
public class BlockColoredStone extends BlockColored
{
	public BlockColoredStone(BlockInfo info,int group)
	{
		super(info,group);
	}
	@Override
	public Item getItemDropped(IBlockState state,Random rand,int fortune)
	{
		return Item.getItemFromBlock(MDBlock.cobble[this.group]);
	}
}
