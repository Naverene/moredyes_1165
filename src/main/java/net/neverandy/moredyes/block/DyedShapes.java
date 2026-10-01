package net.neverandy.moredyes.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.neverandy.moredyes.ConfigHandler;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.reference.ColorStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * The slabs, stairs and walls of one dyed block type, in every color. Each shape copies the properties of the dyed
 * full block of its color, so a glowstone slab glows and an ice slab is slippery. Every color of a shape shares one
 * model, since the color comes from the tint (see client/ColorHandlers).
 */
public final class DyedShapes
{
    /** Which render layer a shape is drawn in; the client maps this to a RenderType. */
    public enum Layer
    {
        SOLID, CUTOUT, TRANSLUCENT
    }

    public static final List<DyedShapes> ALL = new ArrayList<>();

    /** The registry prefix, such as "stone" for "stoneslab_334c59", and the English name, such as "Stone". */
    public final String type;
    public final String displayName;
    public final Block[] full;
    /** Tinted textures in textures/block/tinted. */
    public final String side;
    public final String top;
    public final String bottom;
    public final Layer layer;
    /** The vanilla block of this type, for recipes that start from it (for example "stone_bricks"). */
    public final String vanilla;

    public final SlabBlock[] slabs = new SlabBlock[ColorStrings.ALL.length];
    public final StairsBlock[] stairs = new StairsBlock[ColorStrings.ALL.length];
    /** Empty when walls are turned off in the config. */
    public final WallBlock[] walls;

    private DyedShapes(String type, String displayName, Block[] full, String side, String top, String bottom, Layer layer, String vanilla)
    {
        this.type = type;
        this.displayName = displayName;
        this.full = full;
        this.side = side;
        this.top = top;
        this.bottom = bottom;
        this.layer = layer;
        this.vanilla = vanilla;
        this.walls = new WallBlock[ConfigHandler.wallBlocks.get() ? ColorStrings.ALL.length : 0];
    }

    /** A type with one texture on every face. */
    public static DyedShapes of(String type, String displayName, Block[] full, String texture, String vanilla)
    {
        return of(type, displayName, full, texture, texture, texture, Layer.SOLID, vanilla);
    }

    public static DyedShapes of(String type, String displayName, Block[] full, String side, String top, String bottom, Layer layer, String vanilla)
    {
        DyedShapes shapes = new DyedShapes(type, displayName, full, side, top, bottom, layer, vanilla);
        ALL.add(shapes);
        return shapes;
    }

    /** Must run after the full blocks are made, since each shape copies the full block's properties. */
    public void register()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            Block block = full[i];
            AbstractBlock.Properties properties = AbstractBlock.Properties.from(block);
            slabs[i] = add(type + "slab_" + color, new SlabBlock(properties));
            stairs[i] = add(type + "stairs_" + color, new StairsBlock(block::getDefaultState, properties));
            if (walls.length > 0)
            {
                walls[i] = add(type + "wall_" + color, new WallBlock(properties));
            }
        }
    }

    private static <B extends Block> B add(String name, B block)
    {
        MDBlock.BLOCKS.register(name, () -> block);
        MDBlock.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(MoreDyes.tabShapes)));
        return block;
    }
}
