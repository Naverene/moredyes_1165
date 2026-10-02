package net.neverandy.moredyes;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neverandy.moredyes.block.Kind;
import net.neverandy.moredyes.color.MixColors;
import net.neverandy.moredyes.entity.DyedSheep;
import net.neverandy.moredyes.handler.CauldronWashing;
import net.neverandy.moredyes.registry.ModBlockEntities;
import net.neverandy.moredyes.registry.ModBlocks;
import net.neverandy.moredyes.registry.ModCreativeTabs;
import net.neverandy.moredyes.registry.ModItems;

@Mod(MoreDyes.MOD_ID)
public class MoreDyes {

    public static final String MOD_ID = "moredyes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreDyes(IEventBus modBus, ModContainer container) {
        ModBlocks.register(modBus);
        ModItems.register(modBus);
        ModBlockEntities.register(modBus);
        ModCreativeTabs.register(modBus);
        DyedSheep.register(modBus);
        modBus.addListener(CauldronWashing::register);
        modBus.addListener(MoreDyes::commonSetup);
        container.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Each kind burns like its vanilla block (see FireBlock.bootStrap).
            FireBlock fire = (FireBlock) Blocks.FIRE;
            for (Kind kind : Kind.values()) {
                int ignite = fire.getIgniteOdds(kind.vanilla().defaultBlockState());
                int burn = fire.getBurnOdds(kind.vanilla().defaultBlockState());
                if (ignite > 0 || burn > 0) {
                    ModBlocks.all(kind).forEach(block -> fire.setFlammable(block.get(), ignite, burn));
                }
            }
        });
        LOGGER.info("More Dyes registered {} colors of {} kinds of block", MixColors.ALL.size(), Kind.values().length);
    }
}
