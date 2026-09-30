package net.neverandy.moredyes.data.client;


import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;


public class ModBlockStateProvider extends BlockStateProvider
{
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, Reference.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        MoreDyes.LOGGER.info("Starting for loop");
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            simpleBlock(blockGlass(i), texture(blockGlass(i), "glass", i));
            simpleBlock(blockSand(i), texture(blockSand(i), "sand", i));

            simpleBlock(blockBrick(i), texture(blockBrick(i), "brick", i));
            simpleBlock(blockClay(i), texture(blockClay(i), "clay", i));
            simpleBlock(blockWool(i), texture(blockWool(i), "wool", i));
            simpleBlock(blockCobble(i), texture(blockCobble(i), "cobble", i));
            simpleBlock(blockStonebrick(i), texture(blockStonebrick(i), "stonebrick", i));
            simpleBlock(blockStonebrickCracked(i), texture(blockStonebrickCracked(i), "stonebrickcracked", i));
            simpleBlock(blockStonebrickCarved(i), texture(blockStonebrickCarved(i), "stonebrickcarved", i));
            simpleBlock(blockStone(i), texture(blockStone(i), "stone", i));
            simpleBlock(blockObsidian(i), texture(blockObsidian(i), "obsidian", i));
            simpleBlock(blockLapis(i), texture(blockLapis(i), "lapis", i));
            simpleBlock(blockGlowstone(i), texture(blockGlowstone(i), "glowstone", i));
            simpleBlock(blockCoal(i), texture(blockCoal(i), "coal", i));
            simpleBlock(blockSoulsand(i), texture(blockSoulsand(i), "soulsand", i));
            simpleBlock(blockRedstone(i), texture(blockRedstone(i), "redstone", i));
            simpleBlock(blockQuartz(i), texture(blockQuartz(i), "quartz", i));
            simpleBlock(blockGlassFoggy(i), texture(blockGlassFoggy(i), "glassFoggy", i));
            simpleBlock(blockTulip(i), textureSapling(blockTulip(i), "tulip", i));
            simpleBlock(blockWorkbench(i), textureWorkbench(blockWorkbench(i), "workbench", i));
            simpleBlock(blockSandstone(i), textureSandstone(blockSandstone(i), "sandstone", i));
            simpleBlock(blockSandstoneCarved(i), textureSandstone(blockSandstoneCarved(i), "sandstonecarved", i));
            simpleBlock(blockSandstoneSmooth(i), textureSandstone(blockSandstoneSmooth(i), "sandstonesmooth", i));
            simpleBlock(blockAndesite(i), texture(blockAndesite(i), "andesite", i));
            simpleBlock(blockDiorite(i), texture(blockDiorite(i), "diorite", i));
            simpleBlock(blockConcretePowder(i), texture(blockConcretePowder(i), "concretepowder", i));
            simpleBlock(blockConcrete(i), texture(blockConcrete(i), "concrete", i));
            simpleBlock(MDBlock.hardenedClayArray[i], texture(MDBlock.hardenedClayArray[i], "hardenedclay", i));

            //Planks
            simpleBlock(blockOakPlank(i), texture(blockOakPlank(i), "oakplank", i));
            simpleBlock(blockAcaciaPlank(i), texture(blockAcaciaPlank(i), "acaciaplank", i));
            simpleBlock(blockDarkOakPlank(i), texture(blockDarkOakPlank(i), "darkoakplank", i));
            simpleBlock(blockSprucePlank(i), texture(blockSprucePlank(i), "spruceplank", i));
            simpleBlock(blockJunglePlank(i), texture(blockJunglePlank(i), "jungleplank", i));
            simpleBlock(blockBirchPlank(i), texture(blockBirchPlank(i), "birchplank", i));

            //Sapling
            simpleBlock(blockOakSapling(i), textureSapling(blockOakSapling(i), "oaksapling", i));
            simpleBlock(blockBirchSapling(i), textureSapling(blockBirchSapling(i), "birchsapling", i));
            simpleBlock(blockDarkOakSapling(i), textureSapling(blockDarkOakSapling(i), "darkoaksapling", i));
            simpleBlock(blockJungleSapling(i), textureSapling(blockJungleSapling(i), "junglesapling", i));
            simpleBlock(blockAcaciaSapling(i), textureSapling(blockAcaciaSapling(i), "acaciasapling", i));
            simpleBlock(blockSpruceSapling(i), textureSapling(blockSpruceSapling(i), "sprucesapling", i));

