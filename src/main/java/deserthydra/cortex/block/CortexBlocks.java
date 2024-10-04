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
				.mapColor(MapColor.RED)
				.toolRequired()
				.strength(5.0F, 6.0F)
				.luminance(state -> 12)
				.dynamicBounds()
				.offsetType(AbstractBlock.OffsetType.XZ)
				.sounds(BlockSoundGroup.METAL)
				.solid()
		)
	);

	public static final Block LAPIS_FORMATION = Registry.register(
		Registries.BLOCK,
		CortexUtils.id("lapis_formation"),
		new LapisFormationBlock(
			AbstractBlock.Settings.create()
				.mapColor(MapColor.BLUE)
				.toolRequired()
				.strength(5.0F, 6.0F)
				.luminance(state -> 15)
				.dynamicBounds()
				.offsetType(AbstractBlock.OffsetType.XZ)
				.sounds(BlockSoundGroup.METAL)
				.solid()
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

	public static final Block SUSPICIOUS_SOUL_SAND = Registry.register(
		Registries.BLOCK,
		CortexUtils.id("suspicious_soul_sand"),
		new SuspiciousSoulSandBlock(
			Blocks.SOUL_SAND,
			SoundEvents.ITEM_BRUSH_BRUSHING_SAND,
			SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE,
			AbstractBlock.Settings.create()
				.mapColor(MapColor.BROWN)
				.instrument(NoteBlockInstrument.COW_BELL)
				.strength(0.5F)
				.sounds(BlockSoundGroup.SOUL_SAND)
				.velocityMultiplier(0.4F)
				.solidBlock(Blocks::solid)
				.blockVision(Blocks::solid)
				.suffocates(Blocks::solid)
		)
	);

	public static final Block SUSPICIOUS_SOUL_SOIL = Registry.register(
		Registries.BLOCK,
		CortexUtils.id("suspicious_soul_soil"),
		new SolidBrushableBlock(
			Blocks.SOUL_SOIL,
			SoundEvents.ITEM_BRUSH_BRUSHING_SAND,
			SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE,
			AbstractBlock.Settings.create()
				.mapColor(MapColor.BLACK)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.strength(0.5F)
				.sounds(BlockSoundGroup.SOUL_SOIL)
		)
	);

	public static void init() {
		BlockEntityType.BRUSHABLE_BLOCK.addSupportedBlock(SUSPICIOUS_NETHERRACK);
		BlockEntityType.BRUSHABLE_BLOCK.addSupportedBlock(SUSPICIOUS_SOUL_SAND);
		BlockEntityType.BRUSHABLE_BLOCK.addSupportedBlock(SUSPICIOUS_SOUL_SOIL);
	}
}
