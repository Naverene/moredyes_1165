package net.neverandy.moredyes.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.neverandy.moredyes.utility.BlockInfo;

public class FlowerBlock extends BushBlock
{
    private static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    public FlowerBlock(BlockInfo info)
    {
        super(Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness,info.resistance)
                .harvestLevel(info.harvestLevel)
                .harvestTool(info.harvestTool)
                .sound(info.sound)
                .setLightLevel(value -> info.lightlevel)
                // Like vanilla flowers: walk-through and see-through. Without notSolid the flower blocks the light
                // in its own space and is drawn black.
                .doesNotBlockMovement()
                .notSolid());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
    {
        Vector3d offset = state.getOffset(world, pos);
        return SHAPE.withOffset(offset.x, offset.y, offset.z);
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }
}