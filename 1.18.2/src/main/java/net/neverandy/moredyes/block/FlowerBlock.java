package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FlowerBlock extends BushBlock
{
    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    public FlowerBlock(BlockInfo info)
    {
        super(Properties.of(info.blockMaterial)
                .strength(info.hardness,info.resistance)
                .sound(info.sound)
                .lightLevel(value -> info.lightlevel)
                // Like vanilla flowers: walk-through and see-through. Without notSolid the flower blocks the light
                // in its own space and is drawn black.
                .noCollission()
                .noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context)
    {
        Vec3 offset = state.getOffset(world, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Override
    public BlockBehaviour.OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }
}