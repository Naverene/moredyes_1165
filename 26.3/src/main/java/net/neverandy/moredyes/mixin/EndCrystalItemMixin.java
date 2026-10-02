package net.neverandy.moredyes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.world.item.EndCrystalItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.neverandy.moredyes.block.Kind;
import net.neverandy.moredyes.registry.ModBlocks;

/** Lets end crystals be placed on dyed obsidian, as they can on vanilla obsidian. */
@Mixin(EndCrystalItem.class)
abstract class EndCrystalItemMixin {

    @WrapOperation(
        method = "useOn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
    private boolean moredyes$dyedObsidian(BlockState state, Object block, Operation<Boolean> original) {
        return original.call(state, block) || block == Blocks.OBSIDIAN && ModBlocks.isKind(state, Kind.OBSIDIAN);
    }
}
