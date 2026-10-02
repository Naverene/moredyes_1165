package net.neverandy.moredyes.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.neverandy.moredyes.utility.BlockInfo;

/** Dyed glass, clear or foggy. Like vanilla glass it drops nothing unless broken with silk touch. */
public class BlockColoredGlass extends BlockColored
{
	private final boolean translucent;

	public BlockColoredGlass(BlockInfo info,int group,boolean translucent)
	{
		super(info,group);
		this.translucent=translucent;
	}
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}
	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return this.translucent?BlockRenderLayer.TRANSLUCENT:BlockRenderLayer.CUTOUT;
	}
	/** Hides the faces between two glass blocks of the same shade. */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState,IBlockAccess blockAccess,BlockPos pos,EnumFacing side)
	{
		return blockAccess.getBlockState(pos.offset(side))!=blockState&&super.shouldSideBeRendered(blockState,blockAccess,pos,side);
	}
}
