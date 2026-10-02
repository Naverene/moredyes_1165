package net.neverandy.moredyes.block;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.tileentity.TileEntityDyedChest;

@EventBusSubscriber(modid=Reference.MOD_ID)
public class MDBlock
{
	// One block per color group; the metadata is the shade.
	public static Block[] wool;
	public static Block[] stonebrick;
	public static Block[] stonebrickCarved;
	public static Block[] stonebrickCracked;
	public static Block[] stone;
	public static Block[] cobble;
	public static Block[] diorite;
	public static Block[] obsidian;
	public static Block[] soulsand;
	public static Block[] quartz;
	public static Block[] clay;
	public static Block[] hardenedClay;
	public static Block[] coal;
	public static Block[] glowstone;
	public static Block[] lapis;
	public static Block[] redstone;
	public static Block[] brick;
	public static Block[] sand;
	public static Block[] sandstone;
	public static Block[] glass;
	public static Block[] glassFoggy;
	public static Block[] glassPane;
	public static Block[] glassFoggyPane;
	public static Block[] bookshelf;
	public static Block[] workbench;
	public static Block[] plank;
	public static Block[] sapling;
	public static Block[] tulip;
	/** Only when Thermal Foundation is installed, otherwise null. */
	public static Block[] rockwool;

	// One block per color, in the order of ColorStrings.ALL.
	public static Block[] log;
	public static Block[] leaf;
	public static Block[] chest;

	/** Every block of the mod, in the order it is registered and listed in the creative tabs. */
	public static final List<Block> ALL=new ArrayList<Block>();
	/** The vanilla block each dyed block turns back into when its color is washed out. */
	public static final Map<Block,ItemStack> WASHED=new LinkedHashMap<Block,ItemStack>();

