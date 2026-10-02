package net.neverandy.moredyes.item;

import net.minecraft.item.Item;
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