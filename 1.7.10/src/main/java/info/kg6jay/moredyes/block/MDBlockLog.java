package info.kg6jay.moredyes.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.block.ILayeredBlock.RenderLayer;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.utility.BlockInfo;
import info.kg6jay.moredyes.utility.ColorUtil;

/**
 * Dyed log: the rings on the top and bottom take the dye color, the bark keeps its natural color. Drawn in two
 * layers: layer 0 is the tinted top and bottom, layer 1 the untinted bark.
 */
public class MDBlockLog extends MDBlockColored implements ILayeredBlock {

    @SideOnly(Side.CLIENT)
    protected IIcon topIcon, barkIcon;

    public MDBlockLog(String[] colors, BlockInfo info, String colorSet) {
        super(colors, info, colorSet);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        byte range = 4;
        int loaded = range + 1;

        if (world.checkChunksExist(x - loaded, y - loaded, z - loaded, x + loaded, y + loaded, z + loaded)) {
            for (int dx = -range; dx <= range; ++dx) {
                for (int dy = -range; dy <= range; ++dy) {
                    for (int dz = -range; dz <= range; ++dz) {
                        Block b = world.getBlock(x + dx, y + dy, z + dz);
                        if (b.isLeaves(world, x + dx, y + dy, z + dz)) {
                            b.beginLeavesDecay(world, x + dx, y + dy, z + dz);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public int getRenderType() {
        return RenderIds.layeredCube;
    }

    @Override
    public int getLayerCount() {
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getLayerIcon(int side, int meta, int layer) {
        boolean end = side == 0 || side == 1;
        if (layer == 0) {
            return end ? this.topIcon : null;
        }
        return end ? null : this.barkIcon;
    }

    @Override
    public int getLayerColor(int meta, int layer) {
        return layer == 0 ? ColorUtil.shade(this.blockColors, meta) : ColorUtil.WHITE;
    }

    @Override
    public boolean isPlant() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        // Outside the layered renderer (particles, other mods) return the natural texture for the side.
        return side == 0 || side == 1 ? this.topIcon : this.barkIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return this.getLayerColor(world.getBlockMetadata(x, y, z), Math.max(RenderLayer.current, 0));
    }

    /** Skips the sides that are not part of the layer being drawn. */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        if (RenderLayer.current >= 0) {
            int meta = world.getBlockMetadata(
                x - Facing.offsetsXForSide[side],
                y - Facing.offsetsYForSide[side],
                z - Facing.offsetsZForSide[side]);
            if (this.getLayerIcon(side, meta, RenderLayer.current) == null) {
                return false;
            }
        }
        return super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.topIcon = TintedTextures.register(iconRegister, "log/top");
        this.barkIcon = iconRegister.registerIcon("log_oak");
    }
}
