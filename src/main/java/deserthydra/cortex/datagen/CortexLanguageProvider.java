/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.item.CortexItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class CortexLanguageProvider extends FabricLanguageProvider {
	public CortexLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generateTranslations(HolderLookup.Provider lookup, TranslationBuilder builder) {
		builder.add(CortexBlocks.REDSTONE_FORMATION, "Redstone Formation");
		builder.add(CortexBlocks.LAPIS_FORMATION, "Lapis Lazuli Formation");
		builder.add(CortexBlocks.SUSPICIOUS_NETHERRACK, "Suspicious Netherrack");
		builder.add(CortexBlocks.SUSPICIOUS_SOUL_SAND, "Suspicious Soul Sand");
		builder.add(CortexBlocks.SUSPICIOUS_SOUL_SOIL, "Suspicious Soul Soil");

		builder.add(CortexItems.RAW_DIAMOND, "Raw Diamond");
		builder.add(CortexItems.RAW_EMERALD, "Raw Emerald");
		builder.add(CortexItems.REDSTONE, "Redstone");
		builder.add(CortexItems.ANCIENT_DEBRIS, "Ancient Debris");
	}
}