	public static void initialize()
	{
		int groups=ColorStrings.GROUPS.length;
		int colors=ColorStrings.ALL.length;
		wool=new Block[groups];
		stonebrick=new Block[groups];
		stonebrickCarved=new Block[groups];
		stonebrickCracked=new Block[groups];
		stone=new Block[groups];
		cobble=new Block[groups];
		diorite=new Block[groups];
		obsidian=new Block[groups];
		soulsand=new Block[groups];
		quartz=new Block[groups];
		clay=new Block[groups];
		hardenedClay=new Block[groups];
		coal=new Block[groups];
		glowstone=new Block[groups];
		lapis=new Block[groups];
		redstone=new Block[groups];
		brick=new Block[groups];
		sand=new Block[groups];
		sandstone=new Block[groups];
		glass=new Block[groups];
		glassFoggy=new Block[groups];
		glassPane=new Block[groups];
		glassFoggyPane=new Block[groups];
		bookshelf=new Block[groups];
		workbench=new Block[groups];
		plank=new Block[groups];
		sapling=new Block[groups];
		tulip=new Block[groups];
		log=new Block[colors];
		leaf=new Block[colors];
		chest=new Block[colors];
		boolean thermal=Loader.isModLoaded("thermalfoundation");
		if(thermal)
		{
			rockwool=new Block[groups];
		}
		for(int g=0;g<groups;g++)
		{
			wool[g]=new BlockColored(Reference.BLOCK_INFO_WOOL,g);
			stonebrick[g]=new BlockColored(Reference.BLOCK_INFO_STONE_BRICK,g);
			stonebrickCarved[g]=new BlockColored(Reference.BLOCK_INFO_STONE_BRICK_CARVED,g);
			stonebrickCracked[g]=new BlockColored(Reference.BLOCK_INFO_STONE_BRICK_CRACKED,g);
			stone[g]=new BlockColoredStone(Reference.BLOCK_INFO_STONE,g);
			cobble[g]=new BlockColored(Reference.BLOCK_INFO_COBBLE,g);
			diorite[g]=new BlockColored(Reference.BLOCK_INFO_DIORITE,g);
			obsidian[g]=new BlockColoredObsidian(Reference.BLOCK_INFO_OBSIDIAN,g);
			soulsand[g]=new BlockColoredSoulSand(Reference.BLOCK_INFO_SOULSAND,g);
			quartz[g]=new BlockColored(Reference.BLOCK_INFO_QUARTZ,g);
			clay[g]=new BlockColored(Reference.BLOCK_INFO_CLAY,g);
			hardenedClay[g]=new BlockColored(Reference.BLOCK_INFO_HARDENED_CLAY,g);
			coal[g]=new BlockColored(Reference.BLOCK_INFO_COAL,g);
			glowstone[g]=new BlockColoredGlowstone(Reference.BLOCK_INFO_GLOWSTONE,g);
			lapis[g]=new BlockColored(Reference.BLOCK_INFO_LAPIS,g);
			redstone[g]=new BlockColoredPowered(Reference.BLOCK_INFO_REDSTONE,g);
			brick[g]=new BlockColored(Reference.BLOCK_INFO_BRICK,g);
			sand[g]=new BlockColoredSand(Reference.BLOCK_INFO_SAND,g);
			sandstone[g]=new BlockColored(Reference.BLOCK_INFO_SANDSTONE,g);
			glass[g]=new BlockColoredGlass(Reference.BLOCK_INFO_GLASS,g,false);
			glassFoggy[g]=new BlockColoredGlass(Reference.BLOCK_INFO_GLASS_FOGGY,g,true);
			glassPane[g]=new BlockColoredPane(Reference.BLOCK_INFO_GLASS_PANE,g,false);
			glassFoggyPane[g]=new BlockColoredPane(Reference.BLOCK_INFO_GLASS_FOGGY_PANE,g,true);
			bookshelf[g]=new BlockColoredBookshelf(Reference.BLOCK_INFO_BOOKSHELF,g);
			workbench[g]=new BlockColoredWorkbench(Reference.BLOCK_INFO_WORKBENCH,g);
			plank[g]=new BlockColored(Reference.BLOCK_INFO_PLANK,g);
			sapling[g]=new BlockColoredSapling(Reference.BLOCK_INFO_SAPLING,g);
			tulip[g]=new BlockColoredFlower(Reference.BLOCK_INFO_TULIP,g);
			if(thermal)
			{
				rockwool[g]=new BlockColored(Reference.BLOCK_INFO_ROCK_WOOL,g);
			}
		}
		for(int i=0;i<colors;i++)
		{
			log[i]=new BlockDyedLog(Reference.BLOCK_INFO_LOG,i);
			leaf[i]=new BlockDyedLeaves(Reference.BLOCK_INFO_LEAVES,i);
			chest[i]=new BlockDyedChest(Reference.BLOCK_INFO_CHEST,i);
		}
		add(wool,stone,cobble,stonebrick,stonebrickCracked,stonebrickCarved,diorite,brick,clay,hardenedClay,sand,sandstone,
				glass,glassFoggy,glassPane,glassFoggyPane,quartz,obsidian,soulsand,glowstone,coal,lapis,redstone,rockwool,
				bookshelf,workbench,chest,sapling,log,leaf,plank,tulip);
		registerWashing();
	}
	private static void add(Block[]... kinds)
	{
		for(Block[] kind:kinds)
		{
			if(kind!=null)
			{
				for(Block block:kind)
				{
					ALL.add(block);
				}
			}
		}
	}
	@SubscribeEvent
	public static void onBlockRegister(final RegistryEvent.Register<Block> event)
	{
		for(Block block:ALL)
		{
			event.getRegistry().register(block);
		}
		GameRegistry.registerTileEntity(TileEntityDyedChest.class,new ResourceLocation(Reference.MOD_ID,"chest"));
	}

	private static void registerWashing()
	{
		washed(wool,new ItemStack(Blocks.WOOL,1,0));
		washed(stonebrick,new ItemStack(Blocks.STONEBRICK,1,0));
		washed(stonebrickCracked,new ItemStack(Blocks.STONEBRICK,1,2));
		washed(stonebrickCarved,new ItemStack(Blocks.STONEBRICK,1,3));
		washed(stone,new ItemStack(Blocks.STONE,1,0));
		washed(cobble,new ItemStack(Blocks.COBBLESTONE));
		washed(diorite,new ItemStack(Blocks.STONE,1,3));
		washed(obsidian,new ItemStack(Blocks.OBSIDIAN));
		washed(soulsand,new ItemStack(Blocks.SOUL_SAND));
		washed(quartz,new ItemStack(Blocks.QUARTZ_BLOCK,1,0));
		washed(clay,new ItemStack(Blocks.STAINED_HARDENED_CLAY,1,0));
		washed(hardenedClay,new ItemStack(Blocks.HARDENED_CLAY));
		washed(coal,new ItemStack(Blocks.COAL_BLOCK));
		washed(glowstone,new ItemStack(Blocks.GLOWSTONE));
		washed(lapis,new ItemStack(Blocks.LAPIS_BLOCK));
		washed(redstone,new ItemStack(Blocks.REDSTONE_BLOCK));
		washed(brick,new ItemStack(Blocks.BRICK_BLOCK));
		washed(sand,new ItemStack(Blocks.SAND,1,0));
		washed(sandstone,new ItemStack(Blocks.SANDSTONE,1,0));
		washed(glass,new ItemStack(Blocks.GLASS));
		washed(glassPane,new ItemStack(Blocks.GLASS_PANE));
		washed(bookshelf,new ItemStack(Blocks.BOOKSHELF));
		washed(workbench,new ItemStack(Blocks.CRAFTING_TABLE));
		washed(chest,new ItemStack(Blocks.CHEST));
		washed(plank,new ItemStack(Blocks.PLANKS,1,0));
		washed(log,new ItemStack(Blocks.LOG,1,0));
		washed(leaf,new ItemStack(Blocks.LEAVES,1,0));
		washed(sapling,new ItemStack(Blocks.SAPLING,1,0));
	}
	private static void washed(Block[] dyed,ItemStack vanilla)
	{
		for(Block block:dyed)
		{
			WASHED.put(block,vanilla);
		}
	}

