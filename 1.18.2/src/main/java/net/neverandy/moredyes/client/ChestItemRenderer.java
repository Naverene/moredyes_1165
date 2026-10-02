package net.neverandy.moredyes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

/** Draws a dyed chest item as a small tinted chest, like vanilla does for its chest item. */
public class ChestItemRenderer extends BlockEntityWithoutLevelRenderer
{
    public ChestItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet models)
    {
        super(dispatcher, models);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transform, PoseStack matrix, MultiBufferSource buffer, int light, int overlay)
    {
        if (stack.getItem() instanceof BlockItem)
        {
            ChestRenderer.renderItem(((BlockItem) stack.getItem()).getBlock(), matrix, buffer, light, overlay);
        }
    }
}
