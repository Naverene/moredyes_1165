package net.neverandy.moredyes.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.neverandy.moredyes.handler.ConfigHandler;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.BlockInfo;

/** A log of a dye tree. Each color is its own block, because the metadata holds the direction the log lies in. */
public class BlockDyedLog extends BlockLog implements IColoredBlock
{
	private final int colorIndex;
	private final String typeName;

	public BlockDyedLog(BlockInfo info,int colorIndex)
	{
		super();
		this.colorIndex=colorIndex;
		this.typeName=info.blockName;
		info.apply(this,ColorStrings.ALL[colorIndex]);
		this.setSoundType(info.sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS,BlockLog.EnumAxis.Y));
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state=this.getDefaultState();
		switch(meta&12)
		{
			case 0:
				return state.withProperty(LOG_AXIS,BlockLog.EnumAxis.Y);
			case 4:
				return state.withProperty(LOG_AXIS,BlockLog.EnumAxis.X);
			case 8:
				return state.withProperty(LOG_AXIS,BlockLog.EnumAxis.Z);
			default:
				return state.withProperty(LOG_AXIS,BlockLog.EnumAxis.NONE);
		}
	}
	@Override
	public int getMetaFromState(IBlockState state)
	{
		switch(state.getValue(LOG_AXIS))
		{
			case X:
				return 4;
			case Z:
				return 8;
			case NONE:
				return 12;
			default:
				return 0;
		}
	}
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this,LOG_AXIS);
	}
	@Override
	public boolean canCreatureSpawn(IBlockState state,IBlockAccess world,BlockPos pos,EntityLiving.SpawnPlacementType type)
	{
		return !ConfigHandler.preventMobSpawning&&super.canCreatureSpawn(state,world,pos,type);
	}
	@Override
	public String getTypeName()
	{
		return this.typeName;
	}
	@Override
	public int getShadeCount()
	{
		return 1;
	}
	@Override
	public int getColorIndex(int meta)
	{
		return this.colorIndex;
	}
	@Override
	public String getColorName(int meta)
	{
		return ColorStrings.ALL[this.colorIndex];
	}
}
