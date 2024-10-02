package deserthydra.cortex.recipe;

import deserthydra.cortex.CortexUtils;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CortexRecipeTypes {
	public static final RecipeType<AnvilRecipe> ANVIL = Registry.register(
		Registries.RECIPE_TYPE,
		CortexUtils.id("anvil"),
		new RecipeType<AnvilRecipe>() {
			@Override
			public String toString() {
				return "cortex:anvil";
			}
		}
	);

	public static void init() {}
}
