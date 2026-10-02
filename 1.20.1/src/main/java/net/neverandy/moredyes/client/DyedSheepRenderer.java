package net.neverandy.moredyes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.neverandy.moredyes.entity.DyedSheep;
import net.neverandy.moredyes.reference.ColorStrings;

/** The vanilla sheep renderer, with wool that can also take a MoreDyes color (see entity/DyedSheep). */
public class DyedSheepRenderer extends SheepRenderer
{
    public DyedSheepRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        layers.removeIf(layer -> layer instanceof SheepFurLayer);
        addLayer(new Wool(this, context.getModelSet()));
    }

    private static class Wool extends SheepFurLayer
    {
        private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
        private final SheepFurModel<Sheep> woolModel;

        Wool(RenderLayerParent<Sheep, SheepModel<Sheep>> renderer, EntityModelSet models)
        {
            super(renderer, models);
            woolModel = new SheepFurModel<>(models.bakeLayer(ModelLayers.SHEEP_FUR));
        }

        @Override
        public void render(PoseStack pose, MultiBufferSource buffer, int light, Sheep sheep, float limbSwing, float limbSwingAmount,
                           float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
        {
            int color = DyedSheep.getColor(sheep);
            boolean jeb = sheep.hasCustomName() && "jeb_".equals(sheep.getName().getString());
            if (color < 0 || jeb || sheep.isInvisible())
            {
                super.render(pose, buffer, light, sheep, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
                return;
            }
            if (sheep.isSheared())
            {
                return;
            }
            int rgb = ColorHandlers.vivid(Integer.parseInt(ColorStrings.ALL[color], 16));
            coloredCutoutModelCopyLayerRender(getParentModel(), woolModel, TEXTURE, pose, buffer, light, sheep, limbSwing, limbSwingAmount,
                    ageInTicks, netHeadYaw, headPitch, partialTicks,
                    (rgb >> 16 & 255) / 255.0F, (rgb >> 8 & 255) / 255.0F, (rgb & 255) / 255.0F);
        }
    }
}
