package net.neverandy.moredyes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/** Draws a dyed chest item as a small tinted chest, like vanilla does for its chest item. */
public class ChestItemRenderer extends BlockEntityWithoutLevelRenderer
{
    private static ChestItemRenderer instance;
    private ChestRenderer chest;

    private ChestItemRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public static ChestItemRenderer get()
    {
        if (instance == null)
        {
            instance = new ChestItemRenderer();
        }
        return instance;
    }

    /** The chest models come from code, not resource packs, so they don't need baking again when resources reload. */
    @Override
    public void onResourceManagerReload(ResourceManager resourceManager)
    {
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffer, int light, int overlay)
    {
        if (stack.getItem() instanceof BlockItem item)
        {
            if (chest == null)
            {
                chest = new ChestRenderer(Minecraft.getInstance().getEntityModels());
            }
            chest.renderItem(item.getBlock(), pose, buffer, light, overlay);
        }
    }
}
