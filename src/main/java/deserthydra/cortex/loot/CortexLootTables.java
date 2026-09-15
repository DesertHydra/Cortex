/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.loot;

import com.mojang.logging.LogUtils;
import deserthydra.cortex.util.CortexUtils;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;

import java.util.Map;

public class CortexLootTables {
	private static final Logger LOGGER = LogUtils.getLogger();

	public static final ResourceKey<LootTable> NETHER_RUINED_PORTAL_ARCHAEOLOGY = ResourceKey.create(
		Registries.LOOT_TABLE,
		CortexUtils.id("archaeology/nether_ruined_portal")
	);

	public static final ResourceKey<LootTable> NETHER_FOSSIL_ARCHAEOLOGY = ResourceKey.create(
		Registries.LOOT_TABLE,
		CortexUtils.id("archaeology/nether_fossil")
	);

	public static final ResourceKey<LootTable> CHESTS_BASTION_BRIDGE = ResourceKey.create(
		Registries.LOOT_TABLE,
		CortexUtils.id("chests/bastion_bridge")
	);

	public static final ResourceKey<LootTable> CHESTS_BASTION_HOGLIN_STABLE = ResourceKey.create(
		Registries.LOOT_TABLE,
		CortexUtils.id("chests/bastion_hoglin_stable")
	);

	public static final ResourceKey<LootTable> CHESTS_BASTION_OTHER = ResourceKey.create(
		Registries.LOOT_TABLE,
		CortexUtils.id("chests/bastion_other")
	);

	public static final ResourceKey<LootTable> CHESTS_BASTION_TREASURE = ResourceKey.create(
		Registries.LOOT_TABLE,
		CortexUtils.id("chests/bastion_treasure")
	);

	public static final Map<ResourceKey<LootTable>, ResourceKey<LootTable>> VANILLA_REPLACEMENTS = Map.of(
		BuiltInLootTables.BASTION_BRIDGE, CortexLootTables.CHESTS_BASTION_BRIDGE,
		BuiltInLootTables.BASTION_HOGLIN_STABLE, CortexLootTables.CHESTS_BASTION_HOGLIN_STABLE,
		BuiltInLootTables.BASTION_OTHER, CortexLootTables.CHESTS_BASTION_OTHER,
		BuiltInLootTables.BASTION_TREASURE, CortexLootTables.CHESTS_BASTION_TREASURE
	);

	public static void registerReplacements() {
		LootTableEvents.REPLACE.register((key, _, source, registries) -> {
			var custom = CortexLootTables.VANILLA_REPLACEMENTS.get(key);
			if(custom != null) {
				if(source != LootTableSource.VANILLA) {
					LOGGER.warn("Loot table '{}' has already been replaced, not applying Cortex replacement!", key.identifier());
					return null;
				}

				var lootTables = registries.lookupOrThrow(Registries.LOOT_TABLE);

				return lootTables.get(custom)
					.filter(Holder.Reference::isBound)
					.map(Holder.Reference::value)
					.orElse(null);
			}

			return null;
		});
	}
}
