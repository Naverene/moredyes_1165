package net.neverandy.moredyes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.neverandy.moredyes.block.DyedPistonHeadBlock;

/** A vanilla piston head only stays on a vanilla piston; a dyed head stays on the dyed piston of its color. */
@Mixin(PistonHeadBlock.class)
abstract class PistonHeadBlockMixin {

    @ModifyExpressionValue(
        method = "isFittingBase",
        at = {
            @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;PISTON:Lnet/minecraft/world/level/block/Block;"),
            @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;STICKY_PISTON:Lnet/minecraft/world/level/block/Block;") })
    private Block moredyes$dyedBase(Block base, @Local(argsOnly = true, ordinal = 0) BlockState armState) {
        return (Object) this instanceof DyedPistonHeadBlock dyed ? dyed.base(armState.getValue(PistonHeadBlock.TYPE)) : base;
    }
}
