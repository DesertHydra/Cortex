/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen.common;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.item.CortexItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class CortexBlockLootTableProvider extends FabricBlockLootSubProvider {
	public CortexBlockLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);

		this.add(CortexBlocks.REDSTONE_FORMATION, block -> this.createSilkTouchDispatchTable(
			block,
			this.applyExplosionDecay(
				block,
				LootItem.lootTableItem(CortexItems.REDSTONE)
					.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
					.apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
			)
		));

		this.add(CortexBlocks.LAPIS_FORMATION, block -> this.createSilkTouchDispatchTable(
			block,
			this.applyExplosionDecay(
				block,
				LootItem.lootTableItem(Items.LAPIS_LAZULI)
					.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
					.apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))
			)
		));

		// Make Diamond Ores drop Raw Diamond
		this.add(Blocks.DIAMOND_ORE, block -> this.createOreDrop(block, CortexItems.RAW_DIAMOND));
		this.add(Blocks.DEEPSLATE_DIAMOND_ORE, block -> this.createOreDrop(block, CortexItems.RAW_DIAMOND));

		// Make Emerald Ores drop Raw Emerald
		this.add(Blocks.EMERALD_ORE, block -> this.createOreDrop(block, CortexItems.RAW_EMERALD));
		this.add(Blocks.DEEPSLATE_EMERALD_ORE, block -> this.createOreDrop(block, CortexItems.RAW_EMERALD));

		// Make Redstone Ores drop our Redstone
		this.add(Blocks.REDSTONE_ORE, block -> this.createOreDrop(block, CortexItems.REDSTONE));
		this.add(Blocks.DEEPSLATE_REDSTONE_ORE, block -> this.createOreDrop(block, CortexItems.REDSTONE));

		// Make Lapis Ores drop 1 (one) Lapis
		this.add(Blocks.LAPIS_ORE, block -> this.createOreDrop(block, Items.LAPIS_LAZULI));
		this.add(Blocks.DEEPSLATE_LAPIS_ORE, block -> this.createOreDrop(block, Items.LAPIS_LAZULI));

		// Make suspicious blocks drop nothing
		this.add(CortexBlocks.SUSPICIOUS_NETHERRACK, noDrop());
		this.add(CortexBlocks.SUSPICIOUS_SOUL_SAND, noDrop());
		this.add(CortexBlocks.SUSPICIOUS_SOUL_SOIL, noDrop());
	}
}
