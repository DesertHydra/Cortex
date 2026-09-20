package deserthydra.cortex.datagen.common;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.block.LapisFormationBlock;
import deserthydra.cortex.block.RedstoneFormationBlock;
import deserthydra.cortex.worldgen.CortexConfiguredFeatures;
import deserthydra.cortex.worldgen.CortexPlacedFeatures;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.function.Predicate;

public class CortexFeatureProvider {

	public static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		context.register(CortexConfiguredFeatures.REDSTONE_FORMATIONS, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(facingStateProvider(CortexBlocks.REDSTONE_FORMATION.defaultBlockState(), Direction.Plane.HORIZONTAL, RedstoneFormationBlock.FACING))));
		context.register(CortexConfiguredFeatures.LAPIS_FORMATIONS, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(facingStateProvider(CortexBlocks.LAPIS_FORMATION.defaultBlockState(), Direction.Plane.HORIZONTAL, LapisFormationBlock.FACING))));
	}

	public static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
		var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		context.register(CortexPlacedFeatures.REDSTONE_FORMATIONS, new PlacedFeature(
			configuredFeatures.getOrThrow(CortexConfiguredFeatures.REDSTONE_FORMATIONS),
			List.of(
				CountPlacement.of(4096),
				RandomOffsetPlacement.ofTriangle(7, 3),
				HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-31), VerticalAnchor.absolute(16)),
				BiomeFilter.biome(),
				BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE,
					BlockPredicate.matchesTag(Vec3i.ZERO.below(), ConventionalBlockTags.REDSTONE_ORES)
				))
			)
		));

		context.register(CortexPlacedFeatures.LAPIS_FORMATIONS, new PlacedFeature(
			configuredFeatures.getOrThrow(CortexConfiguredFeatures.LAPIS_FORMATIONS),
			List.of(
				CountPlacement.of(4096),
				RandomOffsetPlacement.ofTriangle(7, 3),
				HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-31), VerticalAnchor.absolute(33)),
				BiomeFilter.biome(),
				BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE,
					BlockPredicate.matchesTag(Vec3i.ZERO.below(), ConventionalBlockTags.LAPIS_ORES)
				))
			)
		));

		// these replace vanilla ore gen
		context.register(CortexPlacedFeatures.ORE_COPPER, new PlacedFeature(configuredFeatures.getOrThrow(OreFeatures.ORE_COPPPER_SMALL), List.of(
			CountPlacement.of(5),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)),
			BiomeFilter.biome()
		)));

		context.register(CortexPlacedFeatures.ORE_COPPER_LARGE, new PlacedFeature(configuredFeatures.getOrThrow(OreFeatures.ORE_COPPER_LARGE), List.of(
			CountPlacement.of(5),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)),
			BiomeFilter.biome()
		)));

		context.register(CortexPlacedFeatures.ORE_REDSTONE, new PlacedFeature(configuredFeatures.getOrThrow(OreFeatures.ORE_REDSTONE), List.of(
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(15)),
			BiomeFilter.biome()
		)));

		context.register(CortexPlacedFeatures.ORE_REDSTONE_LOWER, new PlacedFeature(configuredFeatures.getOrThrow(OreFeatures.ORE_REDSTONE), List.of(
			CountPlacement.of(5),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32)),
			BiomeFilter.biome()
		)));
	}

	private static WeightedStateProvider facingStateProvider(BlockState baseState, Predicate<Direction> validDirections, Property<Direction> directionProperty) {
		return new WeightedStateProvider(Util.make(WeightedList.<BlockState>builder(), list -> {
			for (Direction direction : Direction.values()) {
				if(validDirections.test(direction)) {
					list.add(baseState.setValue(directionProperty, direction), 1);
				}
			}
		}).build());
	}
}
