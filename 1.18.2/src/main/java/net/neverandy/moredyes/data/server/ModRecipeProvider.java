package net.neverandy.moredyes.data.server;

import com.google.gson.JsonObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.data.condition.WallsEnabledCondition;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.world.DyeTrees;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Every MoreDyes recipe, following 1.7.10:
 * <ul>
 * <li>dyes are mixed from two vanilla dyes (or four for one color), and a tulip makes one dye of its color;</li>
 * <li>eight vanilla blocks around a dye make eight dyed blocks ("dyeing/");</li>
 * <li>a dyed block with a water bucket gives the vanilla block back ("washing/");</li>
 * <li>dyed blocks turn into each other the way their vanilla blocks do (logs to planks, cobble to stone...).</li>
 * </ul>
 */
public class ModRecipeProvider extends RecipeProvider
{
    /** Result color, count, then the vanilla dyes mixed to make it. From 1.7.10's CraftManager. */
    private static final String[][] DYE_MIXES = {
        {"ecbf99", "2", "white", "orange"},
        {"d9a6ec", "2", "white", "magenta"},
        {"b3ccec", "2", "white", "light_blue"},
        {"f2f299", "2", "white", "yellow"},
        {"bfe68c", "2", "white", "lime"},
        {"f9bfd2", "2", "white", "pink"},
        {"cccccc", "2", "white", "light_gray"},
        {"a6bfcc", "2", "white", "cyan"},
        {"bf9fd9", "2", "white", "purple"},
        {"99a6d9", "2", "white", "blue"},
        {"b3a699", "2", "white", "brown"},
        {"b3bf99", "2", "white", "green"},
        {"cc9999", "4", "white", "white", "red", "red"},
        {"c56685", "2", "orange", "magenta"},
        {"9f8c85", "2", "orange", "light_blue"},
        {"deb233", "2", "orange", "yellow"},
        {"aca526", "2", "orange", "lime"},
        {"e57f6c", "2", "orange", "pink"},
        {"92663f", "2", "orange", "gray"},
        {"b98c66", "2", "orange", "light_gray"},
        {"927f66", "2", "orange", "cyan"},
        {"ac5f72", "2", "orange", "purple"},
        {"866672", "2", "orange", "blue"},
        {"9f6633", "2", "orange", "brown"},
        {"9f7f33", "2", "orange", "green"},
        {"b95933", "2", "orange", "red"},
        {"794c26", "2", "orange", "black"},
        {"8c72d8", "2", "magenta", "light_blue"},
        {"cb9886", "2", "magenta", "yellow"},
        {"998c79", "2", "magenta", "lime"},
        {"d265bf", "2", "magenta", "pink"},
        {"7f4c92", "2", "magenta", "gray"},
        {"a672b9", "2", "magenta", "light_gray"},
        {"7f65b9", "2", "magenta", "cyan"},
        {"9946c5", "2", "magenta", "purple"},
        {"734cc5", "2", "magenta", "blue"},
        {"8c4c86", "2", "magenta", "brown"},
        {"8c6586", "2", "magenta", "green"},
        {"a64086", "2", "magenta", "red"},
        {"663379", "2", "magenta", "black"},
        {"a5bf86", "2", "light_blue", "yellow"},
        {"72b279", "2", "light_blue", "lime"},
        {"ac8cbf", "2", "light_blue", "pink"},
        {"597392", "2", "light_blue", "gray"},
        {"7f99b9", "2", "light_blue", "light_gray"},
        {"598cb9", "2", "light_blue", "cyan"},
        {"726cc5", "2", "light_blue", "purple"},
        {"4d73c5", "2", "light_blue", "blue"},
        {"667386", "2", "light_blue", "brown"},
        {"668c86", "2", "light_blue", "green"},
        {"7f6686", "2", "light_blue", "red"},
        {"405979", "2", "light_blue", "black"},
        {"b2d926", "2", "yellow", "lime"},
        {"ebb26c", "2", "yellow", "pink"},
        {"99993f", "2", "yellow", "gray"},
        {"bfbf66", "2", "yellow", "light_gray"},
        {"99b266", "2", "yellow", "cyan"},
        {"b29272", "2", "yellow", "purple"},
        {"8c9972", "2", "yellow", "blue"},
        {"a69933", "2", "yellow", "brown"},
        {"a6b233", "2", "yellow", "green"},
        {"bf8c33", "2", "yellow", "red"},
        {"7f7f26", "2", "yellow", "black"},
        {"b8a65f", "2", "lime", "pink"},
        {"668c32", "2", "lime", "gray"},
        {"8cb359", "2", "lime", "light_gray"},
        {"66a659", "2", "lime", "cyan"},
        {"7f5665", "2", "lime", "purple"},
        {"598c65", "2", "lime", "blue"},
        {"738c26", "2", "lime", "brown"},
        {"73a626", "2", "lime", "green"},
        {"8c8026", "2", "lime", "red"},
        {"4c7319", "2", "lime", "black"},
        {"864c5f", "2", "pink", "gray"},
        {"c6596c", "2", "pink", "light_gray"},
        {"ac7f6c", "2", "pink", "cyan"},
        {"ac666c", "2", "pink", "purple"},
        {"9366ab", "2", "pink", "blue"},
        {"b95fab", "2", "pink", "brown"},
        {"9f7f9f", "2", "pink", "green"},
        {"c68c9f", "2", "pink", "red"},
        {"9f6679", "2", "pink", "black"},
        {"727272", "2", "gray", "light_gray"},
        {"4c6572", "2", "gray", "cyan"},
        {"65467f", "2", "gray", "purple"},
        {"404c7f", "2", "gray", "blue"},
        {"594c40", "2", "gray", "brown"},
        {"596540", "2", "gray", "green"},
        {"724040", "2", "gray", "red"},
        {"333333", "2", "gray", "black"},
        {"738c99", "2", "light_gray", "cyan"},
        {"8c6ca5", "2", "light_gray", "purple"},
        {"6673a5", "2", "light_gray", "blue"},
        {"807366", "2", "light_gray", "brown"},
        {"808c66", "2", "light_gray", "green"},
        {"996666", "2", "light_gray", "red"},
        {"595959", "2", "light_gray", "black"},
        {"655fa5", "2", "cyan", "purple"},
        {"4066a5", "2", "cyan", "blue"},
        {"596666", "2", "cyan", "brown"},
        {"597f66", "2", "cyan", "green"},
        {"725966", "2", "cyan", "red"},
        {"334c59", "2", "cyan", "black"},
        {"5945b2", "2", "purple", "blue"},
        {"734573", "2", "purple", "brown"},
        {"735f73", "2", "purple", "green"},
        {"8c3973", "2", "purple", "red"},
        {"4c2c66", "2", "purple", "black"},
        {"4c4c73", "2", "blue", "brown"},
        {"4c6573", "2", "blue", "green"},
        {"664073", "2", "blue", "red"},
        {"263366", "2", "blue", "black"},
        {"403326", "2", "brown", "green"},
        {"7f4033", "2", "brown", "red"},
        {"666533", "2", "brown", "black"},
        {"7f5933", "2", "green", "red"},
        {"404c26", "2", "green", "black"},
        {"592626", "2", "red", "black"},
    };

