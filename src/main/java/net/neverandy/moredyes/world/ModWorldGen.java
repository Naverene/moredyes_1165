package net.neverandy.moredyes.world;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
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
    public static final RegistryObject<Feature<NoFeatureConfig>> DYE_TREE = FEATURES.register("dye_tree", () -> new Feature<NoFeatureConfig>(NoFeatureConfig.CODEC)
    {
        @Override
        public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
        {
            String wood = DyeTrees.WOODS[rand.nextInt(DyeTrees.WOODS.length)];
            return DyeTrees.tree(wood, rand.nextInt(ColorStrings.ALL.length)).generate(reader, generator, rand, pos);
        }
    });

    /** Biomes the trees and flowers don't grow in. */
    private static final EnumSet<Biome.Category> EXCLUDED = EnumSet.of(Biome.Category.NONE, Biome.Category.THEEND, Biome.Category.NETHER,
            Biome.Category.OCEAN, Biome.Category.RIVER, Biome.Category.BEACH, Biome.Category.DESERT, Biome.Category.MESA,
            Biome.Category.ICY, Biome.Category.MUSHROOM);

    private static ConfiguredFeature<?, ?> dyeTrees;
    private static ConfiguredFeature<?, ?> tulips;

    private ModWorldGen() {}

    public static void register(IEventBus modBus)
    {
        FEATURES.register(modBus);
    }

    /** Builds and registers the configured features. Call from common setup, after the blocks exist. */
    public static void setup()
    {
        // About one dye tree every four chunks.
        dyeTrees = register("dye_trees", DYE_TREE.get().withConfiguration(NoFeatureConfig.INSTANCE)
                .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).square().chance(4));

        WeightedBlockStateProvider anyTulip = new WeightedBlockStateProvider();
        for (Block tulip : MDBlock.tulipArray)
        {
            anyTulip.addWeightedBlockstate(tulip.getDefaultState(), 1);
        }
        // A patch of tulips about every other chunk, placed the same way as vanilla flowers.
        tulips = register("tulips", Feature.FLOWER.withConfiguration(
                new BlockClusterFeatureConfig.Builder(anyTulip, SimpleBlockPlacer.PLACER).tries(32).build())
                .withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                .chance(2));
    }

    private static ConfiguredFeature<?, ?> register(String name, ConfiguredFeature<?, ?> feature)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MOD_ID, name), feature);
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
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, dyeTrees);
        }
        if (ConfigHandler.worldGenFlower.get())
        {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, tulips);
        }
    }
}
