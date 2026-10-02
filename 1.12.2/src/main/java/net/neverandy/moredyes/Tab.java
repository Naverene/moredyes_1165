package net.neverandy.moredyes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class Tab extends CreativeTabs
{
	private ItemStack tabIcon=new ItemStack(Items.DIAMOND);

	public Tab(String tabID)
	{
		super("moredyes."+tabID.toLowerCase());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		return this.tabIcon;
	}
	/** Set once the items exist; the tabs are made before them. */
	public void setTabIcon(ItemStack icon)
	{
		this.tabIcon=icon;
	}
}
