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
import net.minecraft.world.item.crafting.RecipeType;

public class CortexRecipeTypes {
	public static final RecipeType<AnvilRecipe> ANVIL = RegistryUtils.registerRecipeType("anvil");
	public static final RecipeType<GrindstoneGrindingRecipe> GRINDSTONE_GRINDING = RegistryUtils.registerRecipeType("grindstone_grinding");

	public static void init() {}
}
