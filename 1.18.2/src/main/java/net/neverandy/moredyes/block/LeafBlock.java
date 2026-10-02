package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.LeavesBlock;
import net.neverandy.moredyes.utility.BlockInfo;


import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class LeafBlock extends LeavesBlock
{
    public LeafBlock(BlockInfo info)
    {
        super(Properties.of(info.blockMaterial)
                .strength(info.hardness,info.resistance)
                .sound(info.sound)
                .randomTicks()
                .noOcclusion()
                .lightLevel(value -> info.lightlevel));
    }
}