    private Consumer<FinishedRecipe> out;

    public ModRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    /** Recipes unlock without advancements, so none are written. */
    @Override
    protected void saveAdvancement(HashCache cache, JsonObject json, Path path)
    {
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {
        out = consumer;
        for (String[] mix : DYE_MIXES)
        {
            int color = Arrays.asList(ColorStrings.ALL).indexOf(mix[0]);
            ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(MDItem.dye[color], Integer.parseInt(mix[1]));
            for (int d = 2; d < mix.length; d++)
            {
                builder.requires(mc(mix[d] + "_dye"));
            }
            builder.unlockedBy("has_dye", has(mc(mix[2] + "_dye"))).save(out, id("dye/" + mix[0]));
        }

        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            ShapelessRecipeBuilder.shapeless(MDItem.dye[i]).requires(MDBlock.tulipArray[i])
                    .unlockedBy("has_tulip", has(MDBlock.tulipArray[i])).save(out, id("dye/" + ColorStrings.ALL[i] + "_from_tulip"));

            dyeable(i, MDBlock.woolArray[i], "white_wool");
            dyeable(i, MDBlock.stoneArray[i], "stone");
            dyeable(i, MDBlock.cobbleArray[i], "cobblestone");
            dyeable(i, MDBlock.stonebrickArray[i], "stone_bricks");
            dyeable(i, MDBlock.stonebrickCrackedArray[i], "cracked_stone_bricks");
            dyeable(i, MDBlock.stonebrickCarvedArray[i], "chiseled_stone_bricks");
            dyeable(i, MDBlock.brickArray[i], "bricks");
            dyeable(i, MDBlock.clayArray[i], "clay");
            dyeable(i, MDBlock.hardenedClayArray[i], "terracotta");
            dyeable(i, MDBlock.coalArray[i], "coal_block");
            dyeable(i, MDBlock.lapisArray[i], "lapis_block");
            dyeable(i, MDBlock.redstoneArray[i], "redstone_block");
            dyeable(i, MDBlock.quartzArray[i], "quartz_block");
            dyeable(i, MDBlock.obsidianArray[i], "obsidian");
            dyeable(i, MDBlock.glowstoneArray[i], "glowstone");
            dyeable(i, MDBlock.soulsandArray[i], "soul_sand");
            dyeable(i, MDBlock.sandArray[i], "sand");
            dyeable(i, MDBlock.sandstoneArray[i], "sandstone");
            dyeable(i, MDBlock.sandstoneCarvedArray[i], "chiseled_sandstone");
            dyeable(i, MDBlock.sandstoneSmoothArray[i], "cut_sandstone");
            dyeable(i, MDBlock.glassArray[i], "glass");
            dyeable(i, MDBlock.andesiteArray[i], "andesite");
            dyeable(i, MDBlock.dioriteArray[i], "diorite");
            dyeable(i, MDBlock.concreteArray[i], "white_concrete");
            dyeable(i, MDBlock.concretePowderArray[i], "white_concrete_powder");
            washable(MDBlock.glassFoggyArray[i], "glass");

            // Like the vanilla blocks
            smelt(MDBlock.cobbleArray[i], MDBlock.stoneArray[i], 0.1F);
            smelt(MDBlock.stonebrickArray[i], MDBlock.stonebrickCrackedArray[i], 0.1F);
            smelt(MDBlock.sandArray[i], MDBlock.glassArray[i], 0.1F);
            smelt(MDBlock.glassArray[i], MDBlock.glassFoggyArray[i], 0.1F);
            smelt(MDBlock.clayArray[i], MDBlock.hardenedClayArray[i], 0.35F);
            square(MDBlock.stoneArray[i], MDBlock.stonebrickArray[i]);
            square(MDBlock.sandArray[i], MDBlock.sandstoneArray[i]);
            square(MDBlock.sandstoneArray[i], MDBlock.sandstoneSmoothArray[i]);
            unpack(MDBlock.redstoneArray[i], Items.REDSTONE);
            unpack(MDBlock.lapisArray[i], Items.LAPIS_LAZULI);

            Block[] allPlanks = new Block[DyeTrees.WOODS.length];
            for (int w = 0; w < DyeTrees.WOODS.length; w++)
            {
                wood(i, DyeTrees.WOODS[w]);
                allPlanks[w] = DyeTrees.planks(DyeTrees.WOODS[w])[i];
            }

            Block workbench = MDBlock.workbenchArray[i];
            ShapedRecipeBuilder.shaped(workbench).define('P', Ingredient.of(allPlanks)).pattern("PP").pattern("PP")
                    .unlockedBy("has_planks", has(allPlanks[0])).save(out, id(name(workbench)));
            ShapelessRecipeBuilder.shapeless(workbench).requires(Items.CRAFTING_TABLE).requires(MDItem.dye[i])
                    .unlockedBy("has_dye", has(MDItem.dye[i])).save(out, id("dyeing/" + name(workbench)));
            washable(workbench, "crafting_table");

            Block chest = MDBlock.chestArray[i];
            ShapedRecipeBuilder.shaped(chest).define('P', Ingredient.of(allPlanks)).pattern("PPP").pattern("P P").pattern("PPP")
                    .unlockedBy("has_planks", has(allPlanks[0])).save(out, id(name(chest)));
            ShapelessRecipeBuilder.shapeless(chest).requires(Items.CHEST).requires(MDItem.dye[i])
                    .unlockedBy("has_dye", has(MDItem.dye[i])).save(out, id("dyeing/" + name(chest)));
            washable(chest, "chest");

            Block shelf = MDBlock.bookshelfArray[i];
            ShapedRecipeBuilder.shaped(shelf).define('P', Ingredient.of(allPlanks)).define('B', Items.BOOK)
                    .pattern("PPP").pattern("BBB").pattern("PPP")
                    .unlockedBy("has_planks", has(allPlanks[0])).save(out, id(name(shelf)));
            dyeable(i, shelf, "bookshelf");

            // The vanilla piston recipe in dyed planks and dyed cobblestone of one color, and slime makes it sticky.
            Block piston = MDBlock.pistonArray[i];
            Block stickyPiston = MDBlock.stickyPistonArray[i];
            ShapedRecipeBuilder.shaped(piston).define('P', Ingredient.of(allPlanks)).define('C', MDBlock.cobbleArray[i])
                    .define('I', Items.IRON_INGOT).define('R', Items.REDSTONE)
                    .pattern("PPP").pattern("CIC").pattern("CRC")
                    .unlockedBy("has_planks", has(allPlanks[0])).save(out, id(name(piston)));
            ShapedRecipeBuilder.shaped(stickyPiston).define('S', Items.SLIME_BALL).define('P', piston)
                    .pattern("S").pattern("P")
                    .unlockedBy("has_piston", has(piston)).save(out, id(name(stickyPiston)));
            dyeable(i, piston, "piston");
            dyeable(i, stickyPiston, "sticky_piston");

            // Six glass make sixteen panes, like vanilla; clear panes can also be dyed and washed.
            panes(MDBlock.glassArray[i], MDBlock.glassPaneArray[i]);
            panes(MDBlock.glassFoggyArray[i], MDBlock.glassFoggyPaneArray[i]);
            dyeable(i, MDBlock.glassPaneArray[i], "glass_pane");

            dyeable(i, MDBlock.graniteArray[i], "granite");
            dyeable(i, MDBlock.polishedAndesiteArray[i], "polished_andesite");
            dyeable(i, MDBlock.polishedDioriteArray[i], "polished_diorite");
            dyeable(i, MDBlock.polishedGraniteArray[i], "polished_granite");
            dyeable(i, MDBlock.endstoneArray[i], "end_stone");
            dyeable(i, MDBlock.mossyCobbleArray[i], "mossy_cobblestone");
            dyeable(i, MDBlock.mossyStonebrickArray[i], "mossy_stone_bricks");
            dyeable(i, MDBlock.quartzBricksArray[i], "quartz_bricks");
            dyeable(i, MDBlock.quartzChiseledArray[i], "chiseled_quartz_block");
            dyeable(i, MDBlock.quartzPillarArray[i], "quartz_pillar");
            dyeable(i, MDBlock.quartzSmoothArray[i], "smooth_quartz");
            dyeable(i, MDBlock.boneBlockArray[i], "bone_block");
            dyeable(i, MDBlock.gravelArray[i], "gravel");
            dyeable(i, MDBlock.iceArray[i], "ice");
            dyeable(i, MDBlock.packedIceArray[i], "packed_ice");
            dyeable(i, MDBlock.snowArray[i], "snow_block");
            square(MDBlock.andesiteArray[i], MDBlock.polishedAndesiteArray[i]);
            square(MDBlock.dioriteArray[i], MDBlock.polishedDioriteArray[i]);
            square(MDBlock.graniteArray[i], MDBlock.polishedGraniteArray[i]);
            square(MDBlock.quartzArray[i], MDBlock.quartzBricksArray[i]);
            smelt(MDBlock.quartzArray[i], MDBlock.quartzSmoothArray[i], 0.1F);
            mossy(MDBlock.cobbleArray[i], MDBlock.mossyCobbleArray[i]);
            mossy(MDBlock.stonebrickArray[i], MDBlock.mossyStonebrickArray[i]);
            ShapedRecipeBuilder.shaped(MDBlock.quartzPillarArray[i], 2).define('Q', MDBlock.quartzArray[i]).pattern("Q").pattern("Q")
                    .unlockedBy("has_block", has(MDBlock.quartzArray[i])).save(out, id(name(MDBlock.quartzPillarArray[i])));
            ShapedRecipeBuilder.shaped(MDBlock.packedIceArray[i]).define('I', MDBlock.iceArray[i])
                    .pattern("III").pattern("III").pattern("III")
                    .unlockedBy("has_block", has(MDBlock.iceArray[i])).save(out, id(name(MDBlock.packedIceArray[i])));
            unpack(MDBlock.boneBlockArray[i], Items.BONE_MEAL);

            // Dyed flowers are for decoration; they don't make dye, or eight flowers and a dye would make eight dyes.
            for (int f = 0; f < MDBlock.SMALL_FLOWERS.length; f++)
            {
                dyeable(i, MDBlock.smallFlowerArrays[f][i], MDBlock.SMALL_FLOWERS[f][1]);
            }
            for (int f = 0; f < MDBlock.TALL_FLOWERS.length; f++)
            {
                dyeable(i, MDBlock.tallFlowerArrays[f][i], MDBlock.TALL_FLOWERS[f][1]);
            }

            for (DyedShapes shapes : DyedShapes.ALL)
            {
                shapes(i, shapes);
            }
        }
    }

