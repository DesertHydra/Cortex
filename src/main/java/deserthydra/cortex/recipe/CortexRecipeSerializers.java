/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
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
