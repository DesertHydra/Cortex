/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen.common;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.data.CortexTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockIds;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class CortexBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	public CortexBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider wrapperLookup) {
		builder(BlockTags.NEEDS_IRON_TOOL)
			.add(blockId(CortexBlocks.REDSTONE_FORMATION))
			.add(blockId(CortexBlocks.LAPIS_FORMATION));

		builder(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(blockId(CortexBlocks.SUSPICIOUS_SOUL_SAND))
			.add(blockId(CortexBlocks.SUSPICIOUS_SOUL_SOIL));

		builder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(blockId(CortexBlocks.REDSTONE_FORMATION))
			.add(blockId(CortexBlocks.LAPIS_FORMATION))
			.add(blockId(CortexBlocks.SUSPICIOUS_NETHERRACK));

		builder(BlockTags.INFINIBURN_NETHER)
			.add(blockId(CortexBlocks.SUSPICIOUS_NETHERRACK));

		builder(BlockTags.SOUL_SPEED_BLOCKS)
			.add(blockId(CortexBlocks.SUSPICIOUS_SOUL_SAND))
			.add(blockId(CortexBlocks.SUSPICIOUS_SOUL_SOIL));

		builder(CortexTags.Blocks.GRINDSTONE_GRINDING_TARGETS)
			.add(blockId(Blocks.GRINDSTONE));
	}

	private static ResourceKey<Block> blockId(Block block) {
		return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
	}
}
