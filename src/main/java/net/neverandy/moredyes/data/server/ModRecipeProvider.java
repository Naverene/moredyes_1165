package net.neverandy.moredyes.data.server;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.data.ForgeRecipeProvider;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.reference.ColorStrings;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends ForgeRecipeProvider
{
    public ModRecipeProvider(DataGenerator gen)
    {
        super(gen);
    }

    @Override
    public void registerRecipes(@NotNull Consumer<IFinishedRecipe> consumer)
    {
        circleRecipe(consumer);
        woodToPlanks(consumer);
        variousStonebrickRecipes(consumer);
        sandToSandstone(consumer);
        craftingTables(consumer);
        smeltingRecipe(consumer);
        washingRecipes(consumer);
        //pistonRecipe(consumer); // pistons are not registered yet (see MDBlock.initialize)
        dyeLogs(consumer);

    }
    public void pistonRecipe(Consumer<IFinishedRecipe> consumer)
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapedRecipeBuilder.shapedRecipe(MDBlock.pistonArray[i])
                    .key('c', MDBlock.cobbleArray[i])
                    .key('w', MDBlock.oakPlankArray[i])
                    .key('i', Items.IRON_INGOT)
                    .key('r', Items.REDSTONE)
                    .patternLine("www")
                    .patternLine("cic")
                    .patternLine("crc")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "piston_recipe" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.pistonArray[i])
                    .addIngredient(Blocks.PISTON)
                    .addIngredient(MDItem.dye[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "piston_from_dye_recipe" + ColorStrings.ALL[i]);
        }
    }

    public void circleRecipe(Consumer<IFinishedRecipe> consumer)
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapedRecipeBuilder.shapedRecipe(MDBlock.stoneArray[i], 8)
                    .key('s', Blocks.STONE)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_stone_" + ColorStrings.ALL[i]);

            ShapedRecipeBuilder.shapedRecipe(MDBlock.lapisArray[i], 8)
                    .key('s', Blocks.LAPIS_BLOCK)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_lapis_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.glassArray[i], 8)
                    .key('s', Blocks.GLASS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_glass_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.obsidianArray[i], 8)
                    .key('s', Blocks.OBSIDIAN)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_obsidian_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.clayArray[i], 8)
                    .key('s', Blocks.CLAY)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_clay_" + ColorStrings.ALL[i]);

            ShapedRecipeBuilder.shapedRecipe(MDBlock.coalArray[i], 8)
                    .key('s', Blocks.COAL_BLOCK)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_coal_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.brickArray[i], 8)
                    .key('s', Blocks.BRICKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_bricks_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.cobbleArray[i], 8)
                    .key('s', Blocks.COBBLESTONE)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_cobble_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.oakPlankArray[i], 8)
                    .key('s', Blocks.OAK_PLANKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_oak_plank_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.darkOakPlankArray[i], 8)
                    .key('s', Blocks.DARK_OAK_PLANKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_dark_oak_plank_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.birchPlankArray[i], 8)
                    .key('s', Blocks.BIRCH_PLANKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_birch_plank_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.acaciaPlankArray[i], 8)
                    .key('s', Blocks.ACACIA_PLANKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_acacia_plank_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.junglePlankArray[i], 8)
                    .key('s', Blocks.JUNGLE_PLANKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_jungle_plank_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.oakLogArray[i], 8)
                    .key('s', Blocks.OAK_LOG)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_oak_log_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.darkOakLogArray[i], 8)
                    .key('s', Blocks.DARK_OAK_LOG)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_dark_oak_log_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.birchLogArray[i], 8)
                    .key('s', Blocks.BIRCH_LOG)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_birch_log_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.acaciaLogArray[i], 8)
                    .key('s', Blocks.ACACIA_LOG)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_acacia_log_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.jungleLogArray[i], 8)
                    .key('s', Blocks.JUNGLE_LOG)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_jungle_log_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.sandstoneArray[i], 8)
                    .key('s', Blocks.SANDSTONE)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_sandstone_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.sandArray[i], 8)
                    .key('s', Blocks.SAND)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_sand_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.workbenchArray[i], 8)
                    .key('s', Blocks.CRAFTING_TABLE)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .patternLine("sss")
                    .build(consumer, "circle_recipe_crafting_table_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.woolArray[i], 8)
                    .key('s', Blocks.WHITE_WOOL)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_wool_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.stonebrickCrackedArray[i], 8)
                    .key('s', Blocks.STONE_BRICKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_stonebrick_cracked_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.stonebrickArray[i], 8)
                    .key('s', Blocks.STONE_BRICKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_stonebrick_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.soulsandArray[i], 8)
                    .key('s', Blocks.SOUL_SAND)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_soul_sand_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.stonebrickCarvedArray[i], 8)
                    .key('s', Blocks.CHISELED_STONE_BRICKS)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_stonebrick_carved_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.dioriteArray[i], 8)
                    .key('s', Blocks.DIORITE)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_diorite_" + ColorStrings.ALL[i]);
            ShapedRecipeBuilder.shapedRecipe(MDBlock.andesiteArray[i], 8)
                    .key('s', Blocks.ANDESITE)
                    .key('d', MDItem.dye[i])
                    .patternLine("sss")
                    .patternLine("sds")
                    .patternLine("sss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "circle_recipe_andesite_" + ColorStrings.ALL[i]);
        }
    }
    public void variousStonebrickRecipes(Consumer<IFinishedRecipe> consumer)
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapedRecipeBuilder.shapedRecipe(MDBlock.stonebrickArray[i], 4)
                    .key('s', MDBlock.stoneArray[i])
                    .patternLine("ss")
                    .patternLine("ss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "stone_to_stonebrick_" + ColorStrings.ALL[i]);
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MDBlock.stonebrickArray[i]), MDBlock.stonebrickCrackedArray[i], 0.1f, 20)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "stonebrick_to_cracked_stonebrick_" + ColorStrings.ALL[i]);
        }
    }
    public void craftingTables(Consumer<IFinishedRecipe> consumer)
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapedRecipeBuilder.shapedRecipe(MDBlock.workbenchArray[i], 1)
                    .key('s', MDBlock.oakPlankArray[i])
                    .patternLine("ss")
                    .patternLine("ss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "workbench_crafting_" + ColorStrings.ALL[i]);
        }
    }

    public void smeltingRecipe(Consumer<IFinishedRecipe> consumer)
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MDBlock.sandArray[i]), MDBlock.glassArray[i], 2.0f, 20)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "sand_to_glass_" + ColorStrings.ALL[i]);
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MDBlock.cobbleArray[i]), MDBlock.stoneArray[i], 0.1f, 20)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "cobblestone_to_stone_" + ColorStrings.ALL[i]);
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MDBlock.clayArray[i]), MDBlock.hardenedClayArray[i], 1.0f, 20)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "clay_to_hardened_clay" + ColorStrings.ALL[i]);
        }

    }
    public void sandToSandstone(Consumer<IFinishedRecipe> consumer)
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapedRecipeBuilder.shapedRecipe(MDBlock.sandstoneArray[i])
                    .key('s', MDBlock.sandstoneArray[i])
                    .patternLine("ss")
                    .patternLine("ss")
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "sand_to_sandstone_" + ColorStrings.ALL[i]);
        }
    }

    public void woodToPlanks(Consumer<IFinishedRecipe> consumer)
    {
        //Shapeless
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.oakPlankArray[i], 4).addIngredient(MDBlock.oakLogArray[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "oak_log_to_planks_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.birchPlankArray[i], 4).addIngredient(MDBlock.birchPlankArray[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "birch_log_to_planks_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.acaciaPlankArray[i], 4).addIngredient(MDBlock.acaciaLogArray[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "acacia_log_to_planks_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.darkOakPlankArray[i], 4).addIngredient(MDBlock.darkOakLogArray[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "dark_oak_log_to_planks_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.junglePlankArray[i], 4).addIngredient(MDBlock.jungleLogArray[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "jungle_log_to_planks_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.sprucePlankArray[i], 4).addIngredient(MDBlock.spruceLogArray[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "spruce_log_to_planks_" + ColorStrings.ALL[i]);
        }

    }
    public void washingRecipes(Consumer<IFinishedRecipe> consumer)
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.STONE, 1)
                    .addIngredient(MDBlock.stoneArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "stone_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.COBBLESTONE, 1)
                    .addIngredient(MDBlock.cobbleArray[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "cobblestone_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.STONE_BRICKS, 1)
                    .addIngredient(MDBlock.stonebrickArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "stonebrick_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.CRACKED_STONE_BRICKS, 1)
                    .addIngredient(MDBlock.stonebrickCrackedArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "cracked_stonebrick_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.CLAY, 1)
                    .addIngredient(MDBlock.clayArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "clay_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.COAL_BLOCK, 1)
                    .addIngredient(MDBlock.coalArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "coal_block_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.BRICKS, 1)
                    .addIngredient(MDBlock.brickArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "bricks_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.OAK_LOG, 1)
                    .addIngredient(MDBlock.oakLogArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "log_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.OAK_PLANKS, 1)
                    .addIngredient(MDBlock.oakPlankArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "plank_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.GLASS, 1)
                    .addIngredient(MDBlock.glassArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "glass_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.GLOWSTONE, 1)
                    .addIngredient(MDBlock.glowstoneArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "glowstone_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.REDSTONE_BLOCK, 1)
                    .addIngredient(MDBlock.redstoneArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "redstone_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.WHITE_WOOL, 1)
                    .addIngredient(MDBlock.woolArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "white_wool_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.SOUL_SAND, 1)
                    .addIngredient(MDBlock.soulsandArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "soul_sand_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.LAPIS_BLOCK, 1)
                    .addIngredient(MDBlock.lapisArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "lapis_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.OBSIDIAN, 1)
                    .addIngredient(MDBlock.obsidianArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "obsidian_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.QUARTZ_BLOCK, 1)
                    .addIngredient(MDBlock.quartzArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "quartz_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.OAK_LEAVES, 1)
                    .addIngredient(MDBlock.oakLeafArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "leaves_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.ANDESITE, 1)
                    .addIngredient(MDBlock.andesiteArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "andesite_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.DIORITE, 1)
                    .addIngredient(MDBlock.dioriteArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "diorite_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.CRAFTING_TABLE, 1)
                    .addIngredient(MDBlock.workbenchArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "crafting_table_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.SAND, 1)
                    .addIngredient(MDBlock.sandArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "sand_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.SANDSTONE, 1)
                    .addIngredient(MDBlock.sandstoneArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "sandstone_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.WHITE_CONCRETE_POWDER, 1)
                    .addIngredient(MDBlock.concretePowderArray[i])
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "concrete_powder_washing_recipe_" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(Blocks.WHITE_CONCRETE)
                    .addIngredient(Ingredient.fromItems(MDBlock.concreteArray[i]))
                    .addIngredient(Items.WATER_BUCKET)
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "concrete_washing_recipe_" + ColorStrings.ALL[i]);
        }


    }
    public void planksToSticks()
    {
        /*
        for(int i = 0; i < blocks.length; i++)
        {
            ShapedRecipeBuilder.shapedRecipe(MDItem.sticksArray[i], 4)
                    .key('s', MDBlock.plankArray[i])
                    .patternLine("s")
                    .patternLine("s");
        }
        */
    }

    public void dyeLogs(Consumer<IFinishedRecipe> consumer)
    {
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.oakLogArray[i], 1)
                    .addIngredient(Blocks.OAK_LOG)
                    .addIngredient(MDItem.dye[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "oak_logs_from_dye" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.acaciaLogArray[i], 1)
                    .addIngredient(Blocks.ACACIA_LOG)
                    .addIngredient(MDItem.dye[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "acacia_logs_from_dye" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.birchLogArray[i], 1)
                    .addIngredient(Blocks.BIRCH_LOG)
                    .addIngredient(MDItem.dye[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "birch_logs_from_dye" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.jungleLogArray[i], 1)
                    .addIngredient(Blocks.JUNGLE_LOG)
                    .addIngredient(MDItem.dye[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "jungle_logs_from_dye" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.spruceLogArray[i], 1)
                    .addIngredient(Blocks.SPRUCE_LOG)
                    .addIngredient(MDItem.dye[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "spruce_logs_from_dye" + ColorStrings.ALL[i]);
            ShapelessRecipeBuilder.shapelessRecipe(MDBlock.darkOakLogArray[i], 1)
                    .addIngredient(Blocks.DARK_OAK_LOG)
                    .addIngredient(MDItem.dye[i])
                    .addCriterion("stone",InventoryChangeTrigger.Instance.forItems(Blocks.STONE))
                    .build(consumer, "dark_oak_logs_from_dye" + ColorStrings.ALL[i]);
        }
    }
}
