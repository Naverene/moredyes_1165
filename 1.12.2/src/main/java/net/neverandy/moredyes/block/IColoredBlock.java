package net.neverandy.moredyes.block;

import net.neverandy.moredyes.utility.ColorUtil;

/** A block drawn in dye colors: its grey texture is multiplied by the color. */
public interface IColoredBlock
{
	/** Name of the kind of block ("wool"), shared by every color; also the name of its blockstate file. */
	String getTypeName();
	/** How many metadata values of the item hold a color: the shades of a group, or 1 for a block of one color. */
	int getShadeCount();
	/** Position in ColorStrings.ALL of the color of this metadata. */
	int getColorIndex(int meta);
	/** Hex code of the color of this metadata. */
	String getColorName(int meta);
	/** The color of this metadata as 0xRRGGBB. */
	default int getColor(int meta)
	{
		return ColorUtil.fromHex(this.getColorName(meta));
	}
}
