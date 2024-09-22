/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.block.CortexBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.util.Identifier;

public class CortexModelProvider extends FabricModelProvider {
	public CortexModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {
		generator.blockStateCollector.accept(
			BlockStateModelGenerator.createSingletonBlockState(CortexBlocks.REDSTONE_CRYSTALS, Identifier.of("cortex", "block/redstone_crystals"))
		);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		// There is nothing, but block item model is generated anyway
	}
}
