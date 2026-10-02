package net.neverandy.moredyes.block;

import java.util.function.BiFunction;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.registry.ModBlocks;

/**
 * Every kind of block that comes in all of the mixed colors. Each kind is registered once per color under the name
 * {@code <id>_<hex>}, for example {@code moredyes:stone_bricks_c56685}.
 *
 * <p>
 * Each kind copies the properties (hardness, sound, light, map color, piston behavior...) of the vanilla block
 * named next to it, and gets the same tags, so vanilla behaviors that check a tag (nether portal frames, bubble
 * columns, what plants grow on, enchanting power...) work the same way. {@code tools/generate_resources.py} reads the ids and vanilla blocks from this file to write the models,
 * drops, recipes and tags, so keep the first two arguments of each entry as plain string literals.
 */
public enum Kind {

    WOOL("wool", "white_wool", Tab.BLOCKS, simple()),
    STONE("stone", "stone", Tab.BLOCKS, simple()),
    COBBLESTONE("cobblestone", "cobblestone", Tab.BLOCKS, simple()),
    STONE_BRICKS("stone_bricks", "stone_bricks", Tab.BLOCKS, simple()),
    CRACKED_STONE_BRICKS("cracked_stone_bricks", "cracked_stone_bricks", Tab.BLOCKS, simple()),
    CHISELED_STONE_BRICKS("chiseled_stone_bricks", "chiseled_stone_bricks", Tab.BLOCKS, simple()),
    ANDESITE("andesite", "andesite", Tab.BLOCKS, simple()),
    DIORITE("diorite", "diorite", Tab.BLOCKS, simple()),
    BRICKS("bricks", "bricks", Tab.BLOCKS, simple()),
    CLAY("clay", "clay", Tab.BLOCKS, simple()),
    TERRACOTTA("terracotta", "terracotta", Tab.BLOCKS, simple()),
    CONCRETE("concrete", "white_concrete", Tab.BLOCKS, simple()),
    CONCRETE_POWDER("concrete_powder", "white_concrete_powder", Tab.BLOCKS,
        (color, p) -> new ConcretePowderBlock(ModBlocks.get(Kind.CONCRETE, color).get(), p)),
    SAND("sand", "sand", Tab.BLOCKS, (color, p) -> new ColoredFallingBlock(new ColorRGBA(0xFF000000 | color.rgb()), p)),
    SANDSTONE("sandstone", "sandstone", Tab.BLOCKS, simple()),
    CHISELED_SANDSTONE("chiseled_sandstone", "chiseled_sandstone", Tab.BLOCKS, simple()),
    CUT_SANDSTONE("cut_sandstone", "cut_sandstone", Tab.BLOCKS, simple()),
    SOUL_SAND("soul_sand", "soul_sand", Tab.BLOCKS, (color, p) -> new SoulSandBlock(p)),
    OBSIDIAN("obsidian", "obsidian", Tab.BLOCKS, simple()),
    GLOWSTONE("glowstone", "glowstone", Tab.BLOCKS, simple()),
    COAL_BLOCK("coal_block", "coal_block", Tab.BLOCKS, simple()),
    LAPIS_BLOCK("lapis_block", "lapis_block", Tab.BLOCKS, simple()),
    REDSTONE_BLOCK("redstone_block", "redstone_block", Tab.BLOCKS, (color, p) -> new PoweredBlock(p)),
    QUARTZ_BLOCK("quartz_block", "quartz_block", Tab.BLOCKS, simple()),
    GLASS("glass", "glass", Tab.BLOCKS, (color, p) -> new TransparentBlock(p)),
    FOGGY_GLASS("foggy_glass", "glass", Tab.BLOCKS, (color, p) -> new TransparentBlock(p)),
    GLASS_PANE("glass_pane", "glass_pane", Tab.BLOCKS, (color, p) -> new IronBarsBlock(p)),
    FOGGY_GLASS_PANE("foggy_glass_pane", "glass_pane", Tab.BLOCKS, (color, p) -> new IronBarsBlock(p)),
    BOOKSHELF("bookshelf", "bookshelf", Tab.BLOCKS, simple()),
    CRAFTING_TABLE("crafting_table", "crafting_table", Tab.BLOCKS, (color, p) -> new DyedCraftingTableBlock(p)),
    CHEST("chest", "chest", Tab.BLOCKS, (color, p) -> new DyedChestBlock(p)),
    PISTON("piston", "piston", Tab.BLOCKS, (color, p) -> new DyedPistonBaseBlock(false, color, p)),
    STICKY_PISTON("sticky_piston", "sticky_piston", Tab.BLOCKS, (color, p) -> new DyedPistonBaseBlock(true, color, p)),
    /** The moving part of a dyed piston. It has no item, like the vanilla piston head. */
    PISTON_HEAD("piston_head", "piston_head", Tab.NONE, (color, p) -> new DyedPistonHeadBlock(color, p)),

