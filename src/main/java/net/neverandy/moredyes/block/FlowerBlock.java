package net.neverandy.moredyes.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BushBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.neverandy.moredyes.utility.BlockInfo;

public class FlowerBlock extends BushBlock
{
    public FlowerBlock(BlockInfo info)
    {
        super(Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness,info.resistance)
                .harvestLevel(info.harvestLevel)
                .harvestTool(info.harvestTool)
                .sound(info.sound)
                .setLightLevel(value -> info.lightlevel));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public AbstractBlock.OffsetType getOffsetType()
    {
        return OffsetType.XZ;
    }
}