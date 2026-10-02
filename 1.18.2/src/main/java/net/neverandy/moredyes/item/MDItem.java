package net.neverandy.moredyes.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;

public class MDItem
{
    public static MDItemDye[] dye;
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
    public static void initialize()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Items can only be made while the registries are open; see MDBlock.initialize.
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, EventPriority.HIGHEST,
                (RegistryEvent.Register<Block> event) -> createAll());
    }

    private static void createAll()
    {
        dye = new MDItemDye[118];

        String[] all = ColorStrings.ALL;
        for (int i = 0; i < all.length; i++)
        {
            String color = all[i];
            String name = color + "_dye";
            dye[i] = new MDItemDye(name);
            final MDItemDye d = dye[i];
            ITEMS.register(name,() -> d);
        }
    }
}