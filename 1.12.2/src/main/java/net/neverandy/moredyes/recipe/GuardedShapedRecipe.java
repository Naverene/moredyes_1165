package net.neverandy.moredyes.recipe;

import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

/**
 * A vanilla recipe that steps aside when one of the mod's recipes matches the same grid. Dyed planks count as
 * planks, so without this eight dyed planks of one shade would always make a plain chest instead of a dyed one:
 * the game takes the first matching recipe, and the vanilla ones come first.
 */
public class GuardedShapedRecipe extends ShapedRecipes
{
	private final List<IRecipe> preferred;

	public GuardedShapedRecipe(ShapedRecipes original,List<IRecipe> preferred)
	{
		super(original.getGroup(),original.recipeWidth,original.recipeHeight,original.recipeItems,original.getRecipeOutput());
		this.preferred=preferred;
		this.setRegistryName(original.getRegistryName());
	}
	@Override
	public boolean matches(InventoryCrafting inv,World worldIn)
	{
		if(!super.matches(inv,worldIn))
		{
			return false;
		}
		for(IRecipe recipe:this.preferred)
		{
			if(recipe.matches(inv,worldIn))
			{
				return false;
			}
		}
		return true;
	}
}
