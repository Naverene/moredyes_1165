package net.neverandy.moredyes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;

/**
 * The head of a dyed piston. One head block per color serves both the piston and the sticky piston of that color,
 * told apart by the TYPE property like the vanilla head. Vanilla PistonHeadBlock only stays attached to vanilla
 * pistons; its own checks simply fail for a dyed base, so the dyed checks are added on top of them.
 */
public class BlockPistonHead extends PistonHeadBlock
{
    private final int color;

    public BlockPistonHead(int color)
    {
        super(MDBlock.copy(Blocks.PISTON_HEAD));
        this.color = color;
    }

    private BlockPiston base(BlockState head)
    {
        return head.getValue(TYPE) == PistonType.STICKY ? MDBlock.stickyPistonArray[color] : MDBlock.pistonArray[color];
    }

    /** Whether the block behind this head is its extended base. */
    private boolean isAttached(BlockState head, BlockState behind)
    {
        return behind.is(base(head)) && behind.getValue(PistonBaseBlock.EXTENDED) && behind.getValue(FACING) == head.getValue(FACING);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        BlockPos behind = pos.relative(state.getValue(FACING).getOpposite());
        if (!level.isClientSide && player.getAbilities().instabuild && isAttached(state, level.getBlockState(behind)))
        {
            level.destroyBlock(behind, false);
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, level, pos, newState, isMoving);
        BlockPos behind = pos.relative(state.getValue(FACING).getOpposite());
        if (!state.is(newState.getBlock()) && isAttached(state, level.getBlockState(behind)))
        {
            level.destroyBlock(behind, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState behind = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
        return isAttached(state, behind) || behind.is(Blocks.MOVING_PISTON) && behind.getValue(FACING) == state.getValue(FACING);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state)
    {
        return new ItemStack(base(state));
    }
}
