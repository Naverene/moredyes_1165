package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.Block;
import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BasicBlock extends Block
{
    public String blockName;
    private String color;

    public BasicBlock(BlockInfo info)
    {
        super(info.requireToolIfStone(Properties.of(info.blockMaterial)
                .strength(info.hardness, info.resistance)
                .sound(info.sound)
                .lightLevel(value -> info.lightlevel)));
        this.blockName = info.blockName;
    }
}