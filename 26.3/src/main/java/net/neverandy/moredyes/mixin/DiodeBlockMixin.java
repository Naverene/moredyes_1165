package net.neverandy.moredyes.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.DiodeBlock;

import net.neverandy.moredyes.block.Kind;
import net.neverandy.moredyes.registry.ModBlocks;

/**
 * A block of redstone next to the side of a repeater or comparator powers it at full strength, which vanilla checks by
 * comparing with {@code Blocks.REDSTONE_BLOCK}. This makes the dyed blocks of redstone do the same.
 */
@Mixin(DiodeBlock.class)
abstract class DiodeBlockMixin {

    @WrapOperation(
        method = "getAlternateSignal",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/SignalGetter;getControlInputSignal(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)I"))
    private int moredyes$dyedRedstoneBlock(SignalGetter level, BlockPos pos, Direction direction, boolean onlyDiodes,
        Operation<Integer> original) {
        if (!onlyDiodes && ModBlocks.isKind(level.getBlockState(pos), Kind.REDSTONE_BLOCK)) {
            return 15;
        }
        return original.call(level, pos, direction, onlyDiodes);
    }
}
