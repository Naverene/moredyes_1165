package net.neverandy.moredyes.client;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.tileentity.ModTileEntities;

/** Hooks up the renderers for dyed chests, dyed sheep and moving dyed pistons. */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientRenderers
{
    private ClientRenderers() {}

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModTileEntities.CHEST.get(), ChestRenderer::new);
        event.registerEntityRenderer(EntityType.SHEEP, DyedSheepRenderer::new);
        // Replaces the vanilla renderer for blocks being moved by pistons, so dyed pistons move their own heads.
        event.registerBlockEntityRenderer(BlockEntityType.PISTON, DyedPistonRenderer::new);
    }
}
