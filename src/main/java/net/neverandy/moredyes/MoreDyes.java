package net.neverandy.moredyes;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.entity.EntityType;
import net.neverandy.moredyes.client.DyedPistonRenderer;
import net.neverandy.moredyes.client.DyedSheepRenderer;
import net.neverandy.moredyes.network.ModNetwork;
import net.neverandy.moredyes.client.ChestRenderer;
import net.neverandy.moredyes.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neverandy.moredyes.block.BlockGlass;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.world.ModWorldGen;
import net.neverandy.moredyes.reference.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MOD_ID)
public class MoreDyes
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup tabTrees = new ItemGroup("trees")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(MDBlock.darkOakLogArray[78]);
        }
    };
    public static final ItemGroup tabPlants = new ItemGroup("plants")
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MDBlock.oakSaplingArray[56]);
        }
    };
    public static final ItemGroup tabDyes = new ItemGroup("dyes")
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MDItem.dye[99]);
        }
    };
    public static final ItemGroup tabBlocks = new ItemGroup("blocks")
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(MDBlock.brickArray[100]);
        }
    };
    public static final ItemGroup tabShapes = new ItemGroup("shapes")
    {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(DyedShapes.ALL.get(0).stairs[100]);
        }
    };

    public MoreDyes()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_CONFIG);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //RegistryHandler.init();
        MDBlock.initialize();
        MDItem.initialize();
        ModWorldGen.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModTileEntities.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.addListener(ModWorldGen::onBiomeLoading);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModNetwork.register();
        event.enqueueWork(ModWorldGen::setup);
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.CHEST.get(), ChestRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SHEEP, DyedSheepRenderer::new);
        // Replaces the vanilla renderer for blocks being moved by pistons, so dyed pistons move their own heads.
        ClientRegistry.bindTileEntityRenderer(TileEntityType.PISTON, DyedPistonRenderer::new);
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        for (BlockGlass block: MDBlock.glassArray)
        {
            event.enqueueWork(() -> RenderTypeLookup.setRenderLayer(block, RenderType.getCutout()));
        }
        event.enqueueWork(() ->
        {
            for (int i = 0; i < ColorStrings.ALL.length; i++)
            {
                RenderTypeLookup.setRenderLayer(MDBlock.glassPaneArray[i], RenderType.getCutoutMipped());
                // Foggy glass is half see-through, so it needs the translucent layer.
                RenderTypeLookup.setRenderLayer(MDBlock.glassFoggyArray[i], RenderType.getTranslucent());
                RenderTypeLookup.setRenderLayer(MDBlock.glassFoggyPaneArray[i], RenderType.getTranslucent());
            }
        });
        event.enqueueWork(() ->
        {
            for (int i = 0; i < ColorStrings.ALL.length; i++)
            {
                RenderTypeLookup.setRenderLayer(MDBlock.tulipArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.oakSaplingArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.birchSaplingArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.acaciaSaplingArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.darkOakSaplingArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.jungleSaplingArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.spruceSaplingArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.oakLeafArray[i], RenderType.getCutoutMipped());
                RenderTypeLookup.setRenderLayer(MDBlock.birchLeafArray[i], RenderType.getCutoutMipped());
                RenderTypeLookup.setRenderLayer(MDBlock.acaciaLeafArray[i], RenderType.getCutoutMipped());
                RenderTypeLookup.setRenderLayer(MDBlock.darkOakLeafArray[i], RenderType.getCutoutMipped());
                RenderTypeLookup.setRenderLayer(MDBlock.jungleLeafArray[i], RenderType.getCutoutMipped());
                RenderTypeLookup.setRenderLayer(MDBlock.spruceLafArray[i], RenderType.getCutoutMipped());
                // The slime on a sticky piston is a separate, untinted layer with see-through gaps.
                RenderTypeLookup.setRenderLayer(MDBlock.stickyPistonArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.pistonHeadArray[i], RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(MDBlock.iceArray[i], RenderType.getTranslucent());
                for (Block[] flowers : MDBlock.smallFlowerArrays)
                {
                    RenderTypeLookup.setRenderLayer(flowers[i], RenderType.getCutout());
                }
                for (Block[] flowers : MDBlock.tallFlowerArrays)
                {
                    RenderTypeLookup.setRenderLayer(flowers[i], RenderType.getCutout());
                }
            }
            for (DyedShapes shapes : DyedShapes.ALL)
            {
                if (shapes.layer == DyedShapes.Layer.SOLID)
                {
                    continue;
                }
                RenderType layer = shapes.layer == DyedShapes.Layer.CUTOUT ? RenderType.getCutout() : RenderType.getTranslucent();
                for (Block[] blocks : new Block[][]{shapes.slabs, shapes.stairs, shapes.walls})
                {
                    for (Block block : blocks)
                    {
                        RenderTypeLookup.setRenderLayer(block, layer);
                    }
                }
            }
        });

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(Reference.MOD_ID, "helloworld", () ->
        {
            LOGGER.info("Hello world from the MDK");
    
            

            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}