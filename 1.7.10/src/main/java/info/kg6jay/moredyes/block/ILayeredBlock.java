package info.kg6jay.moredyes.block;

import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * A block drawn in several layers, each with its own texture and tint, for blocks where only part of the texture
 * should take the dye color: the petals of a flower but not its stem, or the top of a log but not its bark.
 */
public interface ILayeredBlock {

    int getLayerCount();

    /** The icon for this side on this layer, or null if the side is not drawn on this layer. */
    @SideOnly(Side.CLIENT)
    IIcon getLayerIcon(int side, int meta, int layer);

    /** RGB tint for this layer. */
    int getLayerColor(int meta, int layer);

    /** True if the layers are drawn as crossed squares (like flowers) instead of as a cube. */
    boolean isPlant();

    /**
     * The layer currently being drawn by the layered block renderer, read by getIcon, colorMultiplier and
     * shouldSideBeRendered. -1 outside of that renderer.
     */
    final class RenderLayer {

        public static int current = -1;

        private RenderLayer() {}
    }
}
