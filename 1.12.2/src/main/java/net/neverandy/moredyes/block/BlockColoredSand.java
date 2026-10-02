package net.neverandy.moredyes.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.neverandy.moredyes.handler.ConfigHandler;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.BlockInfo;
import net.neverandy.moredyes.utility.ColorUtil;

/** Dyed sand falls like vanilla sand. The metadata is the shade. */
public class BlockColoredSand extends BlockFalling implements IColoredBlock
{
	protected final String[] colors;
	protected final int group;
	private final String typeName;

	public BlockColoredSand(BlockInfo info,int group)
	{
		super(info.blockMaterial);
		this.group=group;
		this.colors=ColorStrings.GROUPS[group];
		this.typeName=info.blockName;
		info.apply(this,ColorStrings.GROUP_NAMES[group]);
		this.setSoundType(info.sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockColored.SHADE,0));
	}
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this,BlockColored.SHADE);
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(BlockColored.SHADE,ColorUtil.clampShade(this.colors,meta));
	}
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(BlockColored.SHADE);
	}
	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state);
	}
	@Override
	public void getSubBlocks(CreativeTabs tab,NonNullList<ItemStack> items)
	{
		for(int i=0;i<this.colors.length;i++)
		{
			items.add(new ItemStack(this,1,i));
		}
	}
	@Override
	public boolean canCreatureSpawn(IBlockState state,IBlockAccess world,BlockPos pos,EntityLiving.SpawnPlacementType type)
	{
		return !ConfigHandler.preventMobSpawning&&super.canCreatureSpawn(state,world,pos,type);
	}
	/** Grows cactus and dead bushes, and sugar cane next to water, like vanilla sand. */
	@Override
	public boolean canSustainPlant(IBlockState state,IBlockAccess world,BlockPos pos,EnumFacing direction,IPlantable plantable)
	{
		EnumPlantType type=plantable.getPlantType(world,pos.offset(direction));
		if(type==EnumPlantType.Desert||plantable.getPlant(world,pos.offset(direction)).getBlock()==Blocks.CACTUS)
		{
			return true;
		}
		if(type==EnumPlantType.Beach)
		{
			for(EnumFacing side:EnumFacing.HORIZONTALS)
			{
				if(world.getBlockState(pos.offset(side)).getMaterial()==Material.WATER)
				{
					return true;
				}
			}
			return false;
		}
		return super.canSustainPlant(state,world,pos,direction,plantable);
	}
	/** The falling dust takes the dye color. */
	@Override
	@SideOnly(Side.CLIENT)
	public int getDustColor(IBlockState state)
	{
		return 0xFF000000|this.getColor(this.getMetaFromState(state));
	}
	@Override
	public String getTypeName()
	{
		return this.typeName;
	}
	@Override
	public int getShadeCount()
	{
		return this.colors.length;
	}
	@Override
	public int getColorIndex(int meta)
	{
		return ColorStrings.index(this.group,ColorUtil.clampShade(this.colors,meta));
	}
	@Override
	public String getColorName(int meta)
	{
		return this.colors[ColorUtil.clampShade(this.colors,meta)];
	}
}
