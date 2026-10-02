package info.kg6jay.moredyes.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.block.MDBlockColoredChest;
import info.kg6jay.moredyes.block.RenderIds;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDBlockColoredChest;
import info.kg6jay.moredyes.client.TintedTextures;

/**
 * Draws the dyed chests with the vanilla chest models: a grey copy of the vanilla chest texture tinted with the dye
 * color, with the latch left untinted.
 * Also acts as the inventory renderer, because the vanilla chest item renderer always draws a plain vanilla chest.
 */
@SideOnly(Side.CLIENT)
public class TileEntityMDBlockColoredChestRenderer extends TileEntitySpecialRenderer
    implements ISimpleBlockRenderingHandler {

    private final ModelChest modelSingle = new ModelChest();
    private final ModelChest modelDouble = new ModelLargeChest();
    private final ResourceLocation textureSingle = TintedTextures.modelTexture("chest/normal");
    private final ResourceLocation textureDouble = TintedTextures.modelTexture("chest/double");

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        if (!(tile instanceof TileEntityMDBlockColoredChest chest)
            || !(chest.getBlockType() instanceof MDBlockColoredChest block)) {
            return;
        }
        chest.checkForAdjacentChests();
        // The half at the lower coordinate draws the whole double chest.
        if (chest.adjacentChestXNeg != null || chest.adjacentChestZNeg != null) {
            return;
        }

        boolean isDouble = chest.adjacentChestXPos != null || chest.adjacentChestZPos != null;
        this.bindTexture(isDouble ? this.textureDouble : this.textureSingle);
        float lid = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * partialTicks;
        this.renderModel(
            isDouble ? this.modelDouble : this.modelSingle,
            block.getRenderColor(chest.getBlockMetadata()),
            chest.getFacing(),
            chest.adjacentChestXPos != null,
            chest.adjacentChestZPos != null,
            lid,
            x,
            y,
            z);
    }

    /** Same transforms as the vanilla chest renderer. */
    private void renderModel(ModelChest model, int color, int facing, boolean partnerXPos, boolean partnerZPos,
        float lid, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

        int angle = switch (facing) {
            case 2 -> 180;
            case 4 -> 90;
            case 5 -> -90;
            default -> 0;
        };
        if (facing == 2 && partnerXPos) {
            GL11.glTranslatef(1.0F, 0.0F, 0.0F);
        }
        if (facing == 5 && partnerZPos) {
            GL11.glTranslatef(0.0F, 0.0F, -1.0F);
        }
        GL11.glRotatef((float) angle, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        lid = 1.0F - lid;
        lid = 1.0F - lid * lid * lid;
        model.chestLid.rotateAngleX = -(lid * (float) Math.PI / 2.0F);
        model.chestKnob.rotateAngleX = model.chestLid.rotateAngleX;

        // Same parts as ModelChest.renderAll, with the wood tinted and the latch in its natural color.
        GL11.glColor4f((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, 1.0F);
        model.chestLid.render(0.0625F);
        model.chestBelow.render(0.0625F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        model.chestKnob.render(0.0625F);

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (!(block instanceof MDBlockColoredChest chest)) {
            return;
        }
        // Matches what RenderBlocks does before drawing a vanilla chest item.
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(this.textureSingle);
        this.renderModel(this.modelSingle, chest.getRenderColor(metadata), 3, false, false, 0.0F, 0.0D, 0.0D, 0.0D);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        // Nothing to draw in the chunk mesh; the tile entity renderer draws the chest.
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return RenderIds.chest;
    }
}
