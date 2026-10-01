package net.neverandy.moredyes.world;

import net.minecraft.block.Block;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.neverandy.moredyes.block.MDBlock;

import java.util.Random;

/**
 * Dyed trees: the same shapes as the vanilla tree of each wood, built from the dyed log and leaves of one color.
 * Dark oak grows as a single-sapling tree with the oak shape, since the dyed saplings don't check for a 2x2.
 */
public final class DyeTrees
{
    public static final String[] WOODS = {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak"};

    private DyeTrees() {}

    /** The tree a dyed sapling grows into. */
    public static Tree sapling(String wood, int colorIndex)
    {
        return new Tree()
        {
            @Override
            protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random rand, boolean largeHive)
            {
                return tree(wood, colorIndex);
            }
        };
    }

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> tree(String wood, int colorIndex)
    {
        Block log = logs(wood)[colorIndex];
        Block leaves = leaves(wood)[colorIndex];
        FoliagePlacer foliage;
        AbstractTrunkPlacer trunk;
        switch (wood)
        {
            case "birch":
                foliage = new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3);
                trunk = new StraightTrunkPlacer(5, 2, 0);
                break;
            case "spruce":
                foliage = new SpruceFoliagePlacer(FeatureSpread.create(2, 1), FeatureSpread.create(0, 2), FeatureSpread.create(1, 1));
                trunk = new StraightTrunkPlacer(5, 2, 1);
                break;
            case "jungle":
                foliage = new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3);
                trunk = new StraightTrunkPlacer(4, 8, 0);
                break;
            case "acacia":
                foliage = new AcaciaFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0));
                trunk = new ForkyTrunkPlacer(5, 2, 2);
                break;
            default:
                foliage = new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3);
                trunk = new StraightTrunkPlacer(4, 2, 0);
                break;
        }
        return Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(log.getDefaultState()),
                new SimpleBlockStateProvider(leaves.getDefaultState()),
                foliage, trunk, new TwoLayerFeature(1, 0, wood.equals("spruce") ? 2 : 1))
                .setIgnoreVines().build());
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
            case "spruce": return MDBlock.spruceLafArray;
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
