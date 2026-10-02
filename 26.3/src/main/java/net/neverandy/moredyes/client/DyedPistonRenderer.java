package net.neverandy.moredyes.client;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jspecify.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.PistonHeadRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.neverandy.moredyes.block.DyedPistonBaseBlock;
import net.neverandy.moredyes.block.DyedPistonHeadBlock;

/**
 * Replaces vanilla's renderer for blocks being moved by a piston. It is the vanilla PistonHeadRenderer, except that
 * a retracting dyed piston draws its own head instead of {@code minecraft:piston_head}, and a dyed head that is still
 * close to its piston is drawn short, like the vanilla head.
 */
public class DyedPistonRenderer implements BlockEntityRenderer<PistonMovingBlockEntity, PistonHeadRenderState> {

    @Override
    public PistonHeadRenderState createRenderState() {
        return new PistonHeadRenderState();
    }

    @Override
    public void extractRenderState(PistonMovingBlockEntity entity, PistonHeadRenderState state, float partialTicks,
        Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTicks, cameraPosition, breakProgress);
        state.xOffset = entity.getXOff(partialTicks);
        state.yOffset = entity.getYOff(partialTicks);
        state.zOffset = entity.getZOff(partialTicks);
        state.block = null;
        state.base = null;
        BlockState moved = entity.getMovedState();
        if (!(entity.getLevel() instanceof ClientLevel level) || moved.isAir()) {
            return;
        }
        BlockPos pos = entity.getBlockPos().relative(entity.getMovementDirection().getOpposite());
        Holder<Biome> biome = level.getBiome(pos);
        float progress = entity.getProgress(partialTicks);
        if ((moved.is(Blocks.PISTON_HEAD) || moved.getBlock() instanceof DyedPistonHeadBlock) && progress <= 4.0F) {
            moved = moved.setValue(PistonHeadBlock.SHORT, progress <= 0.5F);
            state.block = movingBlock(pos, moved, biome, level);
        } else if (entity.isSourcePiston() && !entity.isExtending()) {
            boolean sticky = moved.getBlock() instanceof DyedPistonBaseBlock dyed ? dyed.isSticky()
                : moved.is(Blocks.STICKY_PISTON);
            Block headBlock = moved.getBlock() instanceof DyedPistonBaseBlock dyed ? dyed.head() : Blocks.PISTON_HEAD;
            BlockState head = headBlock.defaultBlockState()
                .setValue(PistonHeadBlock.TYPE, sticky ? PistonType.STICKY : PistonType.DEFAULT)
                .setValue(PistonHeadBlock.FACING, moved.getValue(PistonBaseBlock.FACING))
                .setValue(PistonHeadBlock.SHORT, progress >= 0.5F);
            state.block = movingBlock(pos, head, biome, level);
            BlockPos basePos = pos.relative(entity.getMovementDirection());
            state.base = movingBlock(basePos, moved.setValue(PistonBaseBlock.EXTENDED, true), biome, level);
        } else {
            state.block = movingBlock(pos, moved, biome, level);
        }
    }

    @Override
    public void submit(PistonHeadRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
        CameraRenderState camera) {
        if (state.block != null) {
            poseStack.pushPose();
            poseStack.translate(state.xOffset, state.yOffset, state.zOffset);
            collector.submitMovingBlock(poseStack, state.block, 0);
            poseStack.popPose();
            if (state.base != null) {
                collector.submitMovingBlock(poseStack, state.base, 0);
            }
        }
    }

    private static MovingBlockRenderState movingBlock(BlockPos pos, BlockState blockState, Holder<Biome> biome,
        ClientLevel level) {
        MovingBlockRenderState moving = new MovingBlockRenderState();
        moving.randomSeedPos = pos;
        moving.blockPos = pos;
        moving.blockState = blockState;
        moving.biome = biome;
        moving.cardinalLighting = level.cardinalLighting();
        moving.lightEngine = level.getLightEngine();
        return moving;
    }

    @Override
    public int getViewDistance() {
        return 68;
    }

    @Override
    public AABB getRenderBoundingBox(PistonMovingBlockEntity entity) {
        return AABB.INFINITE;
    }
}
