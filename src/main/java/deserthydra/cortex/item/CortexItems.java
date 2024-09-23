/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.item;

import deserthydra.cortex.CortexUtils;
import deserthydra.cortex.block.CortexBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CortexItems {
	public static final Item REDSTONE_FORMATION = Registry.register(
		Registries.ITEM,
		CortexUtils.id("redstone_formation"),
		new BlockItem(CortexBlocks.REDSTONE_FORMATION, new Item.Settings())
	);

	public static final Item LAPIS_LAZULI_FORMATION = Registry.register(
		Registries.ITEM,
		CortexUtils.id("lapis_formation"),
		new BlockItem(CortexBlocks.LAPIS_LAZULI_FORMATION, new Item.Settings())
	);

	public static void init() {
		registerItemGroupOrder();
	}

	private static void registerItemGroupOrder() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL_BLOCKS).register(entries -> {
			entries.addAfter(Items.AMETHYST_CLUSTER, REDSTONE_FORMATION);
			entries.addAfter(REDSTONE_FORMATION, LAPIS_LAZULI_FORMATION);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE_BLOCKS).register(entries -> {
			entries.addAfter(Items.REDSTONE_ORE, REDSTONE_FORMATION);
		});
	}
}
