/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen.common;

import deserthydra.cortex.util.CortexUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CortexWorldGenProvider extends FabricDynamicRegistryProvider {
	public CortexWorldGenProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		addAll(entries, registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
		addAll(entries, registries.lookupOrThrow(Registries.PLACED_FEATURE));
	}

	@Override
	public String getName() {
		return "Cortex World Generation Provider";
	}

	// This will help us datagen some hacky stuff later
	public static <T> List<Holder<T>> addAll(Entries entries, HolderLookup.RegistryLookup<T> registry) {
		return registry.listElementIds()
			.filter(registryKey -> registryKey.identifier().getNamespace().equals(CortexUtils.MOD_ID))
			.map(key -> entries.add(registry, key))
			.toList();
	}
}
