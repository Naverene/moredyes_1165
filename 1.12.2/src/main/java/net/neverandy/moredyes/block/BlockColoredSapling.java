package net.neverandy.moredyes.block;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.BlockInfo;
import net.neverandy.moredyes.utility.ColorUtil;
import net.neverandy.moredyes.world.feature.WorldGenDyeTree;
import net.neverandy.moredyes.world.feature.WorldGenDyeTreeBig;
import net.neverandy.moredyes.world.feature.WorldGenDyeTreeHuge;

/** A dyed sapling grows into a tree with logs and leaves of its shade. The metadata is the shade. */
public class BlockColoredSapling extends BlockBush implements IGrowable,IColoredBlock
{
	protected static final AxisAlignedBB SAPLING_AABB=new AxisAlignedBB(0.1D,0.0D,0.1D,0.9D,0.8D,0.9D);

	protected final String[] colors;
	protected final int group;
	private final String typeName;

	public BlockColoredSapling(BlockInfo info,int group)
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
		return SAPLING_AABB;
	}
	@Override
	public void updateTick(World worldIn,BlockPos pos,IBlockState state,Random rand)
	{
		if(!worldIn.isRemote)
		{
			super.updateTick(worldIn,pos,state,rand);
			if(!worldIn.isAreaLoaded(pos,1))
			{
				return;
			}
			if(worldIn.getBlockState(pos).getBlock()==this&&worldIn.getLightFromNeighbors(pos.up())>=9&&rand.nextInt(7)==0)
			{
				this.generateTree(worldIn,pos,state,rand);
			}
		}
	}
	/** Mostly a normal tree, sometimes a big one, rarely a huge one. */
	public static WorldGenerator randomTree(Random rand,boolean notify,int colorIndex)
	{
		int r=rand.nextInt(100);
		if(r>10)
		{
			return new WorldGenDyeTree(notify,false,colorIndex);
		}
		if(r>1)
		{
			return new WorldGenDyeTreeBig(notify,colorIndex);
		}
		return new WorldGenDyeTreeHuge(notify,colorIndex);
	}
	public void generateTree(World worldIn,BlockPos pos,IBlockState state,Random rand)
	{
		if(!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn,rand,pos))
		{
			return;
		}
		WorldGenerator tree=randomTree(rand,true,this.getColorIndex(this.getMetaFromState(state)));
		worldIn.setBlockState(pos,Blocks.AIR.getDefaultState(),4);
		if(!tree.generate(worldIn,rand,pos))
		{
			worldIn.setBlockState(pos,state,4);
		}
	}
	@Override
	public boolean canGrow(World worldIn,BlockPos pos,IBlockState state,boolean isClient)
	{
		return true;
	}
	@Override
	public boolean canUseBonemeal(World worldIn,Random rand,BlockPos pos,IBlockState state)
	{
		return (double)worldIn.rand.nextFloat()<0.45D;
	}
	@Override
	public void grow(World worldIn,Random rand,BlockPos pos,IBlockState state)
	{
		this.generateTree(worldIn,pos,state,rand);
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
