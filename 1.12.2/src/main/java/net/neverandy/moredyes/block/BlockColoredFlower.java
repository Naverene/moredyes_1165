package net.neverandy.moredyes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.BlockInfo;
import net.neverandy.moredyes.utility.ColorUtil;

/** A dyed tulip, crafted into its dye. The metadata is the shade. */
public class BlockColoredFlower extends BlockBush implements IColoredBlock
{
	protected final String[] colors;
	protected final int group;
	private final String typeName;

	public BlockColoredFlower(BlockInfo info,int group)
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
	public AxisAlignedBB getBoundingBox(IBlockState state,IBlockAccess source,BlockPos pos)
	{
		return super.getBoundingBox(state,source,pos).offset(state.getOffset(source,pos));
	}
	/** Drawn slightly off the middle of the block, like vanilla flowers. */
	@Override
	public Block.EnumOffsetType getOffsetType()
	{
		return Block.EnumOffsetType.XZ;
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
