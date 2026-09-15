/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe;

import deserthydra.cortex.recipe.anvil.AnvilRecipeDisplay;
import deserthydra.cortex.recipe.grinding.GrindstoneGrindingRecipeDisplay;
import deserthydra.cortex.util.CortexUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

public class CortexRecipeDisplayTypes {
	public static final RecipeDisplay.Type<AnvilRecipeDisplay> ANVIL = Registry.register(BuiltInRegistries.RECIPE_DISPLAY,  CortexUtils.id("anvil"), new RecipeDisplay.Type<>(AnvilRecipeDisplay.CODEC, AnvilRecipeDisplay.PACKET_CODEC));
	public static final RecipeDisplay.Type<GrindstoneGrindingRecipeDisplay> GRINDSTONE_GRINDING = Registry.register(BuiltInRegistries.RECIPE_DISPLAY,  CortexUtils.id("grindstone_grinding"), new RecipeDisplay.Type<>(GrindstoneGrindingRecipeDisplay.CODEC, GrindstoneGrindingRecipeDisplay.STREAM_CODEC));

	public static void init() {
	}
}
