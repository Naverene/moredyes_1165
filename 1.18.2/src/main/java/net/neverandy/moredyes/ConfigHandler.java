package net.neverandy.moredyes;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.io.File;


@Mod.EventBusSubscriber
public class ConfigHandler
{
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue worldGenTree;
    public static ForgeConfigSpec.BooleanValue worldGenFlower;
    public static ForgeConfigSpec.DoubleValue sheepSpawnChance;
    public static ForgeConfigSpec.BooleanValue wallBlocks;

    static
    {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        worldGen(SERVER_BUILDER, CLIENT_BUILDER);
        // Read when blocks are registered, so it is in the file loaded at startup (moredyes-client.toml) on both sides.
        wallBlocks = CLIENT_BUILDER.comment("Add dyed walls (for the block types vanilla has walls for). They take about 1 GB of memory; turn this off to save it.",
                "A server and its players must use the same setting, and worlds built with walls lose them when it is off.")
                .define("wall_blocks", true);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent)
    {

    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent)
    {

    }

    public static void loadConfigFile(ForgeConfigSpec config, String path){
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    private static void worldGen(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client){
        worldGenFlower = client.comment("Allow MoreDyes flowers during world generation").define("generate_flowers", true);
        worldGenTree = client.comment("Allow MoreDyes trees during world generation").define("generate_trees", true);
        sheepSpawnChance = server.comment("Chance (0 to 1) that a sheep spawning in the world has a random MoreDyes color")
                .defineInRange("sheep_spawn_chance", 0.05D, 0.0D, 1.0D);
    }
}
