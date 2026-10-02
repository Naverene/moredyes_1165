package info.kg6jay.moredyes.utility;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import info.kg6jay.moredyes.MoreDyes;

public class BlockInfo {

    public String blockName;
    public float hardness;
    public SoundType sound;
    /** Tool class that mines this block fastest, or null for none (like vanilla leaves, wool and glass). */
    public String harvestTool;
    public int harvestLevel;
    public float resistance;
    public Material blockMaterial;
    public CreativeTabs tab;

    public BlockInfo(String blockName, Material mat, float h, SoundType t, String s, int hL, float r,
        CreativeTabs tab) {
        this.blockName = blockName;
        this.blockMaterial = mat;
        this.hardness = h;
        this.sound = t;
        this.harvestTool = s;
        this.harvestLevel = hL;
        this.resistance = r;
        this.tab = tab;
    }

    public BlockInfo(String blockName, Material mat, float h, SoundType t, String s, int hL, float r) {
        this(blockName, mat, h, t, s, hL, r, MoreDyes.tabBlocks);
    }

    public BlockInfo(String blockName, Material mat, float h, SoundType t, String s, int hL) {
        this(blockName, mat, h, t, s, hL, 1.0F);
    }

    public BlockInfo() {
        this("", Material.rock, 1.0f, Block.soundTypeStone, "pickaxe", 1);
    }
}
