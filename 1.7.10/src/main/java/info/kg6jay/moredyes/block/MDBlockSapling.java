package info.kg6jay.moredyes.block;

import static net.minecraftforge.common.EnumPlantType.Plains;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import info.kg6jay.moredyes.utility.BlockInfo;
import info.kg6jay.moredyes.world.feature.WorldGenTreeDye;

public class MDBlockSapling extends MDBlockColoredPlant implements IGrowable, IPlantable {

    private final int colorIndex;

    public MDBlockSapling(String[] colors, BlockInfo info, String colorSet, int index) {
        super(colors, info, colorSet, "sapling/leaves", "sapling/trunk");
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setTickRandomly(true);
        this.colorIndex = index;

    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }

    /**
     * is the block grass, dirt or farmland
     */
    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        this.checkAndDropBlock(world, x, y, z);
    }

    /**
     * checks if the block can stay, if not drop as item
     */
    protected void checkAndDropBlock(World world, int x, int y, int z) {
        if (!this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    /**
     * Can this block stay at this position. Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.getBlock(x, y - 1, z)
            .canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return Plains;
    }

    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            super.updateTick(world, x, y, z, rand);

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(7) == 0) {
                this.func_149879_c(world, x, y, z, rand);
            }
        }
    }

    public void func_149879_c(World world, int x, int y, int z, Random rand) {
        this.func_149878_d(world, x, y, z, rand);
    }

    // growTree();
    public void func_149878_d(World world, int x, int y, int z, Random rand) {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z)) return;
        int meta = world.getBlockMetadata(x, y, z);
        WorldGenerator tree = new WorldGenTreeDye(false, 3, meta, this.colorIndex);
        world.setBlockToAir(x, y, z);

        if (!tree.generate(world, rand, x, y, z)) {
            world.setBlock(x, y, z, this, meta, 4);
        }
    }

    public boolean func_149880_a(World world, int x, int y, int z, int meta) {
        return world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == meta;
    }

    public boolean func_149851_a(World world, int x, int y, int z, boolean isRemote) {
        return true;
    }

    public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
        return (double) world.rand.nextFloat() < 0.45D;
    }

    public void func_149853_b(World world, Random rand, int x, int y, int z) {
        this.func_149879_c(world, x, y, z, rand);
    }
}
