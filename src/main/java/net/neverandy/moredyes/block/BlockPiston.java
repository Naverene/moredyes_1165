package net.neverandy.moredyes.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MovingPistonBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonBlockStructureHelper;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.Map;

/**
 * A dyed piston or sticky piston. It works like the vanilla one, but extends its own dyed head (BlockPistonHead)
 * instead of the vanilla head. Vanilla PistonBlock places Blocks.PISTON_HEAD from private methods, so the moving
 * part (checkForMove, eventReceived and doMove) is copied from vanilla 1.16.5 with the head swapped.
 */
public class BlockPiston extends PistonBlock
{
    private final boolean sticky;
    private BlockPistonHead head;

    public BlockPiston(boolean sticky)
    {
        super(sticky, properties());
        this.sticky = sticky;
    }

    /** The vanilla piston's properties: an extended piston is not a full block. */
    private static AbstractBlock.Properties properties()
    {
        AbstractBlock.IPositionPredicate retracted = (state, world, pos) -> !state.get(EXTENDED);
        return AbstractBlock.Properties.create(Material.PISTON).hardnessAndResistance(1.5F)
                .setOpaque(retracted).setSuffocates(retracted).setBlocksVision(retracted);
    }

    public void setHead(BlockPistonHead head)
    {
        this.head = head;
    }

    public BlockPistonHead getHead()
    {
        return head;
    }

    public boolean isSticky()
    {
        return sticky;
    }

    /**
     * PistonBlock.canPush only treats vanilla pistons as pistons; every other block is moved according to its push
     * reaction. A retracted dyed piston can be pushed and pulled like any block, an extended one cannot.
     */
    @Override
    public PushReaction getPushReaction(BlockState state)
    {
        return state.get(EXTENDED) ? PushReaction.BLOCK : PushReaction.NORMAL;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        if (!world.isRemote)
        {
            checkForMove(world, pos, state);
        }
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!world.isRemote)
        {
            checkForMove(world, pos, state);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        if (!oldState.matchesBlock(state.getBlock()) && !world.isRemote && world.getTileEntity(pos) == null)
        {
            checkForMove(world, pos, state);
        }
    }

    private void checkForMove(World world, BlockPos pos, BlockState state)
    {
        Direction direction = state.get(FACING);
        boolean powered = shouldBeExtended(world, pos, direction);
        if (powered && !state.get(EXTENDED))
        {
            if (new PistonBlockStructureHelper(world, pos, direction, true).canMove())
            {
                world.addBlockEvent(pos, this, 0, direction.getIndex());
            }
        }
        else if (!powered && state.get(EXTENDED))
        {
            BlockPos ahead = pos.offset(direction, 2);
            BlockState aheadState = world.getBlockState(ahead);
            int event = 1;
            if (aheadState.matchesBlock(Blocks.MOVING_PISTON) && aheadState.get(FACING) == direction)
            {
                TileEntity tile = world.getTileEntity(ahead);
                if (tile instanceof PistonTileEntity)
                {
                    PistonTileEntity piston = (PistonTileEntity) tile;
                    if (piston.isExtending() && (piston.getProgress(0.0F) < 0.5F
                            || world.getGameTime() == piston.getLastTicked() || ((ServerWorld) world).isInsideTick()))
                    {
                        event = 2;
                    }
                }
            }
            world.addBlockEvent(pos, this, event, direction.getIndex());
        }
    }

