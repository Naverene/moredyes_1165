package net.neverandy.moredyes.registry;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.DyedChestBlockEntity;
import net.neverandy.moredyes.block.Kind;

public final class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
        .create(Registries.BLOCK_ENTITY_TYPE, MoreDyes.MOD_ID);

    /** One type for the dyed chests of every color. */
    public static final Supplier<BlockEntityType<DyedChestBlockEntity>> CHEST = BLOCK_ENTITIES.register("chest",
        () -> new BlockEntityType<>(DyedChestBlockEntity::new, blocks(Kind.CHEST)));

    private ModBlockEntities() {}

    public static void register(IEventBus modBus) {
        BLOCK_ENTITIES.register(modBus);
    }

    private static Set<Block> blocks(Kind kind) {
        return ModBlocks.all(kind).stream().map(DeferredBlock::get).collect(Collectors.toUnmodifiableSet());
    }
}
