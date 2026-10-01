package net.neverandy.moredyes.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

/** Draws a dyed chest item as a small tinted chest, like vanilla does for its chest item. */
public class ChestItemRenderer extends ItemStackTileEntityRenderer
{
    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transform, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay)
    {
        if (stack.getItem() instanceof BlockItem)
        {
            ChestRenderer.renderItem(((BlockItem) stack.getItem()).getBlock(), matrix, buffer, light, overlay);
        }
    }
}
