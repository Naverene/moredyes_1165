package net.neverandy.moredyes.client;

import com.mojang.serialization.MapCodec;

import org.jspecify.annotations.Nullable;

import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.color.MixColors;

/**
 * Colors every dyed block and item. Their textures are grey, and the game multiplies the faces a model marks with
 * {@code tintindex 0} by the color returned here, the same way it colors grass. The color is the hex code at the end of
 * the registry name, such as {@code wool_334c59}, drawn a little more vividly (see {@link MixColors#vivid}).
 */
public final class Tints {

    private Tints() {}

    /** The opaque ARGB color a dyed color is drawn in. */
    public static int argb(MixColor color) {
        return 0xFF000000 | MixColors.vivid(color.rgb());
    }

    /** The tint for a More Dyes block, or white (no tint) for any other block. */
    public static int blockTint(Block block) {
        return MixColors.of(BuiltInRegistries.BLOCK.getKey(block)).map(Tints::argb).orElse(-1);
    }

    /** The item tint {@code moredyes:dye_color}: the color in the item's registry name. */
    public record DyeColor() implements ItemTintSource {

        public static final MapCodec<DyeColor> MAP_CODEC = MapCodec.unit(new DyeColor());

        @Override
        public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
            return MixColors.of(BuiltInRegistries.ITEM.getKey(stack.getItem())).map(Tints::argb).orElse(-1);
        }

        @Override
        public MapCodec<DyeColor> type() {
            return MAP_CODEC;
        }
    }
}
