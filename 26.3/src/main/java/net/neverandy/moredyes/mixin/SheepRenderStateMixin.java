package net.neverandy.moredyes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.entity.state.SheepRenderState;

import net.neverandy.moredyes.client.MoreDyesClient;
import net.neverandy.moredyes.client.Tints;
import net.neverandy.moredyes.color.MixColor;

/** Draws the wool of a sheep with a More Dyes color in that color, unless it is a rainbow "jeb_" sheep. */
@Mixin(SheepRenderState.class)
abstract class SheepRenderStateMixin {

    @Inject(method = "getWoolColor", at = @At("HEAD"), cancellable = true)
    private void moredyes$dyedWool(CallbackInfoReturnable<Integer> cir) {
        SheepRenderState state = (SheepRenderState) (Object) this;
        MixColor color = state.getRenderData(MoreDyesClient.SHEEP_COLOR);
        if (color != null && !state.isJebSheep) {
            cir.setReturnValue(Tints.argb(color));
        }
    }
}
