/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe;

import deserthydra.cortex.util.CortexUtils;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CortexRecipeDisplayTypes {
	public static void init() {
		Registry.register(Registries.RECIPE_DISPLAY, CortexUtils.id("anvil"), AnvilRecipeDisplay.TYPE);
	}
}