	/**
	 * Ore dictionary names, matching the names Forge gives the vanilla blocks, so recipes from other mods accept the
	 * dyed blocks wherever they accept the vanilla ones. Runs once the items exist.
	 */
	public static void registerOreDictionary()
	{
		// Names Forge has not given the vanilla blocks, so they can be dyed through the same recipes.
		OreDictionary.registerOre("bricksStone",new ItemStack(Blocks.STONEBRICK,1,0));
		OreDictionary.registerOre("bricksStoneCracked",new ItemStack(Blocks.STONEBRICK,1,2));
		OreDictionary.registerOre("bricksStoneCarved",new ItemStack(Blocks.STONEBRICK,1,3));
		OreDictionary.registerOre("blockBrick",new ItemStack(Blocks.BRICK_BLOCK));
		OreDictionary.registerOre("soulsand",new ItemStack(Blocks.SOUL_SAND));
		OreDictionary.registerOre("bookshelf",new ItemStack(Blocks.BOOKSHELF));
		OreDictionary.registerOre("hardenedClay",new ItemStack(Blocks.HARDENED_CLAY));
		OreDictionary.registerOre("stainedClay",new ItemStack(Blocks.STAINED_HARDENED_CLAY,1,OreDictionary.WILDCARD_VALUE));
		ore(wool,"wool","blockWool");
		ore(stonebrick,"bricksStone");
		ore(stonebrickCracked,"bricksStoneCracked");
		ore(stonebrickCarved,"bricksStoneCarved");
		ore(stone,"stone");
		ore(cobble,"cobblestone");
		ore(diorite,"stoneDiorite");
		ore(obsidian,"obsidian");
		ore(soulsand,"soulsand");
		ore(quartz,"blockQuartz");
		ore(clay,"stainedClay");
		ore(hardenedClay,"hardenedClay");
		ore(coal,"blockCoal");
		ore(glowstone,"glowstone");
		ore(lapis,"blockLapis");
		ore(redstone,"blockRedstone");
		ore(brick,"blockBrick");
		ore(sand,"sand");
		ore(sandstone,"sandstone");
		ore(glass,"blockGlass");
		ore(glassFoggy,"blockGlass");
		ore(glassPane,"paneGlass");
		ore(glassFoggyPane,"paneGlass");
		ore(bookshelf,"bookshelf");
		ore(workbench,"workbench");
		ore(chest,"chest","chestWood");
		ore(plank,"plankWood");
		ore(log,"logWood");
		ore(leaf,"treeLeaves");
		ore(sapling,"treeSapling");
		if(rockwool!=null)
		{
			Block thermal=ForgeRegistries.BLOCKS.getValue(new ResourceLocation("thermalfoundation","rockwool"));
			if(thermal!=null&&thermal!=Blocks.AIR)
			{
				OreDictionary.registerOre("blockRockwool",new ItemStack(thermal,1,OreDictionary.WILDCARD_VALUE));
			}
			ore(rockwool,"blockRockwool");
		}
	}
	private static void ore(Block[] blocks,String... names)
	{
		for(Block block:blocks)
		{
			for(String name:names)
			{
				OreDictionary.registerOre(name,new ItemStack(block,1,OreDictionary.WILDCARD_VALUE));
			}
		}
	}

	/** Same fire settings as the vanilla blocks (encouragement, flammability). */
	public static void registerFlammability()
	{
		fire(wool,30,60);
		fire(plank,5,20);
		fire(log,5,5);
		fire(leaf,30,60);
		fire(bookshelf,30,20);
		fire(tulip,60,100);
		fire(coal,5,5);
	}
	private static void fire(Block[] blocks,int encouragement,int flammability)
	{
		for(Block block:blocks)
		{
			Blocks.FIRE.setFireInfo(block,encouragement,flammability);
		}
	}
}
