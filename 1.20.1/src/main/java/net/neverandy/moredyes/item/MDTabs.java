package net.neverandy.moredyes.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.neverandy.moredyes.reference.Reference;

import java.util.ArrayList;
import java.util.List;

/** The five MoreDyes creative tabs. Each lists its items in the order they were registered. */
public enum MDTabs
{
    TREES("trees", 78),
    PLANTS("plants", 56),
    DYES("dyes", 99),
    BLOCKS("blocks", 100),
    SHAPES("shapes", 100);

    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);

    /** The translation key's last part, as in "itemGroup.trees". */
    public final String id;
    /** Which of the tab's items is its icon. */
    private final int icon;
    private final List<RegistryObject<Item>> items = new ArrayList<>();

    MDTabs(String id, int icon)
    {
        this.id = id;
        this.icon = icon;
    }

    public void add(RegistryObject<? extends Item> item)
    {
        @SuppressWarnings("unchecked")
        RegistryObject<Item> entry = (RegistryObject<Item>) item;
        items.add(entry);
    }

    public static void register(IEventBus modBus)
    {
        for (MDTabs tab : values())
        {
            TABS.register(tab.id, () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + tab.id))
                    .icon(() -> new ItemStack(tab.items.get(Math.min(tab.icon, tab.items.size() - 1)).get()))
                    .displayItems((parameters, output) -> tab.items.forEach(item -> output.accept(item.get())))
                    .build());
        }
        TABS.register(modBus);
    }
}
