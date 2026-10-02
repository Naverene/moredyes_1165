package net.neverandy.moredyes.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import net.neverandy.moredyes.block.IColoredBlock;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.utility.LogHelper;

/**
 * The recipes of the mod:
 * <ul>
 * <li>dyes are mixed from two vanilla dyes, and a tulip makes one dye of its color;</li>
 * <li>eight vanilla blocks around a dye make eight dyed blocks;</li>
 * <li>a dyed block with a water bucket gives the vanilla block back;</li>
 * <li>dyed blocks turn into each other the way their vanilla blocks do (logs to planks, cobble to stone...).</li>
 * </ul>
 */
@EventBusSubscriber(modid=Reference.MOD_ID)
public class CraftManager
{
	/**
	 * The vanilla dyes in the order the color groups follow. A group holds the mixes of its own dye with each dye
	 * after it, so the shade of a mix is the position of the second dye among those.
	 */
	private static final String[] DYES={"dyeWhite","dyeOrange","dyeMagenta","dyeLightBlue","dyeYellow","dyeLime","dyePink","dyeGray",
			"dyeLightGray","dyeCyan","dyePurple","dyeBlue","dyeBrown","dyeGreen","dyeRed","dyeBlack"};
	private static final int WHITE=0,YELLOW=4,PINK=6,GRAY=7,PURPLE=10,BLUE=11,GREEN=13,RED=14,BLACK=15;

	private static IForgeRegistry<IRecipe> registry;

	// Runs after the other recipes are registered, so the vanilla recipes replaced below exist.
	@SubscribeEvent(priority=EventPriority.LOW)
	public static void onRecipeRegister(RegistryEvent.Register<IRecipe> event)
	{
		registry=event.getRegistry();
		registerDyeMixes();
		List<IRecipe> chests=new ArrayList<IRecipe>();
		List<IRecipe> workbenches=new ArrayList<IRecipe>();
		List<IRecipe> bookshelves=new ArrayList<IRecipe>();
		for(int i=0;i<ColorStrings.ALL.length;i++)
		{
			registerColor(i,chests,workbenches,bookshelves);
		}
		registerWashing();
		guard("chest",chests);
		guard("crafting_table",workbenches);
		guard("bookshelf",bookshelves);
		registry=null;
	}

	private static void registerDyeMixes()
	{
		for(int group=0;group<ColorStrings.GROUPS.length;group++)
		{
			int shade=0;
			for(int other=group+1;other<DYES.length;other++)
			{
				// White with gray or black are the vanilla light gray and gray, so those two have no shade.
				if(group==WHITE&&(other==GRAY||other==BLACK))
				{
					continue;
				}
				ItemStack result=new ItemStack(MDItem.dye[group],2,shade);
				String name="dye/"+ColorStrings.GROUPS[group][shade];
				if(isVanillaMix(group,other))
				{
					// One of each already makes a vanilla dye, so these take two of each and give four.
					result.setCount(4);
					shapeless(name,"dye",result,DYES[group],DYES[group],DYES[other],DYES[other]);
				}
				else
				{
					shapeless(name,"dye",result,DYES[group],DYES[other]);
				}
				shade++;
			}
		}
	}
	/** True for the pairs of dyes that vanilla already mixes into one of its own dyes. */
	private static boolean isVanillaMix(int first,int second)
	{
		return first==WHITE&&(second==RED||second==GREEN||second==BLUE)
				||first==YELLOW&&second==RED
				||first==PINK&&second==PURPLE
				||first==BLUE&&(second==GREEN||second==RED);
	}

