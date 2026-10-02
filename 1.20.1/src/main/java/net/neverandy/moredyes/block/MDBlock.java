package net.neverandy.moredyes.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neverandy.moredyes.item.ChestItem;
import net.neverandy.moredyes.item.MDTabs;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.world.DyeTrees;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Every dyed block, one per color of each type, named "<type>_<color>" such as "wool_334c59". Each block copies the
 * properties of its vanilla block, so it breaks, sounds and burns the same way. The arrays are filled when the blocks
 * are registered, so they can only be read once registration is done.
 */
public class MDBlock
{
    public static final int COLORS = ColorStrings.ALL.length;

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    /** The tool that mines each array of blocks fastest (a mineable/ tag), for the block tags. */
    public static final Map<Block[], TagKey<Block>> MINEABLE = new IdentityHashMap<>();
    /** Arrays of blocks that need at least a stone, iron or diamond tool to drop, like their vanilla blocks. */
    public static final Map<Block[], TagKey<Block>> NEEDS_TOOL = new IdentityHashMap<>();

    public static Block[] brickArray = new Block[COLORS];
    public static Block[] clayArray = new Block[COLORS];
    public static Block[] woolArray = new Block[COLORS];
    public static Block[] cobbleArray = new Block[COLORS];
    public static Block[] stonebrickArray = new Block[COLORS];
    public static Block[] stonebrickCrackedArray = new Block[COLORS];
    public static Block[] stonebrickCarvedArray = new Block[COLORS];
    public static Block[] stoneArray = new Block[COLORS];
    public static Block[] obsidianArray = new Block[COLORS];
    public static Block[] lapisArray = new Block[COLORS];
    public static Block[] glowstoneArray = new Block[COLORS];
    public static Block[] coalArray = new Block[COLORS];
    public static Block[] soulsandArray = new Block[COLORS];
    public static Block[] redstoneArray = new Block[COLORS];
    public static Block[] quartzArray = new Block[COLORS];
    public static Block[] glassArray = new Block[COLORS];
    public static Block[] glassFoggyArray = new Block[COLORS];
    public static Block[] sandArray = new Block[COLORS];

    public static BlockSapling[] oakSaplingArray = new BlockSapling[COLORS];
    public static BlockSapling[] birchSaplingArray = new BlockSapling[COLORS];
    public static BlockSapling[] acaciaSaplingArray = new BlockSapling[COLORS];
    public static BlockSapling[] darkOakSaplingArray = new BlockSapling[COLORS];
    public static BlockSapling[] jungleSaplingArray = new BlockSapling[COLORS];
    public static BlockSapling[] spruceSaplingArray = new BlockSapling[COLORS];

    public static FlowerBlock[] tulipArray = new FlowerBlock[COLORS];
    public static WorkbenchBlock[] workbenchArray = new WorkbenchBlock[COLORS];

    public static Block[] oakPlankArray = new Block[COLORS];
    public static Block[] birchPlankArray = new Block[COLORS];
    public static Block[] acaciaPlankArray = new Block[COLORS];
    public static Block[] darkOakPlankArray = new Block[COLORS];
    public static Block[] junglePlankArray = new Block[COLORS];
    public static Block[] sprucePlankArray = new Block[COLORS];

    public static LeavesBlock[] oakLeafArray = new LeavesBlock[COLORS];
    public static LeavesBlock[] spruceLeafArray = new LeavesBlock[COLORS];
    public static LeavesBlock[] jungleLeafArray = new LeavesBlock[COLORS];
    public static LeavesBlock[] birchLeafArray = new LeavesBlock[COLORS];
    public static LeavesBlock[] acaciaLeafArray = new LeavesBlock[COLORS];
    public static LeavesBlock[] darkOakLeafArray = new LeavesBlock[COLORS];

