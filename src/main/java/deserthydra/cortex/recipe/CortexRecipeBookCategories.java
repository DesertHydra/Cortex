/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
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

	public static void init() {}
}
