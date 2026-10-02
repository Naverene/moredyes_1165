package net.neverandy.moredyes.utility;

public final class ColorUtil
{
	public static final int WHITE=0xFFFFFF;

	private ColorUtil(){}

	/** Parses a hex color such as "ecbf99" into 0xRRGGBB. */
	public static int fromHex(String hex)
	{
		return Integer.parseInt(hex,16)&0xFFFFFF;
	}
	/** Keeps a metadata value inside a group's shades, falling back to the first shade. */
	public static int clampShade(String[] colors,int meta)
	{
		return meta<0||meta>=colors.length?0:meta;
	}
}
