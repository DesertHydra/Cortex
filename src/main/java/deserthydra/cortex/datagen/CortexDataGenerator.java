/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.worldgen.CortexConfiguredFeatures;
import deserthydra.cortex.worldgen.CortexPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.registry.BootstrapContext;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistrySetBuilder;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class CortexDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		var pack = generator.createPack();
		pack.addProvider(CortexBlockLootTableProvider::new);
		pack.addProvider(CortexBlockTagProvider::new);
		pack.addProvider(CortexLanguageProvider::new);
		pack.addProvider(CortexModelProvider::new);
		pack.addProvider(CortexWorldGenProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder builder) {
		builder.add(RegistryKeys.CONFIGURED_FEATURE, this::bootstrapConfiguredFeatures);
		builder.add(RegistryKeys.PLACED_FEATURE, this::bootstrapPlacedFeatures);
		builder.build(DynamicRegistryManager.EMPTY);
	}

	private void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		DataPool.Builder<BlockState> redstoneDataPool = DataPool.builder();
		for (var direction : Direction.Type.HORIZONTAL) {
			redstoneDataPool.addWeighted(CortexBlocks.REDSTONE_FORMATION.getDefaultState().with(Properties.HORIZONTAL_FACING, direction), 1);
		}

		ConfiguredFeatureUtil.register(
			context,
			CortexConfiguredFeatures.REDSTONE_FORMATIONS,
			Feature.RANDOM_PATCH,
			new RandomPatchFeatureConfig(
				128,
				7,
				3,
				PlacedFeatureUtil.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(
						new WeightedBlockStateProvider(redstoneDataPool)
					),
					BlockPredicate.allOf(
						BlockPredicate.IS_AIR,
						BlockPredicate.matchingBlockTags(Vec3i.ZERO.down(), BlockTags.REDSTONE_ORES)
					)
				)
			)
		);

		DataPool.Builder<BlockState> lapisLazuliDataPool = DataPool.builder();
		for (var direction : Direction.Type.HORIZONTAL) {
			lapisLazuliDataPool.addWeighted(CortexBlocks.LAPIS_FORMATION.getDefaultState().with(Properties.HORIZONTAL_FACING, direction), 1);
		}

		ConfiguredFeatureUtil.register(
			context,
			CortexConfiguredFeatures.LAPIS_FORMATIONS,
			Feature.RANDOM_PATCH,
			new RandomPatchFeatureConfig(
				196,
				7,
				3,
				PlacedFeatureUtil.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(
						new WeightedBlockStateProvider(lapisLazuliDataPool)
					),
					BlockPredicate.allOf(
						BlockPredicate.IS_AIR,
						BlockPredicate.matchingBlockTags(Vec3i.ZERO.down(), BlockTags.LAPIS_ORES)
					)
				)
			)
		);
	}

	private void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
		var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

		PlacedFeatureUtil.register(
			context,
			CortexPlacedFeatures.REDSTONE_FORMATIONS,
			configuredFeatures.getHolderOrThrow(CortexConfiguredFeatures.REDSTONE_FORMATIONS),
			CountPlacementModifier.create(128),
			HeightRangePlacementModifier.create(UniformHeightProvider.create(YOffset.aboveBottom(-31), YOffset.fixed(16))),
			BiomePlacementModifier.getInstance()
		);

		PlacedFeatureUtil.register(
			context,
			CortexPlacedFeatures.LAPIS_FORMATIONS,
			configuredFeatures.getHolderOrThrow(CortexConfiguredFeatures.LAPIS_FORMATIONS),
			CountPlacementModifier.create(128),
			HeightRangePlacementModifier.create(UniformHeightProvider.create(YOffset.fixed(-31), YOffset.fixed(33))),
			BiomePlacementModifier.getInstance()
		);
	}
}
