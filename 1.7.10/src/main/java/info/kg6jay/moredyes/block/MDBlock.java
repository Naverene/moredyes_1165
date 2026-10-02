package info.kg6jay.moredyes.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDBlockColoredChest;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDColor;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDPiston;
import info.kg6jay.moredyes.item.MDItemBlockChest;
import info.kg6jay.moredyes.item.MDItemBlockColored;
import info.kg6jay.moredyes.item.MDItemBlockDyedSlab;
import info.kg6jay.moredyes.item.MDItemBlockTileColored;
import info.kg6jay.moredyes.reference.ColorStrings;
import info.kg6jay.moredyes.reference.Reference;
import info.kg6jay.moredyes.utility.ColorUtil;

public class MDBlock {

    public static String[][] colorStrings = new String[][] { ColorStrings.WHITE, ColorStrings.ORANGE,
        ColorStrings.MAGENTA, ColorStrings.LBLUE, ColorStrings.YELLOW, ColorStrings.LIME, ColorStrings.PINK,
        ColorStrings.DGRAY, ColorStrings.LGRAY, ColorStrings.CYAN, ColorStrings.PURPLE, ColorStrings.BLUE,
        ColorStrings.BROWN, ColorStrings.GREEN, ColorStrings.RED };
    public static String[] colors = new String[] { "white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink",
        "darkGray", "lightGray", "cyan", "purple", "blue", "brown", "green", "red" };
    public static Block[] wool;
    public static Block[] stoneBrick;
    public static Block[] stoneBrickCarved;
    public static Block[] stoneBrickCracked;
    public static Block[] obsidian;
    public static Block[] stone;
    public static Block[] cobble;
    public static Block[] soulsand;
    public static Block[] quartz;

    public static Block[] clay;
    public static Block[] coal;
    public static Block[] glowstone;
    public static Block[] lapis;
    public static Block[] redstone;

    public static Block[] plank;

    public static Block[] rockwool;
    public static Block[] tulip;
    public static Block[] log;
    public static Block[] leaf;
    public static Block[] sapling;

    public static Block[] glassClear;
    public static Block[] glassFoggy;
    public static Block[] glassClearPane;
    public static Block[] glassFoggyPane;
    public static Block[] brick;
    public static Block[] sand;
    public static Block[] workbench;
    public static Block[] chest;
    public static Block[] hardenedClay;
    public static Block[] sandstone;
    public static Block[] bookshelf;
    public static Block[] diorite;
    /** Plain granite, diorite and andesite, the bases for the dyed versions. */
    public static Block granitePlain, dioritePlain, andesitePlain;

    // Blocks from newer Minecraft versions, and the 1.7 blocks their stairs and walls are made from
    public static Block[] granite;
    public static Block[] andesite;
    public static Block[] polishedGranite;
    public static Block[] polishedDiorite;
    public static Block[] polishedAndesite;
    public static Block[] concrete;
    public static Block[] concretePowder;
    public static Block[] soulSoil;
    public static Block[] basalt;
    public static Block[] polishedBasalt;
    public static Block[] smoothStone;
    public static Block[] smoothSandstone;
    public static Block[] smoothQuartz;
    public static Block[] cutSandstone;
    public static Block[] mossyCobble;
    public static Block[] mossyStoneBrick;
    public static Block[] netherBrick;
    public static Block[] crackedNetherBrick;
    public static Block[] chiseledNetherBrick;
    public static Block[] endStoneBrick;
    public static Block[] boneBlock;
    public static Block[] glazedTerracotta;
    public static Block[] netheriteBlock;
    public static Block[] cryingObsidian;
    public static Block[] cornflower;
    public static Block[] chain;

    /**
     * Stairs, slabs and walls. Each is one block for every color, with the color in a tile entity, so these are not
     * arrays. See SHAPES for what each is made from.
     */
    public static Block stoneStairs, graniteStairs, polishedGraniteStairs, dioriteStairs, polishedDioriteStairs,
        andesiteStairs, polishedAndesiteStairs, mossyCobbleStairs, mossyStoneBrickStairs, endStoneBrickStairs,
        smoothSandstoneStairs, smoothQuartzStairs, netherBrickStairs;
    public static Block stoneSlab, cutSandstoneSlab, dioriteSlab;
    public static Block brickWall, stoneBrickWall, mossyStoneBrickWall, graniteWall, dioriteWall, andesiteWall,
        netherBrickWall, sandstoneWall, endStoneBrickWall;
    public static Block ironTrapdoor;
    /** Pistons, like trapdoors, keep their color in a tile entity. The head has no item and is shared by both. */
    public static Block piston, stickyPiston, pistonHead;

