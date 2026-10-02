package net.neverandy.moredyes.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MDChestTileEntity extends ChestBlockEntity
{
    public MDChestTileEntity(BlockPos pos, BlockState state)
    {
        super(ModTileEntities.CHEST.get(), pos, state);
    }
}
