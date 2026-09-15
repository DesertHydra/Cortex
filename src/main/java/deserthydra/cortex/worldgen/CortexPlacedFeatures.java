/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.worldgen;

import deserthydra.cortex.util.CortexUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CortexPlacedFeatures {
	public static final ResourceKey<PlacedFeature> REDSTONE_FORMATIONS = ResourceKey.create(
		Registries.PLACED_FEATURE,
		CortexUtils.id("redstone_formations")
	);

	public static final ResourceKey<PlacedFeature> LAPIS_FORMATIONS = ResourceKey.create(
		Registries.PLACED_FEATURE,
		CortexUtils.id("lapis_formations")
	);

	public static final ResourceKey<PlacedFeature> ORE_COPPER = ResourceKey.create(
		Registries.PLACED_FEATURE,
		CortexUtils.id("ore_copper")
	);

	public static final ResourceKey<PlacedFeature> ORE_COPPER_LARGE = ResourceKey.create(
		Registries.PLACED_FEATURE,
		CortexUtils.id("ore_copper_large")
	);

	public static final ResourceKey<PlacedFeature> ORE_REDSTONE = ResourceKey.create(
		Registries.PLACED_FEATURE,
		CortexUtils.id("ore_redstone")
	);

	public static final ResourceKey<PlacedFeature> ORE_REDSTONE_LOWER = ResourceKey.create(
		Registries.PLACED_FEATURE,
		CortexUtils.id("ore_redstone_lower")
	);
}
