package net.neverandy.moredyes.block;

import net.minecraft.block.FallingBlock;
import net.neverandy.moredyes.utility.BlockInfo;

public class BlockFalling extends FallingBlock
{
    public BlockFalling(BlockInfo info)
    {
        super(Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness,info.resistance)
                .harvestLevel(info.harvestLevel)
                .harvestTool(info.harvestTool)
                .sound(info.sound)
                .setLightLevel(value -> info.lightlevel));
    }
}
