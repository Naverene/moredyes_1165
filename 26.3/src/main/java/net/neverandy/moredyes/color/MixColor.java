package net.neverandy.moredyes.color;

import java.util.Locale;

/**
 * One of the 118 mixed dye colors.
 *
 * @param hex the six-digit lowercase hex code of the color, used in every registry name (for example
 *            {@code moredyes:wool_c56685})
 */
public record MixColor(String hex) {

    public MixColor {
        if (!hex.matches("[0-9a-f]{6}")) {
            throw new IllegalArgumentException("Invalid color hex: " + hex);
        }
    }

    /** The color as a packed 0xRRGGBB integer. */
    public int rgb() {
        return Integer.parseInt(hex, 16);
    }

    /** The registry path of a block or item of this color, for example {@code wool_c56685}. */
    public String id(String prefix) {
        return prefix + "_" + hex;
    }

    /** The upper-case hex code used in display names, for example {@code C56685}. */
    public String label() {
        return hex.toUpperCase(Locale.ROOT);
    }
}