	private static void registerColor(int index,List<IRecipe> chests,List<IRecipe> workbenches,List<IRecipe> bookshelves)
	{
		int g=ColorStrings.groupOf(index);
		int s=ColorStrings.shadeOf(index);
		String color=ColorStrings.ALL[index];
		ItemStack dye=new ItemStack(MDItem.dye[g],1,s);
		ItemStack plank=new ItemStack(MDBlock.plank[g],1,s);

		shapeless("dye_from_tulip/"+color,"dye_from_tulip",dye.copy(),new ItemStack(MDBlock.tulip[g],1,s));

		// Eight blocks around a dye. The ore names also take dyed blocks, so a block can be dyed again.
		dyeing(MDBlock.wool,g,s,"wool");
		dyeing(MDBlock.stone,g,s,"stone");
		dyeing(MDBlock.cobble,g,s,"cobblestone");
		dyeing(MDBlock.stonebrick,g,s,"bricksStone");
		dyeing(MDBlock.stonebrickCracked,g,s,"bricksStoneCracked");
		dyeing(MDBlock.stonebrickCarved,g,s,"bricksStoneCarved");
		dyeing(MDBlock.diorite,g,s,"stoneDiorite");
		dyeing(MDBlock.obsidian,g,s,"obsidian");
		dyeing(MDBlock.soulsand,g,s,"soulsand");
		dyeing(MDBlock.quartz,g,s,"blockQuartz");
		dyeing(MDBlock.clay,g,s,"stainedClay");
		dyeing(MDBlock.hardenedClay,g,s,"hardenedClay");
		dyeing(MDBlock.coal,g,s,"blockCoal");
		dyeing(MDBlock.glowstone,g,s,"glowstone");
		dyeing(MDBlock.lapis,g,s,"blockLapis");
		dyeing(MDBlock.redstone,g,s,"blockRedstone");
		dyeing(MDBlock.brick,g,s,"blockBrick");
		dyeing(MDBlock.sand,g,s,"sand");
		dyeing(MDBlock.sandstone,g,s,"sandstone");
		dyeing(MDBlock.glass,g,s,"blockGlassColorless");
		dyeing(MDBlock.glassPane,g,s,"paneGlassColorless");
		dyeing(MDBlock.bookshelf,g,s,"bookshelf");
		dyeing(MDBlock.plank,g,s,"plankWood");
		if(MDBlock.rockwool!=null)
		{
			dyeing(MDBlock.rockwool,g,s,"blockRockwool");
		}

		// One at a time
		shapeless("dyeing/sapling_"+color,"sapling",new ItemStack(MDBlock.sapling[g],1,s),dye,"treeSapling");
		shapeless("dyeing/chest_"+color,"chest",new ItemStack(MDBlock.chest[index]),dye,"chestWood");
		shapeless("dyeing/workbench_"+color,"workbench",new ItemStack(MDBlock.workbench[g],1,s),dye,"workbench");

		// Like the vanilla blocks
		shapeless("plank/"+color,"plank",new ItemStack(MDBlock.plank[g],4,s),new ItemStack(MDBlock.log[index]));
		shaped("stonebrick/"+color,"stonebrick",new ItemStack(MDBlock.stonebrick[g],4,s),"SS","SS",'S',new ItemStack(MDBlock.stone[g],1,s));
		shaped("sandstone/"+color,"sandstone",new ItemStack(MDBlock.sandstone[g],1,s),"SS","SS",'S',new ItemStack(MDBlock.sand[g],1,s));
		shaped("glass_pane/"+color,"glass_pane",new ItemStack(MDBlock.glassPane[g],16,s),"GGG","GGG",'G',new ItemStack(MDBlock.glass[g],1,s));
		shaped("glass_foggy_pane/"+color,"glass_foggy_pane",new ItemStack(MDBlock.glassFoggyPane[g],16,s),"GGG","GGG",'G',new ItemStack(MDBlock.glassFoggy[g],1,s));
		shapeless("redstone/"+color,"redstone",new ItemStack(Items.REDSTONE,9),new ItemStack(MDBlock.redstone[g],1,s));
		shapeless("lapis/"+color,"lapis",new ItemStack(Items.DYE,9,4),new ItemStack(MDBlock.lapis[g],1,s));
		shapeless("coal/"+color,"coal",new ItemStack(Items.COAL,9),new ItemStack(MDBlock.coal[g],1,s));

		// The vanilla recipes in dyed planks of one shade give the dyed block
		chests.add(shaped("chest/"+color,"chest",new ItemStack(MDBlock.chest[index]),"PPP","P P","PPP",'P',plank));
		workbenches.add(shaped("workbench/"+color,"workbench",new ItemStack(MDBlock.workbench[g],1,s),"PP","PP",'P',plank));
		bookshelves.add(shaped("bookshelf/"+color,"bookshelf",new ItemStack(MDBlock.bookshelf[g],1,s),"PPP","BBB","PPP",'P',plank,'B',Items.BOOK));

		smelting(new ItemStack(MDBlock.log[index]),new ItemStack(Items.COAL,1,1),0.15F);
		smelting(new ItemStack(MDBlock.cobble[g],1,s),new ItemStack(MDBlock.stone[g],1,s),0.1F);
		smelting(new ItemStack(MDBlock.stonebrick[g],1,s),new ItemStack(MDBlock.stonebrickCracked[g],1,s),0.1F);
		smelting(new ItemStack(MDBlock.sand[g],1,s),new ItemStack(MDBlock.glass[g],1,s),0.1F);
		smelting(new ItemStack(MDBlock.glass[g],1,s),new ItemStack(MDBlock.glassFoggy[g],1,s),0.1F);
		smelting(new ItemStack(MDBlock.clay[g],1,s),new ItemStack(MDBlock.hardenedClay[g],1,s),0.1F);
	}
	/** Eight of the given blocks around a dye make eight dyed blocks of its shade. */
	private static void dyeing(Block[] dyed,int group,int shade,String ore)
	{
		String type=((IColoredBlock)dyed[group]).getTypeName();
		shaped("dyeing/"+type+"_"+ColorStrings.GROUPS[group][shade],type,new ItemStack(dyed[group],8,shade),
				"SSS","SDS","SSS",'S',ore,'D',new ItemStack(MDItem.dye[group],1,shade));
	}
	/** A dyed block (any shade) and a water bucket give the vanilla block back; the bucket is returned empty. */
	private static void registerWashing()
	{
		for(Map.Entry<Block,ItemStack> entry:MDBlock.WASHED.entrySet())
		{
			Block dyed=entry.getKey();
			shapeless("washing/"+dyed.getRegistryName().getResourcePath(),"washing",entry.getValue().copy(),
					new ItemStack(dyed,1,OreDictionary.WILDCARD_VALUE),Items.WATER_BUCKET);
		}
	}
	/** Makes a vanilla recipe step aside for the mod's recipes that use the same shape. */
	private static void guard(String vanillaName,List<IRecipe> preferred)
	{
		IRecipe original=registry.getValue(new ResourceLocation("minecraft",vanillaName));
		if(original!=null&&original.getClass()==ShapedRecipes.class)
		{
			registry.register(new GuardedShapedRecipe((ShapedRecipes)original,preferred));
		}
		else
		{
			LogHelper.warn("The vanilla recipe "+vanillaName+" was changed by another mod; dyed planks of one shade may give its result");
		}
	}

	private static IRecipe shaped(String name,String group,ItemStack result,Object... recipe)
	{
		IRecipe r=new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID,group),result,recipe).setRegistryName(Reference.MOD_ID,name);
		registry.register(r);
		return r;
	}
	private static IRecipe shapeless(String name,String group,ItemStack result,Object... recipe)
	{
		IRecipe r=new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID,group),result,recipe).setRegistryName(Reference.MOD_ID,name);
		registry.register(r);
		return r;
	}
	private static void smelting(ItemStack input,ItemStack output,float xp)
	{
		GameRegistry.addSmelting(input,output,xp);
	}
}