    /** A stair, slab or wall block and the dyed blocks it is crafted from. */
    public static final class Shape {

        public enum Kind {
            STAIRS,
            SLAB,
            WALL
        }

        public final Block block;
        public final Block[] base;
        public final Kind kind;

        Shape(Block block, Block[] base, Kind kind) {
            this.block = block;
            this.base = base;
            this.kind = kind;
        }
    }

    public static final List<Shape> SHAPES = new ArrayList<>();

    public static void initialize() {
        int l = colors.length;
        granitePlain = new MDBlockPlainStone(Reference.BLOCK_INFO_GRANITE, 0xC89680);
        dioritePlain = new MDBlockPlainStone(Reference.BLOCK_INFO_DIORITE, ColorUtil.WHITE);
        andesitePlain = new MDBlockPlainStone(Reference.BLOCK_INFO_ANDESITE, ColorUtil.WHITE);

        wool = new Block[l];
        stoneBrick = new Block[l];
        stoneBrickCarved = new Block[l];
        stoneBrickCracked = new Block[l];
        stone = new Block[l];
        cobble = new Block[l];
        obsidian = new Block[l];
        soulsand = new Block[l];
        quartz = new Block[l];
        clay = new Block[l];
        coal = new Block[l];
        glowstone = new Block[l];
        lapis = new Block[l];
        redstone = new Block[l];
        plank = new Block[l];
        chest = new Block[l];

        tulip = new Block[l];
        log = new Block[l];
        leaf = new Block[l];
        sapling = new Block[l];
        glassClear = new Block[l];
        glassFoggy = new Block[l];
        glassClearPane = new Block[l];
        glassFoggyPane = new Block[l];
        brick = new Block[l];
        sand = new Block[l];

        workbench = new Block[l];
        hardenedClay = new Block[l];
        sandstone = new Block[l];
        bookshelf = new Block[l];
        diorite = new Block[l];
        granite = new Block[l];
        andesite = new Block[l];
        polishedGranite = new Block[l];
        polishedDiorite = new Block[l];
        polishedAndesite = new Block[l];
        concrete = new Block[l];
        concretePowder = new Block[l];
        soulSoil = new Block[l];
        basalt = new Block[l];
        polishedBasalt = new Block[l];
        smoothStone = new Block[l];
        smoothSandstone = new Block[l];
        smoothQuartz = new Block[l];
        cutSandstone = new Block[l];
        mossyCobble = new Block[l];
        mossyStoneBrick = new Block[l];
        netherBrick = new Block[l];
        crackedNetherBrick = new Block[l];
        chiseledNetherBrick = new Block[l];
        endStoneBrick = new Block[l];
        boneBlock = new Block[l];
        glazedTerracotta = new Block[l];
        netheriteBlock = new Block[l];
        cryingObsidian = new Block[l];
        cornflower = new Block[l];
        chain = new Block[l];

        for (int i = 0; i < colors.length; i++) {
            String[] shades = colorStrings[i];
            String set = colors[i];
            wool[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_WOOL, set);
            stoneBrick[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_STONE_BRICK, set);
            stoneBrickCarved[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_STONE_BRICK_CARVED, set);
            stoneBrickCracked[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_STONE_BRICK_CRACKED, set);
            stone[i] = new MDBlockStone(shades, Reference.BLOCK_INFO_STONE, set, i);
            cobble[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_COBBLE, set);
            obsidian[i] = new MDBlockObsidian(shades, Reference.BLOCK_INFO_OBSIDIAN, set);
            soulsand[i] = new MDBlockSoulSand(shades, Reference.BLOCK_INFO_SOULSAND, set);
            quartz[i] = new MDBlockColoredMulti(shades, Reference.BLOCK_INFO_QUARTZ, set);
            clay[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_CLAY, set);
            coal[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_COAL, set);
            glowstone[i] = new MDBlockGlowstone(shades, Reference.BLOCK_INFO_GLOWSTONE, set);
            lapis[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_LAPIS, set);
            redstone[i] = new MDBlockColoredPowered(shades, Reference.BLOCK_INFO_REDSTONE, set);
            plank[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_PLANK, set);
            tulip[i] = new MDBlockFlower(shades, Reference.BLOCK_INFO_TULIP, set);
            log[i] = new MDBlockLog(shades, Reference.BLOCK_INFO_LOG, set);
            leaf[i] = new MDBlockLeaf(shades, Reference.BLOCK_INFO_LEAVES, set, i);
            sapling[i] = new MDBlockSapling(shades, Reference.BLOCK_INFO_SAPLING, set, i);
            glassClear[i] = new MDBlockGlass(shades, Reference.BLOCK_INFO_GLASS, set, i, "clear");
            glassFoggy[i] = new MDBlockGlass(shades, Reference.BLOCK_INFO_GLASS, set, i, "foggy");
            glassClearPane[i] = new MDBlockGlassPane(shades, Reference.BLOCK_INFO_GLASS, set, i, "clear");
            glassFoggyPane[i] = new MDBlockGlassPane(shades, Reference.BLOCK_INFO_GLASS, set, i, "foggy");
            brick[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_BRICK, set);
            sand[i] = new MDBlockColoredSand(shades, Reference.BLOCK_INFO_SAND, set);
            workbench[i] = new MDBlockWorkbench(shades, Reference.BLOCK_INFO_WORKBENCH, set);
            chest[i] = new MDBlockColoredChest(shades, Reference.BLOCK_INFO_CHEST, set);
            hardenedClay[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_HARDENED_CLAY, set);
            sandstone[i] = new MDBlockColoredMulti(shades, Reference.BLOCK_INFO_SANDSTONE, set);
            bookshelf[i] = new MDBlockBookshelf(shades, Reference.BLOCK_INFO_BOOKSHELF, set);
            diorite[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_DIORITE, set);

            granite[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_GRANITE, set);
            andesite[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_ANDESITE, set);
            polishedGranite[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_POLISHED_GRANITE, set);
            polishedDiorite[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_POLISHED_DIORITE, set);
            polishedAndesite[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_POLISHED_ANDESITE, set);
            concrete[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_CONCRETE, set);
            concretePowder[i] = new MDBlockConcretePowder(
                shades,
                Reference.BLOCK_INFO_CONCRETE_POWDER,
                set,
                concrete[i]);
            soulSoil[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_SOUL_SOIL, set);
            basalt[i] = new MDBlockColoredMulti(shades, Reference.BLOCK_INFO_BASALT, set);
            polishedBasalt[i] = new MDBlockColoredMulti(shades, Reference.BLOCK_INFO_POLISHED_BASALT, set);
            smoothStone[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_SMOOTH_STONE, set);
            smoothSandstone[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_SMOOTH_SANDSTONE, set);
            smoothQuartz[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_SMOOTH_QUARTZ, set);
            cutSandstone[i] = new MDBlockColoredMulti(shades, Reference.BLOCK_INFO_CUT_SANDSTONE, set);
            mossyCobble[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_MOSSY_COBBLESTONE, set);
            mossyStoneBrick[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_MOSSY_STONE_BRICKS, set);
            netherBrick[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_NETHER_BRICKS, set);
            crackedNetherBrick[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_CRACKED_NETHER_BRICKS, set);
            chiseledNetherBrick[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_CHISELED_NETHER_BRICKS, set);
            endStoneBrick[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_END_STONE_BRICKS, set);
            boneBlock[i] = new MDBlockColoredMulti(shades, Reference.BLOCK_INFO_BONE_BLOCK, set);
            glazedTerracotta[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_GLAZED_TERRACOTTA, set);
            netheriteBlock[i] = new MDBlockColored(shades, Reference.BLOCK_INFO_NETHERITE_BLOCK, set);
            cryingObsidian[i] = new MDBlockObsidian(shades, Reference.BLOCK_INFO_CRYING_OBSIDIAN, set)
                .setLightLevel(10.0F / 15.0F);
            cornflower[i] = new MDBlockFlower(shades, Reference.BLOCK_INFO_CORNFLOWER, set, "cornflower");
            chain[i] = new MDBlockChain(shades, Reference.BLOCK_INFO_CHAIN, set);
        }

        stoneStairs = stairs(stone, "stoneStairs", "stone");
        graniteStairs = stairs(granite, "graniteStairs", "granite");
        polishedGraniteStairs = stairs(polishedGranite, "polishedGraniteStairs", "polishedGranite");
        dioriteStairs = stairs(diorite, "dioriteStairs", "diorite");
        polishedDioriteStairs = stairs(polishedDiorite, "polishedDioriteStairs", "polishedDiorite");
        andesiteStairs = stairs(andesite, "andesiteStairs", "andesite");
        polishedAndesiteStairs = stairs(polishedAndesite, "polishedAndesiteStairs", "polishedAndesite");
        mossyCobbleStairs = stairs(mossyCobble, "mossyCobbleStairs", "mossyCobble");
        mossyStoneBrickStairs = stairs(mossyStoneBrick, "mossyStonebrickStairs", "mossyStonebrick");
        endStoneBrickStairs = stairs(endStoneBrick, "endStoneBrickStairs", "endStoneBrick");
        smoothSandstoneStairs = stairs(smoothSandstone, "smoothSandstoneStairs", "smoothSandstone");
        smoothQuartzStairs = stairs(smoothQuartz, "smoothQuartzStairs", "smoothQuartz");
        netherBrickStairs = stairs(netherBrick, "netherBrickStairs", "netherBrick");

        stoneSlab = slab(stone, "stoneSlab", "stone");
        cutSandstoneSlab = slab(
            cutSandstone,
            "cutSandstoneSlab",
            "cutSandstone/top",
            "cutSandstone/side",
            "cutSandstone/bottom");
        dioriteSlab = slab(diorite, "dioriteSlab", "diorite");

        brickWall = wall(brick, "brickWall", "brick");
        stoneBrickWall = wall(stoneBrick, "stonebrickWall", "stonebrick");
        mossyStoneBrickWall = wall(mossyStoneBrick, "mossyStonebrickWall", "mossyStonebrick");
        graniteWall = wall(granite, "graniteWall", "granite");
        dioriteWall = wall(diorite, "dioriteWall", "diorite");
        andesiteWall = wall(andesite, "andesiteWall", "andesite");
        netherBrickWall = wall(netherBrick, "netherBrickWall", "netherBrick");
        sandstoneWall = wall(sandstone, "sandstoneWall", "sandstone/top", "sandstone/side", "sandstone/bottom");
        endStoneBrickWall = wall(endStoneBrick, "endStoneBrickWall", "endStoneBrick");

        ironTrapdoor = new MDBlockDyedTrapdoor(Reference.BLOCK_INFO_IRON_TRAPDOOR, "ironTrapdoor");
        piston = new MDBlockDyedPiston(false, "piston");
        stickyPiston = new MDBlockDyedPiston(true, "stickyPiston");
        pistonHead = new MDBlockDyedPistonHead();
    }

