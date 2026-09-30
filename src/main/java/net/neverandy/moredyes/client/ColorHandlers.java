package net.neverandy.moredyes.client;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.item.MDItemDye;
import net.neverandy.moredyes.reference.Reference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Colors every dyed block and item. Their textures are grey (textures/block/tinted), and the game multiplies
 * the faces a model marks with tintindex 0 by the color returned here, the same way it colors grass.
 * The color is the hex code in the registry name, such as "wool_334c59" or "334c59_dye".
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ColorHandlers
{
    private static final Pattern HEX = Pattern.compile("(?:^|_)([0-9a-f]{6})(?:_|$)");
    private static final int WHITE = 0xFFFFFF;

    private ColorHandlers() {}

    /** The dye color in a registry name, or white if it has none. */
    public static int colorOf(ResourceLocation name)
    {
        Matcher matcher = HEX.matcher(name.getPath());
        return matcher.find() ? Integer.parseInt(matcher.group(1), 16) : WHITE;
    }

    @SubscribeEvent
    public static void blockColors(ColorHandlerEvent.Block event)
    {
        for (RegistryObject<Block> entry : MDBlock.BLOCKS.getEntries())
        {
            int color = colorOf(entry.getId());
            event.getBlockColors().register((state, world, pos, tintIndex) -> tintIndex == 0 ? color : WHITE, entry.get());
        }
    }

    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event)
    {
        for (RegistryObject<Item> entry : MDBlock.ITEMS.getEntries())
        {
            int color = colorOf(entry.getId());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? color : WHITE, entry.get());
        }
        for (MDItemDye dye : MDItem.dye)
        {
            int color = colorOf(dye.getRegistryName());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? color : WHITE, dye);
        }
    }
}