    private boolean shouldBeExtended(World world, BlockPos pos, Direction facing)
    {
        for (Direction direction : Direction.values())
        {
            if (direction != facing && world.isSidePowered(pos.offset(direction), direction))
            {
                return true;
            }
        }
        if (world.isSidePowered(pos, Direction.DOWN))
        {
            return true;
        }
        BlockPos above = pos.up();
        for (Direction direction : Direction.values())
        {
            if (direction != Direction.DOWN && world.isSidePowered(above.offset(direction), direction))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eventReceived(BlockState state, World world, BlockPos pos, int id, int param)
    {
        Direction direction = state.get(FACING);
        if (!world.isRemote)
        {
            boolean powered = shouldBeExtended(world, pos, direction);
            if (powered && (id == 1 || id == 2))
            {
                world.setBlockState(pos, state.with(EXTENDED, true), 2);
                return false;
            }
            if (!powered && id == 0)
            {
                return false;
            }
        }

        if (id == 0)
        {
            if (ForgeEventFactory.onPistonMovePre(world, pos, direction, true) || !doMove(world, pos, direction, true))
            {
                return false;
            }
            world.setBlockState(pos, state.with(EXTENDED, true), 67);
            world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
        }
        else if (id == 1 || id == 2)
        {
            if (ForgeEventFactory.onPistonMovePre(world, pos, direction, false))
            {
                return false;
            }
            TileEntity headTile = world.getTileEntity(pos.offset(direction));
            if (headTile instanceof PistonTileEntity)
            {
                ((PistonTileEntity) headTile).clearPistonTileEntity();
            }
            BlockState moving = Blocks.MOVING_PISTON.getDefaultState().with(MovingPistonBlock.FACING, direction)
                    .with(MovingPistonBlock.TYPE, sticky ? PistonType.STICKY : PistonType.DEFAULT);
            world.setBlockState(pos, moving, 20);
            world.setTileEntity(pos, MovingPistonBlock.createTilePiston(getDefaultState().with(FACING, Direction.byIndex(param & 7)), direction, false, true));
            world.updateBlock(pos, moving.getBlock());
            moving.updateNeighbours(world, pos, 2);
            if (sticky)
            {
                BlockPos pulled = pos.offset(direction, 2);
                BlockState pulledState = world.getBlockState(pulled);
                boolean stopped = false;
                if (pulledState.matchesBlock(Blocks.MOVING_PISTON))
                {
                    TileEntity tile = world.getTileEntity(pulled);
                    if (tile instanceof PistonTileEntity && ((PistonTileEntity) tile).getFacing() == direction && ((PistonTileEntity) tile).isExtending())
                    {
                        ((PistonTileEntity) tile).clearPistonTileEntity();
                        stopped = true;
                    }
                }
                if (!stopped)
                {
                    if (id != 1 || pulledState.isAir() || !canPush(pulledState, world, pulled, direction.getOpposite(), false, direction)
                            || pulledState.getPushReaction() != PushReaction.NORMAL && !pulledState.matchesBlock(Blocks.PISTON) && !pulledState.matchesBlock(Blocks.STICKY_PISTON))
                    {
                        world.removeBlock(pos.offset(direction), false);
                    }
                    else
                    {
                        doMove(world, pos, direction, false);
                    }
                }
            }
            else
            {
                world.removeBlock(pos.offset(direction), false);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.15F + 0.6F);
        }
        ForgeEventFactory.onPistonMovePost(world, pos, direction, id == 0);
        return true;
    }

    private boolean doMove(World world, BlockPos pos, Direction facing, boolean extending)
    {
        BlockPos headPos = pos.offset(facing);
        if (!extending && world.getBlockState(headPos).matchesBlock(head))
        {
            world.setBlockState(headPos, Blocks.AIR.getDefaultState(), 20);
        }
        PistonBlockStructureHelper helper = new PistonBlockStructureHelper(world, pos, facing, extending);
        if (!helper.canMove())
        {
            return false;
        }

        Map<BlockPos, BlockState> leftBehind = Maps.newHashMap();
        List<BlockPos> toMove = helper.getBlocksToMove();
        List<BlockState> movedStates = Lists.newArrayList();
        for (BlockPos movePos : toMove)
        {
            BlockState moveState = world.getBlockState(movePos);
            movedStates.add(moveState);
            leftBehind.put(movePos, moveState);
        }
        List<BlockPos> toDestroy = helper.getBlocksToDestroy();
        BlockState[] changed = new BlockState[toMove.size() + toDestroy.size()];
        Direction direction = extending ? facing : facing.getOpposite();
        int j = 0;

        for (int k = toDestroy.size() - 1; k >= 0; --k)
        {
            BlockPos destroyPos = toDestroy.get(k);
            BlockState destroyState = world.getBlockState(destroyPos);
            TileEntity tile = destroyState.hasTileEntity() ? world.getTileEntity(destroyPos) : null;
            spawnDrops(destroyState, world, destroyPos, tile);
            world.setBlockState(destroyPos, Blocks.AIR.getDefaultState(), 18);
            changed[j++] = destroyState;
        }

        for (int l = toMove.size() - 1; l >= 0; --l)
        {
            BlockPos from = toMove.get(l);
            BlockState fromState = world.getBlockState(from);
            BlockPos to = from.offset(direction);
            leftBehind.remove(to);
            world.setBlockState(to, Blocks.MOVING_PISTON.getDefaultState().with(FACING, facing), 68);
            world.setTileEntity(to, MovingPistonBlock.createTilePiston(movedStates.get(l), facing, extending, false));
            changed[j++] = fromState;
        }

        if (extending)
        {
            PistonType type = sticky ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState headState = head.getDefaultState().with(PistonHeadBlock.FACING, facing).with(PistonHeadBlock.TYPE, type);
            BlockState moving = Blocks.MOVING_PISTON.getDefaultState().with(MovingPistonBlock.FACING, facing).with(MovingPistonBlock.TYPE, type);
            leftBehind.remove(headPos);
            world.setBlockState(headPos, moving, 68);
            world.setTileEntity(headPos, MovingPistonBlock.createTilePiston(headState, facing, true, true));
        }

        BlockState air = Blocks.AIR.getDefaultState();
        for (BlockPos emptied : leftBehind.keySet())
        {
            world.setBlockState(emptied, air, 82);
        }
        for (Map.Entry<BlockPos, BlockState> entry : leftBehind.entrySet())
        {
            entry.getValue().updateDiagonalNeighbors(world, entry.getKey(), 2);
            air.updateNeighbours(world, entry.getKey(), 2);
            air.updateDiagonalNeighbors(world, entry.getKey(), 2);
        }

        j = 0;
        for (int i = toDestroy.size() - 1; i >= 0; --i)
        {
            BlockState destroyed = changed[j++];
            BlockPos destroyPos = toDestroy.get(i);
            destroyed.updateDiagonalNeighbors(world, destroyPos, 2);
            world.notifyNeighborsOfStateChange(destroyPos, destroyed.getBlock());
        }
        for (int i = toMove.size() - 1; i >= 0; --i)
        {
            world.notifyNeighborsOfStateChange(toMove.get(i), changed[j++].getBlock());
        }
        if (extending)
        {
            world.notifyNeighborsOfStateChange(headPos, head);
        }
        return true;
    }
}
