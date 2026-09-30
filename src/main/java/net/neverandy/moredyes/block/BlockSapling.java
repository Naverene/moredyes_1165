package net.neverandy.moredyes.block;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.neverandy.moredyes.utility.BlockInfo;

public class BlockSapling extends SaplingBlock
{
    public BlockSapling(BlockInfo info, Tree tree)
    {
        super(tree, Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness,info.resistance)
                .harvestLevel(info.harvestLevel)
                .harvestTool(info.harvestTool)
                .setRequiresTool()
                .sound(info.sound)
                .doesNotBlockMovement()
                .tickRandomly()
                .setLightLevel(value -> info.lightlevel));
    }
}
