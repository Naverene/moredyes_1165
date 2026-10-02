package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.FallingBlock;
import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockFalling extends FallingBlock
{
    public BlockFalling(BlockInfo info)
    {
        super(Properties.of(info.blockMaterial)
                .strength(info.hardness,info.resistance)
                .sound(info.sound)
                .lightLevel(value -> info.lightlevel));
    }
}