    public static Block[] oakLogArray = new Block[COLORS];
    public static Block[] birchLogArray = new Block[COLORS];
    public static Block[] darkOakLogArray = new Block[COLORS];
    public static Block[] jungleLogArray = new Block[COLORS];
    public static Block[] acaciaLogArray = new Block[COLORS];
    public static Block[] spruceLogArray = new Block[COLORS];

    public static FenceBlock[] oakFenceArray = new FenceBlock[COLORS];
    public static FenceBlock[] birchFenceArray = new FenceBlock[COLORS];
    public static FenceBlock[] darkOakFenceArray = new FenceBlock[COLORS];
    public static FenceBlock[] jungleFenceArray = new FenceBlock[COLORS];
    public static FenceBlock[] acaciaFenceArray = new FenceBlock[COLORS];
    public static FenceBlock[] spruceFenceArray = new FenceBlock[COLORS];

    public static Block[] sandstoneArray = new Block[COLORS];
    public static Block[] sandstoneCarvedArray = new Block[COLORS];
    public static Block[] sandstoneSmoothArray = new Block[COLORS];
    public static Block[] andesiteArray = new Block[COLORS];
    public static Block[] dioriteArray = new Block[COLORS];
    public static Block[] concretePowderArray = new Block[COLORS];
    public static Block[] concreteArray = new Block[COLORS];
    public static BlockPiston[] pistonArray = new BlockPiston[COLORS];
    public static BlockPiston[] stickyPistonArray = new BlockPiston[COLORS];
    public static BlockPistonHead[] pistonHeadArray = new BlockPistonHead[COLORS];
    public static Block[] hardenedClayArray = new Block[COLORS];

    public static IronBarsBlock[] glassPaneArray = new IronBarsBlock[COLORS];
    public static IronBarsBlock[] glassFoggyPaneArray = new IronBarsBlock[COLORS];
    public static BlockBookshelf[] bookshelfArray = new BlockBookshelf[COLORS];
    public static BlockChest[] chestArray = new BlockChest[COLORS];

    // Full blocks from the sheet's second batch.
    public static Block[] graniteArray = new Block[COLORS];
    public static Block[] polishedAndesiteArray = new Block[COLORS];
    public static Block[] polishedDioriteArray = new Block[COLORS];
    public static Block[] polishedGraniteArray = new Block[COLORS];
    public static Block[] endstoneArray = new Block[COLORS];
    public static Block[] mossyCobbleArray = new Block[COLORS];
    public static Block[] mossyStonebrickArray = new Block[COLORS];
    public static Block[] quartzBricksArray = new Block[COLORS];
    public static Block[] quartzChiseledArray = new Block[COLORS];
    public static RotatedPillarBlock[] quartzPillarArray = new RotatedPillarBlock[COLORS];
    public static Block[] quartzSmoothArray = new Block[COLORS];
    public static RotatedPillarBlock[] boneBlockArray = new RotatedPillarBlock[COLORS];
    public static Block[] gravelArray = new Block[COLORS];
    public static Block[] iceArray = new Block[COLORS];
    public static Block[] packedIceArray = new Block[COLORS];
    public static Block[] snowArray = new Block[COLORS];

    /** Small flowers, as {registry type, vanilla flower}; each array in smallFlowerArrays matches one row. */
    public static final String[][] SMALL_FLOWERS = {
        {"allium", "allium"}, {"azurebluet", "azure_bluet"}, {"cornflower", "cornflower"}, {"dandelion", "dandelion"},
        {"lilyofthevalley", "lily_of_the_valley"}, {"orchid", "blue_orchid"}, {"oxeyedaisy", "oxeye_daisy"}, {"poppy", "poppy"}};
    public static final FlowerBlock[][] smallFlowerArrays = new FlowerBlock[SMALL_FLOWERS.length][COLORS];
    /** Two-block-tall flowers, as {registry type, vanilla flower}. */
    public static final String[][] TALL_FLOWERS = {{"lilac", "lilac"}, {"peony", "peony"}, {"rosebush", "rose_bush"}};
    public static final TallFlowerBlock[][] tallFlowerArrays = new TallFlowerBlock[TALL_FLOWERS.length][COLORS];

