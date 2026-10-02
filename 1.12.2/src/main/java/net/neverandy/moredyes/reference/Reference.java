package net.neverandy.moredyes.reference;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.utility.BlockInfo;

public class Reference
{
	public static final String MOD_ID="moredyes";
	public static final String MOD_NAME="More Dyes";
	public static final String MOD_VERSION="@VERSION@";

	public static final String CLIENT_PROXY="net.neverandy.moredyes.proxy.ClientProxy";
	public static final String SERVER_PROXY="net.neverandy.moredyes.proxy.ServerProxy";

	public static final BlockInfo BLOCK_INFO_WOOL=new BlockInfo("wool",Material.CLOTH,0.8f,SoundType.CLOTH);
	public static final BlockInfo BLOCK_INFO_ROCK_WOOL=new BlockInfo("rockwool",Material.ROCK,2.0f,SoundType.CLOTH).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_STONE=new BlockInfo("stone",Material.ROCK,1.5f,SoundType.STONE).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_COBBLE=new BlockInfo("cobble",Material.ROCK,2.0f,SoundType.STONE).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_STONE_BRICK=new BlockInfo("stonebrick",Material.ROCK,1.5f,SoundType.STONE).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_STONE_BRICK_CARVED=new BlockInfo("stonebrick_carved",Material.ROCK,1.5f,SoundType.STONE).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_STONE_BRICK_CRACKED=new BlockInfo("stonebrick_cracked",Material.ROCK,1.5f,SoundType.STONE).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_DIORITE=new BlockInfo("diorite",Material.ROCK,1.5f,SoundType.STONE).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_OBSIDIAN=new BlockInfo("obsidian",Material.ROCK,50.0f,SoundType.STONE).tool("pickaxe",3).resistance(2000.0f);
	public static final BlockInfo BLOCK_INFO_SOULSAND=new BlockInfo("soulsand",Material.SAND,0.5f,SoundType.SAND).tool("shovel",0);
	public static final BlockInfo BLOCK_INFO_QUARTZ=new BlockInfo("quartz",Material.ROCK,0.8f,SoundType.STONE).tool("pickaxe",0);
	public static final BlockInfo BLOCK_INFO_CLAY=new BlockInfo("clay",Material.ROCK,1.25f,SoundType.STONE).tool("pickaxe",0).resistance(7.0f);
	public static final BlockInfo BLOCK_INFO_HARDENED_CLAY=new BlockInfo("hardened_clay",Material.ROCK,1.25f,SoundType.STONE).tool("pickaxe",0).resistance(7.0f);
	public static final BlockInfo BLOCK_INFO_COAL=new BlockInfo("coal",Material.ROCK,5.0f,SoundType.STONE).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_GLOWSTONE=new BlockInfo("glowstone",Material.GLASS,0.3f,SoundType.GLASS);
	public static final BlockInfo BLOCK_INFO_REDSTONE=new BlockInfo("redstone",Material.IRON,5.0f,SoundType.METAL).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_LAPIS=new BlockInfo("lapis",Material.IRON,3.0f,SoundType.STONE).tool("pickaxe",1).resistance(5.0f);
	public static final BlockInfo BLOCK_INFO_BRICK=new BlockInfo("brick",Material.ROCK,2.0f,SoundType.STONE).tool("pickaxe",0).resistance(10.0f);
	public static final BlockInfo BLOCK_INFO_SAND=new BlockInfo("sand",Material.SAND,0.5f,SoundType.SAND).tool("shovel",0);
	public static final BlockInfo BLOCK_INFO_SANDSTONE=new BlockInfo("sandstone",Material.ROCK,0.8f,SoundType.STONE).tool("pickaxe",0);
	public static final BlockInfo BLOCK_INFO_GLASS=new BlockInfo("glass",Material.GLASS,0.3f,SoundType.GLASS);
	public static final BlockInfo BLOCK_INFO_GLASS_FOGGY=new BlockInfo("glass_foggy",Material.GLASS,0.3f,SoundType.GLASS);
	public static final BlockInfo BLOCK_INFO_GLASS_PANE=new BlockInfo("glass_pane",Material.GLASS,0.3f,SoundType.GLASS);
	public static final BlockInfo BLOCK_INFO_GLASS_FOGGY_PANE=new BlockInfo("glass_foggy_pane",Material.GLASS,0.3f,SoundType.GLASS);
	public static final BlockInfo BLOCK_INFO_BOOKSHELF=new BlockInfo("bookshelf",Material.WOOD,1.5f,SoundType.WOOD).tool("axe",0);
	public static final BlockInfo BLOCK_INFO_WORKBENCH=new BlockInfo("workbench",Material.WOOD,2.5f,SoundType.WOOD).tool("axe",0);
	public static final BlockInfo BLOCK_INFO_CHEST=new BlockInfo("chest",Material.WOOD,2.5f,SoundType.WOOD).tool("axe",0);

	public static final BlockInfo BLOCK_INFO_PLANK=new BlockInfo("plank",Material.WOOD,2.0f,SoundType.WOOD).tool("axe",0).resistance(5.0f).tab(MoreDyes.tabTrees);
	public static final BlockInfo BLOCK_INFO_LOG=new BlockInfo("log",Material.WOOD,2.0f,SoundType.WOOD).tool("axe",0).tab(MoreDyes.tabTrees);
	public static final BlockInfo BLOCK_INFO_LEAVES=new BlockInfo("leaf",Material.LEAVES,0.2f,SoundType.PLANT).tab(MoreDyes.tabTrees);
	public static final BlockInfo BLOCK_INFO_SAPLING=new BlockInfo("sapling",Material.PLANTS,0.0f,SoundType.PLANT).tab(MoreDyes.tabTrees);
	public static final BlockInfo BLOCK_INFO_TULIP=new BlockInfo("tulip",Material.PLANTS,0.0f,SoundType.PLANT).tab(MoreDyes.tabPlants);
}
