package net.neverandy.moredyes.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.neverandy.moredyes.utility.BlockInfo;

/** Dyed soul sand slows entities walking on it and grows nether wart, like vanilla soul sand. */
public class BlockColoredSoulSand extends BlockColored
{
	protected static final AxisAlignedBB SOUL_SAND_AABB=new AxisAlignedBB(0.0D,0.0D,0.0D,1.0D,0.875D,1.0D);

	public BlockColoredSoulSand(BlockInfo info,int group)
	{
		super(info,group);
	}
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState,IBlockAccess worldIn,BlockPos pos)
	{
		return SOUL_SAND_AABB;
	}
	@Override
	public void onEntityCollidedWithBlock(World worldIn,BlockPos pos,IBlockState state,Entity entityIn)
	{
		entityIn.motionX*=0.4D;
		entityIn.motionZ*=0.4D;
	}
	@Override
	public boolean canSustainPlant(IBlockState state,IBlockAccess world,BlockPos pos,EnumFacing direction,IPlantable plantable)
	{
		return plantable.getPlantType(world,pos.offset(direction))==EnumPlantType.Nether||super.canSustainPlant(state,world,pos,direction,plantable);
	}
}
