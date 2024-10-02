package deserthydra.cortex.recipe;

import deserthydra.cortex.CortexUtils;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CortexRecipeSerializers {
	public static final RecipeSerializer<AnvilRecipe> ANVIL = Registry.register(
		Registries.RECIPE_SERIALIZER,
		CortexUtils.id("anvil"),
		new AnvilRecipe.Serializer()
	);

	public static void init() {}
}