    private static Block stairs(Block[] base, String name, String... textures) {
        Block block = new MDBlockDyedStairs(base[0], name, textures);
        SHAPES.add(new Shape(block, base, Shape.Kind.STAIRS));
        return block;
    }

    private static Block slab(Block[] base, String name, String... textures) {
        Block block = new MDBlockDyedSlab(base[0], name, textures);
        SHAPES.add(new Shape(block, base, Shape.Kind.SLAB));
        return block;
    }

    private static Block wall(Block[] base, String name, String... textures) {
        Block block = new MDBlockDyedWall(base[0], name, textures);
        SHAPES.add(new Shape(block, base, Shape.Kind.WALL));
        return block;
    }

    public static void register() {
        register(wool, "Wool");
        register(stoneBrick, "Stonebrick");
        register(stoneBrickCarved, "StonebrickCarved");
        register(stoneBrickCracked, "StonebrickCracked");
        register(stone, "Stone");
        register(cobble, "Cobble");
        register(obsidian, "Obsidian");
        register(soulsand, "Soulsand");
        register(quartz, "Quartz");
        register(clay, "Clay");
        register(coal, "Coal");
        register(glowstone, "Glowstone");
        register(lapis, "Lapis");
        register(redstone, "Redstone");
        register(plank, "Plank");
        register(tulip, "Tulip");
        register(log, "Log");
        register(leaf, "Leaf");
        register(sapling, "Sapling");
        register(glassClear, "GlassClear");
        register(glassFoggy, "GlassFoggy");
        register(glassClearPane, "GlassClearPane");
        register(glassFoggyPane, "GlassFoggyPane");
        register(brick, "Brick");
        register(sand, "Sand");
        register(workbench, "Workbench");
        register(hardenedClay, "HardenedClay");
        register(sandstone, "Sandstone");
        register(bookshelf, "Bookshelf");
        register(diorite, "Diorite");
        GameRegistry.registerBlock(dioritePlain, "diorite");
        for (Block block : chest) {
            GameRegistry
                .registerBlock(block, MDItemBlockChest.class, ((IBlockColored) block).getColorSet() + "MixChest");
        }

        GameRegistry.registerBlock(granitePlain, "granite");
        GameRegistry.registerBlock(andesitePlain, "andesite");
        register(granite, "Granite");
        register(andesite, "Andesite");
        register(polishedGranite, "PolishedGranite");
        register(polishedDiorite, "PolishedDiorite");
        register(polishedAndesite, "PolishedAndesite");
        register(concrete, "Concrete");
        register(concretePowder, "ConcretePowder");
        register(soulSoil, "SoulSoil");
        register(basalt, "Basalt");
        register(polishedBasalt, "PolishedBasalt");
        register(smoothStone, "SmoothStone");
        register(smoothSandstone, "SmoothSandstone");
        register(smoothQuartz, "SmoothQuartz");
        register(cutSandstone, "CutSandstone");
        register(mossyCobble, "MossyCobble");
        register(mossyStoneBrick, "MossyStonebrick");
        register(netherBrick, "NetherBrick");
        register(crackedNetherBrick, "CrackedNetherBrick");
        register(chiseledNetherBrick, "ChiseledNetherBrick");
        register(endStoneBrick, "EndStoneBrick");
        register(boneBlock, "BoneBlock");
        register(glazedTerracotta, "GlazedTerracotta");
        register(netheriteBlock, "NetheriteBlock");
        register(cryingObsidian, "CryingObsidian");
        register(cornflower, "Cornflower");
        register(chain, "Chain");

        for (Shape shape : SHAPES) {
            Class<? extends MDItemBlockTileColored> item = shape.kind == Shape.Kind.SLAB ? MDItemBlockDyedSlab.class
                : MDItemBlockTileColored.class;
            GameRegistry.registerBlock(shape.block, item, registryName(shape.block));
        }
        GameRegistry.registerBlock(ironTrapdoor, MDItemBlockTileColored.class, registryName(ironTrapdoor));
        GameRegistry.registerBlock(piston, MDItemBlockTileColored.class, registryName(piston));
        GameRegistry.registerBlock(stickyPiston, MDItemBlockTileColored.class, registryName(stickyPiston));
        GameRegistry.registerBlock(pistonHead, (Class<? extends ItemBlock>) null, registryName(pistonHead));
    }

