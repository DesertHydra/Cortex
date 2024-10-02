/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
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
