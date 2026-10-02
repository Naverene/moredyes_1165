package info.kg6jay.moredyes.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import info.kg6jay.moredyes.utility.BlockInfo;

/**
 * Dyed concrete powder (Minecraft 1.12): falls like sand, and sets into dyed concrete of the same shade as soon as it
 * touches water, whether it is placed next to water or lands in it.
 */
public class MDBlockConcretePowder extends MDBlockColoredSand {

    private final Block concrete;

    public MDBlockConcretePowder(String[] colors, BlockInfo info, String colorSet, Block concrete) {
        super(colors, info, colorSet);
        this.concrete = concrete;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!this.setIfWet(world, x, y, z)) {
            super.onBlockAdded(world, x, y, z);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        if (!this.setIfWet(world, x, y, z)) {
            super.onNeighborBlockChange(world, x, y, z, neighbor);
        }
    }

    /** Called when the falling block lands. */
    @Override
    public void func_149828_a(World world, int x, int y, int z, int meta) {
        this.setIfWet(world, x, y, z);
    }

    /** Turns into concrete if water touches any side but the bottom, like vanilla. */
    private boolean setIfWet(World world, int x, int y, int z) {
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            if (side != ForgeDirection.DOWN && world.getBlock(x + side.offsetX, y + side.offsetY, z + side.offsetZ)
                .getMaterial() == Material.water) {
                world.setBlock(x, y, z, this.concrete, world.getBlockMetadata(x, y, z), 3);
                return true;
            }
        }
        return false;
    }

    /** Unlike sand, grows no plants. */
    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plantable) {
        return false;
    }
}