    OAK_LOG("oak_log", "oak_log", Tab.TREES, log()),
    OAK_PLANKS("oak_planks", "oak_planks", Tab.TREES, simple()),
    OAK_LEAVES("oak_leaves", "oak_leaves", Tab.TREES, leaves()),
    OAK_SAPLING("oak_sapling", "oak_sapling", Tab.TREES, sapling(Wood.OAK)),
    OAK_FENCE("oak_fence", "oak_fence", Tab.TREES, fence()),
    BIRCH_LOG("birch_log", "birch_log", Tab.TREES, log()),
    BIRCH_PLANKS("birch_planks", "birch_planks", Tab.TREES, simple()),
    BIRCH_LEAVES("birch_leaves", "birch_leaves", Tab.TREES, leaves()),
    BIRCH_SAPLING("birch_sapling", "birch_sapling", Tab.TREES, sapling(Wood.BIRCH)),
    BIRCH_FENCE("birch_fence", "birch_fence", Tab.TREES, fence()),
    SPRUCE_LOG("spruce_log", "spruce_log", Tab.TREES, log()),
    SPRUCE_PLANKS("spruce_planks", "spruce_planks", Tab.TREES, simple()),
    SPRUCE_LEAVES("spruce_leaves", "spruce_leaves", Tab.TREES, leaves()),
    SPRUCE_SAPLING("spruce_sapling", "spruce_sapling", Tab.TREES, sapling(Wood.SPRUCE)),
    SPRUCE_FENCE("spruce_fence", "spruce_fence", Tab.TREES, fence()),
    JUNGLE_LOG("jungle_log", "jungle_log", Tab.TREES, log()),
    JUNGLE_PLANKS("jungle_planks", "jungle_planks", Tab.TREES, simple()),
    JUNGLE_LEAVES("jungle_leaves", "jungle_leaves", Tab.TREES, leaves()),
    JUNGLE_SAPLING("jungle_sapling", "jungle_sapling", Tab.TREES, sapling(Wood.JUNGLE)),
    JUNGLE_FENCE("jungle_fence", "jungle_fence", Tab.TREES, fence()),
    ACACIA_LOG("acacia_log", "acacia_log", Tab.TREES, log()),
    ACACIA_PLANKS("acacia_planks", "acacia_planks", Tab.TREES, simple()),
    ACACIA_LEAVES("acacia_leaves", "acacia_leaves", Tab.TREES, leaves()),
    ACACIA_SAPLING("acacia_sapling", "acacia_sapling", Tab.TREES, sapling(Wood.ACACIA)),
    ACACIA_FENCE("acacia_fence", "acacia_fence", Tab.TREES, fence()),
    DARK_OAK_LOG("dark_oak_log", "dark_oak_log", Tab.TREES, log()),
    DARK_OAK_PLANKS("dark_oak_planks", "dark_oak_planks", Tab.TREES, simple()),
    DARK_OAK_LEAVES("dark_oak_leaves", "dark_oak_leaves", Tab.TREES, leaves()),
    DARK_OAK_SAPLING("dark_oak_sapling", "dark_oak_sapling", Tab.TREES, sapling(Wood.DARK_OAK)),
    DARK_OAK_FENCE("dark_oak_fence", "dark_oak_fence", Tab.TREES, fence()),

    TULIP("tulip", "white_tulip", Tab.PLANTS, (color, p) -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p));

    /** The creative tab a kind is listed in. */
    public enum Tab {
        BLOCKS,
        TREES,
        PLANTS,
        /** Not listed, and no item. */
        NONE
    }

    private final String id;
    private final String vanilla;
    private final Tab tab;
    private final BiFunction<MixColor, BlockBehaviour.Properties, Block> factory;

    Kind(String id, String vanilla, Tab tab, BiFunction<MixColor, BlockBehaviour.Properties, Block> factory) {
        this.id = id;
        this.vanilla = vanilla;
        this.tab = tab;
        this.factory = factory;
    }

    public String id() {
        return id;
    }

    public Tab tab() {
        return tab;
    }

    public boolean hasItem() {
        return tab != Tab.NONE;
    }

    /** The vanilla block this kind copies. */
    public Block vanilla() {
        return BuiltInRegistries.BLOCK.getValue(Identifier.withDefaultNamespace(vanilla));
    }

    public Block create(MixColor color, BlockBehaviour.Properties properties) {
        return factory.apply(color, properties);
    }

    private static BiFunction<MixColor, BlockBehaviour.Properties, Block> simple() {
        return (color, p) -> new Block(p);
    }

    private static BiFunction<MixColor, BlockBehaviour.Properties, Block> log() {
        return (color, p) -> new RotatedPillarBlock(p);
    }

    private static BiFunction<MixColor, BlockBehaviour.Properties, Block> leaves() {
        return (color, p) -> new TintedParticleLeavesBlock(0.01F, p);
    }

    private static BiFunction<MixColor, BlockBehaviour.Properties, Block> fence() {
        return (color, p) -> new FenceBlock(p);
    }

    /**
     * Each colored sapling grows a tree of its own wood and color. The trees are data: see
     * {@code data/moredyes/worldgen/feature/<wood>_<hex>.json}.
     */
    private static BiFunction<MixColor, BlockBehaviour.Properties, Block> sapling(Wood wood) {
        return (color, p) -> {
            String name = color.id(wood.id());
            ResourceKey<Feature> tree = ResourceKey.create(Registries.FEATURE,
                Identifier.fromNamespaceAndPath(MoreDyes.MOD_ID, name));
            TreeGrower grower = new TreeGrower(MoreDyes.MOD_ID + ":" + name, WeightedList.of(tree), WeightedList.of(),
                WeightedList.of(), tree);
            return new SaplingBlock(grower, p);
        };
    }

}
