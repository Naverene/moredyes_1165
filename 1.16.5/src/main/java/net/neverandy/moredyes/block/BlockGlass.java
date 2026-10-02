package net.neverandy.moredyes.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.neverandy.moredyes.utility.BlockInfo;

public class BlockGlass extends AbstractGlassBlock
{
    public String blockName;
    public BlockGlass(BlockInfo info)
    {
        super(AbstractBlock.Properties.create(info.blockMaterial)
                .hardnessAndResistance(info.hardness,info.resistance)
                .harvestLevel(info.harvestLevel)
                .harvestTool(info.harvestTool)
                .sound(info.sound)
                .setLightLevel(value -> info.lightlevel)
                .notSolid().variableOpacity());
    }
}