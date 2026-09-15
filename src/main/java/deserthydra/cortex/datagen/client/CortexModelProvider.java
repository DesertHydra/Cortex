/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen.client;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.item.CortexItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.world.level.block.Block;

import java.util.stream.IntStream;

public class CortexModelProvider extends FabricModelProvider {

	public CortexModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generator) {

		crystalFormationBlock(generator, CortexBlocks.REDSTONE_FORMATION, 3);
		crystalFormationBlock(generator, CortexBlocks.LAPIS_FORMATION, 3);

		// Warning: Netherrack datagen *sucks*
		// Let's try the simple version of this
		generator.createTrivialCube(CortexBlocks.SUSPICIOUS_NETHERRACK);
		generator.createTrivialCube(CortexBlocks.SUSPICIOUS_SOUL_SAND);
		generator.createTrivialCube(CortexBlocks.SUSPICIOUS_SOUL_SOIL);
	}

	private static void crystalFormationBlock(BlockModelGenerators generators, Block block, int numModels) {
		var baseModels = BlockModelGenerators.variants(IntStream.range(0, numModels)
			.mapToObj(i -> ModelLocationUtils.getModelLocation(block, "_" + i))
			.map(BlockModelGenerators::plainModel)
			.toArray(Variant[]::new)
		);
		generators.blockStateOutput.accept(
			MultiVariantGenerator.dispatch(block, baseModels)
				.with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
		);
	}

	@Override
	public void generateItemModels(ItemModelGenerators generator) {
		generator.generateFlatItem(CortexItems.REDSTONE_FORMATION, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(CortexItems.LAPIS_FORMATION, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(CortexItems.RAW_DIAMOND, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(CortexItems.RAW_EMERALD, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(CortexItems.REDSTONE, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(CortexItems.ANCIENT_DEBRIS, ModelTemplates.FLAT_ITEM);
		generator.generateFlatItem(CortexItems.MOLTEN_DEBRIS, ModelTemplates.FLAT_ITEM);
	}
}