    /** The registry name of a block named "moredyes.name" is "name". */
    private static String registryName(Block block) {
        return block.getUnlocalizedName()
            .substring(("tile." + Reference.MOD_ID + ".").length());
    }

    /**
     * Registers one block per color set as "colorSet + Mix + suffix". These names are saved in worlds, so they must
     * not change.
     */
    public static void register(Block[] blocks, String suffix) {
        for (Block block : blocks) {
            String name = ((IBlockColored) block).getColorSet() + "Mix" + suffix;
            GameRegistry.registerBlock(block, MDItemBlockColored.class, name);
        }
    }

    /** The vanilla block each dyed block turns back into when washed in a cauldron. */
    public static final Map<Block, ItemStack> WASHED = new HashMap<>();

    public static void registerWashing() {
        for (int i = 0; i < colors.length; i++) {
            WASHED.put(wool[i], new ItemStack(Blocks.wool, 1, 0));
            WASHED.put(stoneBrick[i], new ItemStack(Blocks.stonebrick, 1, 0));
            WASHED.put(stoneBrickCracked[i], new ItemStack(Blocks.stonebrick, 1, 2));
            WASHED.put(stoneBrickCarved[i], new ItemStack(Blocks.stonebrick, 1, 3));
            WASHED.put(stone[i], new ItemStack(Blocks.stone));
            WASHED.put(cobble[i], new ItemStack(Blocks.cobblestone));
            WASHED.put(obsidian[i], new ItemStack(Blocks.obsidian));
            WASHED.put(soulsand[i], new ItemStack(Blocks.soul_sand));
            WASHED.put(quartz[i], new ItemStack(Blocks.quartz_block));
            WASHED.put(clay[i], new ItemStack(Blocks.stained_hardened_clay, 1, 0));
            WASHED.put(hardenedClay[i], new ItemStack(Blocks.hardened_clay));
            WASHED.put(coal[i], new ItemStack(Blocks.coal_block));
            WASHED.put(glowstone[i], new ItemStack(Blocks.glowstone));
            WASHED.put(lapis[i], new ItemStack(Blocks.lapis_block));
            WASHED.put(redstone[i], new ItemStack(Blocks.redstone_block));
            WASHED.put(plank[i], new ItemStack(Blocks.planks, 1, 0));
            WASHED.put(log[i], new ItemStack(Blocks.log, 1, 0));
            WASHED.put(leaf[i], new ItemStack(Blocks.leaves, 1, 0));
            WASHED.put(sapling[i], new ItemStack(Blocks.sapling, 1, 0));
            WASHED.put(glassClear[i], new ItemStack(Blocks.glass));
            WASHED.put(glassClearPane[i], new ItemStack(Blocks.glass_pane));
            WASHED.put(brick[i], new ItemStack(Blocks.brick_block));
            WASHED.put(sand[i], new ItemStack(Blocks.sand, 1, 0));
            WASHED.put(workbench[i], new ItemStack(Blocks.crafting_table));
            WASHED.put(chest[i], new ItemStack(Blocks.chest));
            WASHED.put(sandstone[i], new ItemStack(Blocks.sandstone, 1, 0));
            WASHED.put(bookshelf[i], new ItemStack(Blocks.bookshelf));
            WASHED.put(diorite[i], new ItemStack(dioritePlain));
            WASHED.put(granite[i], new ItemStack(granitePlain));
            WASHED.put(andesite[i], new ItemStack(andesitePlain));
            WASHED.put(cutSandstone[i], new ItemStack(Blocks.sandstone, 1, 2));
            WASHED.put(mossyCobble[i], new ItemStack(Blocks.mossy_cobblestone));
            WASHED.put(mossyStoneBrick[i], new ItemStack(Blocks.stonebrick, 1, 1));
            WASHED.put(netherBrick[i], new ItemStack(Blocks.nether_brick));
        }
    }

