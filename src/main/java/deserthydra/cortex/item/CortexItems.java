/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.item;

import deserthydra.cortex.block.CortexBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CortexItems {
	public static final Item REDSTONE_CRYSTALS = Registry.register(
		Registries.ITEM,
		Identifier.of("cortex", "redstone_crystals"),
		new BlockItem(CortexBlocks.REDSTONE_CRYSTALS, new Item.Settings())
	);

	public static void init() {}
}
