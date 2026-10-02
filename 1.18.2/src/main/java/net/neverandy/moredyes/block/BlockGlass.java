package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.neverandy.moredyes.utility.BlockInfo;

public class BlockGlass extends AbstractGlassBlock
{
    public String blockName;
    public BlockGlass(BlockInfo info)
    {
        super(BlockBehaviour.Properties.of(info.blockMaterial)
                .strength(info.hardness,info.resistance)
                .sound(info.sound)
                .lightLevel(value -> info.lightlevel)
                .noOcclusion().dynamicShape());
    }
}