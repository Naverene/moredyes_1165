package info.kg6jay.moredyes.handler;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.IFuelHandler;
import info.kg6jay.moredyes.block.MDBlock;

/**
 * Burn times for the dyed blocks that vanilla gives special values. Wooden blocks (planks, logs, crafting tables,
 * chests, bookshelves) already burn for 300 ticks because of their wood material.
 */
public class FuelHandler implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        Block block = Block.getBlockFromItem(fuel.getItem());
        for (int i = 0; i < MDBlock.colors.length; i++) {
            if (block == MDBlock.coal[i]) {
                return 16000;
            }
            if (block == MDBlock.sapling[i]) {
                return 100;
            }
        }
        return 0;
    }
}
