package net.neverandy.moredyes.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.tileentity.ModTileEntities;

/** Client-only setup: which blocks are see-through, and the renderers for chests, pistons and sheep. */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup
{
    private ClientSetup() {}

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public static void setup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            for (int i = 0; i < MDBlock.COLORS; i++)
            {
                layer(RenderType.cutout(), MDBlock.glassArray[i], MDBlock.tulipArray[i],
                        MDBlock.oakSaplingArray[i], MDBlock.birchSaplingArray[i], MDBlock.acaciaSaplingArray[i],
                        MDBlock.darkOakSaplingArray[i], MDBlock.jungleSaplingArray[i], MDBlock.spruceSaplingArray[i],
                        // The slime on a sticky piston is a separate, untinted layer with see-through gaps.
                        MDBlock.stickyPistonArray[i], MDBlock.pistonHeadArray[i]);
                layer(RenderType.cutoutMipped(), MDBlock.glassPaneArray[i],
                        MDBlock.oakLeafArray[i], MDBlock.birchLeafArray[i], MDBlock.acaciaLeafArray[i],
                        MDBlock.darkOakLeafArray[i], MDBlock.jungleLeafArray[i], MDBlock.spruceLeafArray[i]);
                // Foggy glass is half see-through, so it needs the translucent layer.
                layer(RenderType.translucent(), MDBlock.glassFoggyArray[i], MDBlock.glassFoggyPaneArray[i], MDBlock.iceArray[i]);
                for (Block[] flowers : MDBlock.smallFlowerArrays)
                {
                    layer(RenderType.cutout(), flowers[i]);
                }
                for (Block[] flowers : MDBlock.tallFlowerArrays)
                {
                    layer(RenderType.cutout(), flowers[i]);
                }
            }
            for (DyedShapes shapes : DyedShapes.ALL)
            {
                if (shapes.layer == DyedShapes.Layer.SOLID)
                {
                    continue;
                }
                RenderType layer = shapes.layer == DyedShapes.Layer.CUTOUT ? RenderType.cutout() : RenderType.translucent();
                layer(layer, shapes.slabs);
                layer(layer, shapes.stairs);
                layer(layer, shapes.walls);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private static void layer(RenderType type, Block... blocks)
    {
        for (Block block : blocks)
        {
            ItemBlockRenderTypes.setRenderLayer(block, type);
        }
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModTileEntities.CHEST.get(), ChestRenderer::new);
        // Replaces the vanilla renderer for blocks being moved by pistons, so dyed pistons move their own heads.
        event.registerBlockEntityRenderer(BlockEntityType.PISTON, DyedPistonRenderer::new);
        event.registerEntityRenderer(EntityType.SHEEP, DyedSheepRenderer::new);
    }
}
