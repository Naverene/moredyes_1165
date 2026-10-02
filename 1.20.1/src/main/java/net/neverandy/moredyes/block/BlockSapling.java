package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

/** A dyed sapling; it grows into a dyed tree of its own color (see world/DyeTrees). */
public class BlockSapling extends SaplingBlock
{
    public BlockSapling(AbstractTreeGrower tree, Properties properties)
    {
        super(tree, properties);
    }
}
