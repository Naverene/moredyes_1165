package net.neverandy.moredyes;

import net.minecraft.world.entity.EntityType;
import net.neverandy.moredyes.client.DyedPistonRenderer;
import net.neverandy.moredyes.client.DyedSheepRenderer;
import net.neverandy.moredyes.network.ModNetwork;
import net.neverandy.moredyes.client.ChestRenderer;
import net.neverandy.moredyes.tileentity.ModTileEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.item.ItemStack;
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
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neverandy.moredyes.block.BlockGlass;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.data.condition.WallsEnabledCondition;
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
    public static final CreativeModeTab tabTrees = new CreativeModeTab("trees")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(MDBlock.darkOakLogArray[78]);
        }
    };
    public static final CreativeModeTab tabPlants = new CreativeModeTab("plants")
    {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MDBlock.oakSaplingArray[56]);
        }
    };
    public static final CreativeModeTab tabDyes = new CreativeModeTab("dyes")
    {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MDItem.dye[99]);
        }
    };
    public static final CreativeModeTab tabBlocks = new CreativeModeTab("blocks")
    {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MDBlock.brickArray[100]);
        }
    };
    public static final CreativeModeTab tabShapes = new CreativeModeTab("shapes")
    {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(DyedShapes.ALL.get(0).stairs[100]);
        }
    };

    public MoreDyes()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_CONFIG);
        CraftingHelper.register(WallsEnabledCondition.SERIALIZER);

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
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        for (BlockGlass block: MDBlock.glassArray)
        {
            event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout()));
        }
        event.enqueueWork(() ->
        {
            for (int i = 0; i < ColorStrings.ALL.length; i++)
            {
                ItemBlockRenderTypes.setRenderLayer(MDBlock.glassPaneArray[i], RenderType.cutoutMipped());
                // Foggy glass is half see-through, so it needs the translucent layer.
                ItemBlockRenderTypes.setRenderLayer(MDBlock.glassFoggyArray[i], RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.glassFoggyPaneArray[i], RenderType.translucent());
            }
        });
        event.enqueueWork(() ->
        {
            for (int i = 0; i < ColorStrings.ALL.length; i++)
            {
                ItemBlockRenderTypes.setRenderLayer(MDBlock.tulipArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.oakSaplingArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.birchSaplingArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.acaciaSaplingArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.darkOakSaplingArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.jungleSaplingArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.spruceSaplingArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.oakLeafArray[i], RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.birchLeafArray[i], RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.acaciaLeafArray[i], RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.darkOakLeafArray[i], RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.jungleLeafArray[i], RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.spruceLafArray[i], RenderType.cutoutMipped());
                // The slime on a sticky piston is a separate, untinted layer with see-through gaps.
                ItemBlockRenderTypes.setRenderLayer(MDBlock.stickyPistonArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.pistonHeadArray[i], RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(MDBlock.iceArray[i], RenderType.translucent());
                for (Block[] flowers : MDBlock.smallFlowerArrays)
                {
                    ItemBlockRenderTypes.setRenderLayer(flowers[i], RenderType.cutout());
                }
                for (Block[] flowers : MDBlock.tallFlowerArrays)
                {
                    ItemBlockRenderTypes.setRenderLayer(flowers[i], RenderType.cutout());
                }
            }
            for (DyedShapes shapes : DyedShapes.ALL)
            {
                if (shapes.layer == DyedShapes.Layer.SOLID)
                {
                    continue;
                }
                RenderType layer = shapes.layer == DyedShapes.Layer.CUTOUT ? RenderType.cutout() : RenderType.translucent();
                for (Block[] blocks : new Block[][]{shapes.slabs, shapes.stairs, shapes.walls})
                {
                    for (Block block : blocks)
                    {
                        ItemBlockRenderTypes.setRenderLayer(block, layer);
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
    public void onServerStarting(ServerStartingEvent event)
    {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}