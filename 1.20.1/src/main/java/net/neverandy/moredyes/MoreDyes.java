package net.neverandy.moredyes;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.data.condition.WallsEnabledCondition;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.item.MDTabs;
import net.neverandy.moredyes.network.ModNetwork;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.tileentity.ModTileEntities;
import net.neverandy.moredyes.world.ModWorldGen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MOD_ID)
public class MoreDyes
{
    public static final Logger LOGGER = LogManager.getLogger();

    public MoreDyes()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigHandler.SERVER_CONFIG);
        ConfigHandler.loadEarly();
        CraftingHelper.register(WallsEnabledCondition.SERIALIZER);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::setup);

        MDBlock.register(modBus);
        MDItem.register(modBus);
        MDTabs.register(modBus);
        ModTileEntities.register(modBus);
        ModWorldGen.register(modBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModNetwork.register();
    }
}
