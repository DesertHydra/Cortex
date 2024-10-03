/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Holder;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.RegistryKeys;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CortexWorldGenProvider extends FabricDynamicRegistryProvider {
	public CortexWorldGenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		addAll(entries, registries.getLookupOrThrow(RegistryKeys.CONFIGURED_FEATURE));
		addAll(entries, registries.getLookupOrThrow(RegistryKeys.PLACED_FEATURE));
	}

	@Override
	public String getName() {
		return "Cortex World Generation Provider";
	}

	// This will help us datagen some hacky stuff later
	public static <T> List<Holder<T>> addAll(Entries entries, HolderLookup.RegistryLookup<T> registry) {
		return registry.streamElementKeys()
			.filter(registryKey -> registryKey.getValue().getNamespace().equals("cortex"))
			.map(key -> entries.add(registry, key))
			.toList();
	}
}
