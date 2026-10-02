package net.neverandy.moredyes.tileentity;

import net.minecraft.tileentity.TileEntityChest;

/** The contents of a dyed chest: a vanilla chest that is drawn by the dyed chest renderer. */
public class TileEntityDyedChest extends TileEntityChest
{
	/** The color to draw in when the chest is shown as an item and so has no block to take it from. */
	public int itemColor=0xFFFFFF;
}
