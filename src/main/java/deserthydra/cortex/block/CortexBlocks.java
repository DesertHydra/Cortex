/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class CortexBlocks {
	public static final Block REDSTONE_CRYSTALS = Registry.register(
		Registries.BLOCK,
		Identifier.of("cortex", "redstone_crystals"),
		new RedstoneCrystalsBlock(
			AbstractBlock.Settings.create()
				.mapColor(MapColor.FIRE)
				.toolRequired()
				.strength(5.0F, 6.0F)
				.sounds(BlockSoundGroup.METAL)
				.solidBlock(Blocks::nonSolid)
		)
	);

	public static void init() {}
}
