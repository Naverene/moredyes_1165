package net.neverandy.moredyes.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.Map;

/**
 * A dyed piston or sticky piston. It works like the vanilla one, but extends its own dyed head (BlockPistonHead)
 * instead of the vanilla head. Vanilla PistonBaseBlock places Blocks.PISTON_HEAD from private methods, so the moving
 * part (checkIfExtend, triggerEvent and moveBlocks) is copied from vanilla 1.20.1 with the head swapped.
 */
public class BlockPiston extends PistonBaseBlock
{
    private final boolean sticky;
    private final int color;

    public BlockPiston(boolean sticky, int color)
    {
        super(sticky, properties(sticky));
        this.sticky = sticky;
        this.color = color;
    }

    /** The vanilla piston's properties: an extended piston is not a full block. */
    private static BlockBehaviour.Properties properties(boolean sticky)
    {
        return MDBlock.copy(sticky ? Blocks.STICKY_PISTON : Blocks.PISTON);
    }

    public BlockPistonHead getHead()
    {
        return MDBlock.pistonHeadArray[color];
    }

    public boolean isSticky()
    {
        return sticky;
    }

    /**
     * PistonBaseBlock.isPushable only treats vanilla pistons as pistons; every other block is moved according to its
     * push reaction. A retracted dyed piston can be pushed and pulled like any block, an extended one cannot.
     */
    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return state.getValue(EXTENDED) ? PushReaction.BLOCK : PushReaction.NORMAL;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        if (!level.isClientSide)
        {
            checkIfExtend(level, pos, state);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!level.isClientSide)
        {
            checkIfExtend(level, pos, state);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        if (!oldState.is(state.getBlock()) && !level.isClientSide && level.getBlockEntity(pos) == null)
        {
            checkIfExtend(level, pos, state);
        }
    }

    private void checkIfExtend(Level level, BlockPos pos, BlockState state)
    {
        Direction direction = state.getValue(FACING);
        boolean powered = getNeighborSignal(level, pos, direction);
        if (powered && !state.getValue(EXTENDED))
        {
            if (new PistonStructureResolver(level, pos, direction, true).resolve())
            {
                level.blockEvent(pos, this, 0, direction.get3DDataValue());
            }
        }
        else if (!powered && state.getValue(EXTENDED))
        {
            BlockPos ahead = pos.relative(direction, 2);
            BlockState aheadState = level.getBlockState(ahead);
            int event = 1;
            if (aheadState.is(Blocks.MOVING_PISTON) && aheadState.getValue(FACING) == direction
                    && level.getBlockEntity(ahead) instanceof PistonMovingBlockEntity moving && moving.isExtending()
                    && (moving.getProgress(0.0F) < 0.5F || level.getGameTime() == moving.getLastTicked() || ((ServerLevel) level).isHandlingTick()))
            {
                event = 2;
            }
            level.blockEvent(pos, this, event, direction.get3DDataValue());
        }
    }

