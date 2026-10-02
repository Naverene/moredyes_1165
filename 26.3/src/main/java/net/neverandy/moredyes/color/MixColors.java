package net.neverandy.moredyes.color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import net.neverandy.moredyes.MoreDyes;

/** The full, ordered list of mixed dye colors, and helpers to find a color from a registry name. */
public final class MixColors {

    public static final List<MixColor> ALL;
    private static final Map<String, MixColor> BY_HEX;
    private static final Pattern HEX = Pattern.compile("_([0-9a-f]{6})$");

    static {
        List<MixColor> colors = new ArrayList<>();
        for (ColorGroup group : ColorGroup.values()) {
            for (String hex : group.hexes()) {
                colors.add(new MixColor(hex));
            }
        }
        ALL = List.copyOf(colors);
        BY_HEX = ALL.stream().collect(Collectors.toUnmodifiableMap(MixColor::hex, Function.identity()));
    }

    private MixColors() {}

    public static Optional<MixColor> byHex(String hex) {
        return Optional.ofNullable(BY_HEX.get(hex));
    }

    /** The color at the end of a More Dyes registry name such as {@code moredyes:wool_c56685}. */
    public static Optional<MixColor> of(Identifier id) {
        if (!MoreDyes.MOD_ID.equals(id.getNamespace())) {
            return Optional.empty();
        }
        Matcher matcher = HEX.matcher(id.getPath());
        return matcher.find() ? byHex(matcher.group(1)) : Optional.empty();
    }

    /**
     * The color a block or item of this color is drawn in. The palette comes from the muted 1.7.10 colors, so it is
     * drawn a bit more saturated and brighter, closer to the vanilla dyes. Registry names keep the original hex.
     */
    public static int vivid(int rgb) {
        float r = (rgb >> 16 & 255) / 255.0F;
        float g = (rgb >> 8 & 255) / 255.0F;
        float b = (rgb & 255) / 255.0F;
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        if (max == 0.0F) {
            return rgb;
        }
        float hue;
        if (max == min) {
            hue = 0.0F;
        } else if (max == r) {
            hue = ((g - b) / (max - min) + 6.0F) % 6.0F / 6.0F;
        } else if (max == g) {
            hue = ((b - r) / (max - min) + 2.0F) / 6.0F;
        } else {
            hue = ((r - g) / (max - min) + 4.0F) / 6.0F;
        }
        float saturation = Math.min(1.0F, (max - min) / max * 1.25F);
        float value = Math.min(1.0F, max / 0.8F);
        return Mth.hsvToRgb(hue, saturation, value) & 0xFFFFFF;
    }
}
