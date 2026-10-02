package net.neverandy.moredyes.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.neverandy.moredyes.handler.ConfigHandler;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.BlockInfo;
import net.neverandy.moredyes.utility.ColorUtil;

/** A dyed block holding the shades of one color group; the metadata is the shade. */
public class BlockColored extends Block implements IColoredBlock
{
	public static final PropertyInteger SHADE=PropertyInteger.create("shade",0,ColorStrings.MAX_SHADES-1);

	protected final String[] colors;
	protected final int group;
	private final String typeName;

	public BlockColored(BlockInfo info,int group)
	{
		super(info.blockMaterial);
		this.group=group;
		this.colors=ColorStrings.GROUPS[group];
		this.typeName=info.blockName;
		info.apply(this,ColorStrings.GROUP_NAMES[group]);
		this.setSoundType(info.sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHADE,0));
	}
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this,SHADE);
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SHADE,ColorUtil.clampShade(this.colors,meta));
	}
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(SHADE);
	}
	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state);
	}
	/** The picked block keeps its shade, also for blocks that drop something else. */
	@Override
	public ItemStack getItem(World worldIn,BlockPos pos,IBlockState state)
	{
		return new ItemStack(this,1,this.getMetaFromState(state));
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
