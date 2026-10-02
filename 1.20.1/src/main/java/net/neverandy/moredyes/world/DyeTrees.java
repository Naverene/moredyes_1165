package net.neverandy.moredyes.world;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.neverandy.moredyes.block.MDBlock;

/**
 * Dyed trees: the same shapes as the vanilla tree of each wood, built from the dyed log and leaves of one color.
 * Dark oak grows as a single-sapling tree with the oak shape, since the dyed saplings don't check for a 2x2.
 * The trees are built in code rather than registered, since 708 of them (6 woods in 118 colors) would each need
 * their own configured feature file.
 */
public final class DyeTrees
{
    public static final String[] WOODS = {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak"};

    private DyeTrees() {}

    /** The tree a dyed sapling grows into. */
    public static AbstractTreeGrower grower(String wood, int colorIndex)
    {
        return new AbstractTreeGrower()
        {
            @Override
            protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean flowers)
            {
                return null;
            }

            @Override
            public boolean growTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState sapling, RandomSource random)
            {
                BlockState fluid = level.getFluidState(pos).createLegacyBlock();
                level.setBlock(pos, fluid, 4);
                if (tree(wood, colorIndex).place(level, generator, random, pos))
                {
                    if (level.getBlockState(pos) == fluid)
                    {
                        level.sendBlockUpdated(pos, sapling, fluid, 2);
                    }
                    return true;
                }
                level.setBlock(pos, sapling, 4);
                return false;
            }
        };
    }

    public static ConfiguredFeature<TreeConfiguration, ?> tree(String wood, int colorIndex)
    {
        Block log = logs(wood)[colorIndex];
        Block leaves = leaves(wood)[colorIndex];
        FoliagePlacer foliage;
        TrunkPlacer trunk;
        switch (wood)
        {
            case "birch":
                foliage = new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3);
                trunk = new StraightTrunkPlacer(5, 2, 0);
                break;
            case "spruce":
                foliage = new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2));
                trunk = new StraightTrunkPlacer(5, 2, 1);
                break;
            case "jungle":
                foliage = new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3);
                trunk = new StraightTrunkPlacer(4, 8, 0);
                break;
            case "acacia":
                foliage = new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0));
                trunk = new ForkingTrunkPlacer(5, 2, 2);
                break;
            default:
                foliage = new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3);
                trunk = new StraightTrunkPlacer(4, 2, 0);
                break;
        }
        return new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(log), trunk, BlockStateProvider.simple(leaves), foliage,
                new TwoLayersFeatureSize(1, 0, wood.equals("spruce") ? 2 : 1))
                .ignoreVines().build());
    }

    public static Block[] logs(String wood)
    {
        switch (wood)
        {
            case "birch": return MDBlock.birchLogArray;
            case "spruce": return MDBlock.spruceLogArray;
            case "jungle": return MDBlock.jungleLogArray;
            case "acacia": return MDBlock.acaciaLogArray;
            case "dark_oak": return MDBlock.darkOakLogArray;
            default: return MDBlock.oakLogArray;
        }
    }

    public static Block[] leaves(String wood)
    {
        switch (wood)
        {
            case "birch": return MDBlock.birchLeafArray;
            case "spruce": return MDBlock.spruceLeafArray;
            case "jungle": return MDBlock.jungleLeafArray;
            case "acacia": return MDBlock.acaciaLeafArray;
            case "dark_oak": return MDBlock.darkOakLeafArray;
            default: return MDBlock.oakLeafArray;
        }
    }

    public static Block[] planks(String wood)
    {
        switch (wood)
        {
            case "birch": return MDBlock.birchPlankArray;
            case "spruce": return MDBlock.sprucePlankArray;
            case "jungle": return MDBlock.junglePlankArray;
            case "acacia": return MDBlock.acaciaPlankArray;
            case "dark_oak": return MDBlock.darkOakPlankArray;
            default: return MDBlock.oakPlankArray;
        }
    }

    public static Block[] saplings(String wood)
    {
        switch (wood)
        {
            case "birch": return MDBlock.birchSaplingArray;
            case "spruce": return MDBlock.spruceSaplingArray;
            case "jungle": return MDBlock.jungleSaplingArray;
            case "acacia": return MDBlock.acaciaSaplingArray;
            case "dark_oak": return MDBlock.darkOakSaplingArray;
            default: return MDBlock.oakSaplingArray;
        }
    }

    public static Block[] fences(String wood)
    {
        switch (wood)
        {
            case "birch": return MDBlock.birchFenceArray;
            case "spruce": return MDBlock.spruceFenceArray;
            case "jungle": return MDBlock.jungleFenceArray;
            case "acacia": return MDBlock.acaciaFenceArray;
            case "dark_oak": return MDBlock.darkOakFenceArray;
            default: return MDBlock.oakFenceArray;
        }
    }
}
