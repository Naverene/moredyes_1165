package net.neverandy.moredyes.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.PistonType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

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
        super(AbstractBlock.Properties.create(Material.PISTON).hardnessAndResistance(1.5F).noDrops());
    }

    public void setBases(BlockPiston piston, BlockPiston stickyPiston)
    {
        this.piston = piston;
        this.stickyPiston = stickyPiston;
    }

    private BlockPiston base(BlockState head)
    {
        return head.get(TYPE) == PistonType.STICKY ? stickyPiston : piston;
    }

    /** Whether the block behind this head is its extended base. */
    private boolean isAttached(BlockState head, BlockState behind)
    {
        return behind.matchesBlock(base(head)) && behind.get(PistonBlock.EXTENDED) && behind.get(FACING) == head.get(FACING);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        BlockPos behind = pos.offset(state.get(FACING).getOpposite());
        if (!world.isRemote && player.abilities.isCreativeMode && isAttached(state, world.getBlockState(behind)))
        {
            world.destroyBlock(behind, false);
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onReplaced(state, world, pos, newState, isMoving);
        BlockPos behind = pos.offset(state.get(FACING).getOpposite());
        if (!state.matchesBlock(newState.getBlock()) && isAttached(state, world.getBlockState(behind)))
        {
            world.destroyBlock(behind, true);
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
    {
        BlockState behind = world.getBlockState(pos.offset(state.get(FACING).getOpposite()));
        return isAttached(state, behind) || behind.matchesBlock(Blocks.MOVING_PISTON) && behind.get(FACING) == state.get(FACING);
    }

    @Override
    public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state)
    {
        return new ItemStack(base(state));
    }
}
