package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

/**
 * The head of a dyed piston. One head block per color serves both the piston and the sticky piston of that color,
 * told apart by the TYPE property like the vanilla head. Vanilla PistonHeadBlock only stays attached to vanilla
 * pistons; its own checks simply fail for a dyed base, so the dyed checks are added on top of them.
 */
public class BlockPistonHead extends PistonHeadBlock
{
    private BlockPiston piston;
    private BlockPiston stickyPiston;

    public BlockPistonHead()
    {
        super(BlockBehaviour.Properties.of(Material.PISTON).strength(1.5F).noDrops());
    }

    public void setBases(BlockPiston piston, BlockPiston stickyPiston)
    {
        this.piston = piston;
        this.stickyPiston = stickyPiston;
    }

    private BlockPiston base(BlockState head)
    {
        return head.getValue(TYPE) == PistonType.STICKY ? stickyPiston : piston;
    }

    /** Whether the block behind this head is its extended base. */
    private boolean isAttached(BlockState head, BlockState behind)
    {
        return behind.is(base(head)) && behind.getValue(PistonBaseBlock.EXTENDED) && behind.getValue(FACING) == head.getValue(FACING);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player)
    {
        BlockPos behind = pos.relative(state.getValue(FACING).getOpposite());
        if (!world.isClientSide && player.getAbilities().instabuild && isAttached(state, world.getBlockState(behind)))
        {
            world.destroyBlock(behind, false);
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, world, pos, newState, isMoving);
        BlockPos behind = pos.relative(state.getValue(FACING).getOpposite());
        if (!state.is(newState.getBlock()) && isAttached(state, world.getBlockState(behind)))
        {
            world.destroyBlock(behind, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos)
    {
        BlockState behind = world.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
        return isAttached(state, behind) || behind.is(Blocks.MOVING_PISTON) && behind.getValue(FACING) == state.getValue(FACING);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state)
    {
        return new ItemStack(base(state));
    }
}
