package net.neverandy.moredyes.block;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.handler.ConfigHandler;
import net.neverandy.moredyes.handler.GuiHandler;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.BlockInfo;
import net.neverandy.moredyes.utility.ColorUtil;

/** A dyed crafting table. The metadata is the shade. */
public class BlockColoredWorkbench extends BlockWorkbench implements IColoredBlock
{
	protected final String[] colors;
	protected final int group;
	private final String typeName;

	public BlockColoredWorkbench(BlockInfo info,int group)
	{
		super();
		this.group=group;
		this.colors=ColorStrings.GROUPS[group];
		this.typeName=info.blockName;
		info.apply(this,ColorStrings.GROUP_NAMES[group]);
		this.setSoundType(info.sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(BlockColored.SHADE,0));
	}
	/** Opens the crafting grid through the mod's GUI handler: the vanilla one closes unless the block is the vanilla table. */
	@Override
	public boolean onBlockActivated(World worldIn,BlockPos pos,IBlockState state,EntityPlayer playerIn,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ)
	{
		if(!worldIn.isRemote)
		{
			playerIn.openGui(MoreDyes.instance,GuiHandler.COLORED_WORKBENCH_GUI_ID,worldIn,pos.getX(),pos.getY(),pos.getZ());
			playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
		}
		return true;
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
