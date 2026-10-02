package net.neverandy.moredyes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.PistonHeadRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraftforge.client.ForgeHooksClient;
import net.neverandy.moredyes.block.BlockPiston;
import net.neverandy.moredyes.block.BlockPistonHead;

/**
 * Draws blocks that pistons are moving. The vanilla renderer always draws the vanilla head while a piston retracts
 * and only shortens the arm of the vanilla head, so a dyed piston gets the same drawing with its own dyed head.
 * Everything else is drawn by the vanilla renderer.
 */
public class DyedPistonRenderer extends PistonHeadRenderer
{
    public DyedPistonRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(PistonMovingBlockEntity piston, float partialTicks, PoseStack pose, MultiBufferSource buffer, int light, int overlay)
    {
        Level level = piston.getLevel();
        BlockState state = piston.getMovedState();
        if (level == null || !(state.getBlock() instanceof BlockPiston || state.getBlock() instanceof BlockPistonHead))
        {
            super.render(piston, partialTicks, pose, buffer, light, overlay);
            return;
        }

        BlockPos pos = piston.getBlockPos().relative(piston.getMovementDirection().getOpposite());
        float progress = piston.getProgress(partialTicks);
        ModelBlockRenderer.enableCaching();
        pose.pushPose();
        pose.translate(piston.getXOff(partialTicks), piston.getYOff(partialTicks), piston.getZOff(partialTicks));
        if (state.getBlock() instanceof BlockPistonHead && progress <= 4.0F)
        {
            draw(pos, state.setValue(PistonHeadBlock.SHORT, progress <= 0.5F), pose, buffer, level, false, overlay);
        }
        else if (piston.isSourcePiston() && !piston.isExtending() && state.getBlock() instanceof BlockPiston base)
        {
            BlockState head = base.getHead().defaultBlockState()
                    .setValue(PistonHeadBlock.TYPE, base.isSticky() ? PistonType.STICKY : PistonType.DEFAULT)
                    .setValue(PistonHeadBlock.FACING, state.getValue(PistonBaseBlock.FACING))
                    .setValue(PistonHeadBlock.SHORT, progress >= 0.5F);
            draw(pos, head, pose, buffer, level, false, overlay);
            pose.popPose();
            pose.pushPose();
            draw(pos.relative(piston.getMovementDirection()), state.setValue(PistonBaseBlock.EXTENDED, true), pose, buffer, level, true, overlay);
        }
        else
        {
            draw(pos, state, pose, buffer, level, false, overlay);
        }
        pose.popPose();
        ModelBlockRenderer.clearCache();
    }

    private static void draw(BlockPos pos, BlockState state, PoseStack pose, MultiBufferSource buffer, Level level, boolean checkSides, int overlay)
    {
        ForgeHooksClient.renderPistonMovedBlocks(pos, state, pose, buffer, level, checkSides, overlay, Minecraft.getInstance().getBlockRenderer());
    }
}