    /** The vanilla slab, stairs and wall recipes from the dyed full block, and the stonecutter for stone kinds. */
    private void shapes(int i, DyedShapes shapes)
    {
        Block full = shapes.full[i];
        Block slab = shapes.slabs[i];
        Block stairs = shapes.stairs[i];
        ShapedRecipeBuilder.shaped(slab, 6).define('#', full).pattern("###")
                .unlockedBy("has_block", has(full)).save(out, id(name(slab)));
        ShapedRecipeBuilder.shaped(stairs, 4).define('#', full).pattern("#  ").pattern("## ").pattern("###")
                .unlockedBy("has_block", has(full)).save(out, id(name(stairs)));
        boolean stone = full.defaultBlockState().getMaterial() == Material.STONE;
        if (stone)
        {
            stonecutting(full, slab, 2);
            stonecutting(full, stairs, 1);
        }
        if (shapes.walls.length > 0)
        {
            // Only loaded while walls are turned on, since the wall items don't exist otherwise.
            Block wall = shapes.walls[i];
            ConditionalRecipe.builder().addCondition(WallsEnabledCondition.INSTANCE)
                    .addRecipe(ShapedRecipeBuilder.shaped(wall, 6).define('#', full).pattern("###").pattern("###")
                            .unlockedBy("has_block", has(full))::save)
                    .build(out, id(name(wall)));
            if (stone)
            {
                ConditionalRecipe.builder().addCondition(WallsEnabledCondition.INSTANCE)
                        .addRecipe(c -> SingleItemRecipeBuilder.stonecutting(Ingredient.of(full), wall, 1)
                                .unlockedBy("has_block", has(full)).save(c, id("stonecutting/" + name(wall))))
                        .build(out, id("stonecutting/" + name(wall)));
            }
        }
        if (shapes.type.equals("quartz"))
        {
            // Two quartz slabs make chiseled quartz, like vanilla.
            Block chiseled = MDBlock.quartzChiseledArray[i];
            ShapedRecipeBuilder.shaped(chiseled).define('#', slab).pattern("#").pattern("#")
                    .unlockedBy("has_slab", has(slab)).save(out, id(name(chiseled)));
        }
    }

