package net.neverandy.moredyes.block;

import net.minecraft.block.LeavesBlock;
import net.neverandy.moredyes.utility.BlockInfo;


public class LeafBlock extends LeavesBlock
{
    public LeafBlock(BlockInfo info)
    {
        super(Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness,info.resistance)
                .harvestLevel(info.harvestLevel)
                .harvestTool(info.harvestTool)
                .sound(info.sound)
                .tickRandomly()
                .notSolid()
                .setLightLevel(value -> info.lightlevel));
    }
}