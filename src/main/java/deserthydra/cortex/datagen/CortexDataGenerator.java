/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.datagen.client.CortexEnglishLanguageProvider;
import deserthydra.cortex.datagen.client.CortexEnglishLanguageProviderOverride;
import deserthydra.cortex.datagen.client.CortexModelProvider;
import deserthydra.cortex.datagen.common.*;
import deserthydra.cortex.datagen.common.loot.CortexArcheologyLoot;
import deserthydra.cortex.datagen.common.loot.CortexChestLoot;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class CortexDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void buildRegistry(RegistrySetBuilder builder) {
		builder.add(Registries.CONFIGURED_FEATURE, CortexFeatureProvider::bootstrapConfiguredFeatures);
		builder.add(Registries.PLACED_FEATURE, CortexFeatureProvider::bootstrapPlacedFeatures);
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		var pack = generator.createPack();

		// common
		pack.addProvider(CortexBlockLootTableProvider::new);
		pack.addProvider(CortexBlockTagProvider::new);
		// TODO fill required table IDs
		pack.addProvider((output, registriesFuture) -> new LootTableProvider(output, Set.of(), List.of(
			new LootTableProvider.SubProviderEntry(CortexArcheologyLoot::new, LootContextParamSets.ARCHAEOLOGY),
			new LootTableProvider.SubProviderEntry(CortexChestLoot::new, LootContextParamSets.CHEST)
		), registriesFuture));
		pack.addProvider(CortexRecipeProvider.CortexRecipeGenerator::new);
		pack.addProvider(CortexWorldGenProvider::new);

		// client
		pack.addProvider(CortexModelProvider::new);
		pack.addProvider(CortexEnglishLanguageProvider::new);

		// vanilla override
		pack.addProvider(CortexAdvancementProviderOverride::new);
		pack.addProvider(CortexEnglishLanguageProviderOverride::new);
	}
}
