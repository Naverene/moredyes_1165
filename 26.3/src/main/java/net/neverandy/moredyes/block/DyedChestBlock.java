package net.neverandy.moredyes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.neverandy.moredyes.registry.ModBlockEntities;

/**
 * A dyed chest. It behaves exactly like a vanilla chest; two chests of the same color placed side by side join into
 * a double chest, because vanilla only joins chests of the same block. The client draws it with
 * {@code DyedChestRenderer}, tinted with the block's color.
 */
public class DyedChestBlock extends ChestBlock {

    public DyedChestBlock(Properties properties) {
        super(ModBlockEntities.CHEST::get, SoundEvents.CHEST_OPEN, SoundEvents.CHEST_CLOSE, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyedChestBlockEntity(pos, state);
    }
}
