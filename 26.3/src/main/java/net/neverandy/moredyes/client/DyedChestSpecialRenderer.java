package net.neverandy.moredyes.client;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.joml.Vector3fc;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.chest.ChestModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;

import net.neverandy.moredyes.color.MixColors;

/**
 * Draws a dyed chest in the inventory and in hand, like vanilla's {@code minecraft:chest} special model but tinted.
 * Its item model names it as {@code {"type": "moredyes:dyed_chest", "color": "<hex>"}}.
 */
public class DyedChestSpecialRenderer implements NoDataSpecialModelRenderer {

    private final SpriteGetter sprites;
    private final ChestModel model;
    private final SpriteId sprite;
    private final int tint;

    public DyedChestSpecialRenderer(SpriteGetter sprites, ChestModel model, SpriteId sprite, int tint) {
        this.sprites = sprites;
        this.model = model;
        this.sprite = sprite;
        this.tint = tint;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, int overlayCoords,
        boolean hasFoil, int outlineColor) {
        collector.submitModel(model, 0.0F, poseStack, lightCoords, overlayCoords, tint, sprite, sprites, outlineColor);
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        model.setupAnim(0.0F);
        model.root().getExtentsForGui(poseStack, output);
    }

    public record Unbaked(String color) implements NoDataSpecialModelRenderer.Unbaked {

        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i
            .group(Codec.STRING.fieldOf("color").forGetter(Unbaked::color))
            .apply(i, Unbaked::new));

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public DyedChestSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
            ChestModel model = new ChestModel(context.entityModelSet().bakeLayer(ModelLayers.CHEST));
            int tint = MixColors.byHex(color).map(Tints::argb).orElse(-1);
            return new DyedChestSpecialRenderer(context.sprites(), model, DyedChestRenderer.SPRITES.single(), tint);
        }
    }
}