    private void stonecutting(Block input, Block result, int count)
    {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), result, count)
                .unlockedBy("has_block", has(input)).save(out, id("stonecutting/" + name(result)));
    }

    /** A dyed block and a vine make its mossy kind, like vanilla. */
    private void mossy(Block block, Block result)
    {
        ShapelessRecipeBuilder.shapeless(result).requires(block).requires(Items.VINE)
                .unlockedBy("has_block", has(block)).save(out, id(name(result)));
    }

    private void wood(int i, String wood)
    {
        Block log = DyeTrees.logs(wood)[i];
        Block planks = DyeTrees.planks(wood)[i];
        Block fence = DyeTrees.fences(wood)[i];
        Block sapling = DyeTrees.saplings(wood)[i];
        dyeable(i, log, wood + "_log");
        dyeable(i, planks, wood + "_planks");
        dyeable(i, DyeTrees.leaves(wood)[i], wood + "_leaves");
        dyeable(i, fence, wood + "_fence");
        ShapelessRecipeBuilder.shapeless(sapling).requires(mc(wood + "_sapling")).requires(MDItem.dye[i])
                .unlockedBy("has_dye", has(MDItem.dye[i])).save(out, id("dyeing/" + name(sapling)));
        washable(sapling, wood + "_sapling");

        ShapelessRecipeBuilder.shapeless(planks, 4).requires(log)
                .unlockedBy("has_log", has(log)).save(out, id(name(planks)));
        ShapedRecipeBuilder.shaped(fence, 3).define('W', planks).define('#', Items.STICK).pattern("W#W").pattern("W#W")
                .unlockedBy("has_planks", has(planks)).save(out, id(name(fence)));
    }

    /** Eight vanilla blocks around a dye make eight dyed blocks, and water washes the color out again. */
    private void panes(Block glass, Block pane)
    {
        ShapedRecipeBuilder.shaped(pane, 16).define('G', glass).pattern("GGG").pattern("GGG")
                .unlockedBy("has_glass", has(glass)).save(out, id(name(pane)));
    }

    private void dyeable(int i, Block dyed, String vanilla)
    {
        ShapedRecipeBuilder.shaped(dyed, 8).define('S', mc(vanilla)).define('D', MDItem.dye[i])
                .pattern("SSS").pattern("SDS").pattern("SSS")
                .unlockedBy("has_dye", has(MDItem.dye[i])).save(out, id("dyeing/" + name(dyed)));
        washable(dyed, vanilla);
    }

    private void washable(Block dyed, String vanilla)
    {
        ShapelessRecipeBuilder.shapeless(mc(vanilla)).requires(dyed).requires(Items.WATER_BUCKET)
                .unlockedBy("has_block", has(dyed)).save(out, id("washing/" + name(dyed)));
    }

    private void smelt(Block input, Block result, float xp)
    {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), result, xp, 200)
                .unlockedBy("has_block", has(input)).save(out, id(name(result) + "_from_smelting"));
    }

    /** Four blocks in a square make four of the next block, like stone to stone bricks. */
    private void square(Block input, Block result)
    {
        ShapedRecipeBuilder.shaped(result, 4).define('S', input).pattern("SS").pattern("SS")
                .unlockedBy("has_block", has(input)).save(out, id(name(result)));
    }

    private void unpack(Block block, ItemLike item)
    {
        ShapelessRecipeBuilder.shapeless(item, 9).requires(block)
                .unlockedBy("has_block", has(block)).save(out, id("unpacking/" + name(block)));
    }

    private static Item mc(String name)
    {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", name));
        if (item == null || item == Items.AIR)
        {
            throw new IllegalArgumentException("No vanilla item " + name);
        }
        return item;
    }

    private static ResourceLocation id(String path)
    {
        return new ResourceLocation(Reference.MOD_ID, path);
    }

    private static String name(Block block)
    {
        return block.getRegistryName().getPath();
    }
}