    private static final TagKey<Block> PICKAXE = BlockTags.MINEABLE_WITH_PICKAXE;
    private static final TagKey<Block> AXE = BlockTags.MINEABLE_WITH_AXE;
    private static final TagKey<Block> SHOVEL = BlockTags.MINEABLE_WITH_SHOVEL;
    private static final TagKey<Block> HOE = BlockTags.MINEABLE_WITH_HOE;

    public static void register(IEventBus modBus)
    {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);

        // The same order as the 1.16.5 port, which is the order the creative tabs list them in.
        colors("glass", glassArray, () -> new GlassBlock(copy(Blocks.GLASS)), MDTabs.BLOCKS, null);
        colors("sand", sandArray, () -> new FallingBlock(copy(Blocks.SAND)), MDTabs.BLOCKS, SHOVEL);
        colors("leaf", oakLeafArray, () -> new LeavesBlock(copy(Blocks.OAK_LEAVES)), MDTabs.TREES, HOE);
        colors("brick", brickArray, () -> new Block(copy(Blocks.BRICKS)), MDTabs.BLOCKS, PICKAXE);
        colors("clay", clayArray, () -> new Block(copy(Blocks.CLAY)), MDTabs.BLOCKS, SHOVEL);
        colors("wool", woolArray, () -> new Block(copy(Blocks.WHITE_WOOL)), MDTabs.BLOCKS, null);
        colors("cobble", cobbleArray, () -> new Block(copy(Blocks.COBBLESTONE)), MDTabs.BLOCKS, PICKAXE);
        colors("stonebrick", stonebrickArray, () -> new Block(copy(Blocks.STONE_BRICKS)), MDTabs.BLOCKS, PICKAXE);
        colors("stonebrickcracked", stonebrickCrackedArray, () -> new Block(copy(Blocks.CRACKED_STONE_BRICKS)), MDTabs.BLOCKS, PICKAXE);
        colors("stonebrickcarved", stonebrickCarvedArray, () -> new Block(copy(Blocks.CHISELED_STONE_BRICKS)), MDTabs.BLOCKS, PICKAXE);
        colors("stone", stoneArray, () -> new Block(copy(Blocks.STONE)), MDTabs.BLOCKS, PICKAXE);
        colors("obsidian", obsidianArray, () -> new Block(copy(Blocks.OBSIDIAN)), MDTabs.BLOCKS, PICKAXE);
        NEEDS_TOOL.put(obsidianArray, BlockTags.NEEDS_DIAMOND_TOOL);
        colors("lapis", lapisArray, () -> new Block(copy(Blocks.LAPIS_BLOCK)), MDTabs.BLOCKS, PICKAXE);
        NEEDS_TOOL.put(lapisArray, BlockTags.NEEDS_STONE_TOOL);
        colors("glowstone", glowstoneArray, () -> new Block(copy(Blocks.GLOWSTONE)), MDTabs.BLOCKS, null);
        colors("coal", coalArray, () -> new Block(copy(Blocks.COAL_BLOCK)), MDTabs.BLOCKS, PICKAXE);
        // A full block, unlike vanilla soul sand, so it doesn't slow anyone down.
        colors("soulsand", soulsandArray, () -> new Block(copy(Blocks.SOUL_SAND).speedFactor(1.0F)), MDTabs.BLOCKS, SHOVEL);
        colors("plank", oakPlankArray, () -> new Block(copy(Blocks.OAK_PLANKS)), MDTabs.BLOCKS, AXE);
        colors("glassfoggy", glassFoggyArray, () -> new GlassBlock(copy(Blocks.GLASS)), MDTabs.BLOCKS, null);
        colors("quartz", quartzArray, () -> new Block(copy(Blocks.QUARTZ_BLOCK)), MDTabs.BLOCKS, PICKAXE);
        colors("workbench", workbenchArray, () -> new WorkbenchBlock(copy(Blocks.CRAFTING_TABLE)), MDTabs.BLOCKS, AXE);
        colors("chest", chestArray, () -> new BlockChest(copy(Blocks.CHEST)), MDTabs.BLOCKS, AXE, ChestItem::new);
        colors("glasspane", glassPaneArray, () -> new IronBarsBlock(copy(Blocks.GLASS_PANE)), MDTabs.BLOCKS, null);
        colors("glassfoggypane", glassFoggyPaneArray, () -> new IronBarsBlock(copy(Blocks.GLASS_PANE)), MDTabs.BLOCKS, null);
        colors("bookshelf", bookshelfArray, () -> new BlockBookshelf(copy(Blocks.BOOKSHELF)), MDTabs.BLOCKS, AXE);
        colors("tulip", tulipArray, () -> new FlowerBlock(copy(Blocks.RED_TULIP)), MDTabs.PLANTS, null);
        colors("redstone", redstoneArray, () -> new PoweredBlock(copy(Blocks.REDSTONE_BLOCK)), MDTabs.BLOCKS, PICKAXE);
        colors("oaklog", oakLogArray, () -> new Block(copy(Blocks.OAK_LOG)), MDTabs.TREES, AXE);
        colors("sandstone", sandstoneArray, () -> new Block(copy(Blocks.SANDSTONE)), MDTabs.BLOCKS, PICKAXE);
        colors("sandstonecarved", sandstoneCarvedArray, () -> new Block(copy(Blocks.CHISELED_SANDSTONE)), MDTabs.BLOCKS, PICKAXE);
        colors("sandstonesmooth", sandstoneSmoothArray, () -> new Block(copy(Blocks.CUT_SANDSTONE)), MDTabs.BLOCKS, PICKAXE);
        colors("andesite", andesiteArray, () -> new Block(copy(Blocks.ANDESITE)), MDTabs.BLOCKS, PICKAXE);
        colors("diorite", dioriteArray, () -> new Block(copy(Blocks.DIORITE)), MDTabs.BLOCKS, PICKAXE);
        colors("concretepowder", concretePowderArray, () -> new FallingBlock(copy(Blocks.WHITE_CONCRETE_POWDER)), MDTabs.BLOCKS, SHOVEL);
        colors("hardendclay", hardenedClayArray, () -> new Block(copy(Blocks.TERRACOTTA)), MDTabs.BLOCKS, PICKAXE);
        colors("concrete", concreteArray, () -> new Block(copy(Blocks.WHITE_CONCRETE)), MDTabs.BLOCKS, PICKAXE);

