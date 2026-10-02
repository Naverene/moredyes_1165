package info.kg6jay.moredyes.recipe;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import info.kg6jay.moredyes.block.IBlockColored;
import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.block.MDBlockColored;
import info.kg6jay.moredyes.item.MDItem;
import info.kg6jay.moredyes.utility.ColorIndex;

public class CraftManager {

    public static void addCraftingRecipes() {
        ItemStack dyeWhite = new ItemStack(Items.dye, 1, 15);
        ItemStack dyeOrange = new ItemStack(Items.dye, 1, 14);
        ItemStack dyeMagenta = new ItemStack(Items.dye, 1, 13);
        ItemStack dyeLightBlue = new ItemStack(Items.dye, 1, 12);
        ItemStack dyeYellow = new ItemStack(Items.dye, 1, 11);
        ItemStack dyeLime = new ItemStack(Items.dye, 1, 10);
        ItemStack dyePink = new ItemStack(Items.dye, 1, 9);
        ItemStack dyeGray = new ItemStack(Items.dye, 1, 8);
        ItemStack dyeLightGray = new ItemStack(Items.dye, 1, 7);
        ItemStack dyeCyan = new ItemStack(Items.dye, 1, 6);
        ItemStack dyePurple = new ItemStack(Items.dye, 1, 5);
        ItemStack dyeLapis = new ItemStack(Items.dye, 1, 4);
        ItemStack dyeBrown = new ItemStack(Items.dye, 1, 3);
        ItemStack dyeGreen = new ItemStack(Items.dye, 1, 2);
        ItemStack dyeRed = new ItemStack(Items.dye, 1, 1);
        ItemStack dyeBlack = new ItemStack(Items.dye, 1, 0);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 0), dyeWhite, dyeOrange);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 1), dyeWhite, dyeMagenta);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 2), dyeWhite, dyeLightBlue);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 3), dyeWhite, dyeYellow);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 4), dyeWhite, dyeLime);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 5), dyeWhite, dyePink);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 6), dyeWhite, dyeLightGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 7), dyeWhite, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 8), dyeWhite, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 9), dyeWhite, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 10), dyeWhite, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 2, 11), dyeWhite, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[0], 4, 12), dyeWhite, dyeWhite, dyeRed, dyeRed);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 0), dyeOrange, dyeMagenta);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 1), dyeOrange, dyeLightBlue);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 2), dyeOrange, dyeYellow);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 3), dyeOrange, dyeLime);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 4), dyeOrange, dyePink);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 5), dyeOrange, dyeGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 6), dyeOrange, dyeLightGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 7), dyeOrange, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 8), dyeOrange, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 9), dyeOrange, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 10), dyeOrange, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 11), dyeOrange, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 12), dyeOrange, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[1], 2, 13), dyeOrange, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 0), dyeMagenta, dyeLightBlue);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 1), dyeMagenta, dyeYellow);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 2), dyeMagenta, dyeLime);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 3), dyeMagenta, dyePink);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 4), dyeMagenta, dyeGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 5), dyeMagenta, dyeLightGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 6), dyeMagenta, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 7), dyeMagenta, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 8), dyeMagenta, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 9), dyeMagenta, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 10), dyeMagenta, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 11), dyeMagenta, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[2], 2, 12), dyeMagenta, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 0), dyeLightBlue, dyeYellow);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 1), dyeLightBlue, dyeLime);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 2), dyeLightBlue, dyePink);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 3), dyeLightBlue, dyeGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 4), dyeLightBlue, dyeLightGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 5), dyeLightBlue, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 6), dyeLightBlue, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 7), dyeLightBlue, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 8), dyeLightBlue, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 9), dyeLightBlue, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 10), dyeLightBlue, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[3], 2, 11), dyeLightBlue, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 0), dyeYellow, dyeLime);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 1), dyeYellow, dyePink);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 2), dyeYellow, dyeGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 3), dyeYellow, dyeLightGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 4), dyeYellow, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 5), dyeYellow, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 6), dyeYellow, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 7), dyeYellow, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 8), dyeYellow, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 9), dyeYellow, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[4], 2, 10), dyeYellow, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 0), dyeLime, dyePink);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 1), dyeLime, dyeGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 2), dyeLime, dyeLightGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 3), dyeLime, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 4), dyeLime, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 5), dyeLime, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 6), dyeLime, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 7), dyeLime, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 8), dyeLime, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[5], 2, 9), dyeLime, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 0), dyePink, dyeGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 1), dyePink, dyeLightGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 2), dyePink, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 3), dyePink, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 4), dyePink, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 5), dyePink, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 6), dyePink, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 7), dyePink, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[6], 2, 8), dyePink, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[7], 2, 0), dyeGray, dyeLightGray);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[7], 2, 1), dyeGray, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[7], 2, 2), dyeGray, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[7], 2, 3), dyeGray, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[7], 2, 4), dyeGray, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[7], 2, 5), dyeGray, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[7], 2, 6), dyeGray, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[7], 2, 7), dyeGray, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[8], 2, 0), dyeLightGray, dyeCyan);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[8], 2, 1), dyeLightGray, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[8], 2, 2), dyeLightGray, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[8], 2, 3), dyeLightGray, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[8], 2, 4), dyeLightGray, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[8], 2, 5), dyeLightGray, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[8], 2, 6), dyeLightGray, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[9], 2, 0), dyeCyan, dyePurple);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[9], 2, 1), dyeCyan, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[9], 2, 2), dyeCyan, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[9], 2, 3), dyeCyan, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[9], 2, 4), dyeCyan, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[9], 2, 5), dyeCyan, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[10], 2, 0), dyePurple, dyeLapis);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[10], 2, 1), dyePurple, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[10], 2, 2), dyePurple, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[10], 2, 3), dyePurple, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[10], 2, 4), dyePurple, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[11], 2, 0), dyeLapis, dyeBrown);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[11], 2, 1), dyeLapis, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[11], 2, 2), dyeLapis, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[11], 2, 3), dyeLapis, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[12], 2, 0), dyeBrown, dyeGreen);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[12], 2, 1), dyeBrown, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[12], 2, 2), dyeBrown, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[13], 2, 0), dyeGreen, dyeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[13], 2, 1), dyeGreen, dyeBlack);

        GameRegistry.addShapelessRecipe(new ItemStack(MDItem.dye[14], 2, 0), dyeRed, dyeBlack);

        // TODO: Add recipes for all the rest of the dyes.

        for (int a = 0; a < MDBlock.wool.length; a++) {
            for (int i = 0; i <= ((MDBlockColored) MDBlock.wool[a]).getMaxMeta(); i++) {
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapelessOreRecipe(
                            new ItemStack(MDBlock.wool[a], 1, i),
                            new ItemStack(MDItem.dye[a], 1, i),
                            "blockWool"));
                GameRegistry
                    .addShapelessRecipe(new ItemStack(MDItem.dye[a], 1, i), new ItemStack(MDBlock.tulip[a], 1, i));

                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.stoneBrick[a], 4, i),
                    "SS",
                    "SS",
                    'S',
                    new ItemStack(MDBlock.stone[a], 1, i));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.stoneBrick[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            new ItemStack(Blocks.stonebrick, 1, 0),
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.stone[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            "stone",
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.cobble[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            "cobblestone",
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.redstone[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            "blockRedstone",
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.lapis[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            Blocks.lapis_block,
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.glowstone[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            Blocks.glowstone,
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.clay[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            "stainedClay",
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.clay[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            Blocks.stained_hardened_clay,
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.obsidian[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            Blocks.obsidian,
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.soulsand[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            Blocks.soul_sand,
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.quartz[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            "blockQuartz",
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.coal[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            "blockCoal",
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                CraftingManager.getInstance()
                    .getRecipeList()
                    .add(
                        new ShapedOreRecipe(
                            new ItemStack(MDBlock.plank[a], 8, i),
                            "SSS",
                            "SDS",
                            "SSS",
                            'S',
                            "plankWood",
                            'D',
                            new ItemStack(MDItem.dye[a], 1, i)));
                GameRegistry.addShapelessRecipe(
                    new ItemStack(MDBlock.sapling[a], 1, i),
                    new ItemStack(MDItem.dye[a], 1, i),
                    new ItemStack(Blocks.sapling, 1, 0));
                GameRegistry
                    .addShapelessRecipe(new ItemStack(MDBlock.plank[a], 4, i), new ItemStack(MDBlock.log[a], 1, i));
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.glassClear[a], 8, i),
                    "GGG",
                    "GDG",
                    "GGG",
                    'G',
                    new ItemStack(Blocks.glass),
                    'D',
                    new ItemStack(MDItem.dye[a], 1, i));
                GameRegistry
                    .addShapelessRecipe(new ItemStack(Items.redstone, 9), new ItemStack(MDBlock.redstone[a], 1, i));
                GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 9, 4), new ItemStack(MDBlock.lapis[a], 1, i));
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.stoneBrickCarved[a], 8, i),
                    "SSS",
                    "SDS",
                    "SSS",
                    'S',
                    new ItemStack(Blocks.stonebrick, 1, 3),
                    'D',
                    new ItemStack(MDItem.dye[a], 1, i));
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.sand[a], 8, i),
                    "SSS",
                    "SDS",
                    "SSS",
                    'S',
                    new ItemStack(Blocks.sand),
                    'D',
                    new ItemStack(MDItem.dye[a], 1, i));
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.brick[a], 8, i),
                    "SSS",
                    "SDS",
                    "SSS",
                    'S',
                    new ItemStack(Blocks.brick_block),
                    'D',
                    new ItemStack(MDItem.dye[a], 1, i));
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.hardenedClay[a], 8, i),
                    "SSS",
                    "SDS",
                    "SSS",
                    'S',
                    new ItemStack(Blocks.hardened_clay),
                    'D',
                    new ItemStack(MDItem.dye[a], 1, i));

            }
        }
        // --- Chests, crafting tables and the blocks that had no recipe ---
        for (int a = 0; a < MDBlock.chest.length; a++) {
            for (int i = 0; i <= ((IBlockColored) MDBlock.chest[a]).getMaxMeta(); i++) {
                ItemStack plank = new ItemStack(MDBlock.plank[a], 1, i);
                ItemStack dye = new ItemStack(MDItem.dye[a], 1, i);

                // Dyed planks are registered as "plankWood", so the vanilla chest and crafting table recipes also
                // match them. Put these recipes first so a single shade of planks gives the dyed version.
                addPriorityRecipe(
                    new ShapedOreRecipe(new ItemStack(MDBlock.chest[a], 1, i), "PPP", "P P", "PPP", 'P', plank));
                addPriorityRecipe(
                    new ShapedOreRecipe(new ItemStack(MDBlock.workbench[a], 1, i), "PP", "PP", 'P', plank));

                // Dyeing the vanilla blocks
                GameRegistry
                    .addShapelessRecipe(new ItemStack(MDBlock.chest[a], 1, i), new ItemStack(Blocks.chest), dye);
                GameRegistry.addShapelessRecipe(
                    new ItemStack(MDBlock.workbench[a], 1, i),
                    new ItemStack(Blocks.crafting_table),
                    dye);

                // Blocks that had no recipe yet: eight vanilla blocks around a dye
                addSurroundRecipe(MDBlock.stoneBrickCracked[a], i, new ItemStack(Blocks.stonebrick, 1, 2), dye);
                addSurroundRecipe(MDBlock.sandstone[a], i, new ItemStack(Blocks.sandstone), dye);
                addSurroundRecipe(MDBlock.bookshelf[a], i, new ItemStack(Blocks.bookshelf), dye);
                addSurroundRecipe(MDBlock.glassClearPane[a], i, new ItemStack(Blocks.glass_pane), dye);
                // Any mod's diorite can be dyed
                GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                        new ItemStack(MDBlock.diorite[a], 8, i),
                        "SSS",
                        "SDS",
                        "SSS",
                        'S',
                        "stoneDiorite",
                        'D',
                        dye));

                // Glass panes from dyed glass, like vanilla (six glass make sixteen panes)
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.glassClearPane[a], 16, i),
                    "GGG",
                    "GGG",
                    'G',
                    new ItemStack(MDBlock.glassClear[a], 1, i));
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.glassFoggyPane[a], 16, i),
                    "GGG",
                    "GGG",
                    'G',
                    new ItemStack(MDBlock.glassFoggy[a], 1, i));
            }
        }
        CraftingManager.getInstance()
            .getRecipeList()
            .add(new ShapelessOreRecipe(new ItemStack(Items.coal, 9, 0), "blockCoal"));

        addNewerBlockRecipes();
        addShapeRecipes();
    }

    /**
     * The blocks from newer Minecraft versions. Where a block has a vanilla recipe made from blocks this mod dyes, the
     * same recipe works with dyed blocks of one shade (four dyed granite make four dyed polished granite). Blocks
     * that have a vanilla or plain version are dyed like the other blocks: eight around a dye. The rest have recipes
     * of their own, noted below.
     */
    private static void addNewerBlockRecipes() {
        for (int a = 0; a < MDBlock.colors.length; a++) {
            for (int i = 0; i <= ((IBlockColored) MDBlock.granite[a]).getMaxMeta(); i++) {
                ItemStack dye = new ItemStack(MDItem.dye[a], 1, i);

                addOreSurroundRecipe(MDBlock.granite[a], i, "stoneGranite", dye);
                addOreSurroundRecipe(MDBlock.andesite[a], i, "stoneAndesite", dye);
                addSurroundRecipe(MDBlock.mossyCobble[a], i, new ItemStack(Blocks.mossy_cobblestone), dye);
                addSurroundRecipe(MDBlock.mossyStoneBrick[a], i, new ItemStack(Blocks.stonebrick, 1, 1), dye);
                addSurroundRecipe(MDBlock.netherBrick[a], i, new ItemStack(Blocks.nether_brick), dye);
                addSurroundRecipe(MDBlock.cutSandstone[a], i, new ItemStack(Blocks.sandstone, 1, 2), dye);
                addSurroundRecipe(MDBlock.endStoneBrick[a], i, new ItemStack(Blocks.end_stone), dye);

                addSquareRecipe(MDBlock.polishedGranite[a], MDBlock.granite[a], i);
                addSquareRecipe(MDBlock.polishedDiorite[a], MDBlock.diorite[a], i);
                addSquareRecipe(MDBlock.polishedAndesite[a], MDBlock.andesite[a], i);
                addSquareRecipe(MDBlock.polishedBasalt[a], MDBlock.basalt[a], i);
                addSquareRecipe(MDBlock.cutSandstone[a], MDBlock.sandstone[a], i);

                // Concrete powder: four sand, four gravel and a dye (Minecraft 1.12)
                GameRegistry.addShapelessRecipe(
                    new ItemStack(MDBlock.concretePowder[a], 8, i),
                    dye,
                    Blocks.sand,
                    Blocks.sand,
                    Blocks.sand,
                    Blocks.sand,
                    Blocks.gravel,
                    Blocks.gravel,
                    Blocks.gravel,
                    Blocks.gravel);
                // Soul soil: dyed soul sand and dirt
                GameRegistry.addShapelessRecipe(
                    new ItemStack(MDBlock.soulSoil[a], 2, i),
                    new ItemStack(MDBlock.soulsand[a], 1, i),
                    Blocks.dirt);
                // Chiseled nether bricks: two dyed nether bricks on top of each other
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.chiseledNetherBrick[a], 1, i),
                    "N",
                    "N",
                    'N',
                    new ItemStack(MDBlock.netherBrick[a], 1, i));
                // Bone block: eight bone meal around a dye, and back to bone meal
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.boneBlock[a], 1, i),
                    "BBB",
                    "BDB",
                    "BBB",
                    'B',
                    new ItemStack(Items.dye, 1, 15),
                    'D',
                    dye);
                GameRegistry
                    .addShapelessRecipe(new ItemStack(Items.dye, 8, 15), new ItemStack(MDBlock.boneBlock[a], 1, i));
                // Crying obsidian: eight dyed obsidian around a ghast tear
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.cryingObsidian[a], 8, i),
                    "OOO",
                    "OTO",
                    "OOO",
                    'O',
                    new ItemStack(MDBlock.obsidian[a], 1, i),
                    'T',
                    Items.ghast_tear);
                // Netherite does not exist in 1.7: obsidian, gold and a dye
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.netheriteBlock[a], 4, i),
                    "OGO",
                    "GDG",
                    "OGO",
                    'O',
                    Blocks.obsidian,
                    'G',
                    Items.gold_ingot,
                    'D',
                    dye);
                // Iron trapdoor: four iron ingots (Minecraft 1.8) and a dye
                GameRegistry.addShapelessRecipe(
                    new ItemStack(MDBlock.ironTrapdoor, 1, ColorIndex.of(a, i)),
                    Items.iron_ingot,
                    Items.iron_ingot,
                    Items.iron_ingot,
                    Items.iron_ingot,
                    dye);
                // Pistons: a vanilla piston or sticky piston and a dye, and a dyed piston with a slimeball on top
                ItemStack piston = new ItemStack(MDBlock.piston, 1, ColorIndex.of(a, i));
                ItemStack stickyPiston = new ItemStack(MDBlock.stickyPiston, 1, ColorIndex.of(a, i));
                GameRegistry.addShapelessRecipe(piston, Blocks.piston, dye);
                GameRegistry.addShapelessRecipe(stickyPiston, Blocks.sticky_piston, dye);
                GameRegistry.addShapedRecipe(stickyPiston, "S", "P", 'S', Items.slime_ball, 'P', piston);
                GameRegistry.addShapelessRecipe(dye.copy(), new ItemStack(MDBlock.cornflower[a], 1, i));
                // Chain: an iron ingot between two iron nuggets in 1.16; 1.7 has no nuggets, so iron ingots and a dye
                GameRegistry.addShapedRecipe(
                    new ItemStack(MDBlock.chain[a], 4, i),
                    "I",
                    "D",
                    "I",
                    'I',
                    Items.iron_ingot,
                    'D',
                    dye);
            }
        }
    }

    /** Stairs, slabs and walls from dyed blocks of one shade, with the vanilla shapes and amounts. */
    private static void addShapeRecipes() {
        for (MDBlock.Shape shape : MDBlock.SHAPES) {
            for (int color = 0; color < ColorIndex.count(); color++) {
                ItemStack base = new ItemStack(shape.base[ColorIndex.set(color)], 1, ColorIndex.shade(color));
                switch (shape.kind) {
                    case STAIRS -> GameRegistry
                        .addShapedRecipe(new ItemStack(shape.block, 4, color), "S  ", "SS ", "SSS", 'S', base);
                    case SLAB -> GameRegistry.addShapedRecipe(new ItemStack(shape.block, 6, color), "SSS", 'S', base);
                    case WALL -> GameRegistry
                        .addShapedRecipe(new ItemStack(shape.block, 6, color), "SSS", "SSS", 'S', base);
                }
            }
        }
    }

    /**
     * Plain granite, diorite and andesite with their Minecraft 1.8 recipes. Each is only added when no other mod
     * provides that stone (it would clash with theirs), so this runs in postInit after MDBlock.detectStones.
     */
    public static void addStoneRecipes() {
        if (MDBlock.useOwnDiorite) {
            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(MDBlock.dioritePlain, 2),
                    "CQ",
                    "QC",
                    'C',
                    "cobblestone",
                    'Q',
                    "gemQuartz"));
        }
        if (MDBlock.useOwnGranite) {
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(MDBlock.granitePlain),
                    new ItemStack(MDBlock.dioritePlain),
                    "gemQuartz"));
        }
        if (MDBlock.useOwnAndesite) {
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(MDBlock.andesitePlain, 2),
                    new ItemStack(MDBlock.dioritePlain),
                    "cobblestone"));
        }
    }

    private static void addSurroundRecipe(Block output, int meta, ItemStack surround, ItemStack dye) {
        GameRegistry.addShapedRecipe(new ItemStack(output, 8, meta), "SSS", "SDS", "SSS", 'S', surround, 'D', dye);
    }

    private static void addOreSurroundRecipe(Block output, int meta, String surround, ItemStack dye) {
        GameRegistry.addRecipe(
            new ShapedOreRecipe(new ItemStack(output, 8, meta), "SSS", "SDS", "SSS", 'S', surround, 'D', dye));
    }

    /** Four of a dyed block in a square make four of another in the same shade. */
    private static void addSquareRecipe(Block output, Block input, int meta) {
        GameRegistry.addShapedRecipe(new ItemStack(output, 4, meta), "SS", "SS", 'S', new ItemStack(input, 1, meta));
    }

    @SuppressWarnings("unchecked")
    private static void addPriorityRecipe(IRecipe recipe) {
        CraftingManager.getInstance()
            .getRecipeList()
            .add(0, recipe);
    }

    private static void addSmelting(Block input, Block output, int meta) {
        FurnaceRecipes.smelting()
            .func_151394_a(new ItemStack(input, 1, meta), new ItemStack(output, 1, meta), 0.1F);
    }

    public static void addSmeltingRecipes() {
        for (int a = 0; a < MDBlock.cobble.length; a++) {
            for (int i = 0; i <= ((MDBlockColored) MDBlock.cobble[a]).getMaxMeta(); i++) {
                FurnaceRecipes.smelting()
                    .func_151394_a(new ItemStack(MDBlock.log[a], 1, i), new ItemStack(Items.coal, 1, 1), 0.15F);
                FurnaceRecipes.smelting()
                    .func_151394_a(new ItemStack(MDBlock.cobble[a], 1, i), new ItemStack(MDBlock.stone[a], 1, i), 1.0f);
                FurnaceRecipes.smelting()
                    .func_151394_a(
                        new ItemStack(MDBlock.stoneBrick[a], 1, i),
                        new ItemStack(MDBlock.stoneBrickCracked[a], 1, i),
                        1.0f);
                FurnaceRecipes.smelting()
                    .func_151394_a(
                        new ItemStack(MDBlock.sand[a], 1, i),
                        new ItemStack(MDBlock.glassClear[a], 1, i),
                        1.0f);
                FurnaceRecipes.smelting()
                    .func_151394_a(
                        new ItemStack(MDBlock.glassClear[a], 1, i),
                        new ItemStack(MDBlock.glassFoggy[a], 1, i),
                        1.0f);
                FurnaceRecipes.smelting()
                    .func_151394_a(
                        new ItemStack(MDBlock.clay[a], 1, i),
                        new ItemStack(MDBlock.hardenedClay[a], 1, i),
                        1.0f);
                // Minecraft 1.12 to 1.16 smelting recipes
                addSmelting(MDBlock.stone[a], MDBlock.smoothStone[a], i);
                addSmelting(MDBlock.sandstone[a], MDBlock.smoothSandstone[a], i);
                addSmelting(MDBlock.quartz[a], MDBlock.smoothQuartz[a], i);
                addSmelting(MDBlock.netherBrick[a], MDBlock.crackedNetherBrick[a], i);
                addSmelting(MDBlock.hardenedClay[a], MDBlock.glazedTerracotta[a], i);
                // Basalt does not generate in 1.7; it forms from lava over soul soil in 1.16, so it is smelted from
                // dyed soul soil here.
                addSmelting(MDBlock.soulSoil[a], MDBlock.basalt[a], i);
            }
        }
    }
}
