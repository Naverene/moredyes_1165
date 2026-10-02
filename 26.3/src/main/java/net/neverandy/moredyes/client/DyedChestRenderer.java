package net.neverandy.moredyes.client;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jspecify.annotations.Nullable;

import net.minecraft.client.model.object.chest.ChestModel;
import net.minecraft.client.renderer.MultiblockChestResources;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.DyedChestBlockEntity;

/**
 * Draws dyed chests: the vanilla chest model with a grey texture ({@code textures/entity/chest/normal*.png} in this
 * mod), tinted with the chest's color. It follows the vanilla ChestRenderer, which has no way to pass a tint.
 */
public class DyedChestRenderer implements BlockEntityRenderer<DyedChestBlockEntity, DyedChestRenderer.State> {

    /** The grey chest textures, on the vanilla chest atlas. */
    public static final MultiblockChestResources<SpriteId> SPRITES = new MultiblockChestResources<>(
        Identifier.fromNamespaceAndPath(MoreDyes.MOD_ID, "normal"),
        Identifier.fromNamespaceAndPath(MoreDyes.MOD_ID, "normal_left"),
        Identifier.fromNamespaceAndPath(MoreDyes.MOD_ID, "normal_right")).map(Sheets.CHEST_MAPPER::apply);

    private final SpriteGetter sprites;
    private final MultiblockChestResources<ChestModel> models;

    public DyedChestRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        this.models = ChestRenderer.LAYERS.map(layer -> new ChestModel(context.bakeLayer(layer)));
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(DyedChestBlockEntity chest, State state, float partialTicks, Vec3 cameraPosition,
        ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(chest, state, partialTicks, cameraPosition, breakProgress);
        BlockState blockState = chest.getBlockState();
        state.type = blockState.hasProperty(ChestBlock.TYPE) ? blockState.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        state.facing = blockState.getValue(ChestBlock.FACING);
        state.tint = Tints.blockTint(blockState.getBlock());
        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combined;
        if (chest.getLevel() != null && blockState.getBlock() instanceof ChestBlock block) {
            combined = block.combine(blockState, chest.getLevel(), chest.getBlockPos(), true);
        } else {
            combined = DoubleBlockCombiner.Combiner::acceptNone;
        }
        state.open = combined.apply(ChestBlock.opennessCombiner(chest)).get(partialTicks);
        if (state.type != ChestType.SINGLE) {
            state.lightCoords = combined.apply(new BrightnessCombiner<>()).applyAsInt(state.lightCoords);
        }
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.mulPose(ChestRenderer.modelTransformation(state.facing));
        float open = 1.0F - state.open;
        open = 1.0F - open * open * open;
        SpriteId sprite = SPRITES.select(state.type);
        ChestModel model = models.select(state.type);
        collector.submitModel(model, open, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, state.tint, sprite,
            sprites, 0);
        if (state.breakProgress != null) {
            collector.order(1).submitCrumblingOverlay(model, open, poseStack, sprite.renderType(model.renderType()),
                state.lightCoords, OverlayTexture.NO_OVERLAY, -1, state.breakProgress);
        }
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(DyedChestBlockEntity chest) {
        return AABB.encapsulatingFullBlocks(chest.getBlockPos().offset(-1, 0, -1), chest.getBlockPos().offset(1, 1, 1));
    }

    public static class State extends ChestRenderState {
        public int tint = -1;
    }
}
