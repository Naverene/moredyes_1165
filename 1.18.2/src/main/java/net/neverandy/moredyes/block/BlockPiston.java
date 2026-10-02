package net.neverandy.moredyes.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.Map;

/**
 * A dyed piston or sticky piston. It works like the vanilla one, but extends its own dyed head (BlockPistonHead)
 * instead of the vanilla head. Vanilla PistonBaseBlock places Blocks.PISTON_HEAD from private methods, so the moving
 * part (checkForMove, eventReceived and doMove) is copied from vanilla 1.18.2 with the head swapped.
 */
public class BlockPiston extends PistonBaseBlock
{
    private final boolean sticky;
    private BlockPistonHead head;

    public BlockPiston(boolean sticky)
    {
        super(sticky, properties());
        this.sticky = sticky;
    }

    /** The vanilla piston's properties: an extended piston is not a full block. */
    private static BlockBehaviour.Properties properties()
    {
        BlockBehaviour.StatePredicate retracted = (state, world, pos) -> !state.getValue(EXTENDED);
        return BlockBehaviour.Properties.of(Material.PISTON).strength(1.5F)
                .isRedstoneConductor(retracted).isSuffocating(retracted).isViewBlocking(retracted);
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
     * PistonBaseBlock.canPush only treats vanilla pistons as pistons; every other block is moved according to its push
     * reaction. A retracted dyed piston can be pushed and pulled like any block, an extended one cannot.
     */
    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return state.getValue(EXTENDED) ? PushReaction.BLOCK : PushReaction.NORMAL;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        if (!world.isClientSide)
        {
            checkForMove(world, pos, state);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!world.isClientSide)
        {
            checkForMove(world, pos, state);
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        if (!oldState.is(state.getBlock()) && !world.isClientSide && world.getBlockEntity(pos) == null)
        {
            checkForMove(world, pos, state);
        }
    }

    private void checkForMove(Level world, BlockPos pos, BlockState state)
    {
        Direction direction = state.getValue(FACING);
        boolean powered = shouldBeExtended(world, pos, direction);
        if (powered && !state.getValue(EXTENDED))
        {
            if (new PistonStructureResolver(world, pos, direction, true).resolve())
            {
                world.blockEvent(pos, this, 0, direction.get3DDataValue());
            }
        }
        else if (!powered && state.getValue(EXTENDED))
        {
            BlockPos ahead = pos.relative(direction, 2);
            BlockState aheadState = world.getBlockState(ahead);
            int event = 1;
            if (aheadState.is(Blocks.MOVING_PISTON) && aheadState.getValue(FACING) == direction)
            {
                BlockEntity tile = world.getBlockEntity(ahead);
                if (tile instanceof PistonMovingBlockEntity)
                {
                    PistonMovingBlockEntity piston = (PistonMovingBlockEntity) tile;
                    if (piston.isExtending() && (piston.getProgress(0.0F) < 0.5F
                            || world.getGameTime() == piston.getLastTicked() || ((ServerLevel) world).isHandlingTick()))
                    {
                        event = 2;
                    }
                }
            }
            world.blockEvent(pos, this, event, direction.get3DDataValue());
        }
    }

    private boolean shouldBeExtended(Level world, BlockPos pos, Direction facing)
    {
        for (Direction direction : Direction.values())
        {
            if (direction != facing && world.hasSignal(pos.relative(direction), direction))
            {
                return true;
            }
        }
        if (world.hasSignal(pos, Direction.DOWN))
        {
            return true;
        }
        BlockPos above = pos.above();
        for (Direction direction : Direction.values())
        {
            if (direction != Direction.DOWN && world.hasSignal(above.relative(direction), direction))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param)
    {
        Direction direction = state.getValue(FACING);
        if (!world.isClientSide)
        {
            boolean powered = shouldBeExtended(world, pos, direction);
            if (powered && (id == 1 || id == 2))
            {
                world.setBlock(pos, state.setValue(EXTENDED, true), 2);
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
            world.setBlock(pos, state.setValue(EXTENDED, true), 67);
            world.playSound(null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
            world.gameEvent(GameEvent.PISTON_EXTEND, pos);
        }
        else if (id == 1 || id == 2)
        {
            if (ForgeEventFactory.onPistonMovePre(world, pos, direction, false))
            {
                return false;
            }
            BlockEntity headTile = world.getBlockEntity(pos.relative(direction));
            if (headTile instanceof PistonMovingBlockEntity)
            {
                ((PistonMovingBlockEntity) headTile).finalTick();
            }
            BlockState moving = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, direction)
                    .setValue(MovingPistonBlock.TYPE, sticky ? PistonType.STICKY : PistonType.DEFAULT);
            world.setBlock(pos, moving, 20);
            world.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(pos, moving, defaultBlockState().setValue(FACING, Direction.from3DDataValue(param & 7)), direction, false, true));
            world.blockUpdated(pos, moving.getBlock());
            moving.updateNeighbourShapes(world, pos, 2);
            if (sticky)
            {
                BlockPos pulled = pos.relative(direction, 2);
                BlockState pulledState = world.getBlockState(pulled);
                boolean stopped = false;
                if (pulledState.is(Blocks.MOVING_PISTON))
                {
                    BlockEntity tile = world.getBlockEntity(pulled);
                    if (tile instanceof PistonMovingBlockEntity && ((PistonMovingBlockEntity) tile).getDirection() == direction && ((PistonMovingBlockEntity) tile).isExtending())
                    {
                        ((PistonMovingBlockEntity) tile).finalTick();
                        stopped = true;
                    }
                }
                if (!stopped)
                {
                    if (id != 1 || pulledState.isAir() || !isPushable(pulledState, world, pulled, direction.getOpposite(), false, direction)
                            || pulledState.getPistonPushReaction() != PushReaction.NORMAL && !pulledState.is(Blocks.PISTON) && !pulledState.is(Blocks.STICKY_PISTON))
                    {
                        world.removeBlock(pos.relative(direction), false);
                    }
                    else
                    {
                        doMove(world, pos, direction, false);
                    }
                }
            }
            else
            {
                world.removeBlock(pos.relative(direction), false);
            }
            world.playSound(null, pos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
            world.gameEvent(GameEvent.PISTON_CONTRACT, pos);
        }
        ForgeEventFactory.onPistonMovePost(world, pos, direction, id == 0);
        return true;
    }

    private boolean doMove(Level world, BlockPos pos, Direction facing, boolean extending)
    {
        BlockPos headPos = pos.relative(facing);
        if (!extending && world.getBlockState(headPos).is(head))
        {
            world.setBlock(headPos, Blocks.AIR.defaultBlockState(), 20);
        }
        PistonStructureResolver helper = new PistonStructureResolver(world, pos, facing, extending);
        if (!helper.resolve())
        {
            return false;
        }

        Map<BlockPos, BlockState> leftBehind = Maps.newHashMap();
        List<BlockPos> toMove = helper.getToPush();
        List<BlockState> movedStates = Lists.newArrayList();
        for (BlockPos movePos : toMove)
        {
            BlockState moveState = world.getBlockState(movePos);
            movedStates.add(moveState);
            leftBehind.put(movePos, moveState);
        }
        List<BlockPos> toDestroy = helper.getToDestroy();
        BlockState[] changed = new BlockState[toMove.size() + toDestroy.size()];
        Direction direction = extending ? facing : facing.getOpposite();
        int j = 0;

        for (int k = toDestroy.size() - 1; k >= 0; --k)
        {
            BlockPos destroyPos = toDestroy.get(k);
            BlockState destroyState = world.getBlockState(destroyPos);
            BlockEntity tile = destroyState.hasBlockEntity() ? world.getBlockEntity(destroyPos) : null;
            dropResources(destroyState, world, destroyPos, tile);
            world.setBlock(destroyPos, Blocks.AIR.defaultBlockState(), 18);
            if (!destroyState.is(BlockTags.FIRE))
            {
                world.addDestroyBlockEffect(destroyPos, destroyState);
            }
            changed[j++] = destroyState;
        }

        for (int l = toMove.size() - 1; l >= 0; --l)
        {
            BlockPos from = toMove.get(l);
            BlockState fromState = world.getBlockState(from);
            BlockPos to = from.relative(direction);
            leftBehind.remove(to);
            BlockState moving = Blocks.MOVING_PISTON.defaultBlockState().setValue(FACING, facing);
            world.setBlock(to, moving, 68);
            world.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(to, moving, movedStates.get(l), facing, extending, false));
            changed[j++] = fromState;
        }

        if (extending)
        {
            PistonType type = sticky ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState headState = head.defaultBlockState().setValue(PistonHeadBlock.FACING, facing).setValue(PistonHeadBlock.TYPE, type);
            BlockState moving = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, facing).setValue(MovingPistonBlock.TYPE, type);
            leftBehind.remove(headPos);
            world.setBlock(headPos, moving, 68);
            world.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(headPos, moving, headState, facing, true, true));
        }

        BlockState air = Blocks.AIR.defaultBlockState();
        for (BlockPos emptied : leftBehind.keySet())
        {
            world.setBlock(emptied, air, 82);
        }
        for (Map.Entry<BlockPos, BlockState> entry : leftBehind.entrySet())
        {
            entry.getValue().updateIndirectNeighbourShapes(world, entry.getKey(), 2);
            air.updateNeighbourShapes(world, entry.getKey(), 2);
            air.updateIndirectNeighbourShapes(world, entry.getKey(), 2);
        }

        j = 0;
        for (int i = toDestroy.size() - 1; i >= 0; --i)
        {
            BlockState destroyed = changed[j++];
            BlockPos destroyPos = toDestroy.get(i);
            destroyed.updateIndirectNeighbourShapes(world, destroyPos, 2);
            world.updateNeighborsAt(destroyPos, destroyed.getBlock());
        }
        for (int i = toMove.size() - 1; i >= 0; --i)
        {
            world.updateNeighborsAt(toMove.get(i), changed[j++].getBlock());
        }
        if (extending)
        {
            world.updateNeighborsAt(headPos, head);
        }
        return true;
    }
}
