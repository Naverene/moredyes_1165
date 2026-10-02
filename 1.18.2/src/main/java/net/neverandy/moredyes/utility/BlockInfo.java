package net.neverandy.moredyes.utility;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.CreativeModeTab;
import net.neverandy.moredyes.MoreDyes;

public class BlockInfo
{
    public String blockName;
    public float hardness;
    public SoundType sound;
    public ToolType harvestTool;
    public int harvestLevel;
    public float resistance;
    public Material blockMaterial;
    public CreativeModeTab tab;
    public int lightlevel;
    public WoodType woodType;
    public BlockInfo(String blockName, Material mat, float h, SoundType t, ToolType toolType, int hL, float r, CreativeModeTab tab, int lightlevel, WoodType woodType)
    {
        this.blockName=blockName;
        this.blockMaterial=mat;
        this.hardness=h;
        this.sound=t;
        this.harvestTool=toolType;
        this.harvestLevel=hL;
        this.resistance = r;
        this.tab=tab;
        this.lightlevel=lightlevel;
        this.woodType = woodType;
    }
    public BlockInfo(String blockName, Material mat, float h, SoundType t, ToolType toolType, int hL, float r, CreativeModeTab tab, int lightlevel)
    {
        this(blockName, mat, h, t, toolType, hL, r, tab, lightlevel, null);
    }
    public BlockInfo(String blockName,Material mat,float h,SoundType t, ToolType s, int hL,float r)
    {
        this(blockName,mat,h,t,s,hL,r, MoreDyes.tabBlocks, 0, null);
    }
    public BlockInfo(String blockName,Material mat,float h,SoundType t, ToolType s, int hL)
    {
        this(blockName,mat,h,t,s,hL,1.0F);
    }
    public BlockInfo()
    {
        this("",Material.STONE,1.0f,SoundType.STONE, ToolType.HOE,1);
    }

    /**
     * Like vanilla, only stone and metal blocks need the right tool to drop anything. Every other block drops by hand
     * too; its harvest tool only makes breaking faster.
     */
    public BlockBehaviour.Properties requireToolIfStone(BlockBehaviour.Properties properties)
    {
        return blockMaterial == Material.STONE || blockMaterial == Material.METAL ? properties.requiresCorrectToolForDrops() : properties;
    }

    public BlockInfo(String glazed_terracotta, Material rock, DyeColor white, float v) {
    }
}