package net.neverandy.moredyes.block;

import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.neverandy.moredyes.tileentity.MDChestTileEntity;
import net.neverandy.moredyes.tileentity.ModTileEntities;
import net.neverandy.moredyes.utility.BlockInfo;

/**
 * A dyed chest. It behaves exactly like a vanilla chest; two chests of the same color placed side by side join into
 * a double chest, because vanilla only joins chests of the same block. The client draws it with
 * client/ChestRenderer, tinted with the color in the registry name.
 */
public class BlockChest extends ChestBlock
{
    public BlockChest(BlockInfo info)
    {
        super(Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness, info.resistance)
                .harvestTool(info.harvestTool)
                .sound(info.sound), ModTileEntities.CHEST::get);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader world)
    {
        return new MDChestTileEntity();
    }
}
