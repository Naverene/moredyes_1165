package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neverandy.moredyes.ConfigHandler;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.reference.ColorStrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * The types that get walls: the ones vanilla has walls for. A wall has 324 block states, so walls for every type in
     * all 118 colors would need about 1.76 million states, more memory than a game client can load.
     */
    private static final Set<String> WALL_TYPES = new HashSet<>(Arrays.asList(
            "cobble", "mossycobble", "brick", "stonebrick", "mossystonebrick", "granite", "andesite", "diorite", "sandstone", "endstone"));

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
    public final StairBlock[] stairs = new StairBlock[ColorStrings.ALL.length];
    /** Empty for types without walls, and when walls are turned off in the config. */
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
        this.walls = new WallBlock[ConfigHandler.wallBlocks.get() && WALL_TYPES.contains(type) ? ColorStrings.ALL.length : 0];
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
            BlockBehaviour.Properties properties = BlockBehaviour.Properties.copy(block);
            slabs[i] = add(type + "slab_" + color, new SlabBlock(properties));
            stairs[i] = add(type + "stairs_" + color, new StairBlock(block::defaultBlockState, properties));
            if (walls.length > 0)
            {
                // Not solid, so the game skips caching six face-occlusion shapes for each of a wall's 324 states.
                // That cache is most of what walls cost in memory, and walls rarely hide a neighbor's face anyway.
                walls[i] = add(type + "wall_" + color, new DyedWallBlock(BlockBehaviour.Properties.copy(block).noOcclusion()));
            }
        }
    }

    private static <B extends Block> B add(String name, B block)
    {
        MDBlock.BLOCKS.register(name, () -> block);
        MDBlock.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(MoreDyes.tabShapes)));
        return block;
    }
}
