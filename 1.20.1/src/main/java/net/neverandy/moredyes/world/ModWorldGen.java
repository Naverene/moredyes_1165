package net.neverandy.moredyes.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.util.RandomSource;
import net.minecraft.core.BlockPos;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neverandy.moredyes.ConfigHandler;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;

import java.util.function.Supplier;

/**
 * Scatters dye trees and dyed tulips through the overworld, like 1.7.10's TreeGenerator and FlowerGenerator.
 * The features and where they grow are data files (data/moredyes/worldgen and forge/biome_modifier, written by
 * data/server/ModWorldGenProvider). Each can be turned off with generate_trees and generate_flowers in
 * moredyes-client.toml, which the "moredyes:config" placement filter checks.
 */
public final class ModWorldGen
{
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MOD_ID);
    private static final DeferredRegister<PlacementModifierType<?>> PLACEMENTS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, Reference.MOD_ID);

    /** Places one tree of a random color and wood. */
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> DYE_TREE = FEATURES.register("dye_tree",
            () -> new Feature<>(NoneFeatureConfiguration.CODEC)
            {
                @Override
                public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
                {
                    RandomSource random = context.random();
                    String wood = DyeTrees.WOODS[random.nextInt(DyeTrees.WOODS.length)];
                    return DyeTrees.tree(wood, random.nextInt(ColorStrings.ALL.length))
                            .place(context.level(), context.chunkGenerator(), random, context.origin());
                }
            });

    public static final RegistryObject<PlacementModifierType<ConfigFilter>> CONFIG_FILTER = PLACEMENTS.register("config",
            () -> () -> ConfigFilter.CODEC);

    private ModWorldGen() {}

    public static void register(IEventBus modBus)
    {
        FEATURES.register(modBus);
        PLACEMENTS.register(modBus);
    }

    /** Which config option turns a feature on. */
    public enum Option implements StringRepresentable
    {
        TREES("trees", () -> ConfigHandler.worldGenTree.get()),
        FLOWERS("flowers", () -> ConfigHandler.worldGenFlower.get());

        public static final Codec<Option> CODEC = StringRepresentable.fromEnum(Option::values);

        private final String name;
        private final Supplier<Boolean> enabled;

        Option(String name, Supplier<Boolean> enabled)
        {
            this.name = name;
            this.enabled = enabled;
        }

        @Override
        public String getSerializedName()
        {
            return name;
        }
    }

    /** Lets a placed feature through only while its config option is on. */
    public static final class ConfigFilter extends PlacementFilter
    {
        public static final Codec<ConfigFilter> CODEC = Option.CODEC.fieldOf("option").xmap(ConfigFilter::new, filter -> filter.option).codec();

        private final Option option;

        public ConfigFilter(Option option)
        {
            this.option = option;
        }

        @Override
        protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos)
        {
            return option.enabled.get();
        }

        @Override
        public PlacementModifierType<?> type()
        {
            return CONFIG_FILTER.get();
        }
    }
}
