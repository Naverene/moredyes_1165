package info.kg6jay.moredyes.block;

import java.util.Random;

import net.minecraft.item.Item;

import info.kg6jay.moredyes.utility.BlockInfo;

/** Dyed stone drops dyed cobblestone of the same shade, like vanilla stone. Silk touch gives the stone itself. */
public class MDBlockStone extends MDBlockColored {

    private final int setIndex;

    public MDBlockStone(String[] colors, BlockInfo info, String colorSet, int setIndex) {
        super(colors, info, colorSet);
        this.setIndex = setIndex;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(MDBlock.cobble[this.setIndex]);
    }
}