            //Log
            simpleBlock(blockOakLog(i), textureLog(blockOakLog(i), "oaklog", i));
            simpleBlock(blockBirchLog(i), textureLog(blockBirchLog(i), "birchlog", i));
            simpleBlock(blockDarkOakLog(i), textureLog(blockDarkOakLog(i), "darkoaklog", i));
            simpleBlock(blockJungleLog(i), textureLog(blockJungleLog(i), "junglelog", i));
            simpleBlock(blockAcaciaLog(i), textureLog(blockAcaciaLog(i), "acacialog", i));
            simpleBlock(blockSpruceLog(i), textureLog(blockSpruceLog(i), "sprucelog", i));

            //Leaves
            simpleBlock(blockOakLeaf(i), textureLeaves(blockOakLeaf(i), "oakleaf", i));
            simpleBlock(blockBirchLeaf(i), textureLeaves(blockBirchLeaf(i), "birchleaf", i));
            simpleBlock(blockDarkOakLeaf(i), textureLeaves(blockDarkOakLeaf(i), "darkoakleaf", i));
            simpleBlock(blockJungleLeaf(i), textureLeaves(blockJungleLeaf(i), "jungleleaf", i));
            simpleBlock(blockAcaciaLeaf(i), textureLeaves(blockAcaciaLeaf(i), "acacialeaf", i));
            simpleBlock(blockSpruceLeaf(i), textureLeaves(blockSpruceLeaf(i), "spruceleaf", i));

