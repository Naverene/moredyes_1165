package net.neverandy.moredyes.data.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.Reference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Makes the dyed blocks chiselable with Rechiseled, the chisel mod for 1.16.5 (Chisel itself stopped at 1.12.2).
 * Like the 1.7.10 Chisel support, every dyed shade joins the group of its vanilla block, so a chisel turns the
 * vanilla block into any shade and back. Where Rechiseled already has a group for the vanilla block, the shades are
 * appended to it (a file with the same id and "overwrite": false); otherwise a moredyes group is made that also
 * holds the vanilla block. These are plain data files, so nothing happens when Rechiseled isn't installed.
 */
public class ModChiselProvider implements DataProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final DataGenerator gen;
    private final Map<String, List<String>> groups = new LinkedHashMap<>();

    public ModChiselProvider(DataGenerator gen)
    {
        this.gen = gen;
    }

    private void registerGroups()
    {
        // Groups Rechiseled already has; its files already list the vanilla blocks.
        rechiseled("stone", MDBlock.stoneArray, MDBlock.stonebrickArray, MDBlock.stonebrickCrackedArray, MDBlock.stonebrickCarvedArray);
        rechiseled("cobblestone", MDBlock.cobbleArray);
        rechiseled("obsidian", MDBlock.obsidianArray);
        rechiseled("quartz_block", MDBlock.quartzArray);
        rechiseled("coal_block", MDBlock.coalArray);
        rechiseled("glowstone", MDBlock.glowstoneArray);
        rechiseled("lapis_block", MDBlock.lapisArray);
        rechiseled("redstone_block", MDBlock.redstoneArray);
        rechiseled("sandstone", MDBlock.sandstoneArray, MDBlock.sandstoneCarvedArray, MDBlock.sandstoneSmoothArray);
        rechiseled("andesite", MDBlock.andesiteArray);
        rechiseled("diorite", MDBlock.dioriteArray);
        rechiseled("oak_planks", MDBlock.oakPlankArray);
        rechiseled("birch_planks", MDBlock.birchPlankArray);
        rechiseled("spruce_planks", MDBlock.sprucePlankArray);
        rechiseled("jungle_planks", MDBlock.junglePlankArray);
        rechiseled("acacia_planks", MDBlock.acaciaPlankArray);
        rechiseled("dark_oak_planks", MDBlock.darkOakPlankArray);

        // Groups of our own, each starting with the vanilla block.
        own("wool", "white_wool", MDBlock.woolArray);
        own("soul_sand", "soul_sand", MDBlock.soulsandArray);
        own("terracotta", "terracotta", MDBlock.hardenedClayArray);
        own("clay", "clay", MDBlock.clayArray);
        own("bricks", "bricks", MDBlock.brickArray);
        own("sand", "sand", MDBlock.sandArray);
        own("glass", "glass", MDBlock.glassArray);
        own("concrete", "white_concrete", MDBlock.concreteArray);
        own("concrete_powder", "white_concrete_powder", MDBlock.concretePowderArray);
        own("glass_pane", "glass_pane", MDBlock.glassPaneArray);
        own("chest", "chest", MDBlock.chestArray);
        own("bookshelf", "bookshelf", MDBlock.bookshelfArray);
        own("piston", "piston", MDBlock.pistonArray);
        own("sticky_piston", "sticky_piston", MDBlock.stickyPistonArray);
    }

    private void rechiseled(String group, Block[]... dyed)
    {
        groups.put("rechiseled:" + group, names(new ArrayList<>(), dyed));
    }

    private void own(String group, String vanilla, Block[]... dyed)
    {
        List<String> entries = new ArrayList<>();
        entries.add("minecraft:" + vanilla);
        groups.put(Reference.MOD_ID + ":" + group, names(entries, dyed));
    }

    private static List<String> names(List<String> entries, Block[]... dyed)
    {
        for (Block[] blocks : dyed)
        {
            for (Block block : blocks)
            {
                entries.add(block.getRegistryName().toString());
            }
        }
        return entries;
    }

    @Override
    public void run(HashCache cache) throws IOException
    {
        groups.clear();
        registerGroups();
        for (Map.Entry<String, List<String>> group : groups.entrySet())
        {
            String[] id = group.getKey().split(":");
            JsonObject json = new JsonObject();
            json.addProperty("type", "rechiseled:chiseling");
            json.addProperty("overwrite", false);
            JsonArray entries = new JsonArray();
            group.getValue().forEach(entries::add);
            json.add("entries", entries);
            DataProvider.save(GSON, cache, json,
                    gen.getOutputFolder().resolve("data/" + id[0] + "/chiseling_recipes/" + id[1] + ".json"));
        }
    }

    @Override
    public String getName()
    {
        return "Rechiseled chiseling groups";
    }
}
