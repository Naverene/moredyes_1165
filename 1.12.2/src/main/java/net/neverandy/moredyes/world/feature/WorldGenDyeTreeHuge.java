package net.neverandy.moredyes.world.feature;

import java.util.Random;

import net.neverandy.moredyes.block.MDBlock;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenDyeTreeHuge extends WorldGenAbstractTree
{
    /** The base height of the tree */
    protected final int baseHeight;
    /** Sets the metadata for the wood blocks used */
    protected final IBlockState woodMetadata;
    /** Sets the metadata for the leaves used in huge trees */
    protected final IBlockState leavesMetadata;
    protected int extraRandomHeight;

    public WorldGenDyeTreeHuge(boolean notify, int index)
    {
        super(notify);
        this.baseHeight = 15;
        this.extraRandomHeight = 30;
        this.woodMetadata = MDBlock.log[index].getDefaultState();
        this.leavesMetadata = MDBlock.leaf[index].getDefaultState();
    }

    /**
     * calculates the height based on this trees base height and its extra random height
     */
    protected int getHeight(Random rand)
    {
        int i = rand.nextInt(3) + this.baseHeight;

        if (this.extraRandomHeight > 1)
        {
            i += rand.nextInt(this.extraRandomHeight);
        }

        return i;
    }

    /**
     * returns whether or not there is space for a tree to grow at a certain position
     */
    private boolean isSpaceAt(World worldIn, BlockPos leavesPos, int height)
    {
        boolean flag = true;

        if (leavesPos.getY() >= 1 && leavesPos.getY() + height + 1 <= 256)
        {
            for (int i = 0; i <= 1 + height; ++i)
            {
                int j = 2;

                if (i == 0)
                {
                    j = 1;
                }
                else if (i >= 1 + height - 2)
                {
                    j = 2;
                }

                for (int k = -j; k <= j && flag; ++k)
                {
                    for (int l = -j; l <= j && flag; ++l)
                    {
                        if (leavesPos.getY() + i < 0 || leavesPos.getY() + i >= 256 || !this.isReplaceable(worldIn,leavesPos.add(k, i, l)))
                        {
                            flag = false;
                        }
                    }
                }
            }

            return flag;
        }
        else
        {
            return false;
        }
    }

    /**
     * returns whether or not there is dirt underneath the block where the tree will be grown.
     * It also generates dirt around the block in a 2x2 square if there is dirt underneath the blockpos.
     */
    private boolean ensureDirtsUnderneath(BlockPos pos, World worldIn)
    {
        BlockPos blockpos = pos.down();
        IBlockState state = worldIn.getBlockState(blockpos);
        boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, blockpos, EnumFacing.UP, ((BlockSapling)Blocks.SAPLING));

        if (isSoil && pos.getY() >= 2)
        {
            this.onPlantGrow(worldIn, blockpos, pos);
            this.onPlantGrow(worldIn, blockpos.east(), pos);
            this.onPlantGrow(worldIn, blockpos.south(), pos);
            this.onPlantGrow(worldIn, blockpos.south().east(), pos);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * returns whether or not a tree can grow at a specific position.
     * If it can, it generates surrounding dirt underneath.
     */
    protected boolean ensureGrowable(World worldIn, Random rand, BlockPos treePos, int p_175929_4_)
    {
        return this.isSpaceAt(worldIn, treePos, p_175929_4_) && this.ensureDirtsUnderneath(treePos, worldIn);
    }

    /**
     * grow leaves in a circle with the outsides being within the circle
     */
    protected void growLeavesLayerStrict(World worldIn, BlockPos layerCenter, int width)
    {
        int i = width * width;

        for (int j = -width; j <= width + 1; ++j)
        {
            for (int k = -width; k <= width + 1; ++k)
            {
                int l = j - 1;
                int i1 = k - 1;

                if (j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i)
                {
                    BlockPos blockpos = layerCenter.add(j, 0, k);
                    IBlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
                    }
                }
            }
        }
    }

    /**
     * grow leaves in a circle
     */
    protected void growLeavesLayer(World worldIn, BlockPos layerCenter, int width)
    {
        int i = width * width;

        for (int j = -width; j <= width; ++j)
        {
            for (int k = -width; k <= width; ++k)
            {
                if (j * j + k * k <= i)
                {
                    BlockPos blockpos = layerCenter.add(j, 0, k);
                    IBlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
                    }
                }
            }
        }
    }

    //Just a helper macro
    private void onPlantGrow(World world, BlockPos pos, BlockPos source)
    {
        IBlockState state = world.getBlockState(pos);
        state.getBlock().onPlantGrow(state, world, pos, source);
    }
    private void createCrown(World worldIn, BlockPos p_175930_2_, int p_175930_3_)
    {
        int i = 2;

        for (int j = -2; j <= 0; ++j)
        {
            this.growLeavesLayerStrict(worldIn, p_175930_2_.up(j), p_175930_3_ + 1 - j);
        }
    }
    private boolean isAirLeaves(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos);
    }
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int i = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, i))
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.up(i), 2);

            for (int j = position.getY() + i - 2 - rand.nextInt(4); j > position.getY() + i / 2; j -= 2 + rand.nextInt(4))
            {
                float f = rand.nextFloat() * ((float)Math.PI * 2F);
                int k = position.getX() + (int)(0.5F + MathHelper.cos(f) * 4.0F);
                int l = position.getZ() + (int)(0.5F + MathHelper.sin(f) * 4.0F);

                for (int i1 = 0; i1 < 5; ++i1)
                {
                    k = position.getX() + (int)(1.5F + MathHelper.cos(f) * (float)i1);
                    l = position.getZ() + (int)(1.5F + MathHelper.sin(f) * (float)i1);
                    this.setBlockAndNotifyAdequately(worldIn, new BlockPos(k, j - 3 + i1 / 2, l), this.woodMetadata);
                }

                int j2 = 1 + rand.nextInt(2);
                int j1 = j;

                for (int k1 = j - j2; k1 <= j1; ++k1)
                {
                    int l1 = k1 - j1;
                    this.growLeavesLayer(worldIn, new BlockPos(k, k1, l), 1 - l1);
                }
            }

            for (int i2 = 0; i2 < i; ++i2)
            {
                BlockPos blockpos = position.up(i2);

                if (this.isAirLeaves(worldIn,blockpos))
                {
                    this.setBlockAndNotifyAdequately(worldIn, blockpos, this.woodMetadata);
                }

                if (i2 < i - 1)
                {
                    BlockPos blockpos1 = blockpos.east();

                    if (this.isAirLeaves(worldIn,blockpos1))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos1, this.woodMetadata);
                    }

                    BlockPos blockpos2 = blockpos.south().east();

                    if (this.isAirLeaves(worldIn,blockpos2))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos2, this.woodMetadata);
                    }

                    BlockPos blockpos3 = blockpos.south();

                    if (this.isAirLeaves(worldIn,blockpos3))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos3, this.woodMetadata);
                    }
                }
            }

            return true;
        }
    }
}