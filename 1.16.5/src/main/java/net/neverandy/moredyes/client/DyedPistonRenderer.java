package net.neverandy.moredyes.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.PistonTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.neverandy.moredyes.block.BlockPiston;
import net.neverandy.moredyes.block.BlockPistonHead;

/**
 * Draws blocks that pistons are moving. The vanilla renderer always draws the vanilla head while a piston retracts
 * and only shortens the arm of the vanilla head, so a dyed piston gets the same drawing with its own dyed head.
 * Everything else is drawn by the vanilla renderer.
 */
public class DyedPistonRenderer extends PistonTileEntityRenderer
{
    public DyedPistonRenderer(TileEntityRendererDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(PistonTileEntity piston, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay)
    {
        World world = piston.getWorld();
        BlockState state = piston.getPistonState();
        if (world == null || !(state.getBlock() instanceof BlockPiston || state.getBlock() instanceof BlockPistonHead))
        {
            super.render(piston, partialTicks, matrix, buffer, light, overlay);
            return;
        }

        BlockPos pos = piston.getPos().offset(piston.getMotionDirection().getOpposite());
        float progress = piston.getProgress(partialTicks);
        BlockModelRenderer.enableCache();
        matrix.push();
        matrix.translate(piston.getOffsetX(partialTicks), piston.getOffsetY(partialTicks), piston.getOffsetZ(partialTicks));
        if (state.getBlock() instanceof BlockPistonHead && progress <= 4.0F)
        {
            draw(pos, state.with(PistonHeadBlock.SHORT, progress <= 0.5F), matrix, buffer, world, false, overlay);
        }
        else if (piston.shouldPistonHeadBeRendered() && !piston.isExtending() && state.getBlock() instanceof BlockPiston)
        {
            BlockPiston base = (BlockPiston) state.getBlock();
            BlockState head = base.getHead().getDefaultState()
                    .with(PistonHeadBlock.TYPE, base.isSticky() ? PistonType.STICKY : PistonType.DEFAULT)
                    .with(PistonHeadBlock.FACING, state.get(PistonBlock.FACING))
                    .with(PistonHeadBlock.SHORT, progress >= 0.5F);
            draw(pos, head, matrix, buffer, world, false, overlay);
            matrix.pop();
            matrix.push();
            draw(pos.offset(piston.getMotionDirection()), state.with(PistonBlock.EXTENDED, true), matrix, buffer, world, true, overlay);
        }
        else
        {
            draw(pos, state, matrix, buffer, world, false, overlay);
        }
        matrix.pop();
        BlockModelRenderer.disableCache();
    }

    private static void draw(BlockPos pos, BlockState state, MatrixStack matrix, IRenderTypeBuffer buffer, World world, boolean checkSides, int overlay)
    {
        ForgeHooksClient.renderPistonMovedBlocks(pos, state, matrix, buffer, world, checkSides, overlay,
                Minecraft.getInstance().getBlockRendererDispatcher());
    }
}
