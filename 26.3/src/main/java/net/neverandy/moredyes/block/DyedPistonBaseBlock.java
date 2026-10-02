package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.piston.PistonBaseBlock;

import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.registry.ModBlocks;

/**
 * A dyed piston or sticky piston. It works like the vanilla piston, except that it pushes out the piston head of its
 * own color. Vanilla hard-codes {@code minecraft:piston_head} and the two vanilla pistons in a few places; the mixins
 * in {@code PistonBaseBlockMixin} and {@code PistonHeadBlockMixin} let dyed pistons through there.
 */
public class DyedPistonBaseBlock extends PistonBaseBlock {

    private final boolean sticky;
    private final MixColor color;

    public DyedPistonBaseBlock(boolean sticky, MixColor color, Properties properties) {
        super(sticky, properties);
        this.sticky = sticky;
        this.color = color;
    }

    public boolean isSticky() {
        return sticky;
    }

    /** The piston head of this piston's color. */
    public Block head() {
        return ModBlocks.get(Kind.PISTON_HEAD, color).get();
    }
}