    /** Same fire settings as the vanilla blocks (encouragement, flammability). */
    public static void registerFlammability() {
        for (int i = 0; i < colors.length; i++) {
            Blocks.fire.setFireInfo(wool[i], 30, 60);
            Blocks.fire.setFireInfo(plank[i], 5, 20);
            Blocks.fire.setFireInfo(log[i], 5, 5);
            Blocks.fire.setFireInfo(leaf[i], 30, 60);
            Blocks.fire.setFireInfo(bookshelf[i], 30, 20);
            Blocks.fire.setFireInfo(tulip[i], 60, 100);
            Blocks.fire.setFireInfo(coal[i], 5, 5);
            Blocks.fire.setFireInfo(cornflower[i], 60, 100);
        }
    }

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityMDBlockColoredChest.class, "MDBlockColoredChest");
        GameRegistry.registerTileEntity(TileEntityMDColor.class, "MDColor");
        GameRegistry.registerTileEntity(TileEntityMDPiston.class, "MDPiston");
    }

    /**
     * Ore dictionary names, matching the names Forge gives the vanilla blocks (and GregTech's where Forge has none),
     * so recipes from other mods accept the dyed blocks wherever they accept the vanilla ones.
     */
    public static void registerOreDictionary() {
        for (int i = 0; i < 16; i++) {
            OreDictionary.registerOre("wool", new ItemStack(Blocks.wool, 1, i));
            OreDictionary.registerOre("blockWool", new ItemStack(Blocks.wool, 1, i));
            OreDictionary.registerOre("stainedClay", new ItemStack(Blocks.stained_hardened_clay, 1, i));
        }
        OreDictionary.registerOre("soulsand", new ItemStack(Blocks.soul_sand));
        OreDictionary.registerOre("bookshelf", new ItemStack(Blocks.bookshelf));
        OreDictionary.registerOre("chest", new ItemStack(Blocks.chest));
        OreDictionary.registerOre("chestWood", new ItemStack(Blocks.chest));
        OreDictionary.registerOre("craftingTableWood", new ItemStack(Blocks.crafting_table));
        OreDictionary.registerOre("stoneDiorite", new ItemStack(dioritePlain));
        OreDictionary.registerOre("blockDiorite", new ItemStack(dioritePlain));
        OreDictionary.registerOre("stoneGranite", new ItemStack(granitePlain));
        OreDictionary.registerOre("blockGranite", new ItemStack(granitePlain));
        OreDictionary.registerOre("stoneAndesite", new ItemStack(andesitePlain));
        OreDictionary.registerOre("blockAndesite", new ItemStack(andesitePlain));

        for (int a = 0; a < colors.length; a++) {
            for (int i = 0; i <= ((IBlockColored) wool[a]).getMaxMeta(); i++) {
                ore(wool[a], i, "wool", "blockWool");
                ore(stoneBrick[a], i, "bricksStone", "stoneBricks");
                ore(stoneBrickCracked[a], i, "bricksStoneCracked");
                ore(stoneBrickCarved[a], i, "bricksStoneCarved");
                ore(obsidian[a], i, "blockObsidian", "stoneObsidian");
                ore(stone[a], i, "stone");
                ore(cobble[a], i, "cobblestone");
                ore(soulsand[a], i, "soulsand");
                ore(quartz[a], i, "blockQuartz");
                ore(redstone[a], i, "blockRedstone");
                ore(coal[a], i, "blockCoal");
                ore(plank[a], i, "plankWood");
                ore(log[a], i, "logWood");
                ore(leaf[a], i, "treeLeaves");
                ore(sapling[a], i, "treeSapling");
                ore(glowstone[a], i, "glowstone");
                ore(lapis[a], i, "blockLapis");
                ore(clay[a], i, "stainedClay");
                ore(hardenedClay[a], i, "hardenedClay");
                ore(glassClear[a], i, "blockGlass");
                ore(glassFoggy[a], i, "blockGlass");
                ore(glassClearPane[a], i, "paneGlass");
                ore(glassFoggyPane[a], i, "paneGlass");
                ore(workbench[a], i, "craftingTableWood", "workbench");
                ore(chest[a], i, "chestWood", "chest");
                ore(sand[a], i, "sand");
                ore(sandstone[a], i, "sandstone");
                ore(bookshelf[a], i, "bookshelf");
                ore(diorite[a], i, "stoneDiorite", "blockDiorite");
            }
            for (int i = 0; i <= ((IBlockColored) granite[a]).getMaxMeta(); i++) {
                ore(granite[a], i, "stoneGranite", "blockGranite");
                ore(andesite[a], i, "stoneAndesite", "blockAndesite");
                ore(polishedGranite[a], i, "stoneGranitePolished");
                ore(polishedDiorite[a], i, "stoneDioritePolished");
                ore(polishedAndesite[a], i, "stoneAndesitePolished");
                ore(smoothQuartz[a], i, "blockQuartz");
                ore(smoothSandstone[a], i, "sandstone");
                ore(cutSandstone[a], i, "sandstone");
            }
        }
    }

    private static void ore(Block block, int meta, String... names) {
        for (String name : names) {
            OreDictionary.registerOre(name, new ItemStack(block, 1, meta));
        }
    }

    /**
     * True when no other mod provides that stone, so this mod generates it in the world and adds its crafting recipe.
     * Decided in postInit, once every mod has registered its ore names.
     */
    public static boolean useOwnGranite = true, useOwnDiorite = true, useOwnAndesite = true;

    public static void detectStones() {
        useOwnGranite = !providedByOtherMod("stoneGranite", granitePlain);
        useOwnDiorite = !providedByOtherMod("stoneDiorite", dioritePlain);
        useOwnAndesite = !providedByOtherMod("stoneAndesite", andesitePlain);
    }

    private static boolean providedByOtherMod(String oreName, Block own) {
        for (ItemStack stack : OreDictionary.getOres(oreName)) {
            Block block = Block.getBlockFromItem(stack.getItem());
            if (block != own && !(block instanceof IBlockColored)) {
                return true;
            }
        }
        return false;
    }
}
