package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.IronBarsBlock;
import net.neverandy.moredyes.utility.BlockInfo;

/** A dyed glass pane. It connects to every other pane and to walls, like vanilla panes. */
public class BlockGlassPane extends IronBarsBlock
{
    public BlockGlassPane(BlockInfo info)
    {
        super(BlockBehaviour.Properties.of(info.blockMaterial)
                .strength(info.hardness, info.resistance)
                .sound(info.sound)
                .noOcclusion());
    }
}
