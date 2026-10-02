package info.kg6jay.moredyes.utility;

import info.kg6jay.moredyes.block.MDBlock;

/**
 * Numbers every dye shade of every color set from 0 upward: the white set's shades first, then orange's, and so on.
 * Used by the blocks that keep their color in a tile entity (stairs, slabs, walls, trapdoors), which have a single
 * block for all colors, with this number as the item damage.
 */
public final class ColorIndex {

    private static final int[] SET, SHADE;
    private static final int[][] INDEX;

    static {
        int count = 0;
        for (String[] shades : MDBlock.colorStrings) {
            count += shades.length;
        }
        SET = new int[count];
        SHADE = new int[count];
        INDEX = new int[MDBlock.colorStrings.length][];
        int index = 0;
        for (int set = 0; set < MDBlock.colorStrings.length; set++) {
            INDEX[set] = new int[MDBlock.colorStrings[set].length];
            for (int shade = 0; shade < MDBlock.colorStrings[set].length; shade++) {
                SET[index] = set;
                SHADE[index] = shade;
                INDEX[set][shade] = index++;
            }
        }
    }

    private ColorIndex() {}

    /** How many shades there are across all color sets. */
    public static int count() {
        return SET.length;
    }

    public static int of(int set, int shade) {
        return INDEX[set][shade];
    }

    public static int set(int index) {
        return SET[clamp(index)];
    }

    public static int shade(int index) {
        return SHADE[clamp(index)];
    }

    /** The RGB color of the shade with this number. */
    public static int rgb(int index) {
        return ColorUtil.shade(MDBlock.colorStrings[set(index)], shade(index));
    }

    private static int clamp(int index) {
        return index < 0 || index >= SET.length ? 0 : index;
    }
}
