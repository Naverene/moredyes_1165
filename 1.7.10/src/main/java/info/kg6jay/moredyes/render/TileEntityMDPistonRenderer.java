package info.kg6jay.moredyes.render;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.block.MDBlockDyedPiston;
import info.kg6jay.moredyes.block.MDBlockDyedPistonHead;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDPiston;

/**
 * Draws a moving dyed piston or piston head, the same way vanilla draws a moving piston, but with the dyed head: the
 * vanilla renderer always draws a retracting head as vanilla's piston head. The blocks take their color from the
 * TileEntityMDPiston at the position they are drawn for (see MDBlockDyedPiston.colorAt).
 */
@SideOnly(Side.CLIENT)
public class TileEntityMDPistonRenderer extends TileEntitySpecialRenderer {

    private RenderBlocks renderBlocks;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        if (!(tile instanceof TileEntityMDPiston piston)) {
            return;
        }
        Block block = piston.getStoredBlockID();
        float progress = piston.getRenderProgress(partialTicks);
        if (block == null || block.getMaterial() == Material.air || progress >= 1.0F) {
            return;
        }
        if (this.renderBlocks == null || this.renderBlocks.blockAccess != piston.getWorldObj()) {
            this.renderBlocks = new RenderBlocks(piston.getWorldObj());
        }

        Tessellator tessellator = Tessellator.instance;
        this.bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(Minecraft.isAmbientOcclusionEnabled() ? GL11.GL_SMOOTH : GL11.GL_FLAT);

        int px = piston.xCoord, py = piston.yCoord, pz = piston.zCoord;
        int facing = piston.getPistonOrientation();
        float offset = piston.isExtending() ? progress - 1.0F : 1.0F - progress;
        tessellator.startDrawingQuads();
        tessellator.setTranslation(
            x - px + offset * Facing.offsetsXForSide[facing],
            y - py + offset * Facing.offsetsYForSide[facing],
            z - pz + offset * Facing.offsetsZForSide[facing]);
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);

        boolean retracting = block instanceof MDBlockDyedPiston && piston.shouldRenderHead() && !piston.isExtending();
        if (block instanceof MDBlockDyedPistonHead && progress < 0.5F) {
            // A head that has only just started moving out: a short rod, so it does not show behind the piston
            this.renderBlocks.renderPistonExtensionAllFaces(block, px, py, pz, false);
        } else if (retracting) {
            // The head sliding back in, then the piston itself, which does not move
            this.renderBlocks.renderPistonExtensionAllFaces(MDBlock.pistonHead, px, py, pz, progress < 0.5F);
            tessellator.setTranslation(x - px, y - py, z - pz);
            this.renderBlocks.renderPistonBaseAllFaces(block, px, py, pz);
        } else {
            this.renderBlocks.renderBlockAllFaces(block, px, py, pz);
        }

        tessellator.setTranslation(0.0D, 0.0D, 0.0D);
        tessellator.draw();
        RenderHelper.enableStandardItemLighting();
    }
}
