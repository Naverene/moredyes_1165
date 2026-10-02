package net.neverandy.moredyes;

import net.neoforged.neoforge.common.ModConfigSpec;

/** The server config, saved per world in {@code serverconfig/moredyes-server.toml}. */
public final class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue SHEEP_SPAWN_CHANCE = BUILDER
        .comment("Chance (0 to 1) that a sheep spawning in the world has a random More Dyes color")
        .defineInRange("sheep_spawn_chance", 0.05D, 0.0D, 1.0D);

    static final ModConfigSpec SPEC = BUILDER.build();

    private Config() {}
}
