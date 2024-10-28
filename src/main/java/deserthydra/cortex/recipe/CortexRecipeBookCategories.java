package deserthydra.cortex.recipe;

import deserthydra.cortex.util.CortexUtils;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CortexRecipeBookCategories {
	public static final RecipeBookCategory ANVIL = Registry.register(
		Registries.RECIPE_BOOK_CATEGORY,
		CortexUtils.id("anvil"),
		new RecipeBookCategory()
	);
}
