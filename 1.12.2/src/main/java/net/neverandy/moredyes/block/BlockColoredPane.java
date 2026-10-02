package net.neverandy.moredyes.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.BlockInfo;
import net.neverandy.moredyes.utility.ColorUtil;

/** A dyed glass pane, clear or foggy. Like vanilla panes it drops nothing unless broken with silk touch. */
public class BlockColoredPane extends BlockPane implements IColoredBlock
{
	protected final String[] colors;
	protected final int group;
	private final String typeName;
	private final boolean translucent;

	public BlockColoredPane(BlockInfo info,int group,boolean translucent)
	{
		super(info.blockMaterial,false);
		this.group=group;
		this.colors=ColorStrings.GROUPS[group];
		this.typeName=info.blockName;
		this.translucent=translucent;
		info.apply(this,ColorStrings.GROUP_NAMES[group]);
		this.setSoundType(info.sound);
	}
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this,NORTH,EAST,WEST,SOUTH,BlockColored.SHADE);
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return this.translucent?BlockRenderLayer.TRANSLUCENT:BlockRenderLayer.CUTOUT_MIPPED;
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
