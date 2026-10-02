package net.neverandy.moredyes.handler;

import net.minecraft.resources.Identifier;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.neoforged.neoforge.event.RegisterCauldronInteractionEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import net.neverandy.moredyes.block.Kind;
import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.color.MixColors;
import net.neverandy.moredyes.registry.ModItems;

/**
 * Using a stack of dyed blocks on a cauldron with water in it washes the whole stack back to the vanilla block and
 * uses up one level of water, like washing dyed leather armor. It is the same as crafting the block with a water
 * bucket (the {@code washing/} recipes), but for a whole stack at once.
 */
public final class CauldronWashing {

    private static final Identifier WATER = Identifier.withDefaultNamespace("water");

    private CauldronWashing() {}

    public static void register(RegisterCauldronInteractionEvent.Interaction event) {
        for (Kind kind : Kind.values()) {
            if (!kind.hasItem()) {
                continue;
            }
            Item washed = kind.vanilla().asItem();
            for (MixColor color : MixColors.ALL) {
                DeferredItem<?> dyed = ModItems.get(kind, color);
                event.register(WATER, dyed.get(), (state, level, pos, player, hand, held) -> {
                    if (!level.isClientSide()) {
                        player.setItemInHand(hand, new ItemStack(washed, held.getCount()));
                        player.awardStat(Stats.USE_CAULDRON);
                        LayeredCauldronBlock.lowerFillLevel(state, level, pos);
                    }
                    return InteractionResult.SUCCESS;
                });
            }
        }
    }
}
