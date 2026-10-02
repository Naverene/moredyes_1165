package net.neverandy.moredyes.client;

import java.util.List;

import com.google.common.reflect.TypeToken;

import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.state.SheepRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.client.color.block.BlockTintSources;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.entity.DyedSheep;
import net.neverandy.moredyes.registry.ModBlockEntities;
import net.neverandy.moredyes.registry.ModBlocks;

@Mod(value = MoreDyes.MOD_ID, dist = Dist.CLIENT)
public class MoreDyesClient {

    /** The More Dyes color of a sheep being drawn, if it has one. Read by SheepRenderStateMixin. */
    public static final ContextKey<MixColor> SHEEP_COLOR = new ContextKey<>(
        Identifier.fromNamespaceAndPath(MoreDyes.MOD_ID, "sheep_color"));

    public MoreDyesClient(IEventBus modBus) {
        modBus.addListener(MoreDyesClient::blockTints);
        modBus.addListener(MoreDyesClient::itemTints);
        modBus.addListener(MoreDyesClient::specialRenderers);
        modBus.addListener(MoreDyesClient::renderers);
        modBus.addListener(MoreDyesClient::renderStateModifiers);
    }

    private static void blockTints(RegisterColorHandlersEvent.BlockTintSources event) {
        for (var entry : ModBlocks.BLOCKS.getEntries()) {
            Block block = entry.get();
            int tint = Tints.blockTint(block);
            if (tint != -1) {
                event.register(List.of(BlockTintSources.constant(tint)), block);
            }
        }
    }

    private static void itemTints(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(Identifier.fromNamespaceAndPath(MoreDyes.MOD_ID, "dye_color"), Tints.DyeColor.MAP_CODEC);
    }

    private static void specialRenderers(RegisterSpecialModelRendererEvent event) {
        event.register(Identifier.fromNamespaceAndPath(MoreDyes.MOD_ID, "dyed_chest"),
            DyedChestSpecialRenderer.Unbaked.MAP_CODEC);
    }

    private static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.CHEST.get(), DyedChestRenderer::new);
        // Replaces vanilla's renderer for moving pistons, so dyed pistons draw their own head while retracting.
        event.registerBlockEntityRenderer(BlockEntityTypes.PISTON, context -> new DyedPistonRenderer());
    }

    private static void renderStateModifiers(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(new TypeToken<SheepRenderer>() {}, (Sheep sheep, SheepRenderState state) ->
            state.setRenderData(SHEEP_COLOR, DyedSheep.getColor(sheep).orElse(null)));
    }
}
