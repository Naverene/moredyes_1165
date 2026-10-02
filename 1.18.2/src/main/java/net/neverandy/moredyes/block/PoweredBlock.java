package net.neverandy.moredyes.block;

import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class PoweredBlock extends net.minecraft.world.level.block.PoweredBlock
{
    public PoweredBlock(BlockInfo info)
    {
        super(info.requireToolIfStone(Properties.of(info.blockMaterial)
                .strength(info.hardness,info.resistance)
                .sound(info.sound)
                .lightLevel(value -> info.lightlevel)));


    }
}