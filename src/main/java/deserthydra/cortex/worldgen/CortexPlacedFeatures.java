/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.worldgen;

import deserthydra.cortex.CortexUtils;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.PlacedFeature;

public class CortexPlacedFeatures {
	public static final RegistryKey<PlacedFeature> REDSTONE_FORMATIONS = RegistryKey.of(
		RegistryKeys.PLACED_FEATURE,
		CortexUtils.id("redstone_formations")
	);

	public static final RegistryKey<PlacedFeature> LAPIS_FORMATIONS = RegistryKey.of(
		RegistryKeys.PLACED_FEATURE,
		CortexUtils.id("lapis_formations")
	);
}
