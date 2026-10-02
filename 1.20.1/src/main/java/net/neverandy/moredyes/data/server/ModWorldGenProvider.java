package net.neverandy.moredyes.data.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.Reference;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The world generation data for world/ModWorldGen: a dye tree about every fourth chunk and a patch of dyed tulips
 * about every other chunk, in the overworld biomes the 1.16.5 port used (not oceans, rivers, beaches, deserts,
 * badlands, icy biomes, mushroom fields or caves).
 */
public class ModWorldGenProvider implements DataProvider
{
    /** Where the trees and flowers grow. */
    private static final String BIOMES = """
            {
              "type": "forge:and",
              "values": [
                "#minecraft:is_overworld",
                {
                  "type": "forge:not",
                  "value": {
                    "type": "forge:or",
                    "values": [
                      "#minecraft:is_ocean", "#minecraft:is_river", "#minecraft:is_beach", "#minecraft:is_badlands",
                      "#forge:is_desert", "#forge:is_mushroom", "#forge:is_underground",
                      ["minecraft:snowy_plains", "minecraft:ice_spikes", "minecraft:snowy_slopes", "minecraft:frozen_peaks", "minecraft:jagged_peaks"]
                    ]
                  }
                }
              ]
            }""";

    private final PackOutput output;

    public ModWorldGenProvider(PackOutput output)
    {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache)
    {
        List<CompletableFuture<?>> saved = new ArrayList<>();
        saved.add(save(cache, "worldgen/configured_feature/dye_trees", json("""
                {"type": "moredyes:dye_tree", "config": {}}""")));
        saved.add(save(cache, "worldgen/placed_feature/dye_trees", json("""
                {
                  "feature": "moredyes:dye_trees",
                  "placement": [
                    {"type": "moredyes:config", "option": "trees"},
                    {"type": "minecraft:rarity_filter", "chance": 4},
                    {"type": "minecraft:in_square"},
                    {"type": "minecraft:surface_water_depth_filter", "max_water_depth": 0},
                    {"type": "minecraft:heightmap", "heightmap": "OCEAN_FLOOR"},
                    {"type": "minecraft:biome"},
                    {"type": "minecraft:block_predicate_filter", "predicate": {"type": "minecraft:would_survive",
                      "state": {"Name": "minecraft:oak_sapling", "Properties": {"stage": "0"}}}}
                  ]
                }""")));

        JsonArray tulips = new JsonArray();
        for (Block tulip : MDBlock.tulipArray)
        {
            JsonObject entry = new JsonObject();
            JsonObject state = new JsonObject();
            state.addProperty("Name", ForgeRegistries.BLOCKS.getKey(tulip).toString());
            entry.add("data", state);
            entry.addProperty("weight", 1);
            tulips.add(entry);
        }
        JsonObject flowers = json("""
                {
                  "type": "minecraft:flower",
                  "config": {
                    "tries": 32, "xz_spread": 7, "y_spread": 3,
                    "feature": {
                      "feature": {"type": "minecraft:simple_block", "config": {"to_place": {"type": "minecraft:weighted_state_provider"}}},
                      "placement": [{"type": "minecraft:block_predicate_filter", "predicate": {"type": "minecraft:matching_blocks", "blocks": "minecraft:air"}}]
                    }
                  }
                }""");
        flowers.getAsJsonObject("config").getAsJsonObject("feature").getAsJsonObject("feature").getAsJsonObject("config")
                .getAsJsonObject("to_place").add("entries", tulips);
        saved.add(save(cache, "worldgen/configured_feature/tulips", flowers));
        saved.add(save(cache, "worldgen/placed_feature/tulips", json("""
                {
                  "feature": "moredyes:tulips",
                  "placement": [
                    {"type": "moredyes:config", "option": "flowers"},
                    {"type": "minecraft:rarity_filter", "chance": 2},
                    {"type": "minecraft:in_square"},
                    {"type": "minecraft:heightmap", "heightmap": "MOTION_BLOCKING"},
                    {"type": "minecraft:biome"}
                  ]
                }""")));

        for (String feature : new String[]{"dye_trees", "tulips"})
        {
            JsonObject modifier = new JsonObject();
            modifier.addProperty("type", "forge:add_features");
            modifier.add("biomes", json(BIOMES));
            modifier.addProperty("features", Reference.MOD_ID + ":" + feature);
            modifier.addProperty("step", "vegetal_decoration");
            saved.add(save(cache, "forge/biome_modifier/" + feature, modifier));
        }
        return CompletableFuture.allOf(saved.toArray(CompletableFuture[]::new));
    }

    private CompletableFuture<?> save(CachedOutput cache, String path, JsonElement json)
    {
        Path file = output.getOutputFolder().resolve("data/" + Reference.MOD_ID + "/" + path + ".json");
        return DataProvider.saveStable(cache, json, file);
    }

    private static JsonObject json(String text)
    {
        return JsonParser.parseString(text).getAsJsonObject();
    }

    @Override
    public String getName()
    {
        return "More Dyes world generation";
    }
}
