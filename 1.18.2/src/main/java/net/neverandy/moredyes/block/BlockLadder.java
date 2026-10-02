package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.LadderBlock;
import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockLadder extends LadderBlock
{
    public BlockLadder(BlockInfo info)
    {
        super(Properties.of(info.blockMaterial)
                .strength(info.hardness, info.resistance)
                .sound(info.sound)
                .lightLevel(value -> info.lightlevel));
    }
}