        wood("acacia", acaciaLogArray, acaciaLeafArray, acaciaPlankArray, Blocks.ACACIA_LOG, Blocks.ACACIA_LEAVES, Blocks.ACACIA_PLANKS);
        wood("birch", birchLogArray, birchLeafArray, birchPlankArray, Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, Blocks.BIRCH_PLANKS);
        fence("birch", birchFenceArray, Blocks.BIRCH_FENCE);
        wood("jungle", jungleLogArray, jungleLeafArray, junglePlankArray, Blocks.JUNGLE_LOG, Blocks.JUNGLE_LEAVES, Blocks.JUNGLE_PLANKS);
        wood("spruce", spruceLogArray, spruceLeafArray, sprucePlankArray, Blocks.SPRUCE_LOG, Blocks.SPRUCE_LEAVES, Blocks.SPRUCE_PLANKS);
        wood("darkoak", darkOakLogArray, darkOakLeafArray, darkOakPlankArray, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_LEAVES, Blocks.DARK_OAK_PLANKS);
        fence("jungle", jungleFenceArray, Blocks.JUNGLE_FENCE);
        fence("acacia", acaciaFenceArray, Blocks.ACACIA_FENCE);
        fence("darkoak", darkOakFenceArray, Blocks.DARK_OAK_FENCE);
        fence("spruce", spruceFenceArray, Blocks.SPRUCE_FENCE);
        fence("oak", oakFenceArray, Blocks.OAK_FENCE);
        sapling("oak", oakSaplingArray);
        sapling("acacia", acaciaSaplingArray);
        sapling("jungle", jungleSaplingArray);
        sapling("spruce", spruceSaplingArray);
        sapling("birch", birchSaplingArray);
        sapling("dark_oak", darkOakSaplingArray);

