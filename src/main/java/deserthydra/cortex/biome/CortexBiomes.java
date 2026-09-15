package deserthydra.cortex.biome;

import deserthydra.cortex.util.CortexUtils;
import deserthydra.cortex.worldgen.CortexPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.level.levelgen.GenerationStep;

public class CortexBiomes {

	public static void registerBiomeModifications() {
		BiomeModifications.addFeature(
			context -> context.hasFeature(OreFeatures.ORE_REDSTONE),
			GenerationStep.Decoration.UNDERGROUND_DECORATION,
			CortexPlacedFeatures.REDSTONE_FORMATIONS
		);

		BiomeModifications.addFeature(
			context -> context.hasFeature(OreFeatures.ORE_LAPIS),
			GenerationStep.Decoration.UNDERGROUND_DECORATION,
			CortexPlacedFeatures.LAPIS_FORMATIONS
		);

		BiomeModifications.create(CortexUtils.id("remove_large_ancient_debris")).add(
			ModificationPhase.REMOVALS,
			selectionContext -> selectionContext.hasPlacedFeature(OrePlacements.ORE_ANCIENT_DEBRIS_LARGE),
			modificationContext -> modificationContext.getGenerationSettings().removeFeature(OrePlacements.ORE_ANCIENT_DEBRIS_LARGE)
		);

		BiomeModifications.create(CortexUtils.id("remove_small_ancient_debris")).add(
			ModificationPhase.REMOVALS,
			selectionContext -> selectionContext.hasPlacedFeature(OrePlacements.ORE_ANCIENT_DEBRIS_SMALL),
			modificationContext -> modificationContext.getGenerationSettings().removeFeature(OrePlacements.ORE_ANCIENT_DEBRIS_SMALL)
		);

		BiomeModifications.create(CortexUtils.id("replace_redstone"))
			.add(
				ModificationPhase.REPLACEMENTS,
				selectionContext -> selectionContext.hasPlacedFeature(OrePlacements.ORE_REDSTONE),
				modificationContext -> {
					modificationContext.getGenerationSettings().removeFeature(OrePlacements.ORE_REDSTONE);
					modificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CortexPlacedFeatures.ORE_REDSTONE);
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				selectionContext -> selectionContext.hasPlacedFeature(OrePlacements.ORE_REDSTONE_LOWER),
				modificationContext -> {
					modificationContext.getGenerationSettings().removeFeature(OrePlacements.ORE_REDSTONE_LOWER);
					modificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CortexPlacedFeatures.ORE_REDSTONE_LOWER);
				});

		BiomeModifications.create(CortexUtils.id("replace_copper"))
			.add(
				ModificationPhase.REPLACEMENTS,
				selectionContext -> selectionContext.hasPlacedFeature(OrePlacements.ORE_COPPER),
				modificationContext -> {
					modificationContext.getGenerationSettings().removeFeature(OrePlacements.ORE_COPPER);
					modificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CortexPlacedFeatures.ORE_COPPER);
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				selectionContext -> selectionContext.hasPlacedFeature(OrePlacements.ORE_COPPER_LARGE),
				modificationContext -> {
					modificationContext.getGenerationSettings().removeFeature(OrePlacements.ORE_COPPER_LARGE);
					modificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CortexPlacedFeatures.ORE_COPPER_LARGE);
				});
	}
}
