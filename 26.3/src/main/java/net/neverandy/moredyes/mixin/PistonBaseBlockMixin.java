package net.neverandy.moredyes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.neverandy.moredyes.block.DyedPistonBaseBlock;

/**
 * Vanilla pistons always push out {@code minecraft:piston_head}, and only treat {@code minecraft:piston} and
 * {@code minecraft:sticky_piston} as pistons when deciding what can be pushed and pulled. This makes dyed pistons
 * push out their own head and count as pistons too.
 */
@Mixin(PistonBaseBlock.class)
abstract class PistonBaseBlockMixin {

    @ModifyExpressionValue(
        method = "moveBlocks",
        at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;PISTON_HEAD:Lnet/minecraft/world/level/block/Block;"))
    private Block moredyes$dyedHead(Block head) {
        return (Object) this instanceof DyedPistonBaseBlock dyed ? dyed.head() : head;
    }

    @WrapOperation(
        method = "isPushable",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
    private static boolean moredyes$pushableDyedPiston(BlockState state, Object block, Operation<Boolean> original) {
        return isPiston(state, block) || original.call(state, block);
    }

    @WrapOperation(
        method = "triggerEvent",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
    private boolean moredyes$pulledDyedPiston(BlockState state, Object block, Operation<Boolean> original) {
        return isPiston(state, block) || original.call(state, block);
    }

    /** Whether the state is a dyed piston and the block is the vanilla piston it acts as. */
    private static boolean isPiston(BlockState state, Object block) {
        return state.getBlock() instanceof DyedPistonBaseBlock dyed
            && (block == Blocks.PISTON && !dyed.isSticky() || block == Blocks.STICKY_PISTON && dyed.isSticky());
    }
}