        registerPistons();
        registerMoreBlocks();
        registerFlowers();
        DyedShapes.registerAll();
    }

    /**
     * The vanilla block's properties. Its map color is fixed to its default one, since logs and pillars pick theirs from
     * an axis that not every dyed block has.
     */
    static BlockBehaviour.Properties copy(Block vanilla)
    {
        return BlockBehaviour.Properties.copy(vanilla).mapColor(vanilla.defaultMapColor());
    }

    private static void wood(String wood, Block[] logs, LeavesBlock[] leaves, Block[] planks, Block log, Block leaf, Block plank)
    {
        colors(wood + "log", logs, () -> new Block(copy(log)), MDTabs.TREES, AXE);
        colors(wood + "leaves", leaves, () -> new LeavesBlock(copy(leaf)), MDTabs.TREES, HOE);
        colors(wood + "planks", planks, () -> new Block(copy(plank)), MDTabs.TREES, AXE);
    }

    private static void fence(String wood, FenceBlock[] fences, Block vanilla)
    {
        colors(wood + "fence", fences, () -> new FenceBlock(copy(vanilla)), MDTabs.TREES, AXE);
    }

    /** "dark_oak" saplings are named "saplingdarkoak_<color>". */
    private static void sapling(String wood, BlockSapling[] saplings)
    {
        for (int i = 0; i < COLORS; i++)
        {
            final int color = i;
            String name = "sapling" + wood.replace("_", "") + "_" + ColorStrings.ALL[i];
            register(name, saplings, i, () -> new BlockSapling(DyeTrees.grower(wood, color), copy(Blocks.OAK_SAPLING)), MDTabs.TREES, BlockItem::new);
        }
    }

    private static void registerMoreBlocks()
    {
        colors("granite", graniteArray, () -> new Block(copy(Blocks.GRANITE)), MDTabs.BLOCKS, PICKAXE);
        colors("polishedandesite", polishedAndesiteArray, () -> new Block(copy(Blocks.POLISHED_ANDESITE)), MDTabs.BLOCKS, PICKAXE);
        colors("polisheddiorite", polishedDioriteArray, () -> new Block(copy(Blocks.POLISHED_DIORITE)), MDTabs.BLOCKS, PICKAXE);
        colors("polishedgranite", polishedGraniteArray, () -> new Block(copy(Blocks.POLISHED_GRANITE)), MDTabs.BLOCKS, PICKAXE);
        colors("endstone", endstoneArray, () -> new Block(copy(Blocks.END_STONE)), MDTabs.BLOCKS, PICKAXE);
        colors("mossycobble", mossyCobbleArray, () -> new Block(copy(Blocks.MOSSY_COBBLESTONE)), MDTabs.BLOCKS, PICKAXE);
        colors("mossystonebrick", mossyStonebrickArray, () -> new Block(copy(Blocks.MOSSY_STONE_BRICKS)), MDTabs.BLOCKS, PICKAXE);
        colors("quartzbricks", quartzBricksArray, () -> new Block(copy(Blocks.QUARTZ_BRICKS)), MDTabs.BLOCKS, PICKAXE);
        colors("quartzchiseled", quartzChiseledArray, () -> new Block(copy(Blocks.CHISELED_QUARTZ_BLOCK)), MDTabs.BLOCKS, PICKAXE);
        colors("quartzpillar", quartzPillarArray, () -> new RotatedPillarBlock(copy(Blocks.QUARTZ_PILLAR)), MDTabs.BLOCKS, PICKAXE);
        colors("quartzsmooth", quartzSmoothArray, () -> new Block(copy(Blocks.SMOOTH_QUARTZ)), MDTabs.BLOCKS, PICKAXE);
        colors("boneblock", boneBlockArray, () -> new RotatedPillarBlock(copy(Blocks.BONE_BLOCK)), MDTabs.BLOCKS, PICKAXE);
        colors("gravel", gravelArray, () -> new FallingBlock(copy(Blocks.GRAVEL)), MDTabs.BLOCKS, SHOVEL);
        colors("ice", iceArray, () -> new IceBlock(copy(Blocks.ICE)), MDTabs.BLOCKS, PICKAXE);
        colors("packedice", packedIceArray, () -> new Block(copy(Blocks.PACKED_ICE)), MDTabs.BLOCKS, PICKAXE);
        colors("snow", snowArray, () -> new Block(copy(Blocks.SNOW_BLOCK)), MDTabs.BLOCKS, SHOVEL);
    }

    private static void registerFlowers()
    {
        for (int f = 0; f < SMALL_FLOWERS.length; f++)
        {
            colors(SMALL_FLOWERS[f][0], smallFlowerArrays[f], () -> new FlowerBlock(copy(Blocks.POPPY)), MDTabs.PLANTS, null);
        }
        for (int f = 0; f < TALL_FLOWERS.length; f++)
        {
            colors(TALL_FLOWERS[f][0], tallFlowerArrays[f], () -> new TallFlowerBlock(copy(Blocks.LILAC)), MDTabs.PLANTS, null);
        }
    }

    /** A piston and a sticky piston for each color, sharing one head block that has both kinds like vanilla. */
    private static void registerPistons()
    {
        MINEABLE.put(pistonArray, PICKAXE);
        MINEABLE.put(stickyPistonArray, PICKAXE);
        MINEABLE.put(pistonHeadArray, PICKAXE);
        for (int i = 0; i < COLORS; i++)
        {
            final int color = i;
            String hex = ColorStrings.ALL[i];
            register("piston_" + hex, pistonArray, i, () -> new BlockPiston(false, color), MDTabs.BLOCKS, BlockItem::new);
            register("stickypiston_" + hex, stickyPistonArray, i, () -> new BlockPiston(true, color), MDTabs.BLOCKS, BlockItem::new);
            register("pistonhead_" + hex, pistonHeadArray, i, () -> new BlockPistonHead(color), null, null);
        }
    }

    private static <B extends Block> void colors(String type, B[] array, Supplier<B> factory, MDTabs tab, TagKey<Block> tool)
    {
        colors(type, array, factory, tab, tool, BlockItem::new);
    }

    /** Makes one block of a type per color, named "<type>_<color>", with an item in the given tab. */
    private static <B extends Block> void colors(String type, B[] array, Supplier<B> factory, MDTabs tab, TagKey<Block> tool, ItemFactory item)
    {
        if (tool != null)
        {
            MINEABLE.put(array, tool);
        }
        for (int i = 0; i < COLORS; i++)
        {
            register(type + "_" + ColorStrings.ALL[i], array, i, factory, tab, item);
        }
    }

    /** Registers one block, and its item in the tab unless the tab is null. */
    static <B extends Block> RegistryObject<B> register(String name, B[] array, int index, Supplier<B> factory, MDTabs tab, ItemFactory item)
    {
        RegistryObject<B> block = BLOCKS.register(name, () -> array[index] = factory.get());
        if (tab != null)
        {
            tab.add(ITEMS.register(name, () -> item.create(array[index], new Item.Properties())));
        }
        return block;
    }

    /** All the dyed blocks, once they are registered. */
    public static List<Block> all()
    {
        List<Block> blocks = new ArrayList<>();
        for (RegistryObject<Block> entry : BLOCKS.getEntries())
        {
            blocks.add(entry.get());
        }
        return blocks;
    }

    @FunctionalInterface
    public interface ItemFactory
    {
        Item create(Block block, Item.Properties properties);
    }
}
