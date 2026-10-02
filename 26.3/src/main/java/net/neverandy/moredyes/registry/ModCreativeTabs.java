package net.neverandy.moredyes.registry;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.Kind;
import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.color.MixColors;

/** The four creative tabs from the original mod: Dyes, Blocks, Trees and Plants. */
public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister
        .create(Registries.CREATIVE_MODE_TAB, MoreDyes.MOD_ID);

    private static final MixColor ICON_COLOR = MixColors.byHex("c56685").orElseThrow();

    static {
        TABS.register("dyes", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.moredyes.dyes"))
            .icon(() -> new ItemStack(ModItems.dye(ICON_COLOR).get()))
            .displayItems((parameters, output) -> MixColors.ALL.forEach(color -> output.accept(ModItems.dye(color).get())))
            .build());
        register("blocks", Kind.Tab.BLOCKS, () -> ModItems.get(Kind.BRICKS, ICON_COLOR).get());
        register("trees", Kind.Tab.TREES, () -> ModItems.get(Kind.OAK_SAPLING, ICON_COLOR).get());
        register("plants", Kind.Tab.PLANTS, () -> ModItems.get(Kind.TULIP, ICON_COLOR).get());
    }

    private ModCreativeTabs() {}

    public static void register(IEventBus modBus) {
        TABS.register(modBus);
    }

    /** A tab listing every block of the kinds in {@code tab}, grouped by kind and then by color. */
    private static void register(String name, Kind.Tab tab, Supplier<Item> icon) {
        TABS.register(name, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.moredyes." + name))
            .icon(() -> new ItemStack(icon.get()))
            .displayItems((parameters, output) -> {
                for (Kind kind : Kind.values()) {
                    if (kind.tab() == tab) {
                        MixColors.ALL.forEach(color -> output.accept(ModItems.get(kind, color).get()));
                    }
                }
            })
            .build());
    }
}
