/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.item.CortexItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;

public class CortexMod implements ModInitializer {
	@Override
	public void onInitialize() {
		CortexBlocks.init();
		CortexItems.init();

		BiomeModifications.addFeature(
			context -> context.hasFeature(OreConfiguredFeatures.ORE_REDSTONE),
			GenerationStep.Feature.VEGETAL_DECORATION,
			RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("cortex", "redstone_buds"))
		);
	}
}
