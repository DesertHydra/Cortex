package deserthydra.cortex.worldgen;

import deserthydra.cortex.CortexUtils;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CortexConfiguredFeatures {
	public static final RegistryKey<ConfiguredFeature<?, ?>> REDSTONE_FORMATIONS = RegistryKey.of(
		RegistryKeys.CONFIGURED_FEATURE,
		CortexUtils.id("redstone_formations")
	);
}
