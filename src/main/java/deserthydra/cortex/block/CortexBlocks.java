/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.block;

import deserthydra.cortex.CortexUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class CortexBlocks {
	public static final Block REDSTONE_FORMATION = Registry.register(
		Registries.BLOCK,
		CortexUtils.id("redstone_formation"),
		new RedstoneFormationBlock(
			AbstractBlock.Settings.create()
				.mapColor(MapColor.FIRE)
				.toolRequired()
				.strength(5.0F, 6.0F)
				.luminance(state -> 7)
				.dynamicBounds()
				.offsetType(AbstractBlock.OffsetType.XZ)
				.sounds(BlockSoundGroup.METAL)
				.solidBlock(Blocks::nonSolid)
		)
	);

	public static final Block LAPIS_LAZULI_FORMATION = Registry.register(
		Registries.BLOCK,
		CortexUtils.id("lapis_formation"),
		new LapisFormationBlock(
			AbstractBlock.Settings.create()
				.mapColor(MapColor.FIRE)
				.toolRequired()
				.strength(5.0F, 6.0F)
				.luminance(state -> 10)
				.dynamicBounds()
				.offsetType(AbstractBlock.OffsetType.XZ)
				.sounds(BlockSoundGroup.METAL)
				.solidBlock(Blocks::nonSolid)
		)
	);

	public static void init() {}
}
