package net.neverandy.moredyes.registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.Kind;
import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.color.MixColors;
import net.neverandy.moredyes.item.MixDyeItem;

/** Registers the mixed dyes and an item for every block that has one. */
public final class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreDyes.MOD_ID);

    private static final Map<MixColor, DeferredItem<MixDyeItem>> DYES = new LinkedHashMap<>();
    private static final Map<Kind, Map<MixColor, DeferredItem<BlockItem>>> BLOCK_ITEMS = new LinkedHashMap<>();

    static {
        for (MixColor color : MixColors.ALL) {
            DYES.put(color, ITEMS.registerItem(color.id("dye"), p -> new MixDyeItem(color, p)));
        }
        for (Kind kind : Kind.values()) {
            if (!kind.hasItem()) {
                continue;
            }
            Map<MixColor, DeferredItem<BlockItem>> byColor = new LinkedHashMap<>();
            for (MixColor color : MixColors.ALL) {
                byColor.put(color, ITEMS.registerSimpleBlockItem(ModBlocks.get(kind, color), p -> properties(kind, p)));
            }
            BLOCK_ITEMS.put(kind, Collections.unmodifiableMap(byColor));
        }
    }

    private ModItems() {}

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    /** The item components of the vanilla item a kind copies that matter here: burning in a furnace and composting. */
    private static Item.Properties properties(Kind kind, Item.Properties p) {
        return switch (kind) {
            case CRAFTING_TABLE, BOOKSHELF, OAK_LOG, OAK_PLANKS, OAK_FENCE, BIRCH_LOG, BIRCH_PLANKS, BIRCH_FENCE,
                SPRUCE_LOG, SPRUCE_PLANKS, SPRUCE_FENCE, JUNGLE_LOG, JUNGLE_PLANKS, JUNGLE_FENCE, ACACIA_LOG,
                ACACIA_PLANKS, ACACIA_FENCE, DARK_OAK_LOG, DARK_OAK_PLANKS, DARK_OAK_FENCE ->
                p.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS);
            case CHEST -> p.component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                .cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS);
            case COAL_BLOCK -> p.cookingFuel(ContextIntProviders.COOKING_TIME_COAL_BLOCK);
            case OAK_SAPLING, BIRCH_SAPLING, SPRUCE_SAPLING, JUNGLE_SAPLING, ACACIA_SAPLING, DARK_OAK_SAPLING ->
                p.compostable(ContextIntProviders.COMPOSTABLE_LOW).cookingFuel(ContextIntProviders.COOKING_TIME_DRY_PLANTS);
            case OAK_LEAVES, BIRCH_LEAVES, SPRUCE_LEAVES, JUNGLE_LEAVES, ACACIA_LEAVES, DARK_OAK_LEAVES ->
                p.compostable(ContextIntProviders.COMPOSTABLE_LOW);
            case TULIP -> p.compostable(ContextIntProviders.COMPOSTABLE_MEDIUM);
            default -> p;
        };
    }

    public static DeferredItem<MixDyeItem> dye(MixColor color) {
        return DYES.get(color);
    }

    public static DeferredItem<BlockItem> get(Kind kind, MixColor color) {
        return BLOCK_ITEMS.get(kind).get(color);
    }
}
