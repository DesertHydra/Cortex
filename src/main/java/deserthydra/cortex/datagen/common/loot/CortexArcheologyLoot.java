/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen.common.loot;

import deserthydra.cortex.item.CortexItems;
import deserthydra.cortex.loot.CortexLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import java.util.function.BiConsumer;

public record CortexArcheologyLoot(HolderLookup.Provider registries) implements LootTableSubProvider {

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
		biConsumer.accept(
			CortexLootTables.NETHER_RUINED_PORTAL_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(5))
						.add(LootItem.lootTableItem(CortexItems.ANCIENT_DEBRIS))
				)
		);

		biConsumer.accept(
			CortexLootTables.NETHER_FOSSIL_ARCHAEOLOGY,
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(5))
						.add(LootItem.lootTableItem(CortexItems.ANCIENT_DEBRIS))
				)
		);
	}
}