    private boolean getNeighborSignal(SignalGetter level, BlockPos pos, Direction facing)
    {
        for (Direction direction : Direction.values())
        {
            if (direction != facing && level.hasSignal(pos.relative(direction), direction))
            {
                return true;
            }
        }
        if (level.hasSignal(pos, Direction.DOWN))
        {
            return true;
        }
        BlockPos above = pos.above();
        for (Direction direction : Direction.values())
        {
            if (direction != Direction.DOWN && level.hasSignal(above.relative(direction), direction))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param)
    {
        Direction direction = state.getValue(FACING);
        BlockState extended = state.setValue(EXTENDED, true);
        if (!level.isClientSide)
        {
            boolean powered = getNeighborSignal(level, pos, direction);
            if (powered && (id == 1 || id == 2))
            {
                level.setBlock(pos, extended, 2);
                return false;
            }
            if (!powered && id == 0)
            {
                return false;
            }
        }

        if (id == 0)
        {
            if (ForgeEventFactory.onPistonMovePre(level, pos, direction, true) || !moveBlocks(level, pos, direction, true))
            {
                return false;
            }
            level.setBlock(pos, extended, 67);
            level.playSound((Player) null, pos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.25F + 0.6F);
            level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(extended));
        }
        else if (id == 1 || id == 2)
        {
            if (ForgeEventFactory.onPistonMovePre(level, pos, direction, false))
            {
                return false;
            }
            if (level.getBlockEntity(pos.relative(direction)) instanceof PistonMovingBlockEntity headMoving)
            {
                headMoving.finalTick();
            }
            BlockState moving = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, direction)
                    .setValue(MovingPistonBlock.TYPE, sticky ? PistonType.STICKY : PistonType.DEFAULT);
            level.setBlock(pos, moving, 20);
            level.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(pos, moving,
                    defaultBlockState().setValue(FACING, Direction.from3DDataValue(param & 7)), direction, false, true));
            level.blockUpdated(pos, moving.getBlock());
            moving.updateNeighbourShapes(level, pos, 2);
            if (sticky)
            {
                BlockPos pulled = pos.relative(direction, 2);
                BlockState pulledState = level.getBlockState(pulled);
                boolean stopped = false;
                if (pulledState.is(Blocks.MOVING_PISTON) && level.getBlockEntity(pulled) instanceof PistonMovingBlockEntity pulledMoving
                        && pulledMoving.getDirection() == direction && pulledMoving.isExtending())
                {
                    pulledMoving.finalTick();
                    stopped = true;
                }
                if (!stopped)
                {
                    if (id == 1 && !pulledState.isAir() && isPushable(pulledState, level, pulled, direction.getOpposite(), false, direction)
                            && (pulledState.getPistonPushReaction() == PushReaction.NORMAL || pulledState.is(Blocks.PISTON) || pulledState.is(Blocks.STICKY_PISTON)))
                    {
                        moveBlocks(level, pos, direction, false);
                    }
                    else
                    {
                        level.removeBlock(pos.relative(direction), false);
                    }
                }
            }
            else
            {
                level.removeBlock(pos.relative(direction), false);
            }
            level.playSound((Player) null, pos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.15F + 0.6F);
            level.gameEvent(GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(moving));
        }
        ForgeEventFactory.onPistonMovePost(level, pos, direction, id == 0);
        return true;
    }

    private boolean moveBlocks(Level level, BlockPos pos, Direction facing, boolean extending)
    {
        BlockPos headPos = pos.relative(facing);
        BlockPistonHead head = getHead();
        if (!extending && level.getBlockState(headPos).is(head))
        {
            level.setBlock(headPos, Blocks.AIR.defaultBlockState(), 20);
        }
        PistonStructureResolver resolver = new PistonStructureResolver(level, pos, facing, extending);
        if (!resolver.resolve())
        {
            return false;
        }

        Map<BlockPos, BlockState> leftBehind = Maps.newHashMap();
        List<BlockPos> toPush = resolver.getToPush();
        List<BlockState> pushedStates = Lists.newArrayList();
        for (BlockPos pushPos : toPush)
        {
            BlockState pushState = level.getBlockState(pushPos);
            pushedStates.add(pushState);
            leftBehind.put(pushPos, pushState);
        }
        List<BlockPos> toDestroy = resolver.getToDestroy();
        BlockState[] changed = new BlockState[toPush.size() + toDestroy.size()];
        Direction direction = extending ? facing : facing.getOpposite();
        int j = 0;

        for (int k = toDestroy.size() - 1; k >= 0; --k)
        {
            BlockPos destroyPos = toDestroy.get(k);
            BlockState destroyState = level.getBlockState(destroyPos);
            BlockEntity blockEntity = destroyState.hasBlockEntity() ? level.getBlockEntity(destroyPos) : null;
            dropResources(destroyState, level, destroyPos, blockEntity);
            level.setBlock(destroyPos, Blocks.AIR.defaultBlockState(), 18);
            level.gameEvent(GameEvent.BLOCK_DESTROY, destroyPos, GameEvent.Context.of(destroyState));
            if (!destroyState.is(BlockTags.FIRE))
            {
                level.addDestroyBlockEffect(destroyPos, destroyState);
            }
            changed[j++] = destroyState;
        }

        for (int l = toPush.size() - 1; l >= 0; --l)
        {
            BlockPos from = toPush.get(l);
            BlockState fromState = level.getBlockState(from);
            BlockPos to = from.relative(direction);
            leftBehind.remove(to);
            BlockState moving = Blocks.MOVING_PISTON.defaultBlockState().setValue(FACING, facing);
            level.setBlock(to, moving, 68);
            level.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(to, moving, pushedStates.get(l), facing, extending, false));
            changed[j++] = fromState;
        }

        if (extending)
        {
            PistonType type = sticky ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState headState = head.defaultBlockState().setValue(PistonHeadBlock.FACING, facing).setValue(PistonHeadBlock.TYPE, type);
            BlockState moving = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, facing).setValue(MovingPistonBlock.TYPE, type);
            leftBehind.remove(headPos);
            level.setBlock(headPos, moving, 68);
            level.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(headPos, moving, headState, facing, true, true));
        }

        BlockState air = Blocks.AIR.defaultBlockState();
        for (BlockPos emptied : leftBehind.keySet())
        {
            level.setBlock(emptied, air, 82);
        }
        for (Map.Entry<BlockPos, BlockState> entry : leftBehind.entrySet())
        {
            entry.getValue().updateIndirectNeighbourShapes(level, entry.getKey(), 2);
            air.updateNeighbourShapes(level, entry.getKey(), 2);
            air.updateIndirectNeighbourShapes(level, entry.getKey(), 2);
        }

        j = 0;
        for (int i = toDestroy.size() - 1; i >= 0; --i)
        {
            BlockState destroyed = changed[j++];
            BlockPos destroyPos = toDestroy.get(i);
            destroyed.updateIndirectNeighbourShapes(level, destroyPos, 2);
            level.updateNeighborsAt(destroyPos, destroyed.getBlock());
        }
        for (int i = toPush.size() - 1; i >= 0; --i)
        {
            level.updateNeighborsAt(toPush.get(i), changed[j++].getBlock());
        }
        if (extending)
        {
            level.updateNeighborsAt(headPos, head);
        }
        return true;
    }
}
