package info.kg6jay.moredyes.block;

import net.minecraft.world.IBlockAccess;

import info.kg6jay.moredyes.utility.BlockInfo;

public class MDBlockColoredPowered extends MDBlockColored {

    public MDBlockColoredPowered(String[] colors, BlockInfo info, String colorSet) {
        super(colors, info, colorSet);
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_,
        int p_149709_5_) {
        return 15;
    }
}
