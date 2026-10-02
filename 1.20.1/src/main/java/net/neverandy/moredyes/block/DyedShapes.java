package net.neverandy.moredyes.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neverandy.moredyes.ConfigHandler;
import net.neverandy.moredyes.item.MDTabs;
import net.neverandy.moredyes.reference.ColorStrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.neverandy.moredyes.block.MDBlock.*;

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

    public final SlabBlock[] slabs = new SlabBlock[COLORS];
    public final StairBlock[] stairs = new StairBlock[COLORS];
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
        this.walls = new WallBlock[ConfigHandler.wallBlocks() && WALL_TYPES.contains(type) ? COLORS : 0];
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

    /** Whether this type is stone, mined with a pickaxe (ice isn't). Those also get stonecutter recipes. */
    public boolean isStone()
    {
        return MINEABLE.get(full) == net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE && full != iceArray && full != packedIceArray;
    }

    /** Each shape copies the full block's properties, so it is registered after the full block. */
    private void register()
    {
        if (MINEABLE.containsKey(full))
        {
            MINEABLE.put(slabs, MINEABLE.get(full));
            MINEABLE.put(stairs, MINEABLE.get(full));
            MINEABLE.put(walls, MINEABLE.get(full));
        }
        if (NEEDS_TOOL.containsKey(full))
        {
            NEEDS_TOOL.put(slabs, NEEDS_TOOL.get(full));
            NEEDS_TOOL.put(stairs, NEEDS_TOOL.get(full));
            NEEDS_TOOL.put(walls, NEEDS_TOOL.get(full));
        }
        for (int i = 0; i < COLORS; i++)
        {
            final int color = i;
            String hex = ColorStrings.ALL[i];
            MDBlock.register(type + "slab_" + hex, slabs, i, () -> new SlabBlock(MDBlock.copy(full[color])),
                    MDTabs.SHAPES, BlockItem::new);
            MDBlock.register(type + "stairs_" + hex, stairs, i, () -> new StairBlock(() -> full[color].defaultBlockState(),
                    MDBlock.copy(full[color])), MDTabs.SHAPES, BlockItem::new);
            if (walls.length > 0)
            {
                // Not solid, so the game skips caching six face-occlusion shapes for each of a wall's 324 states.
                // That cache is most of what walls cost in memory, and walls rarely hide a neighbor's face anyway.
                MDBlock.register(type + "wall_" + hex, walls, i, () -> new DyedWallBlock(MDBlock.copy(full[color])
                        .noOcclusion()), MDTabs.SHAPES, BlockItem::new);
            }
        }
    }

    /** Dyed slabs, stairs and walls; the textures are the ones the full block's model uses. */
    static void registerAll()
    {
        of("stone", "Stone", stoneArray, "stone", "stone");
        of("cobble", "Cobblestone", cobbleArray, "cobble", "cobblestone");
        of("mossycobble", "Mossy Cobblestone", mossyCobbleArray, "mossy_cobble", "mossy_cobblestone");
        of("stonebrick", "Stone Brick", stonebrickArray, "stonebrick", "stone_bricks");
        of("stonebrickcracked", "Cracked Stone Brick", stonebrickCrackedArray, "stonebrick_cracked", "cracked_stone_bricks");
        of("stonebrickcarved", "Chiseled Stone Brick", stonebrickCarvedArray, "stonebrick_carved", "chiseled_stone_bricks");
        of("mossystonebrick", "Mossy Stone Brick", mossyStonebrickArray, "mossy_stonebrick", "mossy_stone_bricks");
        of("brick", "Brick", brickArray, "brick", "bricks");
        of("clay", "Clay", clayArray, "clay", "clay");
        of("coal", "Coal", coalArray, "coal", "coal_block");
        of("lapis", "Lapis Lazuli", lapisArray, "lapis", "lapis_block");
        of("redstone", "Redstone", redstoneArray, "redstone", "redstone_block");
        of("quartz", "Quartz", quartzArray, "quartz", "quartz_block");
        of("quartzbricks", "Quartz Brick", quartzBricksArray, "quartz_bricks", "quartz_bricks");
        of("quartzchiseled", "Chiseled Quartz", quartzChiseledArray, "quartz_chiseled", "quartz_chiseled_top", "quartz_chiseled_top",
                Layer.SOLID, "chiseled_quartz_block");
        of("quartzpillar", "Quartz Pillar", quartzPillarArray, "quartz_pillar", "quartz_pillar_top", "quartz_pillar_top",
                Layer.SOLID, "quartz_pillar");
        of("quartzsmooth", "Smooth Quartz", quartzSmoothArray, "quartz_smooth", "smooth_quartz");
        of("obsidian", "Obsidian", obsidianArray, "obsidian", "obsidian");
        of("glowstone", "Glowstone", glowstoneArray, "glowstone", "glowstone");
        of("soulsand", "Soul Sand", soulsandArray, "soulsand", "soul_sand");
        of("sand", "Sand", sandArray, "sand", "sand");
        of("sandstone", "Sandstone", sandstoneArray, "sandstone_side", "sandstone_top", "sandstone_bottom", Layer.SOLID, "sandstone");
        of("sandstonecarved", "Chiseled Sandstone", sandstoneCarvedArray, "sandstone_carved", "sandstone_top", "sandstone_top",
                Layer.SOLID, "chiseled_sandstone");
        of("sandstonesmooth", "Cut Sandstone", sandstoneSmoothArray, "sandstone_smooth", "sandstone_top", "sandstone_top",
                Layer.SOLID, "cut_sandstone");
        of("andesite", "Andesite", andesiteArray, "andesite", "andesite");
        of("diorite", "Diorite", dioriteArray, "diorite", "diorite");
        of("granite", "Granite", graniteArray, "granite", "granite");
        of("polishedandesite", "Polished Andesite", polishedAndesiteArray, "polished_andesite", "polished_andesite");
        of("polisheddiorite", "Polished Diorite", polishedDioriteArray, "polished_diorite", "polished_diorite");
        of("polishedgranite", "Polished Granite", polishedGraniteArray, "polished_granite", "polished_granite");
        of("endstone", "End Stone", endstoneArray, "endstone", "end_stone");
        of("ice", "Ice", iceArray, "ice", "ice", "ice", Layer.TRANSLUCENT, "ice");
        of("packedice", "Packed Ice", packedIceArray, "packed_ice", "packed_ice");
        of("snow", "Snow", snowArray, "snow", "snow_block");
        of("boneblock", "Bone Block", boneBlockArray, "bone_block_side", "bone_block_top", "bone_block_top", Layer.SOLID, "bone_block");
        of("glass", "Glass", glassArray, "glass", "glass", "glass", Layer.CUTOUT, "glass");
        of("glassfoggy", "Foggy Glass", glassFoggyArray, "glass_foggy", "glass_foggy", "glass_foggy", Layer.TRANSLUCENT, "glass");
        of("wool", "Wool", woolArray, "wool", "white_wool");
        of("concrete", "Concrete", concreteArray, "concrete", "white_concrete");
        of("concretepowder", "Concrete Powder", concretePowderArray, "concrete_powder", "white_concrete_powder");
        of("oak", "Oak", oakPlankArray, "oak_planks", "oak_planks");
        of("birch", "Birch", birchPlankArray, "birch_planks", "birch_planks");
        of("spruce", "Spruce", sprucePlankArray, "spruce_planks", "spruce_planks");
        of("jungle", "Jungle", junglePlankArray, "jungle_planks", "jungle_planks");
        of("acacia", "Acacia", acaciaPlankArray, "acacia_planks", "acacia_planks");
        of("darkoak", "Dark Oak", darkOakPlankArray, "dark_oak_planks", "dark_oak_planks");
        for (DyedShapes shapes : ALL)
        {
            shapes.register();
        }
    }
}
