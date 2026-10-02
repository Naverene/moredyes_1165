package net.neverandy.moredyes.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.utility.ColorUtil;

/** The dyes of one color group; the metadata is the shade. */
public class MDItemDye extends Item
{
	private final String[] colors;
	private final int group;

	public MDItemDye(int group)
	{
		this.group=group;
		this.colors=ColorStrings.GROUPS[group];
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(MoreDyes.tabDyes);
		this.setUnlocalizedName(Reference.MOD_ID+".dye");
		this.setRegistryName(Reference.MOD_ID,"dye_"+ColorStrings.GROUP_NAMES[group]);
	}
	public String getColorName(int meta)
	{
		return this.colors[ColorUtil.clampShade(this.colors,meta)];
	}
	/** The dye's color; the grey dye texture is multiplied by it. */
	public int getColor(int meta)
	{
		return ColorUtil.fromHex(this.getColorName(meta));
	}
	/** Position in ColorStrings.ALL of the color of this metadata. */
	public int getColorIndex(int meta)
	{
		return ColorStrings.index(this.group,ColorUtil.clampShade(this.colors,meta));
	}
	public int getShadeCount()
	{
		return this.colors.length;
	}
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return ItemBlockColored.coloredName(this.getColorName(stack.getMetadata()),this.getUnlocalizedName()+".name");
	}
	@Override
	public void getSubItems(CreativeTabs tab,NonNullList<ItemStack> items)
	{
		if(this.isInCreativeTab(tab))
		{
			for(int i=0;i<this.colors.length;i++)
			{
				items.add(new ItemStack(this,1,i));
			}
		}
	}
}
