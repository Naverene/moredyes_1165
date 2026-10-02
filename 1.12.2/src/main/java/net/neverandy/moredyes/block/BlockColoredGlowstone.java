package net.neverandy.moredyes.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.neverandy.moredyes.utility.BlockInfo;

/** Dyed glowstone: full light, and drops 2-4 glowstone dust like vanilla. Silk touch gives the block itself. */
public class BlockColoredGlowstone extends BlockColored
{
	public BlockColoredGlowstone(BlockInfo info,int group)
	{
		super(info,group);
		this.setLightLevel(1.0F);
	}
	@Override
	public int quantityDroppedWithBonus(int fortune,Random random)
	{
		return MathHelper.clamp(this.quantityDropped(random)+random.nextInt(fortune+1),1,4);
	}
	@Override
	public int quantityDropped(Random random)
	{
		return 2+random.nextInt(3);
	}
	@Override
	public Item getItemDropped(IBlockState state,Random rand,int fortune)
	{
		return Items.GLOWSTONE_DUST;
	}
	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}
}
