package net.neverandy.moredyes.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PaneBlock;
import net.neverandy.moredyes.utility.BlockInfo;

/** A dyed glass pane. It connects to every other pane and to walls, like vanilla panes. */
public class BlockGlassPane extends PaneBlock
{
    public BlockGlassPane(BlockInfo info)
    {
        super(AbstractBlock.Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness, info.resistance)
                .sound(info.sound)
                .notSolid());
    }
}
