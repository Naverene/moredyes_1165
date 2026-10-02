package net.neverandy.moredyes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;

import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.registry.ModBlocks;

/** The head of a dyed piston. It stays attached to the dyed piston of the same color (see PistonHeadBlockMixin). */
public class DyedPistonHeadBlock extends PistonHeadBlock {

    private final MixColor color;

    public DyedPistonHeadBlock(MixColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    /** The dyed piston of this head's color that a head of the given type belongs to. */
    public Block base(PistonType type) {
        return ModBlocks.get(type == PistonType.STICKY ? Kind.STICKY_PISTON : Kind.PISTON, color).get();
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(base(state.getValue(TYPE)));
    }
}
