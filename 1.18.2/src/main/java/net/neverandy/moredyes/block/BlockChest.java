package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neverandy.moredyes.tileentity.MDChestTileEntity;
import net.neverandy.moredyes.tileentity.ModTileEntities;
import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

/**
 * A dyed chest. It behaves exactly like a vanilla chest; two chests of the same color placed side by side join into
 * a double chest, because vanilla only joins chests of the same block. The client draws it with
 * client/ChestRenderer, tinted with the color in the registry name.
 */
public class BlockChest extends ChestBlock
{
    public BlockChest(BlockInfo info)
    {
        super(Properties.of(info.blockMaterial)
                .strength(info.hardness, info.resistance)
                .sound(info.sound), ModTileEntities.CHEST::get);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new MDChestTileEntity(pos, state);
    }
}
