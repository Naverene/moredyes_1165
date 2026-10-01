package net.neverandy.moredyes.block;

import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.neverandy.moredyes.utility.BlockInfo;

public class BlockLog extends Block
{
    public BlockLog(BlockInfo info)
    {
        super(Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness, info.resistance)
                .harvestLevel(info.harvestLevel)
                .harvestTool(info.harvestTool)
                .sound(info.sound)
                .setLightLevel(value -> info.lightlevel));
        WoodType.register(info.woodType);
    }
}
