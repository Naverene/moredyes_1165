package info.kg6jay.moredyes.compat.thermalexpansion;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.block.MDBlockColored;
import info.kg6jay.moredyes.item.MDItem;
import info.kg6jay.moredyes.item.MDItemBlockColored;
import info.kg6jay.moredyes.reference.Reference;

public class TECompat {

    public static void registerThermalExpansion() {
        MDBlock.rockwool = new Block[MDBlock.colors.length];
        for (int i = 0; i < MDBlock.colors.length; i++) {
            MDBlock.rockwool[i] = new MDBlockColored(
                MDBlock.colorStrings[i],
                Reference.BLOCK_INFO_ROCK_WOOL,
                MDBlock.colors[i]);
            GameRegistry.registerBlock(
                MDBlock.rockwool[i],
                MDItemBlockColored.class,
                ((MDBlockColored) MDBlock.rockwool[i]).getColorSet() + "MixRockwool");
            for (int a = 0; a <= ((MDBlockColored) MDBlock.rockwool[i]).getMaxMeta(); a++) {
                OreDictionary.registerOre("blockClothRock", new ItemStack(MDBlock.rockwool[i], 1, a));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapelessOreRecipe(
                            new ItemStack(MDBlock.rockwool[i], 1, a),
                            new ItemStack(MDItem.dye[i], 1, a),
                            "blockClothRock"));
            }
        }
    }
}
