package net.neverandy.moredyes.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.neverandy.moredyes.world.DyeTrees;
import net.neverandy.moredyes.client.ChestItemRenderer;
import net.neverandy.moredyes.item.ChestItem;
import net.minecraftforge.registries.ForgeRegistries;
import net.neverandy.moredyes.ConfigHandler;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.utility.BlockInfo;

import java.util.function.Supplier;

public class MDBlock
{
    private static final int totalColorCount = 118;
    public static BasicBlock[] brickArray = new BasicBlock[totalColorCount];
    public static BlockItem[] brickItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] clayArray = new BasicBlock[totalColorCount];
    public static BlockItem[] clayItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] woolArray = new BasicBlock[totalColorCount];
    public static BlockItem[] woolItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] cobbleArray = new BasicBlock[totalColorCount];
    public static BlockItem[] cobbleItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] stonebrickArray = new BasicBlock[totalColorCount];
    public static BlockItem[] stonebrickItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] stonebrickCrackedArray = new BasicBlock[totalColorCount];
    public static BlockItem[] stonebrickCrackedItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] stonebrickCarvedArray = new BasicBlock[totalColorCount];
    public static BlockItem[] stonebrickCarvedItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] stoneArray = new BasicBlock[totalColorCount];
    public static BlockItem[] stoneItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] obsidianArray = new BasicBlock[totalColorCount];
    public static BlockItem[] obsidianItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] lapisArray = new BasicBlock[totalColorCount];
    public static BlockItem[] lapisItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] glowstoneArray = new BasicBlock[totalColorCount];
    public static BlockItem[] glowstoneItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] coalArray = new BasicBlock[totalColorCount];
    public static BlockItem[] coalItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] soulsandArray = new BasicBlock[totalColorCount];
    public static BlockItem[] soulsandItemBlockArray = new BlockItem[totalColorCount];
    public static PoweredBlock[] redstoneArray = new PoweredBlock[totalColorCount];
    public static BlockItem[] redstoneItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] quartzArray = new BasicBlock[totalColorCount];
    public static BlockItem[] quartzItemBlockArray = new BlockItem[totalColorCount];
    public static BlockGlass[] glassArray = new BlockGlass[totalColorCount];
    public static BlockItem[] glassItemBlockArray = new BlockItem[totalColorCount];
    public static BlockGlass[] glassFoggyArray = new BlockGlass[totalColorCount];
    public static BlockItem[] glassFoggyItemBlockArray = new BlockItem[totalColorCount];
    public static BlockFalling[] sandArray = new BlockFalling[totalColorCount];
    public static BlockItem[] sandItemBlockArray = new BlockItem[totalColorCount];

    //Sapling
    public static BlockSapling[] oakSaplingArray = new BlockSapling[totalColorCount];
    public static BlockItem[] oakSaplingItemBlockArray = new BlockItem[totalColorCount];
    public static BlockSapling[] birchSaplingArray = new BlockSapling[totalColorCount];
    public static BlockItem[] birchSaplingItemBlockArray = new BlockItem[totalColorCount];
    public static BlockSapling[] acaciaSaplingArray = new BlockSapling[totalColorCount];
    public static BlockItem[] acaciaSaplingItemBlockArray = new BlockItem[totalColorCount];
    public static BlockSapling[] darkOakSaplingArray = new BlockSapling[totalColorCount];
    public static BlockItem[] darkOakSaplingItemBlockArray = new BlockItem[totalColorCount];
    public static BlockSapling[] jungleSaplingArray = new BlockSapling[totalColorCount];
    public static BlockItem[] jungleSaplingItemBlockArray = new BlockItem[totalColorCount];
    public static BlockSapling[] spruceSaplingArray = new BlockSapling[totalColorCount];
    public static BlockItem[] spruceSaplingItemBlockArray = new BlockItem[totalColorCount];

    public static FlowerBlock[] tulipArray = new FlowerBlock[totalColorCount];
    public static BlockItem[] tuLipItemBlockArray = new BlockItem[totalColorCount];
    public static WorkbenchBlock[] workbenchArray = new WorkbenchBlock[totalColorCount];
    public static BlockItem[] workbenchItemBlockArray = new BlockItem[totalColorCount];

    //Planks
    public static BasicBlock[] oakPlankArray = new BasicBlock[totalColorCount];
    public static BlockItem[] oakPlankItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] birchPlankArray = new BasicBlock[totalColorCount];
    public static BlockItem[] birchPlankItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] acaciaPlankArray = new BasicBlock[totalColorCount];
    public static BlockItem[] acaciaPlankItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] darkOakPlankArray = new BasicBlock[totalColorCount];
    public static BlockItem[] darkOakPlankItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] junglePlankArray = new BasicBlock[totalColorCount];
    public static BlockItem[] junglePlankItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] sprucePlankArray = new BasicBlock[totalColorCount];
    public static BlockItem[] sprucePlankItemBlockArray = new BlockItem[totalColorCount];

    //Leaves
    public static LeafBlock[] oakLeafArray = new LeafBlock[totalColorCount];
    public static BlockItem[] oakLeafItemBlockArray = new BlockItem[totalColorCount];
    public static LeafBlock[] spruceLafArray = new LeafBlock[totalColorCount];
    public static BlockItem[] spruceLeafItemBlockArray = new BlockItem[totalColorCount];
    public static LeafBlock[] jungleLeafArray = new LeafBlock[totalColorCount];
    public static BlockItem[] jungleLeafItemBlockArray = new BlockItem[totalColorCount];
    public static LeafBlock[] birchLeafArray = new LeafBlock[totalColorCount];
    public static BlockItem[] birchLeafItemBlockArray = new BlockItem[totalColorCount];
    public static LeafBlock[] acaciaLeafArray = new LeafBlock[totalColorCount];
    public static BlockItem[] acaciaLeafItemBlockArray = new BlockItem[totalColorCount];
    public static LeafBlock[] darkOakLeafArray = new LeafBlock[totalColorCount];
    public static BlockItem[] darkOakLeafItemBlockArray = new BlockItem[totalColorCount];

    //Logs
    public static BlockLog[] oakLogArray = new BlockLog[totalColorCount];
    public static BlockItem[] oakLogItemBlockArray = new BlockItem[totalColorCount];
    public static BlockLog[] birchLogArray = new BlockLog[totalColorCount];
    public static BlockItem[] birchLogItemBlockArray = new BlockItem[totalColorCount];
    public static BlockLog[] darkOakLogArray = new BlockLog[totalColorCount];
    public static BlockItem[] darkOakLogItemBlockArray = new BlockItem[totalColorCount];
    public static BlockLog[] jungleLogArray = new BlockLog[totalColorCount];
    public static BlockItem[] jungleLogItemBlockArray = new BlockItem[totalColorCount];
    public static BlockLog[] acaciaLogArray = new BlockLog[totalColorCount];
    public static BlockItem[] acaciaLogItemBlockArray = new BlockItem[totalColorCount];
    public static BlockLog[] spruceLogArray = new BlockLog[totalColorCount];
    public static BlockItem[] spruceLogItemBlockArray = new BlockItem[totalColorCount];
    
    //Fence
    public static BlockFence[] oakFenceArray = new BlockFence[totalColorCount];
    public static BlockItem[] oakFenceItemBlockArray = new BlockItem[totalColorCount];
    public static BlockFence[] birchFenceArray = new BlockFence[totalColorCount];
    public static BlockItem[] birchFenceItemBlockArray = new BlockItem[totalColorCount];
    public static BlockFence[] darkOakFenceArray = new BlockFence[totalColorCount];
    public static BlockItem[] darkOakFenceItemBlockArray = new BlockItem[totalColorCount];
    public static BlockFence[] jungleFenceArray = new BlockFence[totalColorCount];
    public static BlockItem[] jungleFenceItemBlockArray = new BlockItem[totalColorCount];
    public static BlockFence[] acaciaFenceArray = new BlockFence[totalColorCount];
    public static BlockItem[] acaciaFenceItemBlockArray = new BlockItem[totalColorCount];
    public static BlockFence[] spruceFenceArray = new BlockFence[totalColorCount];
    public static BlockItem[] spruceFenceItemBlockArray = new BlockItem[totalColorCount];


    public static BasicBlock[] sandstoneArray = new BasicBlock[totalColorCount];
    public static BlockItem[] sandstoneItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] sandstoneCarvedArray = new BasicBlock[totalColorCount];
    public static BlockItem[] sandstoneCarvedItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] sandstoneSmoothArray = new BasicBlock[totalColorCount];
    public static BlockItem[] sandstoneSmoothItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] andesiteArray = new BasicBlock[totalColorCount];
    public static BlockItem[] andesiteItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] dioriteArray = new BasicBlock[totalColorCount];
    public static BlockItem[] dioriteItemBlockArray = new BlockItem[totalColorCount];
    public static BlockFalling[] concretePowderArray = new BlockFalling[totalColorCount];
    public static BlockItem[] concretePowderItemBlockArray = new BlockItem[totalColorCount];
    public static BasicBlock[] concreteArray = new BasicBlock[totalColorCount];
    public static BlockItem[] concreteItemBlockArray = new BlockItem[totalColorCount];
    public static BlockLadder[] ladderArray = new BlockLadder[totalColorCount];
    public static BlockItem[] ladderBlockItemBlockArray = new BlockItem[totalColorCount];
    public static BlockPiston[] pistonArray = new BlockPiston[totalColorCount];
    public static BlockPiston[] stickyPistonArray = new BlockPiston[totalColorCount];
    public static BlockPistonHead[] pistonHeadArray = new BlockPistonHead[totalColorCount];
    public static BasicBlock[] hardenedClayArray = new BasicBlock[totalColorCount];
    public static BlockItem[] hardenedClayItemBlockArray = new BlockItem[totalColorCount];

    public static BlockGlassPane[] glassPaneArray = new BlockGlassPane[totalColorCount];
    public static BlockGlassPane[] glassFoggyPaneArray = new BlockGlassPane[totalColorCount];
    public static BlockBookshelf[] bookshelfArray = new BlockBookshelf[totalColorCount];
    public static BlockChest[] chestArray = new BlockChest[totalColorCount];
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);


    public static void initRegistries()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ConfigHandler.loadConfigFile(ConfigHandler.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get()
                .resolve(Reference.MOD_ID + "-client.toml").toString());
        ConfigHandler.loadConfigFile(ConfigHandler.SERVER_CONFIG, FMLPaths.CONFIGDIR.get()
                .resolve(Reference.MOD_ID + "-server.toml").toString());
    }

    public static void initialize()
    {
        //Init the registries that are added to in the rest of the calls.
        initRegistries();

        registerGlass();
        registerSand();
        registerLeaf();
        registerBrick();
        registerClay();
        registerWool();
        registerCobble();
        registerStonebrick();
        registerStonebrickCracked();
        registerStonebrickCarved();
        registerStone();
        registerObsidian();
        registerLapis();
        registerGlowstone();
        registerCoal();
        registerSoulsand();
        registerOakPlank();
        registerGlassFoggy();
        registerQuartz();
        registerWorkbench();
        registerChest();
        registerGlassPanes();
        registerBookshelves();
        registerTulip();
        registerRedstone();
        registerOakLog();
        registerSandstone();
        registerSandstoneCarved();
        registerSandstoneSmooth();
        registerAndesite();
        registerDiorite();
        registerConcretePowder();
        registerHardenedClay();
        registerConcrete();

        //Wood things
        registerAcaciaLog();
        registerAcaciaLeaves();
        registerAcaciaPlanks();
        registerBirchLog();
        registerBirchLeaves();
        registerBirchPlanks();
        registerBirchFence();
        registerJungleLog();
        registerJungleLeaves();
        registerJunglePLanks();
        registerSpruceLog();
        registerSpruceLeaves();
        registerSprucePLanks();
        registerDarkOakLog();
        registerDarkOakLeaves();
        registerDarkOakPlanks();
        registerJungleFence();
        registerAcaciaFence();
        registerDarkOakFence();
        registerSpruceFence();
        registerOakFence();
        registerOakSapling();
        registerAcaciaSapling();
        registerJungleSapling();
        registerSpruceSapling();
        registerBirchSapling();
        registerDarkOakSapling();


        //registerLadder();
        registerPistons();
    }

    private static void registerHardenedClay()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String hardenedClay_name = "hardendclay_" + color;
            final BasicBlock hardenedClay = new BasicBlock(Reference.BLOCK_INFO_TERRACOTTA);
            final BlockItem hardenedClayItem = new BlockItem(hardenedClay, new Item.Properties().group(MoreDyes.tabBlocks));
            hardenedClayArray[i] = hardenedClay;
            hardenedClayItemBlockArray[i] = hardenedClayItem;
            BLOCKS.register(hardenedClay_name, () -> hardenedClay);
            ITEMS.register(hardenedClay_name, () -> hardenedClayItem);
        }
    }

    /** A piston and a sticky piston for each color, sharing one head block that has both kinds like vanilla. */
    private static void registerPistons()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            final BlockPistonHead head = new BlockPistonHead();
            final BlockPiston piston = new BlockPiston(false);
            final BlockPiston stickyPiston = new BlockPiston(true);
            piston.setHead(head);
            stickyPiston.setHead(head);
            head.setBases(piston, stickyPiston);
            pistonArray[i] = piston;
            stickyPistonArray[i] = stickyPiston;
            pistonHeadArray[i] = head;
            BLOCKS.register("piston_" + color, () -> piston);
            BLOCKS.register("stickypiston_" + color, () -> stickyPiston);
            BLOCKS.register("pistonhead_" + color, () -> head);
            ITEMS.register("piston_" + color, () -> new BlockItem(piston, new Item.Properties().group(MoreDyes.tabBlocks)));
            ITEMS.register("stickypiston_" + color, () -> new BlockItem(stickyPiston, new Item.Properties().group(MoreDyes.tabBlocks)));
        }
    }

    private static void registerDiorite()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String diorite_name = "diorite_" + color;
            final BasicBlock diorite = new BasicBlock(Reference.BLOCK_INFO_DIORITE);
            final BlockItem dioriteItem = new BlockItem(diorite, new Item.Properties().group(MoreDyes.tabBlocks));
            dioriteArray[i] = diorite;
            dioriteItemBlockArray[i] = dioriteItem;
            BLOCKS.register(diorite_name, () -> diorite);
            ITEMS.register(diorite_name, () -> dioriteItem);
        }
    }

    private static void registerLadder()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String ladder_name = "ladder_" + color;
            final BlockLadder ladder = new BlockLadder(Reference.BLOCK_INFO_LADDER);
            final BlockItem ladderItem = new BlockItem(ladder, new Item.Properties().group(MoreDyes.tabBlocks));
            ladderArray[i] = ladder;
            ladderBlockItemBlockArray[i] = ladderItem;
            BLOCKS.register(ladder_name, () -> ladder);
            ITEMS.register(ladder_name, () -> ladderItem);
        }
    }

    private static void registerConcretePowder()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String concretePowder_name = "concretepowder_" + color;
            final BlockFalling concretePowder = new BlockFalling(Reference.BLOCK_INFO_CONCRETE_POWDER);
            final BlockItem concretePowderItem = new BlockItem(concretePowder, new Item.Properties().group(MoreDyes.tabBlocks));
            concretePowderArray[i] = concretePowder;
            concretePowderItemBlockArray[i] = concretePowderItem;
            BLOCKS.register(concretePowder_name, () -> concretePowder);
            ITEMS.register(concretePowder_name, () -> concretePowderItem);

        }
    }
    private static void registerConcrete()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String block_name = "concrete_" + color;
            final BasicBlock block = new BasicBlock(Reference.BLOCK_INFO_CONCRETE);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabBlocks));
            concreteArray[i] = block;
            concreteItemBlockArray[i] = blockItem;
            BLOCKS.register(block_name, () -> block);
            ITEMS.register(block_name, () -> blockItem);
        }
    }

    private static void registerAndesite()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String andesite_name = "andesite_" + color;
            final BasicBlock andesite = new BasicBlock(Reference.BLOCK_INFO_ANDESITE);
            final BlockItem andesiteItem = new BlockItem(andesite, new Item.Properties().group(MoreDyes.tabBlocks));
            andesiteArray[i] = andesite;
            andesiteItemBlockArray[i] = andesiteItem;
            BLOCKS.register(andesite_name, () -> andesite);
            ITEMS.register(andesite_name, () -> andesiteItem);
        }
    }

    private static void registerSandstone()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sandstone_name = "sandstone_" + color;
            final BasicBlock sandstone = new BasicBlock(Reference.BLOCK_INFO_SANDSTONE);
            final BlockItem sandstoneItem = new BlockItem(sandstone, new Item.Properties().group(MoreDyes.tabBlocks));
            sandstoneArray[i] = sandstone;
            sandstoneItemBlockArray[i] = sandstoneItem;
            BLOCKS.register(sandstone_name, () -> sandstone);
            ITEMS.register(sandstone_name, () -> sandstoneItem);
        }
    }

    private static void registerSandstoneCarved()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sandstone_name = "sandstonecarved_" + color;
            final BasicBlock sandstoneCarved = new BasicBlock(Reference.BLOCK_INFO_SANDSTONE);
            final BlockItem sandstoneCarvedItem = new BlockItem(sandstoneCarved, new Item.Properties().group(MoreDyes.tabBlocks));
            sandstoneCarvedArray[i] = sandstoneCarved;
            sandstoneCarvedItemBlockArray[i] = sandstoneCarvedItem;
            BLOCKS.register(sandstone_name, () -> sandstoneCarved);
            ITEMS.register(sandstone_name, () -> sandstoneCarvedItem);
        }
    }

    private static void registerSandstoneSmooth()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sandstone_name = "sandstonesmooth_" + color;
            final BasicBlock sandstoneSmooth = new BasicBlock(Reference.BLOCK_INFO_SANDSTONE);
            final BlockItem sandstoneSmoothItem = new BlockItem(sandstoneSmooth, new Item.Properties().group(MoreDyes.tabBlocks));
            sandstoneSmoothArray[i] = sandstoneSmooth;
            sandstoneSmoothItemBlockArray[i] = sandstoneSmoothItem;
            BLOCKS.register(sandstone_name, () -> sandstoneSmooth);
            ITEMS.register(sandstone_name, () -> sandstoneSmoothItem);
        }
    }

    private static void registerGlass()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String glass_name = "glass_" + color;
            final BlockGlass glass = new BlockGlass(Reference.BLOCK_INFO_GLASS);
            final BlockItem glassItem = new BlockItem(glass, new Item.Properties().group(MoreDyes.tabBlocks));
            glassArray[i] = glass;
            glassItemBlockArray[i] = glassItem;
            BLOCKS.register(glass_name, () -> glass);
            ITEMS.register(glass_name, () -> glassItem);

        }
    }

    private static void registerSand()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];

            String sand_name = "sand_" + color;
            final BlockFalling sand = new BlockFalling(Reference.BLOCK_INFO_SAND);
            final BlockItem sandItem = new BlockItem(sand, new Item.Properties().group(MoreDyes.tabBlocks));
            sandArray[i] = sand;
            sandItemBlockArray[i] = sandItem;
            BLOCKS.register(sand_name, () -> sand);
            ITEMS.register(sand_name, () -> sandItem);
        }
    }

    private static void registerLeaf()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];

            String leaf_name = "leaf_" + color;
            final LeafBlock leaf = new LeafBlock(Reference.BLOCK_INFO_LEAVES);
            final BlockItem leafItem = new BlockItem(leaf, new Item.Properties().group(MoreDyes.tabTrees));
            oakLeafArray[i] = leaf;
            oakLeafItemBlockArray[i] = leafItem;
            BLOCKS.register(leaf_name, () -> leaf);
            ITEMS.register(leaf_name, () -> leafItem);
        }
    }

    private static void registerBrick()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];

            String brick_name = "brick_" + color;
            final BasicBlock brick = new BasicBlock(Reference.BLOCK_INFO_BRICK);
            final BlockItem brickItem = new BlockItem(brick, new Item.Properties().group(MoreDyes.tabBlocks));
            brickArray[i] = brick;
            brickItemBlockArray[i] = brickItem;
            BLOCKS.register(brick_name, () -> brick);
            ITEMS.register(brick_name, () -> brickItem);
        }
    }

    private static void registerClay()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String clay_name = "clay_" + color;
            final BasicBlock clay = new BasicBlock(Reference.BLOCK_INFO_CLAY);
            final BlockItem clayItem = new BlockItem(clay, new Item.Properties().group(MoreDyes.tabBlocks));
            clayArray[i] = clay;
            clayItemBlockArray[i] = clayItem;
            BLOCKS.register(clay_name, () -> clay);
            ITEMS.register(clay_name, () -> clayItem);
        }
    }

    private static void registerWool()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String wool_name = "wool_" + color;
            final BasicBlock wool = new BasicBlock(Reference.BLOCK_INFO_WOOL);
            final BlockItem woolItem = new BlockItem(wool, new Item.Properties().group(MoreDyes.tabBlocks));
            woolArray[i] = wool;
            woolItemBlockArray[i] = woolItem;
            BLOCKS.register(wool_name, () -> wool);
            ITEMS.register(wool_name, () -> woolItem);
        }
    }

    private static void registerCobble()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String cobble_name = "cobble_" + color;
            final BasicBlock cobble = new BasicBlock(Reference.BLOCK_INFO_COBBLE);
            final BlockItem cobbleItem = new BlockItem(cobble, new Item.Properties().group(MoreDyes.tabBlocks));
            cobbleArray[i] = cobble;
            cobbleItemBlockArray[i] = cobbleItem;
            BLOCKS.register(cobble_name, () -> cobble);
            ITEMS.register(cobble_name, () -> cobbleItem);
        }
    }

    private static void registerStonebrick()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String stonebrick_name = "stonebrick_" + color;
            final BasicBlock stonebrick = new BasicBlock(Reference.BLOCK_INFO_STONE_BRICK);
            final BlockItem stonebrickItem = new BlockItem(stonebrick, new Item.Properties().group(MoreDyes.tabBlocks));
            stonebrickArray[i] = stonebrick;
            stonebrickItemBlockArray[i] = stonebrickItem;
            BLOCKS.register(stonebrick_name, () -> stonebrick);
            ITEMS.register(stonebrick_name, () -> stonebrickItem);
        }
    }

    private static void registerStonebrickCracked()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String stonebrickCracked_name = "stonebrickcracked_" + color;
            final BasicBlock stonebrickCracked = new BasicBlock(Reference.BLOCK_INFO_STONE_BRICK_CRACKED);
            final BlockItem stonebrickCrackedItem = new BlockItem(stonebrickCracked, new Item.Properties().group(MoreDyes.tabBlocks));
            stonebrickCrackedArray[i] = stonebrickCracked;
            stonebrickCrackedItemBlockArray[i] = stonebrickCrackedItem;
            BLOCKS.register(stonebrickCracked_name, () -> stonebrickCracked);
            ITEMS.register(stonebrickCracked_name, () -> stonebrickCrackedItem);
        }
    }

    private static void registerStonebrickCarved()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String stonebrickCarved_name = "stonebrickcarved_" + color;
            final BasicBlock stonebrickCarved = new BasicBlock(Reference.BLOCK_INFO_STONE_BRICK_CARVED);
            final BlockItem stonebrickCarvedItem = new BlockItem(stonebrickCarved, new Item.Properties().group(MoreDyes.tabBlocks));
            stonebrickCarvedArray[i] = stonebrickCarved;
            stonebrickCarvedItemBlockArray[i] = stonebrickCarvedItem;
            BLOCKS.register(stonebrickCarved_name, () -> stonebrickCarved);
            ITEMS.register(stonebrickCarved_name, () -> stonebrickCarvedItem);
        }
    }

    private static void registerStone()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String stone_name = "stone_" + color;
            final BasicBlock stone = new BasicBlock(Reference.BLOCK_INFO_STONE);
            final BlockItem stoneItem = new BlockItem(stone, new Item.Properties().group(MoreDyes.tabBlocks));
            stoneArray[i] = stone;
            stoneItemBlockArray[i] = stoneItem;
            BLOCKS.register(stone_name, () -> stone);
            ITEMS.register(stone_name, () -> stoneItem);
        }
    }

    private static void registerObsidian()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String obsidian_name = "obsidian_" + color;
            final BasicBlock obsidian = new BasicBlock(Reference.BLOCK_INFO_OBSIDIAN);
            final BlockItem obsidianItem = new BlockItem(obsidian, new Item.Properties().group(MoreDyes.tabBlocks));
            obsidianArray[i] = obsidian;
            obsidianItemBlockArray[i] = obsidianItem;
            BLOCKS.register(obsidian_name, () -> obsidian);
            ITEMS.register(obsidian_name, () -> obsidianItem);
        }
    }

    private static void registerLapis()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String lapis_name = "lapis_" + color;
            final BasicBlock lapis = new BasicBlock(Reference.BLOCK_INFO_LAPIS);
            final BlockItem lapisItem = new BlockItem(lapis, new Item.Properties().group(MoreDyes.tabBlocks));
            lapisArray[i] = lapis;
            lapisItemBlockArray[i] = lapisItem;
            BLOCKS.register(lapis_name, () -> lapis);
            ITEMS.register(lapis_name, () -> lapisItem);
        }
    }

    private static void registerGlowstone()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String glowstone_name = "glowstone_" + color;
            final BasicBlock glowstone = new BasicBlock(Reference.BLOCK_INFO_GLOWSTONE);
            final BlockItem glowstoneItem = new BlockItem(glowstone, new Item.Properties().group(MoreDyes.tabBlocks));
            glowstoneArray[i] = glowstone;
            glowstoneItemBlockArray[i] = glowstoneItem;
            BLOCKS.register(glowstone_name, () -> glowstone);
            ITEMS.register(glowstone_name, () -> glowstoneItem);
        }
    }

    private static void registerCoal()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String coal_name = "coal_" + color;
            final BasicBlock coal = new BasicBlock(Reference.BLOCK_INFO_COAL);
            final BlockItem coalItem = new BlockItem(coal, new Item.Properties().group(MoreDyes.tabBlocks));
            coalArray[i] = coal;
            coalItemBlockArray[i] = coalItem;
            BLOCKS.register(coal_name, () -> coal);
            ITEMS.register(coal_name, () -> coalItem);
        }
    }

    private static void registerSoulsand()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String soulsand_name = "soulsand_" + color;
            final BasicBlock soulsand = new BasicBlock(Reference.BLOCK_INFO_SOULSAND);
            final BlockItem soulsandItem = new BlockItem(soulsand, new Item.Properties().group(MoreDyes.tabBlocks));
            soulsandArray[i] = soulsand;
            soulsandItemBlockArray[i] = soulsandItem;
            BLOCKS.register(soulsand_name, () -> soulsand);
            ITEMS.register(soulsand_name, () -> soulsandItem);
        }
    }

    private static void registerOakPlank()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String plank_name = "plank_" + color;
            final BasicBlock plank = new BasicBlock(Reference.BLOCK_INFO_OAK_PLANKS);
            final BlockItem plankItem = new BlockItem(plank, new Item.Properties().group(MoreDyes.tabBlocks));
            oakPlankArray[i] = plank;
            oakPlankItemBlockArray[i] = plankItem;
            BLOCKS.register(plank_name, () -> plank);
            ITEMS.register(plank_name, () -> plankItem);
        }

    }

    private static void registerRedstone()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String redstone_name = "redstone_" + color;
            final PoweredBlock redstone = new PoweredBlock(Reference.BLOCK_INFO_REDSTONE);
            final BlockItem redstoneItem = new BlockItem(redstone, new Item.Properties().group(MoreDyes.tabBlocks));
            redstoneArray[i] = redstone;
            redstoneItemBlockArray[i] = redstoneItem;
            BLOCKS.register(redstone_name, () -> redstone);
            ITEMS.register(redstone_name, () -> redstoneItem);
        }

    }

    private static void registerQuartz()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String quartz_name = "quartz_" + color;
            final BasicBlock quartz = new BasicBlock(Reference.BLOCK_INFO_QUARTZ);
            final BlockItem quartzItem = new BlockItem(quartz, new Item.Properties().group(MoreDyes.tabBlocks));
            quartzArray[i] = quartz;
            quartzItemBlockArray[i] = quartzItem;
            BLOCKS.register(quartz_name, () -> quartz);
            ITEMS.register(quartz_name, () -> quartzItem);
        }

    }

    private static void registerGlassFoggy() //Doesn't work for some reason?
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String glassFoggy_name = "glassfoggy_" + color;
            final BlockGlass glassFoggy = new BlockGlass(Reference.BLOCK_INFO_GLASS_FOGGY);
            final BlockItem glassFoggyItem = new BlockItem(glassFoggy, new Item.Properties().group(MoreDyes.tabBlocks));
            glassFoggyArray[i] = glassFoggy;
            glassFoggyItemBlockArray[i] = glassFoggyItem;
            BLOCKS.register(glassFoggy_name, () -> glassFoggy);
            ITEMS.register(glassFoggy_name, () -> glassFoggyItem);
        }
    }

    private static void registerOakSapling()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sapling_name = "saplingoak_" + color;
            final BlockSapling sapling = new BlockSapling(Reference.BLOCK_INFO_OAK_SAPLING, DyeTrees.sapling("oak", i));
            final BlockItem saplingItem = new BlockItem(sapling, new Item.Properties().group(MoreDyes.tabTrees));
            oakSaplingArray[i] = sapling;
            oakSaplingItemBlockArray[i] = saplingItem;
            BLOCKS.register(sapling_name, () -> sapling);
            ITEMS.register(sapling_name, () -> saplingItem);
        }

    }
    private static void registerBirchSapling()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sapling_name = "saplingbirch_" + color;
            final BlockSapling sapling = new BlockSapling(Reference.BLOCK_INFO_BIRCH_SAPLING, DyeTrees.sapling("birch", i));
            final BlockItem saplingItem = new BlockItem(sapling, new Item.Properties().group(MoreDyes.tabTrees));
            birchSaplingArray[i] = sapling;
            birchSaplingItemBlockArray[i] = saplingItem;
            BLOCKS.register(sapling_name, () -> sapling);
            ITEMS.register(sapling_name, () -> saplingItem);
        }

    }
    private static void registerDarkOakSapling()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sapling_name = "saplingdarkoak_" + color;
            final BlockSapling sapling = new BlockSapling(Reference.BLOCK_INFO_DARK_OAK_SAPLING, DyeTrees.sapling("dark_oak", i));
            final BlockItem saplingItem = new BlockItem(sapling, new Item.Properties().group(MoreDyes.tabTrees));
            darkOakSaplingArray[i] = sapling;
            darkOakSaplingItemBlockArray[i] = saplingItem;
            BLOCKS.register(sapling_name, () -> sapling);
            ITEMS.register(sapling_name, () -> saplingItem);
        }
    }
    private static void registerAcaciaSapling()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sapling_name = "saplingacacia_" + color;
            final BlockSapling sapling = new BlockSapling(Reference.BLOCK_INFO_ACACIA_SAPLING, DyeTrees.sapling("acacia", i));
            final BlockItem saplingItem = new BlockItem(sapling, new Item.Properties().group(MoreDyes.tabTrees));
            acaciaSaplingArray[i] = sapling;
            acaciaSaplingItemBlockArray[i] = saplingItem;
            BLOCKS.register(sapling_name, () -> sapling);
            ITEMS.register(sapling_name, () -> saplingItem);
        }
    }

    private static void registerSpruceSapling()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sapling_name = "saplingspruce_" + color;
            final BlockSapling sapling = new BlockSapling(Reference.BLOCK_INFO_SPRUCE_SAPLING, DyeTrees.sapling("spruce", i));
            final BlockItem saplingItem = new BlockItem(sapling, new Item.Properties().group(MoreDyes.tabTrees));
            spruceSaplingArray[i] = sapling;
            spruceSaplingItemBlockArray[i] = saplingItem;
            BLOCKS.register(sapling_name, () -> sapling);
            ITEMS.register(sapling_name, () -> saplingItem);
        }
    }
    private static void registerJungleSapling()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String sapling_name = "saplingjungle_" + color;
            final BlockSapling sapling = new BlockSapling(Reference.BLOCK_INFO_JUNGLE_SAPLING, DyeTrees.sapling("jungle", i));
            final BlockItem saplingItem = new BlockItem(sapling, new Item.Properties().group(MoreDyes.tabTrees));
            jungleSaplingArray[i] = sapling;
            jungleSaplingItemBlockArray[i] = saplingItem;
            BLOCKS.register(sapling_name, () -> sapling);
            ITEMS.register(sapling_name, () -> saplingItem);
        }

    }
    private static void registerTulip()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String tulip_name = "tulip_" + color;
            final FlowerBlock tulip = new FlowerBlock(Reference.BLOCK_INFO_TULIP);
            final BlockItem tulipItem = new BlockItem(tulip, new Item.Properties().group(MoreDyes.tabPlants));
            tulipArray[i] = tulip;
            tuLipItemBlockArray[i] = tulipItem;
            BLOCKS.register(tulip_name, () -> tulip);
            ITEMS.register(tulip_name, () -> tulipItem);
        }
    }

    private static void registerWorkbench()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String workbench_name = "workbench_" + color;
            final WorkbenchBlock workbench = new WorkbenchBlock(Reference.BLOCK_INFO_WORKBENCH);
            final BlockItem workbenchItem = new BlockItem(workbench, new Item.Properties().group(MoreDyes.tabBlocks));
            workbenchArray[i] = workbench;
            workbenchItemBlockArray[i] = workbenchItem;
            BLOCKS.register(workbench_name, () -> workbench);
            ITEMS.register(workbench_name, () -> workbenchItem);
        }
    }

    private static void registerBookshelves()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String name = "bookshelf_" + ColorStrings.ALL[i];
            final BlockBookshelf shelf = new BlockBookshelf(Reference.BLOCK_INFO_BOOKSHELF);
            final BlockItem shelfItem = new BlockItem(shelf, new Item.Properties().group(MoreDyes.tabBlocks));
            bookshelfArray[i] = shelf;
            BLOCKS.register(name, () -> shelf);
            ITEMS.register(name, () -> shelfItem);
        }
    }

    private static void registerGlassPanes()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            glassPaneArray[i] = registerPane("glasspane_" + ColorStrings.ALL[i]);
            glassFoggyPaneArray[i] = registerPane("glassfoggypane_" + ColorStrings.ALL[i]);
        }
    }

    private static BlockGlassPane registerPane(String name)
    {
        final BlockGlassPane pane = new BlockGlassPane(Reference.BLOCK_INFO_GLASS_PANE);
        final BlockItem paneItem = new BlockItem(pane, new Item.Properties().group(MoreDyes.tabBlocks));
        BLOCKS.register(name, () -> pane);
        ITEMS.register(name, () -> paneItem);
        return pane;
    }

    private static void registerChest()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String name = "chest_" + ColorStrings.ALL[i];
            final BlockChest chest = new BlockChest(Reference.BLOCK_INFO_CHEST);
            final ChestItem chestItem = new ChestItem(chest, new Item.Properties().group(MoreDyes.tabBlocks)
                    .setISTER(() -> ChestItemRenderer::new));
            chestArray[i] = chest;
            BLOCKS.register(name, () -> chest);
            ITEMS.register(name, () -> chestItem);
        }
    }

    private static void registerOakLog()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String oaklog_name = "oaklog_" + color;
            final BlockLog log = new BlockLog(Reference.BLOCK_INFO_OAK_LOG);
            final BlockItem logItem = new BlockItem(log, new Item.Properties().group(MoreDyes.tabTrees));
            oakLogArray[i] = log;
            oakLogItemBlockArray[i] = logItem;
            BLOCKS.register(oaklog_name, () -> log);
            ITEMS.register(oaklog_name, () -> logItem);
        }
    }

    private static void registerBirchLog()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String log_name = "birchlog_" + color;
            final BlockLog log = new BlockLog(Reference.BLOCK_INFO_BIRCH_LOG);
            final BlockItem logItem = new BlockItem(log, new Item.Properties().group(MoreDyes.tabTrees));
            birchLogArray[i] = log;
            birchLogItemBlockArray[i] = logItem;
            BLOCKS.register(log_name, () -> log);
            ITEMS.register(log_name, () -> logItem);
        }
    }

    private static void registerBirchLeaves()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String log_name = "birchleaves_" + color;
            final LeafBlock leaves = new LeafBlock(Reference.BLOCK_INFO_BIRCH_LEAVES);
            final BlockItem leavesItem = new BlockItem(leaves, new Item.Properties().group(MoreDyes.tabTrees));
            birchLeafArray[i] = leaves;
            birchLeafItemBlockArray[i] = leavesItem;
            BLOCKS.register(log_name, () -> leaves);
            ITEMS.register(log_name, () -> leavesItem);
        }
    }

    private static void registerBirchPlanks()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "birchplanks_" + color;
            final BasicBlock block = new BasicBlock(Reference.BLOCK_INFO_BIRCH_PLANKS);
            final BlockItem leavesItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            birchPlankArray[i] = block;
            birchPlankItemBlockArray[i] = leavesItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> leavesItem);
        }
    }

    private static void registerJungleLog()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String log_name = "junglelog_" + color;
            final BlockLog log = new BlockLog(Reference.BLOCK_INFO_JUNGLE_LOG);
            final BlockItem logItem = new BlockItem(log, new Item.Properties().group(MoreDyes.tabTrees));
            jungleLogArray[i] = log;
            jungleLogItemBlockArray[i] = logItem;
            BLOCKS.register(log_name, () -> log);
            ITEMS.register(log_name, () -> logItem);
        }
    }

    private static void registerJunglePLanks()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "jungleplanks_" + color;
            final BasicBlock block = new BasicBlock(Reference.BLOCK_INFO_JUNGLE_PLANKS);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            junglePlankArray[i] = block;
            junglePlankItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }

    private static void registerJungleLeaves()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "jungleleaves_" + color;
            final LeafBlock block = new LeafBlock(Reference.BLOCK_INFO_BIRCH_PLANKS);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            jungleLeafArray[i] = block;
            jungleLeafItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }

    private static void registerAcaciaLog()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String log_name = "acacialog_" + color;
            final BlockLog log = new BlockLog(Reference.BLOCK_INFO_ACACIA_LOG);
            final BlockItem logItem = new BlockItem(log, new Item.Properties().group(MoreDyes.tabTrees));
            acaciaLogArray[i] = log;
            acaciaLogItemBlockArray[i] = logItem;
            BLOCKS.register(log_name, () -> log);
            ITEMS.register(log_name, () -> logItem);
        }
    }

    private static void registerAcaciaPlanks()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "acaciaplanks_" + color;
            final BasicBlock block = new BasicBlock(Reference.BLOCK_INFO_BIRCH_PLANKS);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            acaciaPlankArray[i] = block;
            acaciaPlankItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
    private static void registerBlocksNotWorking(String blockname, BlockInfo info, Supplier<? extends Block> c) //Do Not use
    {//TODO: Figure out Lambda and pass class/function as a parameter instead
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = blockname + color;
            final LeafBlock block = new LeafBlock(Reference.BLOCK_INFO_ACACIA_LEAVES);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            acaciaLeafArray[i] = block;
            acaciaLeafItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }

    private static void registerAcaciaLeaves()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "acacialeaves_" + color;
            final LeafBlock block = new LeafBlock(Reference.BLOCK_INFO_ACACIA_LEAVES);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            acaciaLeafArray[i] = block;
            acaciaLeafItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }

    private static void registerSpruceLog()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String log_name = "sprucelog_" + color;
            final BlockLog log = new BlockLog(Reference.BLOCK_INFO_SPRUCE_LOG);
            final BlockItem logItem = new BlockItem(log, new Item.Properties().group(MoreDyes.tabTrees));
            spruceLogArray[i] = log;
            spruceLogItemBlockArray[i] = logItem;
            BLOCKS.register(log_name, () -> log);
            ITEMS.register(log_name, () -> logItem);
        }
    }

    private static void registerSprucePLanks()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "spruceplanks_" + color;
            final BasicBlock block = new BasicBlock(Reference.BLOCK_INFO_SPRUCE_PLANKS);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            sprucePlankArray[i] = block;
            sprucePlankItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
    private static void registerSpruceFence()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "sprucefence_" + color;
            final BlockFence block = new BlockFence(Reference.BLOCK_INFO_SPRUCE_FENCE);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            spruceFenceArray[i] = block;
            spruceFenceItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
    private static void registerBirchFence()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "birchfence_" + color;
            final BlockFence block = new BlockFence(Reference.BLOCK_INFO_SPRUCE_FENCE);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            birchFenceArray[i] = block;
            birchFenceItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
    private static void registerOakFence()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "oakfence_" + color;
            final BlockFence block = new BlockFence(Reference.BLOCK_INFO_OAK_FENCE);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            oakFenceArray[i] = block;
            oakFenceItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
    private static void registerDarkOakFence()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "darkoakfence_" + color;
            final BlockFence block = new BlockFence(Reference.BLOCK_INFO_DARK_OAK_FENCE);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            darkOakFenceArray[i] = block;
            darkOakFenceItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
    private static void registerAcaciaFence()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "acaciafence_" + color;
            final BlockFence block = new BlockFence(Reference.BLOCK_INFO_ACACIA_FENCE);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            acaciaFenceArray[i] = block;
            acaciaFenceItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
    private static void registerJungleFence()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "junglefence_" + color;
            final BlockFence block = new BlockFence(Reference.BLOCK_INFO_JUNGLE_FENCE);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            jungleFenceArray[i] = block;
            jungleFenceItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
    private static void registerSpruceLeaves()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "spruceleaves_" + color;
            final LeafBlock block = new LeafBlock(Reference.BLOCK_INFO_SPRUCE_LEAVES);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            spruceLafArray[i] = block;
            spruceLeafItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }

    private static void registerDarkOakLog()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String log_name = "darkoaklog_" + color;
            final BlockLog log = new BlockLog(Reference.BLOCK_INFO_DARK_OAK_LOG);
            final BlockItem logItem = new BlockItem(log, new Item.Properties().group(MoreDyes.tabTrees));
            darkOakLogArray[i] = log;
            darkOakLogItemBlockArray[i] = logItem;
            BLOCKS.register(log_name, () -> log);
            ITEMS.register(log_name, () -> logItem);
        }
    }

    private static void registerDarkOakPlanks()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "darkoakplanks_" + color;
            final BasicBlock block = new BasicBlock(Reference.BLOCK_INFO_DARK_OAK_PLANKS);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            darkOakPlankArray[i] = block;
            darkOakPlankItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }

    private static void registerDarkOakLeaves()
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            String color = ColorStrings.ALL[i];
            String blockName = "darkoakleaves_" + color;
            final LeafBlock block = new LeafBlock(Reference.BLOCK_INFO_DARK_OAK_LEAVES);
            final BlockItem blockItem = new BlockItem(block, new Item.Properties().group(MoreDyes.tabTrees));
            darkOakLeafArray[i] = block;
            darkOakLeafItemBlockArray[i] = blockItem;
            BLOCKS.register(blockName, () -> block);
            ITEMS.register(blockName, () -> blockItem);
        }
    }
}