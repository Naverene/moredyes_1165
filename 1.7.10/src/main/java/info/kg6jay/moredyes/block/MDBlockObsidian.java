package info.kg6jay.moredyes.block;

import info.kg6jay.moredyes.utility.BlockInfo;

/** Dyed obsidian cannot be moved by pistons, like vanilla obsidian. */
public class MDBlockObsidian extends MDBlockColored {

    public MDBlockObsidian(String[] colors, BlockInfo info, String colorSet) {
        super(colors, info, colorSet);
    }

    @Override
    public int getMobilityFlag() {
        return 2;
    }
}
