package net.neverandy.moredyes.world;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neverandy.moredyes.ConfigHandler;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;

import java.util.EnumSet;
import java.util.Random;

/**
 * Scatters dye trees and dyed tulips through the overworld, like 1.7.10's TreeGenerator and FlowerGenerator.
 * Each can be turned off with generate_trees and generate_flowers in moredyes-client.toml.
 */
public final class ModWorldGen
{
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);

    /** Places one tree of a random color and wood. */
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> DYE_TREE = FEATURES.register("dye_tree", () -> new Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC)
    {
        @Override
        public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
        {
            Random rand = context.random();
            String wood = DyeTrees.WOODS[rand.nextInt(DyeTrees.WOODS.length)];
            return DyeTrees.tree(wood, rand.nextInt(ColorStrings.ALL.length))
                    .place(context.level(), context.chunkGenerator(), rand, context.origin());
        }
    });

    /** Biomes the trees and flowers don't grow in. */
    private static final EnumSet<Biome.BiomeCategory> EXCLUDED = EnumSet.of(Biome.BiomeCategory.NONE, Biome.BiomeCategory.THEEND,
            Biome.BiomeCategory.NETHER, Biome.BiomeCategory.OCEAN, Biome.BiomeCategory.RIVER, Biome.BiomeCategory.BEACH,
            Biome.BiomeCategory.DESERT, Biome.BiomeCategory.MESA, Biome.BiomeCategory.ICY, Biome.BiomeCategory.MUSHROOM,
            Biome.BiomeCategory.UNDERGROUND);

    private static Holder<PlacedFeature> dyeTrees;
    private static Holder<PlacedFeature> tulips;

    private ModWorldGen() {}

    public static void register(IEventBus modBus)
    {
        FEATURES.register(modBus);
    }

    /** Builds and registers the configured and placed features. Call from common setup, after the blocks exist. */
    public static void setup()
    {
        // About one dye tree every four chunks.
        dyeTrees = PlacementUtils.register(Reference.MOD_ID + ":dye_trees",
                FeatureUtils.register(Reference.MOD_ID + ":dye_trees", DYE_TREE.get()),
                RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

        SimpleWeightedRandomList.Builder<BlockState> anyTulip = SimpleWeightedRandomList.builder();
        for (Block tulip : MDBlock.tulipArray)
        {
            anyTulip.add(tulip.defaultBlockState(), 1);
        }
        // A patch of tulips about every other chunk, placed the same way as vanilla flowers.
        tulips = PlacementUtils.register(Reference.MOD_ID + ":tulips",
                FeatureUtils.register(Reference.MOD_ID + ":tulips", Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(32,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(anyTulip))))),
                RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    /** Adds the features to each biome as it loads. Registered on the Forge event bus. */
    public static void onBiomeLoading(BiomeLoadingEvent event)
    {
        if (dyeTrees == null || EXCLUDED.contains(event.getCategory()))
        {
            return;
        }
        if (ConfigHandler.worldGenTree.get())
        {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, dyeTrees);
        }
        if (ConfigHandler.worldGenFlower.get())
        {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, tulips);
        }
    }
}
