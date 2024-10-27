/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.loot;

import deserthydra.cortex.util.CortexUtils;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class CortexLootTables {
	public static final RegistryKey<LootTable> NETHER_RUINED_PORTAL_ARCHAEOLOGY = RegistryKey.of(
		RegistryKeys.LOOT_TABLE,
		CortexUtils.id("archaeology/nether_ruined_portal")
	);

	public static final RegistryKey<LootTable> NETHER_FOSSIL_ARCHAEOLOGY = RegistryKey.of(
		RegistryKeys.LOOT_TABLE,
		CortexUtils.id("archaeology/nether_fossil")
	);
}
