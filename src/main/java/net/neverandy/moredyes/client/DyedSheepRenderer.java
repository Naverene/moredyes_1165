package net.neverandy.moredyes.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.ResourceLocation;
import net.neverandy.moredyes.entity.DyedSheep;
import net.neverandy.moredyes.reference.ColorStrings;

/** The vanilla sheep renderer, with wool that can also take a MoreDyes color (see entity/DyedSheep). */
public class DyedSheepRenderer extends SheepRenderer
{
    public DyedSheepRenderer(EntityRendererManager manager)
    {
        super(manager);
        layerRenderers.removeIf(layer -> layer instanceof SheepWoolLayer);
        addLayer(new Wool(this));
    }

    private static class Wool extends SheepWoolLayer
    {
        private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
        private final SheepWoolModel<SheepEntity> woolModel = new SheepWoolModel<>();

        Wool(IEntityRenderer<SheepEntity, SheepModel<SheepEntity>> renderer)
        {
            super(renderer);
        }

        @Override
        public void render(MatrixStack matrix, IRenderTypeBuffer buffer, int light, SheepEntity sheep, float limbSwing, float limbSwingAmount,
                           float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
        {
            int color = DyedSheep.getColor(sheep);
            boolean jeb = sheep.hasCustomName() && "jeb_".equals(sheep.getName().getUnformattedComponentText());
            if (color < 0 || jeb)
            {
                super.render(matrix, buffer, light, sheep, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
                return;
            }
            if (sheep.getSheared() || sheep.isInvisible())
            {
                return;
            }
            int rgb = ColorHandlers.vivid(Integer.parseInt(ColorStrings.ALL[color], 16));
            renderCopyCutoutModel(getEntityModel(), woolModel, TEXTURE, matrix, buffer, light, sheep, limbSwing, limbSwingAmount,
                    ageInTicks, netHeadYaw, headPitch, partialTicks,
                    (rgb >> 16 & 255) / 255.0F, (rgb >> 8 & 255) / 255.0F, (rgb & 255) / 255.0F);
        }
    }
}
