/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.item;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.util.RegistryUtils;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class CortexItems {
	public static final Item REDSTONE_FORMATION = RegistryUtils.registerBlock(CortexBlocks.REDSTONE_FORMATION);

	public static final Item LAPIS_FORMATION = RegistryUtils.registerBlock(CortexBlocks.LAPIS_FORMATION);

	public static final Item SUSPICIOUS_NETHERRACK = RegistryUtils.registerBlock(CortexBlocks.SUSPICIOUS_NETHERRACK);

	public static final Item SUSPICIOUS_SOUL_SAND = RegistryUtils.registerBlock(CortexBlocks.SUSPICIOUS_SOUL_SAND);

	public static final Item SUSPICIOUS_SOUL_SOIL = RegistryUtils.registerBlock(CortexBlocks.SUSPICIOUS_SOUL_SOIL);

	public static final Item RAW_DIAMOND = RegistryUtils.register("raw_diamond");

	public static final Item RAW_EMERALD = RegistryUtils.register("raw_emerald");

	public static final Item REDSTONE = RegistryUtils.register("redstone");

	public static final Item ANCIENT_DEBRIS = RegistryUtils.register("ancient_debris", new Item.Properties().fireResistant());

	public static final Item MOLTEN_DEBRIS = RegistryUtils.register("molten_debris", new Item.Properties().fireResistant());

	public static void init() {
		registerItemGroupOrder();
	}

	private static void registerItemGroupOrder() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
			entries.insertAfter(Items.AMETHYST_CLUSTER, REDSTONE_FORMATION);
			entries.insertAfter(REDSTONE_FORMATION, LAPIS_FORMATION);
		});

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
			entries.insertAfter(Items.SUSPICIOUS_GRAVEL, SUSPICIOUS_NETHERRACK);
			entries.insertAfter(SUSPICIOUS_NETHERRACK, SUSPICIOUS_SOUL_SAND);
			entries.insertAfter(SUSPICIOUS_SOUL_SAND, SUSPICIOUS_SOUL_SOIL);
		});

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
			entries.insertAfter(Items.REDSTONE, REDSTONE);
			entries.insertAfter(Items.REDSTONE_BLOCK, REDSTONE_FORMATION);
		});

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
			entries.insertAfter(Items.RAW_GOLD, RAW_DIAMOND, RAW_EMERALD);
			entries.insertAfter(Items.LAPIS_LAZULI, REDSTONE);
			entries.insertAfter(Items.DIAMOND, ANCIENT_DEBRIS);
			entries.insertAfter(Items.GOLD_NUGGET, Items.NETHERITE_SCRAP);
		});

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
			entries.insertAfter(Items.SNORT_POTTERY_SHERD, Items.ANCIENT_DEBRIS);
		});
	}
}
