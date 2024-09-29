/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.block.CortexBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class CortexBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public CortexBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider wrapperLookup) {
		getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
			.add(CortexBlocks.REDSTONE_FORMATION)
			.add(CortexBlocks.LAPIS_FORMATION);

		getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
			.add(CortexBlocks.SUSPICIOUS_SOUL_SAND)
			.add(CortexBlocks.SUSPICIOUS_SOUL_SOIL);

		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
			.add(CortexBlocks.REDSTONE_FORMATION)
			.add(CortexBlocks.LAPIS_FORMATION)
			.add(CortexBlocks.SUSPICIOUS_NETHERRACK);

		getOrCreateTagBuilder(BlockTags.INFINIBURN_NETHER)
			.add(CortexBlocks.SUSPICIOUS_NETHERRACK);

		getOrCreateTagBuilder(BlockTags.SOUL_SPEED_BLOCKS)
			.add(CortexBlocks.SUSPICIOUS_SOUL_SAND)
			.add(CortexBlocks.SUSPICIOUS_SOUL_SOIL);
	}
}
