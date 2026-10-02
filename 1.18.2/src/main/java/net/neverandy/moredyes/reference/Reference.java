package net.neverandy.moredyes.reference;

import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.effect.MobEffects;
import net.neverandy.moredyes.utility.ToolType;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.utility.BlockInfo;

import static net.minecraft.world.level.block.SoundType.*;

public class Reference
{
    public static final String MOD_ID="moredyes";
    public static final String MOD_NAME="More Dyes";
    public static final String MOD_VERSION="@VERSION@";

    public static final BlockInfo BLOCK_INFO_WOOL= new BlockInfo ("wool", Material.WOOL,0.8f, WOOL,null,0,1f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_STONE=new BlockInfo ("stone",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_COBBLE=new BlockInfo ("cobble",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_STONE_BRICK_CARVED=new BlockInfo ("stonebrickcarved",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f);
    public static final BlockInfo BLOCK_INFO_STONE_BRICK_CRACKED=new BlockInfo ("stonebrickcracked",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f);
    public static final BlockInfo BLOCK_INFO_STONE_BRICK=new BlockInfo ("stonebrick",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f);
    public static final BlockInfo BLOCK_INFO_OBSIDIAN=new BlockInfo ("obsidian",Material.STONE,50.0f, METAL,ToolType.PICKAXE,3,1200.0f);
    public static final BlockInfo BLOCK_INFO_SOULSAND = new BlockInfo ("soulsand",Material.SAND,0.5f, SAND, ToolType.SHOVEL, 0,0.5f);
    public static final BlockInfo BLOCK_INFO_QUARTZ = new BlockInfo ("quartz",Material.STONE,3.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CLAY = new BlockInfo ("clay",Material.CLAY,1.25f, GRAVEL, null, 0, 0.6f);
    public static final BlockInfo BLOCK_INFO_COAL = new BlockInfo ("coal",Material.STONE,3.0f, STONE,ToolType.PICKAXE,1,3.0f);
    public static final BlockInfo BLOCK_INFO_GLOWSTONE = new BlockInfo ("glowstone",Material.GLASS,0.3f, GLASS,ToolType.PICKAXE,1, 0.3f, MoreDyes.tabBlocks, 15);
    public static final BlockInfo BLOCK_INFO_REDSTONE = new BlockInfo ("redstone",Material.METAL,5.0f, METAL,ToolType.PICKAXE,1,6.0f);
    public static final BlockInfo BLOCK_INFO_LAPIS = new BlockInfo ("lapis",Material.STONE,3.0f, METAL,ToolType.PICKAXE,2,3.0f);
    public static final BlockInfo BLOCK_INFO_PLANK = new BlockInfo ("plank",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0);    // might need to delete this
    public static final BlockInfo BLOCK_INFO_TULIP = new BlockInfo ("tulip",Material.PLANT,0, GRAVEL,null,0,0.0f,MoreDyes.tabPlants, 0);
    public static final BlockInfo BLOCK_INFO_LOG = new BlockInfo ("log",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);    // might need to delete this
    public static final BlockInfo BLOCK_INFO_LEAVES = new BlockInfo ("leaf",Material.LEAVES,0.2f, GRAVEL,ToolType.AXE,0,1.0f,MoreDyes.tabTrees, 0);    // might need to delete this
    public static final BlockInfo BLOCK_INFO_SAPLING = new BlockInfo ("sapling",Material.PLANT,0, GRAVEL,null,0,1.0f,MoreDyes.tabTrees, 0);    // might need to delete this

    public static final BlockInfo BLOCK_INFO_GLASS = new BlockInfo ("glass",Material.GLASS,0.4f, GLASS,null,0,1.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_GLASS_FOGGY = new BlockInfo ("glassfoggy",Material.GLASS,0.4f, GLASS,null,0,1.0f,MoreDyes.tabBlocks, 0);

    public static final BlockInfo BLOCK_INFO_SLAB = new BlockInfo ("slab",Material.STONE,1.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_WORKBENCH = new BlockInfo ("craftingtable",Material.WOOD,2.5f, WOOD,ToolType.AXE,0,2.5f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_BRICK = new BlockInfo ("brick",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,0,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SAND = new BlockInfo ("sand", Material.SAND, 0.3f, SAND, ToolType.SHOVEL, 0,1f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SANDSTONE = new BlockInfo ("sandstone", Material.STONE, 2f, STONE, ToolType.PICKAXE, 1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_DIORITE = new BlockInfo ("Diorite", Material.STONE, 1.5f, STONE, ToolType.PICKAXE, 1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_ANDESITE = new BlockInfo ("andesite", Material.STONE, 1.5f, STONE, ToolType.PICKAXE, 1,10f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CONCRETE_POWDER = new BlockInfo ("concretepowder", Material.SAND, 0.3f, SAND, ToolType.SHOVEL, 0,1f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_ROCK_WOOL = new BlockInfo ("rockwool",Material.WOOL,0.8f, WOOL,null,0,1f,MoreDyes.tabBlocks, 0);

    // Below are the lines I have added.  Starting from the top of Blocks.java and skipping stuff already here. To start just copy and pasting then I'll go global replace to fix the lines.
    public static final BlockInfo BLOCK_INFO_GRANITE = new BlockInfo ("granite",Material.STONE,1.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_GRANITE = new BlockInfo ("polished_granite",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_DIORITE = new BlockInfo ("polished_diorite",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_ANDESITE = new BlockInfo ("polished_andesite",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_DIRT = new BlockInfo ("dirt",Material.DIRT,0.5F, GRAVEL,null,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_OAK_PLANKS = new BlockInfo ("oak_planks",Material.WOOD, 2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_PLANKS = new BlockInfo ("spruce_planks",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_PLANKS = new BlockInfo ("birch_planks",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_PLANKS = new BlockInfo ("jungle_planks",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_PLANKS = new BlockInfo ("acacia_planks",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_PLANKS = new BlockInfo ("dark_oak_planks",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_OAK_SAPLING = new BlockInfo ("oak_sapling", Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0); // all Saplings will need ,.tickRandomly in their .java
    public static final BlockInfo BLOCK_INFO_SPRUCE_SAPLING = new BlockInfo ("spruce_sapling", Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_BIRCH_SAPLING = new BlockInfo ("birch_sapling", Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_JUNGLE_SAPLING = new BlockInfo ("jungle_sapling", Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_ACACIA_SAPLING = new BlockInfo ("acacia_sapling", Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_SAPLING = new BlockInfo ("dark_oak_sapling", Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_GRAVEL = new BlockInfo ("gravel", Material.SAND,0.6F, GRAVEL,null,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_OAK_LOG = new BlockInfo ("oak_log",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_LOG = new BlockInfo ("spruce_log",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_LOG = new BlockInfo ("birch_log",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_LOG = new BlockInfo ("jungle_log",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_LOG = new BlockInfo ("acacia_log",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_LOG = new BlockInfo ("dark_oak_log",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_OAK_LEAVES = new BlockInfo ("oak_leaves",Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_SPRUCE_LEAVES = new BlockInfo ("spruce_leaves",Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_BIRCH_LEAVES = new BlockInfo ("birch_leaves",Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_JUNGLE_LEAVES = new BlockInfo ("jungle_leaves",Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_ACACIA_LEAVES = new BlockInfo ("acacia_leaves",Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_LEAVES = new BlockInfo ("dark_oak_leaves",Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_DISPENSER = new BlockInfo ("dispenser",Material.STONE,3.5F, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CHISELED_SANDSTONE = new BlockInfo ("chiseled_sandstone",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CUT_SANDSTONE = new BlockInfo ("cut_sandstone",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_NOTE_BLOCK = new BlockInfo ("note_block",Material.WOOD,0.8F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_BED = new BlockInfo ("bed",Material.WOOD, 1.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_OXEYE_DAISY = new BlockInfo ("oxeye_daisy",Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0); //MobEffects.REGENERATION, ,
    public static final BlockInfo BLOCK_INFO_CORNFLOWER = new BlockInfo ("cornflower",Material.PLANT,0, GRASS,null,0,1.0f, MoreDyes.tabTrees, 0);  //,
    public static final BlockInfo BLOCK_INFO_BOOKSHELF = new BlockInfo ("bookshelf",Material.WOOD,1.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_MOSSY_COBBLESTONE = new BlockInfo ("mossy_cobblestone",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks,0);
    public static final BlockInfo BLOCK_INFO_OAK_STAIRS = new BlockInfo ("oak_stairs",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_FURNACE = new BlockInfo ("furnace",Material.STONE,3.5F, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks,13);
    public static final BlockInfo BLOCK_INFO_OAK_SIGN = new BlockInfo ("oak_sign",Material.WOOD,0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_SIGN = new BlockInfo ("spruce_sign",Material.WOOD, 0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_SIGN = new BlockInfo ("birch_sign",Material.WOOD,0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_ACACIA_SIGN = new BlockInfo ("acacia_sign",Material.WOOD,0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_JUNGLE_SIGN = new BlockInfo ("jungle_sign",Material.WOOD, 0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_SIGN = new BlockInfo ("dark_oak_sign",Material.WOOD, 0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_OAK_DOOR = new BlockInfo ("oak_door",Material.WOOD,3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_LADDER = new BlockInfo ("ladder",Material.DECORATION,0.4F, LADDER,ToolType.AXE,0,0.4f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_COBBLESTONE_STAIRS = new BlockInfo ("cobblestone_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_OAK_WALL_SIGN = new BlockInfo ("oak_wall_sign",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_WALL_SIGN = new BlockInfo ("spruce_wall_sign",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_WALL_SIGN = new BlockInfo ("birch_wall_sign",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_ACACIA_WALL_SIGN = new BlockInfo ("acacia_wall_sign",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_JUNGLE_WALL_SIGN = new BlockInfo ("jungle_wall_sign",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_WALL_SIGN = new BlockInfo ("dark_oak_wall_sign",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_STONE_PRESSURE_PLATE = new BlockInfo ("stone_pressure_plate",Material.STONE,0.5F, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_IRON_DOOR = new BlockInfo ("iron_door",Material.METAL, 5.0F, METAL,ToolType.PICKAXE,1,5.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_OAK_PRESSURE_PLATE = new BlockInfo ("oak_pressure_plate",Material.WOOD, 0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_PRESSURE_PLATE = new BlockInfo ("spruce_pressure_plate",Material.WOOD, 0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_PRESSURE_PLATE = new BlockInfo ("birch_pressure_plate",Material.WOOD, 0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_PRESSURE_PLATE = new BlockInfo ("jungle_pressure_plate",Material.WOOD, 0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_PRESSURE_PLATE = new BlockInfo ("acacia_pressure_plate",Material.WOOD, 0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_PRESSURE_PLATE = new BlockInfo ("dark_oak_pressure_plate",Material.WOOD, 0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_STONE_BUTTON = new BlockInfo ("stone_button",Material.DECORATION,0.5F, STONE,null,0,1f,MoreDyes.tabBlocks,0 );
    public static final BlockInfo BLOCK_INFO_JUKEBOX = new BlockInfo ("jukebox",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,6.0f, MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_OAK_FENCE = new BlockInfo ("oak_fence",Material.WOOD, 2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_PUMPKIN = new BlockInfo ("pumpkin",Material.VEGETABLE,1.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_NETHERRACK = new BlockInfo ("netherrack",Material.STONE,0.4F, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SOUL_SOIL = new BlockInfo ("soul_soil",Material.DIRT,0.5F, GRAVEL,null,1,10.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_BASALT = new BlockInfo ("basalt",Material.STONE,1.25F, STONE,ToolType.PICKAXE,1,4.25f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_BASALT = new BlockInfo ("polished_basalt",Material.STONE,1.25F, STONE,ToolType.PICKAXE,1, 4.2F,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CARVED_PUMPKIN = new BlockInfo ("carved_pumpkin",Material.VEGETABLE,1.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_JACK_O_LANTERN = new BlockInfo ("jack_o_lantern",Material.VEGETABLE,1.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_CAKE = new BlockInfo ("cake",Material.CAKE,0.5F, WOOL,null,0,0,MoreDyes.tabBlocks,0);
    public static final BlockInfo BLOCK_INFO_STAINED_GLASS = new BlockInfo ("stained_glass",Material.GLASS,0.4f, GLASS,null,0,1.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_OAK_TRAPDOOR = new BlockInfo ("oak_trapdoor",Material.WOOD,3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_TRAPDOOR = new BlockInfo ("spruce_trapdoor",Material.WOOD,3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_TRAPDOOR = new BlockInfo ("birch_trapdoor",Material.WOOD,3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_TRAPDOOR = new BlockInfo ("jungle_trapdoor",Material.WOOD,3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_TRAPDOOR = new BlockInfo ("acacia_trapdoor",Material.WOOD,3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_TRAPDOOR = new BlockInfo ("dark_oak_trapdoor",Material.WOOD,3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_MOSSY_STONE_BRICKS = new BlockInfo ("mossy_stone_bricks",Material.STONE,1.5F, STONE,ToolType.PICKAXE,1, 6.0F);
    public static final BlockInfo BLOCK_INFO_CRACKED_STONE_BRICKS = new BlockInfo ("cracked_stone_bricks",Material.STONE,1.5F, STONE,ToolType.PICKAXE,1, 6.0F);
    public static final BlockInfo BLOCK_INFO_CHISELED_STONE_BRICKS = new BlockInfo ("chiseled_stone_bricks",Material.STONE,1.5F, STONE,ToolType.PICKAXE,1,6.0F);
    public static final BlockInfo BLOCK_INFO_INFESTED_STONE = new BlockInfo ("infested_stone",Material.CLAY,0, STONE,ToolType.PICKAXE,1, 0.75F);
    public static final BlockInfo BLOCK_INFO_INFESTED_COBBLESTONE = new BlockInfo ("infested_cobblestone",Material.CLAY,0, STONE,ToolType.PICKAXE,1,0.75F);
    public static final BlockInfo BLOCK_INFO_INFESTED_STONE_BRICKS = new BlockInfo ("infested_stone_bricks",Material.CLAY,0, STONE,ToolType.PICKAXE,1,0.75F);
    public static final BlockInfo BLOCK_INFO_INFESTED_MOSSY_STONE_BRICKS = new BlockInfo ("infested_mossy_stone_bricks",Material.CLAY,0, STONE,ToolType.PICKAXE,1, 0.75F);
    public static final BlockInfo BLOCK_INFO_INFESTED_CRACKED_STONE_BRICKS = new BlockInfo ("infested_cracked_stone_bricks",Material.CLAY,0, STONE,ToolType.PICKAXE,1,0.75F);
    public static final BlockInfo BLOCK_INFO_INFESTED_CHISELED_STONE_BRICKS = new BlockInfo ("infested_chiseled_stone_bricks",Material.CLAY,0, STONE,ToolType.PICKAXE,1, 0.75F);
    public static final BlockInfo BLOCK_INFO_IRON_BARS = new BlockInfo ("iron_bars",Material.METAL,5.0F, METAL, ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CHAIN = new BlockInfo ("chain",Material.METAL,5.0F, CHAIN,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_GLASS_PANE = new BlockInfo ("glass_pane",Material.GLASS,0.4f, GLASS,null,0,1.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_OAK_FENCE_GATE = new BlockInfo ("oak_fence_gate", Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_BRICK_STAIRS = new BlockInfo ("brick_stairs",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,10f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_STONE_BRICK_STAIRS = new BlockInfo ("stone_brick_stairs",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,10f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_NETHER_BRICKS = new BlockInfo ("nether_bricks",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_NETHER_BRICK_FENCE = new BlockInfo ("nether_brick_fence",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_NETHER_BRICK_STAIRS = new BlockInfo ("nether_brick_stairs",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_ENCHANTING_TABLE = new BlockInfo ("enchanting_table",Material.STONE,5.0F, STONE,ToolType.PICKAXE,6, 1200.0F,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CAULDRON = new BlockInfo ("cauldron",Material.METAL,2.0F, METAL,ToolType.PICKAXE,1,9f,MoreDyes.tabBlocks, 15);
    public static final BlockInfo BLOCK_INFO_END_STONE = new BlockInfo ("end_stone",Material.STONE,3.0F, STONE,ToolType.PICKAXE,1, 9.0F,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_REDSTONE_LAMP = new BlockInfo ("redstone_lamp",Material.BUILDABLE_GLASS,0.4f, GLASS,null,0,1.0f,MoreDyes.tabBlocks,15);
    public static final BlockInfo BLOCK_INFO_SANDSTONE_STAIRS = new BlockInfo ("sandstone_stairs",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SPRUCE_STAIRS = new BlockInfo ("spruce_stairs",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_STAIRS = new BlockInfo ("birch_stairs",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_STAIRS = new BlockInfo ("jungle_stairs",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_COBBLESTONE_WALL = new BlockInfo ("cobblestone_wall",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,10f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_MOSSY_COBBLESTONE_WALL = new BlockInfo ("mossy_cobblestone_wall",Material.STONE,2.0f, STONE,ToolType.PICKAXE,1,10f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_FLOWER_POT = new BlockInfo ("flower_pot",Material.DECORATION, 0, GLASS,null,0,0,MoreDyes.tabBlocks,0);
    public static final BlockInfo BLOCK_INFO_OAK_BUTTON = new BlockInfo ("oak_button",Material.DECORATION,0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0,WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_BUTTON = new BlockInfo ("spruce_button",Material.DECORATION,0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_BUTTON = new BlockInfo ("birch_button",Material.DECORATION,0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_BUTTON = new BlockInfo ("jungle_button",Material.DECORATION,0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_BUTTON = new BlockInfo ("acacia_button",Material.DECORATION,0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_BUTTON = new BlockInfo ("dark_oak_button",Material.DECORATION,0.5F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_LIGHT_WEIGHTED_PRESSURE_PLATE = new BlockInfo ("light_weighted_pressure_plate",Material.METAL, 1.0f, METAL, ToolType.PICKAXE,1,0.5f,MoreDyes.tabBlocks, 0, null);
    public static final BlockInfo BLOCK_INFO_HEAVY_WEIGHTED_PRESSURE_PLATE = new BlockInfo ("heavy_weighted_pressure_plate",Material.METAL, 1.0f, METAL, ToolType.PICKAXE,1,0.5f,MoreDyes.tabBlocks, 0, null);
    public static final BlockInfo BLOCK_INFO_HOPPER = new BlockInfo ("hopper",Material.METAL,3.0F, METAL,ToolType.PICKAXE,1,4.5f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CHISELED_QUARTZ_Block = new BlockInfo ("chiseled_quartz_block",Material.STONE,3.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_QUARTZ_PILLAR = new BlockInfo ("quartz_pillar",Material.STONE,3.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_QUARTZ_STAIRS = new BlockInfo ("quartz_stairs",Material.STONE,3.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_TERRACOTTA = new BlockInfo ("terracotta",Material.STONE,1.25f, STONE,ToolType.PICKAXE,1,4.2f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_STAINED_GLASS_PANE = new BlockInfo ("stained_glass_pane",Material.GLASS,0.4f, GLASS,null,0,1.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_ACACIA_STAIRS = new BlockInfo ("acacia_stairs",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_STAIRS = new BlockInfo ("dark_oak_stairs",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_IRON_TRAPDOOR = new BlockInfo ("iron_trapdoor",Material.METAL,5.0F, METAL, ToolType.PICKAXE,1,5.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_HAY_BLOCK = new BlockInfo ("hay_block",Material.GRASS,0.5F, GRASS,null,0,0.5f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CARPET = new BlockInfo ("carpet",Material.CLOTH_DECORATION,0.1F, WOOL,null,0,0.1f,MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_SUNFLOWER = new BlockInfo ("sunflower",Material.REPLACEABLE_PLANT, 0.0f, GRASS,null,0,0.1f,MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_LILAC = new BlockInfo ("lilac",Material.REPLACEABLE_PLANT,0.0f, GRASS,null,0,0.1f,MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_ROSE_BUSH = new BlockInfo ("rose_bush",Material.REPLACEABLE_PLANT, 0.0f, GRASS,null,0,0.1f,MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_PEONY = new BlockInfo ("peony",Material.REPLACEABLE_PLANT, 0.0f, GRASS,null,0,0.1f,MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_OAK_SLAB = new BlockInfo ("oak_slab",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_SLAB = new BlockInfo ("spruce_slab",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_SLAB = new BlockInfo ("birch_slab",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_SLAB = new BlockInfo ("jungle_slab",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_SLAB = new BlockInfo ("acacia_slab",Material.WOOD,2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_SLAB = new BlockInfo ("dark_oak_slab",Material.WOOD,2.0F,WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_STONE_SLAB = new BlockInfo ("stone_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SMOOTH_STONE_SLAB = new BlockInfo ("smooth_stone_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SANDSTONE_SLAB = new BlockInfo ("sandstone_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CUT_SANDSTONE_SLAB = new BlockInfo ("cut_sandstone_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_PETRIFIED_OAK_SLAB = new BlockInfo ("petrified_oak_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0, WoodType.OAK);
    public static final BlockInfo BLOCK_INFO_COBBLESTONE_SLAB = new BlockInfo ("cobblestone_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_BRICK_SLAB = new BlockInfo ("brick_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_STONE_BRICK_SLAB = new BlockInfo ("stone_brick_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_NETHER_BRICK_SLAB = new BlockInfo ("nether_brick_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_QUARTZ_SLAB = new BlockInfo ("quartz_slab",Material.STONE,3.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SMOOTH_STONE = new BlockInfo ("smooth_stone",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SMOOTH_SANDSTONE = new BlockInfo ("smooth_sandstone",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SMOOTH_QUARTZ = new BlockInfo ("smooth_quartz",Material.STONE,3.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SPRUCE_FENCE_GATE = new BlockInfo ("spruce_fence_gate", Material.WOOD, 2.0F,WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_FENCE_GATE = new BlockInfo ("birch_fence_gate", Material.WOOD, 2.0F,WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_FENCE_GATE = new BlockInfo ("jungle_fence_gate", Material.WOOD, 2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_FENCE_GATE = new BlockInfo ("acacia_fence_gate", Material.WOOD, 2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_FENCE_GATE = new BlockInfo ("dark_oak_fence_gate", Material.WOOD, 2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_FENCE = new BlockInfo ("spruce_fence",Material.WOOD, 2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_FENCE = new BlockInfo ("birch_fence",Material.WOOD, 2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_FENCE = new BlockInfo ("jungle_fence",Material.WOOD, 2.0F, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_FENCE = new BlockInfo ("acacia_fence",Material.WOOD, 2.0F,WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_FENCE = new BlockInfo ("dark_oak_fence",Material.WOOD, 2.0F,WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_SPRUCE_DOOR = new BlockInfo ("spruce_door",Material.WOOD, 3.0F,WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.SPRUCE);
    public static final BlockInfo BLOCK_INFO_BIRCH_DOOR = new BlockInfo ("birch_door",Material.WOOD, 3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.BIRCH);
    public static final BlockInfo BLOCK_INFO_JUNGLE_DOOR = new BlockInfo ("jungle_door",Material.WOOD, 3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees,0, WoodType.JUNGLE);
    public static final BlockInfo BLOCK_INFO_ACACIA_DOOR = new BlockInfo ("acacia_door",Material.WOOD, 3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.ACACIA);
    public static final BlockInfo BLOCK_INFO_DARK_OAK_DOOR = new BlockInfo ("dark_oak_door",Material.WOOD, 3.0F, WOOD,ToolType.AXE,0,3.0f, MoreDyes.tabTrees, 0, WoodType.DARK_OAK);
    public static final BlockInfo BLOCK_INFO_END_STONE_BRICKS = new BlockInfo ("end_stone_bricks",Material.STONE,3.0F, STONE,ToolType.PICKAXE,1,9f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_BONE_BLOCK = new BlockInfo ("bone_block",Material.STONE,2.0F, BONE_BLOCK,ToolType.PICKAXE,1,3f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SHULKER_BOX = new BlockInfo ("shulker_box",Material.SHULKER_SHELL,2.5f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_GLAZED_TERRACOTTA = new BlockInfo ("glazed_terracotta",Material.STONE,1.4F, STONE,ToolType.PICKAXE,1,1.2f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CONCRETE = new BlockInfo ("concrete",Material.STONE,1.8F, STONE,ToolType.PICKAXE,1,1.8f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CONDUIT = new BlockInfo ("conduit",Material.GLASS,3.0F, GLASS,null,0,3,MoreDyes.tabBlocks,15);
    public static final BlockInfo BLOCK_INFO_POLISHED_GRANITE_STAIRS = new BlockInfo ("polished_granite_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_MOSSY_STONE_BRICK_STAIRS = new BlockInfo ("mossy_stone_brick_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_DIORITE_STAIRS = new BlockInfo ("polished_diorite_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_MOSSY_COBBLESTONE_STAIRS = new BlockInfo ("mossy_cobblestone_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_END_STONE_BRICK_STAIRS = new BlockInfo ("end_stone_brick_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,9.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_STONE_STAIRS = new BlockInfo ("stone_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SMOOTH_SANDSTONE_STAIRS = new BlockInfo ("smooth_sandstone_stairs",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SMOOTH_QUARTZ_STAIRS = new BlockInfo ("smooth_quartz_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_GRANITE_STAIRS = new BlockInfo ("granite_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_ANDESITE_STAIRS = new BlockInfo ("andesite_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_RED_NETHER_BRICK_STAIRS = new BlockInfo ("red_nether_brick_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_ANDESITE_STAIRS = new BlockInfo ("polished_andesite_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_DIORITE_STAIRS = new BlockInfo ("diorite_stairs",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_GRANITE_SLAB = new BlockInfo ("polished_granite_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_MOSSY_STONE_BRICK_SLAB = new BlockInfo ("mossy_stone_brick_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_DIORITE_SLAB = new BlockInfo ("polished_diorite_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_MOSSY_COBBLESTONE_SLAB = new BlockInfo ("mossy_cobblestone_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_END_STONE_BRICK_SLAB = new BlockInfo ("end_stone_brick_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SMOOTH_SANDSTONE_SLAB = new BlockInfo ("smooth_sandstone_slab",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SMOOTH_QUARTZ_SLAB = new BlockInfo ("smooth_quartz_slab",Material.STONE,3.0f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_GRANITE_SLAB = new BlockInfo ("granite_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_ANDESITE_SLAB = new BlockInfo ("andesite_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_RED_NETHER_BRICK_SLAB = new BlockInfo ("red_nether_brick_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_POLISHED_ANDESITE_SLAB = new BlockInfo ("polished_andesite_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_DIORITE_SLAB = new BlockInfo ("diorite_slab",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_BRICK_WALL = new BlockInfo ("brick_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_MOSSY_STONE_BRICK_WALL = new BlockInfo ("mossy_stone_brick_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_GRANITE_WALL = new BlockInfo ("granite_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_STONE_BRICK_WALL = new BlockInfo ("stone_brick_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_NETHER_BRICK_WALL = new BlockInfo ("nether_brick_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_ANDESITE_WALL = new BlockInfo ("andesite_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_RED_NETHER_BRICK_WALL = new BlockInfo ("red_nether_brick_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SANDSTONE_WALL = new BlockInfo ("sandstone_wall",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_END_STONE_BRICK_WALL = new BlockInfo ("end_stone_brick_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_DIORITE_WALL = new BlockInfo ("diorite_wall",Material.STONE,1.5f, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_SCAFFOLDING = new BlockInfo ("scaffolding",Material.DECORATION,0, SCAFFOLDING,null,0,0,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_LOOM = new BlockInfo ("loom",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_BARREL = new BlockInfo ("barrel",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_SMOKER = new BlockInfo ("smoker",Material.STONE,3.5F, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 013);
    public static final BlockInfo BLOCK_INFO_BLAST_FURNACE = new BlockInfo ("blast_furnace",Material.STONE,3.5F, STONE,ToolType.PICKAXE,1,10.0f,MoreDyes.tabBlocks, 013);
    public static final BlockInfo BLOCK_INFO_CARTOGRAPHY_TABLE = new BlockInfo ("cartography_table",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_FLETCHING_TABLE = new BlockInfo ("fletching_table",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_GRINDSTONE = new BlockInfo ("grindstone",Material.HEAVY_METAL,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_LECTERN = new BlockInfo ("lectern",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_SMITHING_TABLE = new BlockInfo ("smithing_table",Material.WOOD,2.0f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_STONECUTTER = new BlockInfo ("stonecutter",Material.STONE,3.5F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_BELL = new BlockInfo ("bell",Material.METAL,5.0F, ANVIL,ToolType.PICKAXE,1,5.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_LANTERN = new BlockInfo ("lantern",Material.METAL,3.5F, LANTERN,ToolType.PICKAXE,1,3.5f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_COMPOSTER = new BlockInfo ("composter",Material.WOOD,0.6f, WOOD,ToolType.AXE,0,0.6f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_TARGET = new BlockInfo ("target",Material.GRASS,0.5F, GRASS,ToolType.AXE,0,0.5f, MoreDyes.tabTrees, 0);
    public static final BlockInfo BLOCK_INFO_NETHERITE_Block = new BlockInfo ("netherite_block",Material.METAL,50.0F, NETHERITE_BLOCK,ToolType.PICKAXE,4,1200.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CRYING_OBSIDIAN = new BlockInfo ("crying_obsidian",Material.STONE,50.0F, STONE,ToolType.PICKAXE,4,1200.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CHISELED_NETHER_BRICKS = new BlockInfo ("chiseled_nether_bricks",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CRACKED_NETHER_BRICKS = new BlockInfo ("cracked_nether_bricks",Material.STONE,2.0F, STONE,ToolType.PICKAXE,1,6.0f,MoreDyes.tabBlocks, 0);
    public static final BlockInfo BLOCK_INFO_CHEST = new BlockInfo ("chest",Material.WOOD,2.5f, WOOD,ToolType.AXE,0,1.0f, MoreDyes.tabTrees, 0);





        public static final String[] BLOCKTYPES = {"wool", "stone", "cobble", "stonebrickcarved","stonebrickcracked",
            "stonebrick", "obsidian", "soulsand", "quartz", "clay", "coal", "glowstone", "redstone", "lapis", "plank", "tulip", "log",
            "leaf", "sapling", "glass", "glassfoggy", "workbench", "brick", "sand", "concretepowder",
            "sandstonesmooth", "sandstonecarved", "diorite", "andesite", "slab", "granite", "polished_granite", "polished_diorite", "polished_andesite", "dirt", "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks",
            "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_leaves", "acacia_sapling", "dark_oak_sapling", "gravel", "oak_log", "spruce_log", "birch_log", "jungle_log", "acacia_log", "dark_oak_log",
            "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves", "acacia_leaves", "dark_oak_leaves", "dispenser", "chiseled_sandstone", "cut_sandstone", "note_block", "bed", "piston", "piston_head",
            "oxeye_daisy", "cornflower", "bookshelf", "mossy_cobblestone", "oak_stairs", "furnace", "oak_sign", "oak_sign", "spruce_sign", "birch_sign", "acacia_sign", "jungle_sign", "dark_oak_sign", "oak_door" ,"ladder", "cobblestone_stairs",
            "oak_wall_sign", "spruce_wall_sign", "birch_wall_sign", "acacia_wall_sign", "jungle_wall_sign", "dark_oak_wall_sign", "stone_pressure_plate", "iron_door", "oak_pressure_plate", "spruce_pressure_plate", "birch_pressure_plate",
            "jungle_pressure_plate", "acacia_pressure_plate", "dark_oak_pressure_plate", "stone_button", "stone_button", "jukebox", "oak_fence", "pumpkin", "netherrack", "soul_soil", "basalt", "polished_basalt", "carved_pumpkin0",
            "jack_o_lantern", "cake", "stained_glass", "oak_trapdoor", "spruce_trapdoor", "birch_trapdoor", "jungle_trapdoor", "acacia_trapdoor", "dark_oak_trapdoor", "mossy_stone_bricks", "cracked_stone_bricks",
            "chiseled_stone_bricks", "infested_stone", "infested_cobblestone", "infested_stone_bricks", "infested_mossy_stone_bricks", "infested_cracked_stone_bricks", "infested_chiseled_stone_bricks", "iron_bars",
            "chain", "glass_pane", "oak_fence_gate", "brick_stairs", "stone_brick_stairs", "nether_bricks", "nether_brick_fence", "nether_brick_stairs", "enchanting_table", "cauldron", "end_stone", "redstone_lamp",
            "sandstone_stairs", "spruce_stairs", "birch_stairs", "jungle_stairs", "cobblestone_wall", "mossy_cobblestone_wall", "flower_pot", "oak_button", "spruce_button", "birch_button", "jungle_button",
            "acacia_button", "dark_oak_button", "light_weighted_pressure_plate", "heavy_weighted_pressure_plate", "hopper", "chiseled_quartz_block", "quartz_pillar", "quartz_stairs", "terracotta", "stained_glass_pane",
            "acacia_stairs", "dark_oak_stairs", "iron_trapdoor", "hay_block", "carpet", "sunflower", "lilac", "rose_bush", "peony", "oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab",
            "stone_slab", "smooth_stone_slab", "sandstone_slab", "cut_sandstone_slab", "petrified_oak_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab", "smooth_stone",
            "smooth_sandstone", "smooth_quartz", "spruce_fence_gate", "birch_fence_gate", "jungle_fence_gate", "acacia_fence_gate", "dark_oak_fence_gate", "spruce_fence", "birch_fence", "jungle_fence", "acacia_fence", "dark_oak_fence",
            "spruce_door", "birch_door", "jungle_door", "acacia_door", "dark_oak_door", "end_stone_bricks", "bone_block", "shulker_box", "glazed_terracotta", "concrete", "conduit", "polished_granite_stairs", "mossy_stone_brick_stairs",
            "polished_diorite_stairs", "mossy_cobblestone_stairs", "end_stone_brick_stairs", "stone_stairs", "stone_stairs", "smooth_sandstone_stairs", "smooth_quartz_stairs", "granite_stairs", "andesite_stairs", "red_nether_brick_stairs",
            "polished_andesite_stairs", "diorite_slab", "brick_wall", "mossy_stone_brick_wall", "granite_wall", "stone_brick_wall", "nether_brick_wall", "andesite_wall", "red_nether_brick_wall", "sandstone_wall", "end_stone_brick_wall",
            "diorite_wall", "scaffolding", "loom", "barrel", "smoker", "blast_furnace", "cartography_table", "fletching_table", "grindstone", "lectern", "composter", "target", "netherite_block", "crying_obsidian",
            "chiseled_nether_bricks", "cracked_nether_bricks", "chest"};





}
