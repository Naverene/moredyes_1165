package info.kg6jay.moredyes.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import info.kg6jay.moredyes.block.ILayeredBlock;
import info.kg6jay.moredyes.block.ILayeredBlock.RenderLayer;

/**
 * Draws an ILayeredBlock once per layer with the vanilla cube or crossed-squares renderer. The block reads
 * RenderLayer.current to return that layer's icons and tint.
 */
public class LayeredBlockRenderer implements ISimpleBlockRenderingHandler {

    private final int renderId;
    private final boolean plant;

    public LayeredBlockRenderer(int renderId, boolean plant) {
        this.renderId = renderId;
        this.plant = plant;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        if (!(block instanceof ILayeredBlock layered)) {
            return false;
        }
        int meta = world.getBlockMetadata(x, y, z);
        boolean drawn = false;
        try {
            for (int layer = 0; layer < layered.getLayerCount(); ++layer) {
                RenderLayer.current = layer;
                if (this.plant) {
                    if (layered.getLayerIcon(0, meta, layer) != null) {
                        drawn |= renderer.renderCrossedSquares(block, x, y, z);
                    }
                } else {
                    drawn |= renderer.renderStandardBlock(block, x, y, z);
                }
            }
        } finally {
            RenderLayer.current = -1;
        }
        return drawn;
    }

    /** Only used for cubes; plants are drawn as flat items by MDItemBlockColored. */
    @Override
    public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
        if (!(block instanceof ILayeredBlock layered)) {
            return;
        }
        Tessellator tessellator = Tessellator.instance;
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        for (int layer = 0; layer < layered.getLayerCount(); ++layer) {
            int color = layered.getLayerColor(meta, layer);
            GL11.glColor4f((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, 1.0F);
            for (int side = 0; side < 6; ++side) {
                IIcon icon = layered.getLayerIcon(side, meta, layer);
                if (icon == null) {
                    continue;
                }
                tessellator.startDrawingQuads();
                switch (side) {
                    case 0 -> {
                        tessellator.setNormal(0.0F, -1.0F, 0.0F);
                        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
                    }
                    case 1 -> {
                        tessellator.setNormal(0.0F, 1.0F, 0.0F);
                        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
                    }
                    case 2 -> {
                        tessellator.setNormal(0.0F, 0.0F, -1.0F);
                        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
                    }
                    case 3 -> {
                        tessellator.setNormal(0.0F, 0.0F, 1.0F);
                        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
                    }
                    case 4 -> {
                        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
                    }
                    default -> {
                        tessellator.setNormal(1.0F, 0.0F, 0.0F);
                        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
                    }
                }
                tessellator.draw();
            }
        }
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return !this.plant;
    }

    @Override
    public int getRenderId() {
        return this.renderId;
    }
}
