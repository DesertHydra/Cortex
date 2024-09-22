/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.item.CortexItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.RegistryKeys;

import java.util.concurrent.CompletableFuture;

public class CortexBlockLootTableProvider extends FabricBlockLootTableProvider {
	protected CortexBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		this.add(CortexBlocks.REDSTONE_CRYSTALS, this.dropsWithSilkTouch(
			CortexBlocks.REDSTONE_CRYSTALS,
			this.applyExplosionDecay(
				CortexItems.REDSTONE_CRYSTALS,
				ItemEntry.builder(Items.REDSTONE)
					.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4.0F, 5.0F)))
					.apply(ApplyBonusLootFunction.method_456(this.field_51845.getLookupOrThrow(RegistryKeys.ENCHANTMENT).getHolderOrThrow(Enchantments.FORTUNE)))
			)
		));
	}
}
