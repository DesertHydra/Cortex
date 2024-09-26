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
import net.minecraft.block.Blocks;
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
		var enchantmentsLookup = this.field_51845.getLookupOrThrow(RegistryKeys.ENCHANTMENT);

		this.add(CortexBlocks.REDSTONE_FORMATION, block -> this.oreDrops(block, CortexItems.REDSTONE));

		this.add(CortexBlocks.LAPIS_FORMATION, block -> this.dropsWithSilkTouch(
			block,
			this.applyExplosionDecay(
				block,
				ItemEntry.builder(Items.LAPIS_LAZULI)
					.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0F, 6.0F)))
					.apply(ApplyBonusLootFunction.method_455(enchantmentsLookup.getHolderOrThrow(Enchantments.FORTUNE)))
			)
		));

		// Make Diamond Ore drop Raw Diamond
		this.add(Blocks.DIAMOND_ORE, block -> this.oreDrops(block, CortexItems.RAW_DIAMOND));
		this.add(Blocks.DEEPSLATE_DIAMOND_ORE, block -> this.oreDrops(block, CortexItems.RAW_DIAMOND));

		// Make Redstone Ore drop our Redstone
		this.add(Blocks.REDSTONE_ORE, block -> this.oreDrops(block, CortexItems.REDSTONE));
		this.add(Blocks.DEEPSLATE_REDSTONE_ORE, block -> this.oreDrops(block, CortexItems.REDSTONE));
	}
}
