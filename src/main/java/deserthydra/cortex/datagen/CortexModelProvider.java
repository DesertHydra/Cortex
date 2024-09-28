/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.item.CortexItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

import java.util.List;

public class CortexModelProvider extends FabricModelProvider {
	public CortexModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {
		generator.blockStateCollector.accept(
			VariantsBlockStateSupplier.create(
				CortexBlocks.REDSTONE_FORMATION
			)
			.coordinate(
				BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
					.register(Direction.EAST, List.of(
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R90)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_0")),
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R90)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_1")),
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R90)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_2"))
					))
					.register(Direction.SOUTH, List.of(
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R180)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_0")),
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R180)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_1")),
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R180)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_2"))
					))
					.register(Direction.WEST, List.of(
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R270)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_0")),
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R270)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_1")),
						BlockStateVariant.create()
							.put(VariantSettings.Y, VariantSettings.Rotation.R270)
							.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_2"))
					))
					.register(Direction.NORTH, List.of(
						BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_0")),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_1")),
						BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.REDSTONE_FORMATION, "_2"))
					))
			)
		);

		generator.blockStateCollector.accept(
			VariantsBlockStateSupplier.create(
					CortexBlocks.LAPIS_FORMATION
				)
				.coordinate(
					BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
						.register(Direction.EAST, List.of(
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R90)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_0")),
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R90)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_1")),
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R90)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_2"))
						))
						.register(Direction.SOUTH, List.of(
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R180)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_0")),
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R180)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_1")),
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R180)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_2"))
						))
						.register(Direction.WEST, List.of(
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R270)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_0")),
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R270)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_1")),
							BlockStateVariant.create()
								.put(VariantSettings.Y, VariantSettings.Rotation.R270)
								.put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_2"))
						))
						.register(Direction.NORTH, List.of(
							BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_0")),
							BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_1")),
							BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CortexBlocks.LAPIS_FORMATION, "_2"))
						))
				)
		);
	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		generator.register(CortexItems.REDSTONE_FORMATION, Models.SINGLE_LAYER_ITEM);
		generator.register(CortexItems.LAPIS_FORMATION, Models.SINGLE_LAYER_ITEM);
		generator.register(CortexItems.RAW_DIAMOND, Models.SINGLE_LAYER_ITEM);
		generator.register(CortexItems.RAW_EMERALD, Models.SINGLE_LAYER_ITEM);
		generator.register(CortexItems.REDSTONE, Models.SINGLE_LAYER_ITEM);
	}
}
