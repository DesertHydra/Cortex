/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.item;

import deserthydra.cortex.util.CortexUtils;
import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.util.RegistryUtils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CortexItems {
	public static final Item REDSTONE_FORMATION = Items.register(CortexBlocks.REDSTONE_FORMATION);

	public static final Item LAPIS_FORMATION = Items.register(CortexBlocks.LAPIS_FORMATION);

	public static final Item SUSPICIOUS_NETHERRACK = Items.register(CortexBlocks.SUSPICIOUS_NETHERRACK);

	public static final Item SUSPICIOUS_SOUL_SAND = Items.register(CortexBlocks.SUSPICIOUS_SOUL_SAND);

	public static final Item SUSPICIOUS_SOUL_SOIL = Items.register(CortexBlocks.SUSPICIOUS_SOUL_SOIL);

	public static final Item RAW_DIAMOND = RegistryUtils.register("raw_diamond");

	public static final Item RAW_EMERALD = RegistryUtils.register("raw_emerald");

	public static final Item REDSTONE = RegistryUtils.register("redstone");

	public static final Item ANCIENT_DEBRIS = RegistryUtils.register("ancient_debris", new Item.Settings().fireproof());

	public static final Item MOLTEN_DEBRIS = RegistryUtils.register("molten_debris", new Item.Settings().fireproof());

	public static void init() {
		registerItemGroupOrder();
	}

	private static void registerItemGroupOrder() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL_BLOCKS).register(entries -> {
			entries.addAfter(Items.AMETHYST_CLUSTER, REDSTONE_FORMATION);
			entries.addAfter(REDSTONE_FORMATION, LAPIS_FORMATION);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL_BLOCKS).register(entries -> {
			entries.addAfter(Items.SUSPICIOUS_GRAVEL, SUSPICIOUS_NETHERRACK);
			entries.addAfter(SUSPICIOUS_NETHERRACK, SUSPICIOUS_SOUL_SAND);
			entries.addAfter(SUSPICIOUS_SOUL_SAND, SUSPICIOUS_SOUL_SOIL);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE_BLOCKS).register(entries -> {
			entries.addAfter(Items.REDSTONE, REDSTONE);
			entries.addAfter(Items.REDSTONE_BLOCK, REDSTONE_FORMATION);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addAfter(Items.RAW_GOLD, RAW_DIAMOND, RAW_EMERALD);
			entries.addAfter(Items.LAPIS_LAZULI, REDSTONE);
			entries.addAfter(Items.DIAMOND, ANCIENT_DEBRIS);
			entries.addAfter(Items.GOLD_NUGGET, Items.NETHERITE_SCRAP);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addAfter(Items.SNORT_POTTERY_SHERD, Items.ANCIENT_DEBRIS);
		});
	}
}
