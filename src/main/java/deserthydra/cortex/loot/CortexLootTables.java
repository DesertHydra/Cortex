package deserthydra.cortex.loot;

import deserthydra.cortex.CortexUtils;
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
