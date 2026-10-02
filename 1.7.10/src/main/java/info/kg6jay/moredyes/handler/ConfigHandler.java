package info.kg6jay.moredyes.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import info.kg6jay.moredyes.reference.Reference;

public class ConfigHandler {

    public static Configuration config;
    public static boolean chisel_plank = true;
    public static boolean chisel_support = true;
    public static boolean worldgen_flower = true;
    public static boolean worldgen_tree = true;
    public static boolean worldgen_granite = true;
    public static boolean worldgen_diorite = true;
    public static boolean worldgen_andesite = true;
    public static boolean preventMobSpawning = true;
    public static double sheepSpawnChance = 0.05;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        chisel_support = config.getBoolean(
            "ChiselSupport_Overall",
            Configuration.CATEGORY_GENERAL,
            true,
            "Set to false to disable Chisel support entirely");
        chisel_plank = config.getBoolean(
            "ChiselSupport_Plank",
            Configuration.CATEGORY_GENERAL,
            true,
            "Set to false to disable Chisel support for dyed planks");
        worldgen_flower = config.getBoolean(
            "WorldGen_Flower",
            Configuration.CATEGORY_GENERAL,
            true,
            "Set to false to disable world gen of flowers");
        worldgen_tree = config.getBoolean(
            "WorldGen_Tree",
            Configuration.CATEGORY_GENERAL,
            true,
            "Set to false to disable world gen of dye trees");
        worldgen_granite = config.getBoolean(
            "WorldGen_Granite",
            Configuration.CATEGORY_GENERAL,
            true,
            "Set to false to disable world gen of granite. It is also skipped when another mod provides granite.");
        worldgen_andesite = config.getBoolean(
            "WorldGen_Andesite",
            Configuration.CATEGORY_GENERAL,
            true,
            "Set to false to disable world gen of andesite. It is also skipped when another mod provides andesite.");
        worldgen_diorite = config.getBoolean(
            "WorldGen_Diorite",
            Configuration.CATEGORY_GENERAL,
            true,
            "Set to false to disable world gen of diorite. It is also skipped when another mod provides diorite.");
        preventMobSpawning = config.getBoolean(
            "preventMobSpawning",
            Configuration.CATEGORY_GENERAL,
            true,
            "If true, mobs cannot spawn on any block from this mod. If false, the blocks follow the vanilla rules.");
        sheepSpawnChance = config
            .get(
                Configuration.CATEGORY_GENERAL,
                "sheepSpawnChance",
                0.05,
                "Chance, from 0 to 1, that a new sheep spawns in a random More Dyes shade. 0 turns it off.",
                0.0,
                1.0)
            .getDouble();
        // Replaced by preventMobSpawning.
        config.getCategory(Configuration.CATEGORY_GENERAL)
            .remove("mobSpawnOnBlock");
        config.getCategory(Configuration.CATEGORY_GENERAL)
            .remove("overrideDefaultMobSpawning");
        if (config.hasChanged()) {
            config.save();
        }
    }
}
