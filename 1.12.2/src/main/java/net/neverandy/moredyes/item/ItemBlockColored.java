package net.neverandy.moredyes.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.neverandy.moredyes.block.IColoredBlock;

/** The item of a dyed block. Its metadata is the shade, for the blocks that hold a whole color group. */
public class ItemBlockColored extends ItemBlock
{
	public ItemBlockColored(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(((IColoredBlock)block).getShadeCount()>1);
	}
	@Override
	public int getMetadata(int damage)
	{
		return this.getHasSubtypes()?damage:0;
	}
	/** Every color shares the name of its kind of block, with the color in front: "ECBF99 Wool". */
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return coloredName(((IColoredBlock)this.block).getColorName(stack.getMetadata()),this.block.getUnlocalizedName()+".name");
	}
	@SuppressWarnings("deprecation")
	public static String coloredName(String color,String nameKey)
	{
		return I18n.translateToLocalFormatted("moredyes.colored_name",color.toUpperCase(),I18n.translateToLocal(nameKey));
	}
}
