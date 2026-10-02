package net.neverandy.moredyes.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;

@EventBusSubscriber(modid=Reference.MOD_ID)
public class MDItem
{
	/** One dye item per color group; the metadata is the shade. */
	public static Item[] dye;

	public static void initialize()
	{
		dye=new Item[ColorStrings.GROUPS.length];
		for(int g=0;g<dye.length;g++)
		{
			dye[g]=new MDItemDye(g);
		}
	}
	/** The dye of a color, by its position in ColorStrings.ALL. */
	public static ItemStack dyeStack(int colorIndex,int count)
	{
		return new ItemStack(dye[ColorStrings.groupOf(colorIndex)],count,ColorStrings.shadeOf(colorIndex));
	}
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(dye);
		for(Block block:MDBlock.ALL)
		{
			event.getRegistry().register(new ItemBlockColored(block).setRegistryName(block.getRegistryName()));
		}
		for(Item item:dye)
		{
			OreDictionary.registerOre("dye",new ItemStack(item,1,OreDictionary.WILDCARD_VALUE));
		}
		MDBlock.registerOreDictionary();
	}
}
