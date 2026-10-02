package net.neverandy.moredyes.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.Kind;
import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.color.MixColors;

/** Registers one block of every {@link Kind} in every {@link MixColor}, and flower pots for the plants. */
public final class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreDyes.MOD_ID);

    /** The kinds that can be put in a flower pot. */
    public static final List<Kind> POTTABLE = List.of(Kind.TULIP, Kind.OAK_SAPLING, Kind.BIRCH_SAPLING,
        Kind.SPRUCE_SAPLING, Kind.JUNGLE_SAPLING, Kind.ACACIA_SAPLING, Kind.DARK_OAK_SAPLING);

    private static final Map<Kind, Map<MixColor, DeferredBlock<Block>>> BY_KIND = new EnumMap<>(Kind.class);
    private static final List<DeferredBlock<FlowerPotBlock>> POTTED = new ArrayList<>();
    private static volatile Map<Block, Kind> KINDS;

    static {
        for (Kind kind : Kind.values()) {
            Map<MixColor, DeferredBlock<Block>> byColor = new LinkedHashMap<>();
            for (MixColor color : MixColors.ALL) {
                byColor.put(color, BLOCKS.registerBlock(color.id(kind.id()), p -> kind.create(color, p),
                    () -> BlockBehaviour.Properties.ofFullCopy(kind.vanilla())));
            }
            BY_KIND.put(kind, Collections.unmodifiableMap(byColor));
        }
        // Vanilla's empty pot finds these when a plant is used on it, because they name it as their empty pot.
        for (Kind kind : POTTABLE) {
            for (MixColor color : MixColors.ALL) {
                DeferredBlock<Block> plant = get(kind, color);
                POTTED.add(BLOCKS.registerBlock("potted_" + color.id(kind.id()),
                    p -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, plant, p),
                    () -> BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_WHITE_TULIP)));
            }
        }
    }

    private ModBlocks() {}

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    public static DeferredBlock<Block> get(Kind kind, MixColor color) {
        return BY_KIND.get(kind).get(color);
    }

    /** All colors of one kind, in color order. */
    public static List<DeferredBlock<Block>> all(Kind kind) {
        return List.copyOf(BY_KIND.get(kind).values());
    }

    /** Whether a block state is one of the colors of the given kind. */
    public static boolean isKind(BlockState state, Kind kind) {
        return kindOf(state.getBlock()) == kind;
    }

    /** The kind of a More Dyes block, or null for any other block. */
    public static Kind kindOf(Block block) {
        Map<Block, Kind> kinds = KINDS;
        if (kinds == null) {
            kinds = new IdentityHashMap<>();
            for (Map.Entry<Kind, Map<MixColor, DeferredBlock<Block>>> entry : BY_KIND.entrySet()) {
                for (DeferredBlock<Block> holder : entry.getValue().values()) {
                    kinds.put(holder.get(), entry.getKey());
                }
            }
            KINDS = kinds;
        }
        return kinds.get(block);
    }

    public static List<DeferredBlock<FlowerPotBlock>> potted() {
        return Collections.unmodifiableList(POTTED);
    }
}
