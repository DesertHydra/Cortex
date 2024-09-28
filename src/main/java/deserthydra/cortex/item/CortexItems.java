/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.item;

import deserthydra.cortex.CortexUtils;
import deserthydra.cortex.block.CortexBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CortexItems {
	public static final Map<Identifier, Item> ITEMS_TO_REGISTER = new HashMap<>();

	/**
	 * Registers an item using a Cortex id.
	 */
	public static Item item(String path, Item item) {
		ITEMS_TO_REGISTER.put(CortexUtils.id(path), item);
		return item;
	}

	public static final Item REDSTONE_FORMATION = item(
		"redstone_formation",
		new BlockItem(CortexBlocks.REDSTONE_FORMATION, new Item.Settings())
	);

	public static final Item LAPIS_FORMATION = item(
		"lapis_formation",
		new BlockItem(CortexBlocks.LAPIS_FORMATION, new Item.Settings())
	);

	public static final Item RAW_DIAMOND = item(
		"raw_diamond",
		new Item(new Item.Settings())
	);

	public static final Item RAW_EMERALD = item(
		"raw_emerald",
		new Item(new Item.Settings())
	);

	public static final Item REDSTONE = item(
		"redstone",
		new Item(new Item.Settings())
	);

	public static void init() {
		for (var entry : ITEMS_TO_REGISTER.entrySet()) {
			Registry.register(Registries.ITEM, entry.getKey(), entry.getValue());
		}

		registerItemGroupOrder();
	}

	private static void registerItemGroupOrder() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL_BLOCKS).register(entries -> {
			entries.addAfter(Items.AMETHYST_CLUSTER, REDSTONE_FORMATION);
			entries.addAfter(REDSTONE_FORMATION, LAPIS_FORMATION);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE_BLOCKS).register(entries -> {
			entries.addAfter(Items.REDSTONE, REDSTONE);
			entries.addAfter(Items.REDSTONE_BLOCK, REDSTONE_FORMATION);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addAfter(Items.RAW_GOLD, RAW_DIAMOND, RAW_EMERALD);
			entries.addAfter(Items.LAPIS_LAZULI, REDSTONE);
		});
	}
}
