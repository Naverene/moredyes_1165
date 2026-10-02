package net.neverandy.moredyes;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import net.neverandy.moredyes.reference.Reference;

import java.io.File;

public class ConfigHandler
{
    public static final ForgeConfigSpec SERVER_CONFIG;
    public static final ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue worldGenTree;
    public static ForgeConfigSpec.BooleanValue worldGenFlower;
    public static ForgeConfigSpec.DoubleValue sheepSpawnChance;
    private static ForgeConfigSpec.BooleanValue wallBlocks;

    static
    {
        ForgeConfigSpec.Builder server = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder client = new ForgeConfigSpec.Builder();

        worldGenFlower = client.comment("Allow MoreDyes flowers during world generation").define("generate_flowers", true);
        worldGenTree = client.comment("Allow MoreDyes trees during world generation").define("generate_trees", true);
        sheepSpawnChance = server.comment("Chance (0 to 1) that a sheep spawning in the world has a random MoreDyes color")
                .defineInRange("sheep_spawn_chance", 0.05D, 0.0D, 1.0D);
        // Read when blocks are registered, so it is in the file loaded at startup (moredyes-client.toml) on both sides.
        wallBlocks = client.comment("Add dyed walls (for the block types vanilla has walls for). They take about 1 GB of memory; turn this off to save it.",
                "A server and its players must use the same setting, and worlds built with walls lose them when it is off.")
                .define("wall_blocks", true);

        SERVER_CONFIG = server.build();
        CLIENT_CONFIG = client.build();
    }

    /**
     * Loads moredyes-client.toml right away, on servers too. The blocks are decided while the mod is constructed,
     * before Forge loads config files, and a dedicated server never loads client configs at all.
     */
    public static void loadEarly()
    {
        loadConfigFile(CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Reference.MOD_ID + "-client.toml").toString());
    }

    /** Whether dyed walls are registered. */
    public static boolean wallBlocks()
    {
        return wallBlocks.get();
    }

    private static void loadConfigFile(ForgeConfigSpec config, String path)
    {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}
