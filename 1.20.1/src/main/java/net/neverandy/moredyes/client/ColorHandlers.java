package net.neverandy.moredyes.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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

    /** The color a dyed block is drawn in: the dye color in its registry name, or white if it has none. */
    public static int colorOf(ResourceLocation name)
    {
        Matcher matcher = HEX.matcher(name.getPath());
        return matcher.find() ? vivid(Integer.parseInt(matcher.group(1), 16)) : WHITE;
    }

    /**
     * The palette comes from the muted 1.7 colors, so it is drawn more saturated and brighter, closer to the modern dyes.
     * The registry names keep the original hex, so worlds are unaffected.
     */
    public static int vivid(int rgb)
    {
        float r = (rgb >> 16 & 255) / 255.0F, g = (rgb >> 8 & 255) / 255.0F, b = (rgb & 255) / 255.0F;
        float max = Math.max(r, Math.max(g, b)), min = Math.min(r, Math.min(g, b));
        if (max == 0.0F)
        {
            return rgb;
        }
        float hue;
        if (max == min)
        {
            hue = 0.0F;
        }
        else if (max == r)
        {
            hue = ((g - b) / (max - min) + 6.0F) % 6.0F / 6.0F;
        }
        else if (max == g)
        {
            hue = ((b - r) / (max - min) + 2.0F) / 6.0F;
        }
        else
        {
            hue = ((r - g) / (max - min) + 4.0F) / 6.0F;
        }
        float saturation = Math.min(1.0F, (max - min) / max * 1.25F);
        float value = Math.min(1.0F, max / 0.8F);
        return Mth.hsvToRgb(hue, saturation, value);
    }

    @SubscribeEvent
    public static void blockColors(RegisterColorHandlersEvent.Block event)
    {
        for (RegistryObject<Block> entry : MDBlock.BLOCKS.getEntries())
        {
            int color = colorOf(entry.getId());
            event.register((state, level, pos, tintIndex) -> tintIndex == 0 ? color : WHITE, entry.get());
        }
    }

    @SubscribeEvent
    public static void itemColors(RegisterColorHandlersEvent.Item event)
    {
        for (RegistryObject<Item> entry : MDBlock.ITEMS.getEntries())
        {
            int color = colorOf(entry.getId());
            event.register((stack, tintIndex) -> tintIndex == 0 ? color : WHITE, entry.get());
        }
        for (MDItemDye dye : MDItem.dye)
        {
            int color = colorOf(ForgeRegistries.ITEMS.getKey(dye));
            event.register((stack, tintIndex) -> tintIndex == 0 ? color : WHITE, dye);
        }
    }
}
