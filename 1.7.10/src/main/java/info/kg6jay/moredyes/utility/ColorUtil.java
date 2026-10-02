package info.kg6jay.moredyes.utility;

public final class ColorUtil {

    public static final int WHITE = 0xFFFFFF;

    private ColorUtil() {}

    /** Parses a hex color such as "ecbf99" into 0xRRGGBB. */
    public static int fromHex(String hex) {
        return Integer.parseInt(hex, 16) & 0xFFFFFF;
    }

    /** The color of the shade stored in the given metadata, falling back to the first shade. */
    public static int shade(String[] colors, int meta) {
        if (meta < 0 || meta >= colors.length) {
            meta = 0;
        }
        return fromHex(colors[meta]);
    }
}
