/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.block;

import deserthydra.cortex.CortexUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class CortexBlocks {
	public static final Block REDSTONE_FORMATION = Registry.register(
		Registries.BLOCK,
		CortexUtils.id("redstone_formation"),
		new RedstoneFormationBlock(
			AbstractBlock.Settings.create()
				.mapColor(MapColor.FIRE)
				.toolRequired()
				.strength(5.0F, 6.0F)
				.luminance(state -> 15)
				.dynamicBounds()
				.offsetType(AbstractBlock.OffsetType.XZ)
				.sounds(BlockSoundGroup.METAL)
				.solidBlock(Blocks::nonSolid)
		)
	);

	public static final Block LAPIS_FORMATION = Registry.register(
		Registries.BLOCK,
		CortexUtils.id("lapis_formation"),
		new LapisFormationBlock(
			AbstractBlock.Settings.create()
				.mapColor(MapColor.FIRE)
				.toolRequired()
				.strength(5.0F, 6.0F)
				.luminance(state -> 15)
				.dynamicBounds()
				.offsetType(AbstractBlock.OffsetType.XZ)
				.sounds(BlockSoundGroup.METAL)
				.solidBlock(Blocks::nonSolid)
		)
	);

	public static final Block SUSPICIOUS_NETHERRACK = Registry.register(
		Registries.BLOCK,
		CortexUtils.id("suspicious_netherrack"),
		new SolidBrushableBlock(
			Blocks.NETHERRACK,
			SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL,
			SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE,
			AbstractBlock.Settings.create()
				.mapColor(MapColor.NETHER)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.toolRequired()
				.strength(0.4F)
				.sounds(BlockSoundGroup.NETHERRACK)
		)
	);

	public static void init() {
		BlockEntityType.BRUSHABLE_BLOCK.addSupportedBlock(SUSPICIOUS_NETHERRACK);
	}
}
