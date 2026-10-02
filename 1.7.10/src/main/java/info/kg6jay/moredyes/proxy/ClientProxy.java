package info.kg6jay.moredyes.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.World;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import info.kg6jay.moredyes.block.RenderIds;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDBlockColoredChest;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDPiston;
import info.kg6jay.moredyes.client.LayeredBlockRenderer;
import info.kg6jay.moredyes.client.RenderColoredSheep;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.entity.SheepColor;
import info.kg6jay.moredyes.render.TileEntityMDBlockColoredChestRenderer;
import info.kg6jay.moredyes.render.TileEntityMDPistonRenderer;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        // Must be in place before the game loads its textures at the end of mod loading.
        TintedTextures.install();
    }

    @Override
    public void registerRenderThings() {
        RenderIds.chest = RenderingRegistry.getNextAvailableRenderId();
        TileEntityMDBlockColoredChestRenderer chestRenderer = new TileEntityMDBlockColoredChestRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMDBlockColoredChest.class, chestRenderer);
        RenderingRegistry.registerBlockHandler(chestRenderer);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMDPiston.class, new TileEntityMDPistonRenderer());

        RenderIds.layeredCube = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new LayeredBlockRenderer(RenderIds.layeredCube, false));
        RenderIds.layeredPlant = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new LayeredBlockRenderer(RenderIds.layeredPlant, true));

        // Draws sheep dyed with More Dyes in their shade; sheep without one look the same as in vanilla.
        RenderingRegistry.registerEntityRenderingHandler(EntitySheep.class, new RenderColoredSheep());
    }

    @Override
    public void setSheepColor(int entityId, int color) {
        World world = Minecraft.getMinecraft().theWorld;
        SheepColor sheepColor = world == null ? null : SheepColor.of(world.getEntityByID(entityId));
        if (sheepColor != null) {
            sheepColor.put(color);
        }
    }
}
