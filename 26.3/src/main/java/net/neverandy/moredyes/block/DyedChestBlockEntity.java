package net.neverandy.moredyes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.neverandy.moredyes.registry.ModBlockEntities;

/** The vanilla chest block entity under its own type, so the client can draw dyed chests with their own renderer. */
public class DyedChestBlockEntity extends ChestBlockEntity {

    public DyedChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHEST.get(), pos, state);
    }
}
