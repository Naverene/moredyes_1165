package info.kg6jay.moredyes.block;

import net.minecraft.world.IBlockAccess;

import info.kg6jay.moredyes.utility.BlockInfo;

/**
 * Dyed chain (Minecraft 1.16): a thin upright post drawn as two crossed squares, like iron bars seen from the side.
 * The metadata holds the color, so unlike the 1.16 chain it always hangs upright.
 */
public class MDBlockChain extends MDBlockColored {

    private static final float MIN = 6.5F / 16.0F, MAX = 9.5F / 16.0F;

    public MDBlockChain(String[] colors, BlockInfo info, String colorSet) {
        super(colors, info, colorSet);
        this.setBlockBounds(MIN, 0.0F, MIN, MAX, 1.0F, MAX);
    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return false;
    }
}