            //Fence
            fenceBlock(blockOakFence(i), textureFence(blockOakFence(i), "fenceoak", i));
            fenceBlock(blockDarkOakFence(i), textureFence(blockDarkOakFence(i), "fencedarkoak", i));
            fenceBlock(blockAcaciaFence(i), textureFence(blockAcaciaFence(i), "fenceacacia", i));
            fenceBlock(blockBirchFence(i), textureFence(blockBirchFence(i), "fencebirch", i));
            fenceBlock(blockJungleFence(i), textureFence(blockJungleFence(i), "fencejungle", i));
            fenceBlock(blockSpruceFence(i), textureFence(blockSpruceFence(i), "fencespruce", i));
        }

    }
    public ModelFile texture(@NotNull Block block, @NotNull String blockType, int arrayIndex)
    {
        ResourceLocation name = block.getRegistryName();
        assert name != null;
        ResourceLocation resourceLocation = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/" + ColorStrings.ALL[arrayIndex]);
        return models().cubeAll(block.getRegistryName().getPath(), resourceLocation);
    }

    public ModelFile textureLog(@NotNull Block block, @NotNull String blockType, int arrayIndex)
    {
        ResourceLocation name = block.getRegistryName();
        assert name != null;
        ResourceLocation top = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/top/" + ColorStrings.ALL[arrayIndex]);
        ResourceLocation side = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/side");
        switch (blockType)
        {
            case "acacialog":
                top = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/acacialog/" + ColorStrings.ALL[arrayIndex]);
                side = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/acacia_log");
                break;
            case "birchlog":
                top = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/birchlog/" + ColorStrings.ALL[arrayIndex]);
                side = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/birch_log");
                break;
            case "darkoaklog":
                top = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/darkoaklog/" + ColorStrings.ALL[arrayIndex]);
                side = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/darkoak_log");
                break;
            case "sprucelog":
                top = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/sprucelog/" + ColorStrings.ALL[arrayIndex]);
                side = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/spruce_log");
                break;
            case "junglelog":
                top = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/junglelog/" + ColorStrings.ALL[arrayIndex]);
                side = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/jungle_log");
                break;
            case "oaklog":
                top = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/oaklog/" + ColorStrings.ALL[arrayIndex]);
                side = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/oak_log");
                break;
        }
        return models().cubeColumn(block.getRegistryName().getPath(), side, top);
    }
    public ModelFile textureLeaves(@NotNull Block block, @NotNull String blockType, int arrayIndex)
    {
        ResourceLocation name = block.getRegistryName();
        assert name != null;
        ResourceLocation resourceLocation;
        switch (blockType)
        {
            case "acacialeaf":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/acacialeaf/" + ColorStrings.ALL[arrayIndex]);
                return models().cubeAll(name.getPath(), resourceLocation);
            case "birchleaf":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/birchleaf/" + ColorStrings.ALL[arrayIndex]);
                return models().cubeAll(name.getPath(), resourceLocation);
            case "darkoakleaf":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/darkoakleaf/" + ColorStrings.ALL[arrayIndex]);
                return models().cubeAll(name.getPath(), resourceLocation);
            case "spruceleaf":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/spruceleaf/" + ColorStrings.ALL[arrayIndex]);
                return models().cubeAll(name.getPath(), resourceLocation);
            case "jungleleaf":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/jungleleaf/" + ColorStrings.ALL[arrayIndex]);
                return models().cubeAll(name.getPath(), resourceLocation);
            case "oakleaf":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/oakleaf/" + ColorStrings.ALL[arrayIndex]);
                return models().cubeAll(name.getPath(), resourceLocation);
        }
        return null;
    }

    public ModelFile textureSandstone(@NotNull Block block, @NotNull String blockType, int arrayIndex)
    {
        ResourceLocation name = block.getRegistryName();
        ResourceLocation side = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/side/" + ColorStrings.ALL[arrayIndex]);
        ResourceLocation top = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/top/" + ColorStrings.ALL[arrayIndex]);
        ResourceLocation bottom = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/bottom/" + ColorStrings.ALL[arrayIndex]);

        if (blockType.equals("sandstonecarved"))
        {
            side = new ResourceLocation(name.getNamespace(),
                    ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/" + ColorStrings.ALL[arrayIndex]);
            top = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/sandstone/top/" + ColorStrings.ALL[arrayIndex]);
            bottom = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/sandstone/bottom/" + ColorStrings.ALL[arrayIndex]);
        } else if (blockType.equals("sandstonesmooth"))
        {
            side = new ResourceLocation(name.getNamespace(),
                    ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/" + ColorStrings.ALL[arrayIndex]);
            top = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/sandstone/top/" + ColorStrings.ALL[arrayIndex]);
            bottom = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/sandstone/bottom/" + ColorStrings.ALL[arrayIndex]);
        }
        return models().cubeBottomTop(block.getRegistryName().getPath(), side, bottom, top);
    }

    public ModelFile textureWorkbench(@NotNull Block block, @NotNull String blockType, int arrayIndex)
    {
        ResourceLocation name = block.getRegistryName();
        ResourceLocation top = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/top/" + ColorStrings.ALL[arrayIndex]);
        ResourceLocation side = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/side/" + ColorStrings.ALL[arrayIndex]);
        ResourceLocation bottom = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/oakplank/" + ColorStrings.ALL[arrayIndex]);
        ResourceLocation front = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase(Locale.ROOT) + "/front/" + ColorStrings.ALL[arrayIndex]);
        return models().orientableWithBottom(block.getRegistryName().getPath(), side, front, bottom, top);

    }

    public ModelFile textureSapling(Block block, String blockType, int arrayIndex)
    {
        ResourceLocation name = block.getRegistryName();
        assert name != null;
        ResourceLocation resourceLocation;

        resourceLocation = new ResourceLocation(name.getNamespace(),
                ModelProvider.BLOCK_FOLDER + "/" + blockType.toLowerCase() + "/" + ColorStrings.ALL[arrayIndex]);
        return models().cross(name.getPath(), resourceLocation);

    }
    public ResourceLocation textureFence(@NotNull Block block, @NotNull String blockType, int arrayIndex)
    {
        ResourceLocation name = block.getRegistryName();
        assert name != null;
        ResourceLocation resourceLocation;

        switch (blockType)
        {
            case "fenceoak":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/oakplank/" + ColorStrings.ALL[arrayIndex]);
                return resourceLocation;
            case "fencebirch":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/birchplank/" + ColorStrings.ALL[arrayIndex]);
                return resourceLocation;
            case "fencedarkoak":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/darkoakplank/" + ColorStrings.ALL[arrayIndex]);
                return resourceLocation;
            case "fenceacacia":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/acaciaplank/" + ColorStrings.ALL[arrayIndex]);
                return resourceLocation;
            case "fencejungle":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/jungleplank/" + ColorStrings.ALL[arrayIndex]);
                return resourceLocation;
            case "fencespruce":
                resourceLocation = new ResourceLocation(name.getNamespace(),
                        ModelProvider.BLOCK_FOLDER + "/spruceplank/" + ColorStrings.ALL[arrayIndex]);
                return resourceLocation;
        }
        return null;
    }

    public static Block blockGlass(int arrayIndex)
    {
        return MDBlock.glassArray[arrayIndex];
    }

    //Oak
    public static Block blockOakLog(int arrayIndex)
    {
        return MDBlock.oakLogArray[arrayIndex];
    }

    public static Block blockOakLeaf(int arrayIndex)
    {
        return MDBlock.oakLeafArray[arrayIndex];
    }

    public static Block blockOakPlank(int arrayIndex)
    {
        return MDBlock.oakPlankArray[arrayIndex];
    }
    private FenceBlock blockOakFence(int arrayIndex)
    {
        return MDBlock.oakFenceArray[arrayIndex];
    }

    //Dark Oak
    public static Block blockDarkOakLog(int arrayIndex)
    {
        return MDBlock.darkOakLogArray[arrayIndex];
    }

    public static Block blockDarkOakLeaf(int arrayIndex)
    {
        return MDBlock.darkOakLeafArray[arrayIndex];
    }

    public static Block blockDarkOakPlank(int arrayIndex)
    {
        return MDBlock.darkOakPlankArray[arrayIndex];
    }
    private FenceBlock blockDarkOakFence(int arrayIndex)
    {
        return MDBlock.darkOakFenceArray[arrayIndex];
    }


    //Acacia
    public static Block blockAcaciaLog(int arrayIndex)
    {
        return MDBlock.acaciaLogArray[arrayIndex];
    }

    public static Block blockAcaciaLeaf(int arrayIndex)
    {
        return MDBlock.acaciaLeafArray[arrayIndex];
    }

    public static Block blockAcaciaPlank(int arrayIndex)
    {
        return MDBlock.acaciaPlankArray[arrayIndex];
    }
    private FenceBlock blockAcaciaFence(int arrayIndex)
    {
        return MDBlock.acaciaFenceArray[arrayIndex];
    }


    //Spruce
    public static Block blockSpruceLog(int arrayIndex)
    {
        return MDBlock.spruceLogArray[arrayIndex];
    }

    public static Block blockSpruceLeaf(int arrayIndex)
    {
        return MDBlock.spruceLafArray[arrayIndex];
    }

    public static Block blockSprucePlank(int arrayIndex)
    {
        return MDBlock.sprucePlankArray[arrayIndex];
    }
    private FenceBlock blockSpruceFence(int arrayIndex)
    {
        return MDBlock.spruceFenceArray[arrayIndex];
    }

    //Birch
    public static Block blockBirchLog(int arrayIndex)
    {
        return MDBlock.birchLogArray[arrayIndex];
    }

    public static Block blockBirchLeaf(int arrayIndex)
    {
        return MDBlock.birchLeafArray[arrayIndex];
    }

    public static Block blockBirchPlank(int arrayIndex)
    {
        return MDBlock.birchPlankArray[arrayIndex];
    }
    private FenceBlock blockBirchFence(int arrayIndex)
    {
        return MDBlock.birchFenceArray[arrayIndex];
    }

    //Jungle
    public static Block blockJungleLog(int arrayIndex)
    {
        return MDBlock.jungleLogArray[arrayIndex];
    }

    public static Block blockJungleLeaf(int arrayIndex)
    {
        return MDBlock.jungleLeafArray[arrayIndex];
    }

    public static Block blockJunglePlank(int arrayIndex)
    {
        return MDBlock.junglePlankArray[arrayIndex];
    }
    private FenceBlock blockJungleFence(int arrayIndex)
    {
        return MDBlock.jungleFenceArray[arrayIndex];
    }


    public static Block blockSand(int arrayIndex)
    {
        return MDBlock.sandArray[arrayIndex];
    }

    public static Block blockBrick(int arrayIndex)
    {
        return MDBlock.brickArray[arrayIndex];
    }

    public static Block blockClay(int arrayIndex)
    {
        return MDBlock.clayArray[arrayIndex];
    }

    public static Block blockWool(int arrayIndex)
    {
        return MDBlock.woolArray[arrayIndex];
    }

    public static Block blockCobble(int arrayIndex)
    {
        return MDBlock.cobbleArray[arrayIndex];
    }

    public static Block blockStonebrick(int arrayIndex)
    {
        return MDBlock.stonebrickArray[arrayIndex];
    }

    public static Block blockStonebrickCracked(int arrayIndex)
    {
        return MDBlock.stonebrickCrackedArray[arrayIndex];
    }

    public static Block blockStonebrickCarved(int arrayIndex)
    {
        return MDBlock.stonebrickCarvedArray[arrayIndex];
    }

    public static Block blockStone(int arrayIndex)
    {
        return MDBlock.stoneArray[arrayIndex];
    }

    public static Block blockObsidian(int arrayIndex)
    {
        return MDBlock.obsidianArray[arrayIndex];
    }

    public static Block blockLapis(int arrayIndex)
    {
        return MDBlock.lapisArray[arrayIndex];
    }

    public static Block blockGlowstone(int arrayIndex)
    {
        return MDBlock.glowstoneArray[arrayIndex];
    }

    public static Block blockCoal(int arrayIndex)
    {
        return MDBlock.coalArray[arrayIndex];
    }

    public static Block blockSoulsand(int arrayIndex)
    {
        return MDBlock.soulsandArray[arrayIndex];
    }

    public static Block blockRedstone(int arrayIndex)
    {
        return MDBlock.redstoneArray[arrayIndex];
    }

    public static Block blockQuartz(int arrayIndex)
    {
        return MDBlock.quartzArray[arrayIndex];
    }

    public static Block blockGlassFoggy(int arrayIndex)
    {
        return MDBlock.glassFoggyArray[arrayIndex];
    }

    public static Block blockOakSapling(int arrayIndex)
    {
        return MDBlock.oakSaplingArray[arrayIndex];
    }
    public static Block blockDarkOakSapling(int arrayIndex)
    {
        return MDBlock.darkOakSaplingArray[arrayIndex];
    }
    public static Block blockAcaciaSapling(int arrayIndex)
    {
        return MDBlock.acaciaSaplingArray[arrayIndex];
    }
    public static Block blockSpruceSapling(int arrayIndex)
    {
        return MDBlock.spruceSaplingArray[arrayIndex];
    }
    public static Block blockJungleSapling(int arrayIndex)
    {
        return MDBlock.jungleSaplingArray[arrayIndex];
    }
    public static Block blockBirchSapling(int arrayIndex)
    {
        return MDBlock.birchSaplingArray[arrayIndex];
    }

    public static Block blockTulip(int arrayIndex)
    {
        return MDBlock.tulipArray[arrayIndex];
    }

    public static Block blockWorkbench(int arrayIndex)
    {
        return MDBlock.workbenchArray[arrayIndex];
    }

    public static Block blockSandstone(int arrayIndex)
    {
        return MDBlock.sandstoneArray[arrayIndex];
    }

    public static Block blockSandstoneCarved(int arrayIndex)
    {
        return MDBlock.sandstoneCarvedArray[arrayIndex];
    }

    public static Block blockSandstoneSmooth(int arrayIndex)
    {
        return MDBlock.sandstoneSmoothArray[arrayIndex];
    }

    public static Block blockAndesite(int arrayIndex)
    {
        return MDBlock.andesiteArray[arrayIndex];
    }

    public static Block blockDiorite(int arrayIndex)
    {
        return MDBlock.dioriteArray[arrayIndex];
    }

    public static Block blockConcretePowder(int arrayIndex)
    {
        return MDBlock.concretePowderArray[arrayIndex];
    }
    public static Block blockConcrete(int arrayIndex)
    {
        return MDBlock.concreteArray[arrayIndex];
    }


}
