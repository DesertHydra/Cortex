/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.block;

import deserthydra.cortex.util.RegistryUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class CortexBlocks {
	public static final Block REDSTONE_FORMATION = RegistryUtils.register(
		"redstone_formation",
		RedstoneFormationBlock::new,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_RED)
			.requiresCorrectToolForDrops()
			.strength(5.0F, 6.0F)
			.lightLevel(_ -> 12)
			.isRedstoneConductor(Blocks::never)
			.dynamicShape()
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.sound(SoundType.METAL)
			.forceSolidOn()
	);

	public static final Block LAPIS_FORMATION = RegistryUtils.register(
		"lapis_formation",
		LapisFormationBlock::new,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_BLUE)
			.requiresCorrectToolForDrops()
			.strength(5.0F, 6.0F)
			.lightLevel(_ -> 15)
			.dynamicShape()
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.sound(SoundType.METAL)
			.forceSolidOn()
	);

	public static final Block SUSPICIOUS_NETHERRACK = RegistryUtils.register(
		"suspicious_netherrack",
		properties -> new SolidBrushableBlock(
			Blocks.NETHERRACK,
			SoundEvents.BRUSH_GRAVEL,
			SoundEvents.BRUSH_GRAVEL_COMPLETED,
			properties
		),
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.NETHER)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(0.4F)
			.sound(SoundType.NETHERRACK)
	);

	public static final Block SUSPICIOUS_SOUL_SAND = RegistryUtils.register(
		"suspicious_soul_sand",
		properties -> new SuspiciousSoulSandBlock(
			Blocks.SOUL_SAND,
			SoundEvents.BRUSH_SAND,
			SoundEvents.BRUSH_SAND_COMPLETED,
			properties
		),
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_BROWN)
			.instrument(NoteBlockInstrument.COW_BELL)
			.strength(0.5F)
			.sound(SoundType.SOUL_SAND)
			.speedFactor(0.4F)
			.isValidSpawn(Blocks::always)
			.isRedstoneConductor(Blocks::always)
			.isViewBlocking(Blocks::always)
			.isSuffocating(Blocks::always)
			.postProcess(Blocks::postProcessAbove)
	);

	public static final Block SUSPICIOUS_SOUL_SOIL = RegistryUtils.register(
		"suspicious_soul_soil",
		settings -> new SolidBrushableBlock(
			Blocks.SOUL_SOIL,
			SoundEvents.BRUSH_SAND,
			SoundEvents.BRUSH_SAND_COMPLETED,
			settings
		),
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_BLACK)
			.instrument(NoteBlockInstrument.BASEDRUM)
			.strength(0.5F)
			.sound(SoundType.SOUL_SOIL)
	);

	public static void init() {
		BlockEntityType.BRUSHABLE_BLOCK.addValidBlock(SUSPICIOUS_NETHERRACK);
		BlockEntityType.BRUSHABLE_BLOCK.addValidBlock(SUSPICIOUS_SOUL_SAND);
		BlockEntityType.BRUSHABLE_BLOCK.addValidBlock(SUSPICIOUS_SOUL_SOIL);
	}
}
