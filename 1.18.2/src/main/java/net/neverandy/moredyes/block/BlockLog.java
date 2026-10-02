package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockLog extends Block
{
    public BlockLog(BlockInfo info)
    {
        super(Properties.of(info.blockMaterial)
                .strength(info.hardness, info.resistance)
                .sound(info.sound)
                .lightLevel(value -> info.lightlevel));
        WoodType.register(info.woodType);
    }
}
