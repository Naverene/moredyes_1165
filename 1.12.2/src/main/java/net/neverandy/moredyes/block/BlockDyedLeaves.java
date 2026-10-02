package net.neverandy.moredyes.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.BlockInfo;

/**
 * The leaves of a dye tree. Each color is its own block, because the metadata holds the vanilla leaf decay flags.
 * They drop the sapling of their color and, often, the dye itself.
 */
public class BlockDyedLeaves extends BlockLeaves implements IColoredBlock
{
	private final int colorIndex;
	private final String typeName;

	public BlockDyedLeaves(BlockInfo info,int colorIndex)
	{
		super();
		this.colorIndex=colorIndex;
		this.typeName=info.blockName;
		// Always drawn with see-through leaves; the fast graphics setting only reaches the vanilla leaves.
		this.leavesFancy=true;
		info.apply(this,ColorStrings.ALL[colorIndex]);
		this.setSoundType(info.sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY,true).withProperty(DECAYABLE,true));
	}
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this,CHECK_DECAY,DECAYABLE);
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(DECAYABLE,(meta&4)==0).withProperty(CHECK_DECAY,(meta&8)>0);
	}
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta=0;
		if(!state.getValue(DECAYABLE))
		{
			meta|=4;
		}
		if(state.getValue(CHECK_DECAY))
		{
			meta|=8;
		}
		return meta;
	}
	/** Leaves placed by hand never decay, like vanilla leaves. */
	@Override
	public IBlockState getStateForPlacement(World worldIn,BlockPos pos,net.minecraft.util.EnumFacing facing,float hitX,float hitY,float hitZ,int meta,net.minecraft.entity.EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(DECAYABLE,false).withProperty(CHECK_DECAY,false);
	}
	@Override
	public Item getItemDropped(IBlockState state,Random rand,int fortune)
	{
		return Item.getItemFromBlock(MDBlock.sapling[ColorStrings.groupOf(this.colorIndex)]);
	}
	/** The shade of the sapling that drops. */
	@Override
	public int damageDropped(IBlockState state)
	{
		return ColorStrings.shadeOf(this.colorIndex);
	}
	/** Dye trees drop their dye where an oak would drop an apple, and far more often. */
	@Override
	protected void dropApple(World worldIn,BlockPos pos,IBlockState state,int chance)
	{
		if(worldIn.rand.nextInt(chance)<=50)
		{
			spawnAsEntity(worldIn,pos,MDItem.dyeStack(this.colorIndex,1));
		}
	}
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(this);
	}
	@Override
	public ItemStack getItem(World worldIn,BlockPos pos,IBlockState state)
	{
		return new ItemStack(this);
	}
	@Override
	public List<ItemStack> onSheared(ItemStack item,IBlockAccess world,BlockPos pos,int fortune)
	{
		return Arrays.asList(new ItemStack(this));
	}
	@Override
	public BlockPlanks.EnumType getWoodType(int meta)
	{
		return BlockPlanks.EnumType.OAK;
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
