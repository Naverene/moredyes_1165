package info.kg6jay.moredyes.compat.gregtech;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMaps;
import info.kg6jay.moredyes.block.IBlockColored;
import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.item.MDItem;

/**
 * GregTech (GTNH) machine recipes. Only loaded when GregTech is installed.
 * <ul>
 * <li>Mixer (LV): eight vanilla blocks and one dye make eight dyed blocks.</li>
 * <li>Chemical Bath: a dyed block and 50 L of chlorine bleach it back to the vanilla block, the same recipe GregTech
 * uses to bleach dyed wool.</li>
 * </ul>
 */
public class GTCompat {

    private static final int DYE_DURATION = 8 * 20;
    private static final int BLEACH_DURATION = 20 * 20;
    private static final int BLEACH_EUT = 2;

    public static void registerRecipes() {
        for (Map.Entry<Block, ItemStack> entry : MDBlock.WASHED.entrySet()) {
            Block dyed = entry.getKey();
            ItemStack vanilla = entry.getValue();
            int setIndex = setIndexOf(dyed);
            boolean dyeable = dyed != MDBlock.log[setIndex] && dyed != MDBlock.leaf[setIndex];

            for (int meta = 0; meta <= ((IBlockColored) dyed).getMaxMeta(); meta++) {
                if (dyeable) {
                    GTValues.RA.stdBuilder()
                        .itemInputs(copy(vanilla, 8), new ItemStack(MDItem.dye[setIndex], 1, meta))
                        .itemOutputs(new ItemStack(dyed, 8, meta))
                        .duration(DYE_DURATION)
                        .eut(TierEU.RECIPE_LV)
                        .addTo(RecipeMaps.mixerRecipes);
                }
                GTValues.RA.stdBuilder()
                    .itemInputs(new ItemStack(dyed, 1, meta))
                    .itemOutputs(copy(vanilla, 1))
                    .fluidInputs(Materials.Chlorine.getGas(50L))
                    .duration(BLEACH_DURATION)
                    .eut(BLEACH_EUT)
                    .addTo(RecipeMaps.chemicalBathRecipes);
            }
        }
    }

    private static ItemStack copy(ItemStack stack, int size) {
        ItemStack copy = stack.copy();
        copy.stackSize = size;
        return copy;
    }

    /** Which color set (and so which dye item) a dyed block belongs to. */
    private static int setIndexOf(Block block) {
        String colorSet = ((IBlockColored) block).getColorSet();
        for (int i = 0; i < MDBlock.colors.length; i++) {
            if (MDBlock.colors[i].equals(colorSet)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown color set " + colorSet);
    }
}
