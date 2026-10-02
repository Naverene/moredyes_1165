package info.kg6jay.moredyes.compat.chisel;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import com.cricketcraft.chisel.api.carving.CarvingUtils;
import com.cricketcraft.chisel.api.carving.ICarvingGroup;
import com.cricketcraft.chisel.api.carving.ICarvingRegistry;

import info.kg6jay.moredyes.block.IBlockColored;
import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.handler.ConfigHandler;
import info.kg6jay.moredyes.reference.Reference;

/**
 * Makes the dyed blocks chiselable. Every shade is added to the carving group of its vanilla block (Chisel's own
 * group if it has one, such as its stone or diorite groups, otherwise a new group holding the vanilla block), so a
 * chisel turns the vanilla block into any dyed shade and back. Chisel's Auto Chisel and GregTech's auto chisels read
 * the same groups, so they work too.
 * Must run after Chisel has registered its own groups (postInit).
 */
public class ChiselCompat {

    /** Sorts the dyed variants after Chisel's own designs in the chisel GUI. */
    private static final int ORDER_START = 1000;

    public static void registerChisel() {
        if (!ConfigHandler.chisel_support) {
            return;
        }
        ICarvingRegistry chisel = CarvingUtils.getChiselRegistry();
        if (chisel == null) {
            return;
        }

        add(chisel, "wool", MDBlock.wool, Blocks.wool, 0);
        add(chisel, "stone", MDBlock.stone, Blocks.stone, 0);
        add(chisel, "cobblestone", MDBlock.cobble, Blocks.cobblestone, 0);
        add(chisel, "stonebrick", MDBlock.stoneBrick, Blocks.stonebrick, 0);
        add(chisel, "stonebrickCracked", MDBlock.stoneBrickCracked, Blocks.stonebrick, 2);
        add(chisel, "stonebrickCarved", MDBlock.stoneBrickCarved, Blocks.stonebrick, 3);
        add(chisel, "obsidian", MDBlock.obsidian, Blocks.obsidian, 0);
        add(chisel, "soulsand", MDBlock.soulsand, Blocks.soul_sand, 0);
        add(chisel, "quartz", MDBlock.quartz, Blocks.quartz_block, 0);
        add(chisel, "hardenedClay", MDBlock.hardenedClay, Blocks.hardened_clay, 0);
        add(chisel, "stainedClay", MDBlock.clay, Blocks.stained_hardened_clay, 0);
        add(chisel, "coal", MDBlock.coal, Blocks.coal_block, 0);
        add(chisel, "glowstone", MDBlock.glowstone, Blocks.glowstone, 0);
        add(chisel, "lapis", MDBlock.lapis, Blocks.lapis_block, 0);
        add(chisel, "redstone", MDBlock.redstone, Blocks.redstone_block, 0);
        if (ConfigHandler.chisel_plank) {
            add(chisel, "plank", MDBlock.plank, Blocks.planks, 0);
        }
        add(chisel, "brick", MDBlock.brick, Blocks.brick_block, 0);
        add(chisel, "sand", MDBlock.sand, Blocks.sand, 0);
        add(chisel, "sandstone", MDBlock.sandstone, Blocks.sandstone, 0);
        add(chisel, "glass", MDBlock.glassClear, Blocks.glass, 0);
        add(chisel, "glassPane", MDBlock.glassClearPane, Blocks.glass_pane, 0);
        add(chisel, "bookshelf", MDBlock.bookshelf, Blocks.bookshelf, 0);
        add(chisel, "diorite", MDBlock.diorite, MDBlock.dioritePlain, 0);
        add(chisel, "granite", MDBlock.granite, MDBlock.granitePlain, 0);
        add(chisel, "andesite", MDBlock.andesite, MDBlock.andesitePlain, 0);
        add(chisel, "cobblestoneMossy", MDBlock.mossyCobble, Blocks.mossy_cobblestone, 0);
        add(chisel, "stonebrickMossy", MDBlock.mossyStoneBrick, Blocks.stonebrick, 1);
        add(chisel, "netherBrick", MDBlock.netherBrick, Blocks.nether_brick, 0);
        add(chisel, "sandstoneCut", MDBlock.cutSandstone, Blocks.sandstone, 2);
    }

    private static void add(ICarvingRegistry chisel, String name, Block[] blocks, Block vanilla, int vanillaMeta) {
        ICarvingGroup group = chisel.getGroup(vanilla, vanillaMeta);
        if (group == null) {
            // Chisel's own group of the same name, such as its diorite
            group = chisel.getGroup(name);
        }
        String groupName;
        if (group != null) {
            groupName = group.getName();
        } else {
            groupName = Reference.MOD_ID + ":" + name;
        }
        if (chisel.getGroup(vanilla, vanillaMeta) == null) {
            chisel.addVariation(groupName, vanilla, vanillaMeta, 0);
        }

        int order = ORDER_START;
        for (Block block : blocks) {
            for (int meta = 0; meta <= ((IBlockColored) block).getMaxMeta(); meta++) {
                chisel.addVariation(groupName, block, meta, order++);
            }
        }
    }
}
