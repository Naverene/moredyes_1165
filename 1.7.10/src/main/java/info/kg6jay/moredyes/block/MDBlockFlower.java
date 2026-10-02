package info.kg6jay.moredyes.block;

import static net.minecraftforge.common.EnumPlantType.*;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.common.registry.GameRegistry;
import info.kg6jay.moredyes.MoreDyes;
import info.kg6jay.moredyes.utility.BlockInfo;

public class MDBlockFlower extends MDBlockColoredPlant implements IPlantable {

    protected MDBlockFlower(String[] colors, BlockInfo info, String colorSet) {
        this(colors, info, colorSet, "tulip");
    }

    /** @param texture the flower's texture key; its petals are "texture/petals" and its stem "texture/stem" */
    protected MDBlockFlower(String[] colors, BlockInfo info, String colorSet, String texture) {
        super(colors, info, colorSet, texture + "/petals", texture + "/stem");
        this.setTickRandomly(true);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
        this.setCreativeTab(MoreDyes.tabPlants);
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
        return block == Blocks.grass || block == Blocks.dirt
            || block == Blocks.farmland
            || block == GameRegistry.findBlock("resourceengineering", "blockLoadedGrass");
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        this.checkAndDropBlock(world, x, y, z);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand) {
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_,
        int p_149668_4_) {
        return null;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return Plains;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }
}
