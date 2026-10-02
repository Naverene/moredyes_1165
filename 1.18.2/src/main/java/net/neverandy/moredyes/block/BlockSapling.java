package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockSapling extends SaplingBlock
{
    public BlockSapling(BlockInfo info, AbstractTreeGrower tree)
    {
        super(tree, Properties.of(info.blockMaterial)
                .strength(info.hardness,info.resistance)
                .sound(info.sound)
                .noCollission()
                .randomTicks()
                .lightLevel(value -> info.lightlevel));
    }
}
