/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe;

import deserthydra.cortex.recipe.anvil.AnvilRecipe;
import deserthydra.cortex.recipe.grinding.GrindstoneGrindingRecipe;
import deserthydra.cortex.util.CortexUtils;
import deserthydra.cortex.util.RegistryUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CortexRecipeSerializers {
	public static final RecipeSerializer<AnvilRecipe> ANVIL = Registry.register(
		BuiltInRegistries.RECIPE_SERIALIZER,
		CortexUtils.id("anvil"),
		new RecipeSerializer<>(AnvilRecipe.CODEC, AnvilRecipe.STREAM_CODEC)
	);

	public static final RecipeSerializer<GrindstoneGrindingRecipe> GRINDSTONE_GRINDING = Registry.register(
		BuiltInRegistries.RECIPE_SERIALIZER,
		CortexUtils.id("grindstone_grinding"),
		new RecipeSerializer<>(GrindstoneGrindingRecipe.CODEC, GrindstoneGrindingRecipe.STREAM_CODEC)
	);

	public static void init() {}
}
