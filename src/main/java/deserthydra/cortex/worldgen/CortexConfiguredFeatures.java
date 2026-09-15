/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.worldgen;

import deserthydra.cortex.util.CortexUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CortexConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> REDSTONE_FORMATIONS = ResourceKey.create(
		Registries.CONFIGURED_FEATURE,
		CortexUtils.id("redstone_formations")
	);

	public static final ResourceKey<ConfiguredFeature<?, ?>> LAPIS_FORMATIONS = ResourceKey.create(
		Registries.CONFIGURED_FEATURE,
		CortexUtils.id("lapis_formations")
	);
}
