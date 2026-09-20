/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.mixin;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.loot.CortexLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(NetherFossilPieces.NetherFossilPiece.class)
public abstract class NetherFossilPieceMixin extends TemplateStructurePiece {
	@Unique
	private static final Map<Block, BlockState> BLOCKS_TO_SUSPICIOUS_BLOCKS = Map.of(
		Blocks.SOUL_SAND, CortexBlocks.SUSPICIOUS_SOUL_SAND.defaultBlockState(),
		Blocks.SOUL_SOIL, CortexBlocks.SUSPICIOUS_SOUL_SOIL.defaultBlockState(),
		Blocks.NETHERRACK, CortexBlocks.SUSPICIOUS_NETHERRACK.defaultBlockState()
	);

	private NetherFossilPieceMixin(StructurePieceType type, int genDepth, StructureTemplateManager structureTemplateManager, Identifier templateLocation, String templateName, StructurePlaceSettings placeSettings, BlockPos position) {
		super(type, genDepth, structureTemplateManager, templateLocation, templateName, placeSettings, position);
		throw new UnsupportedOperationException();
	}

	@Inject(method = "postProcess", at = @At("RETURN"))
	private void generateSuspiciousBlocks(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBB, ChunkPos chunkPos, BlockPos referencePos, CallbackInfo ci) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int x = this.boundingBox.minX() + 1; x < this.boundingBox.maxX(); x++) {
			for (int z = this.boundingBox.minZ() + 1; z < this.boundingBox.maxZ(); z++) {
				mutable.set(x, this.boundingBox.minY() - 1, z);
				if (BLOCKS_TO_SUSPICIOUS_BLOCKS.containsKey(level.getBlockState(mutable).getBlock())) {
					mutable.move(Direction.DOWN);

					for (int i = 3; i < 6; i++) {
						mutable.move(Direction.DOWN);
						if (random.nextFloat() < 0.03F) {
							var downBlockState = level.getBlockState(mutable);
							var replacementState = BLOCKS_TO_SUSPICIOUS_BLOCKS.get(downBlockState.getBlock());
							if (replacementState != null) {
								boolean noNearbyNeighbors = BlockPos.withinManhattanStream(mutable, 2, 2, 2).noneMatch(outPos -> BLOCKS_TO_SUSPICIOUS_BLOCKS.containsValue(level.getBlockState(outPos)));
								if (noNearbyNeighbors) {
									level.setBlock(mutable, replacementState, Block.UPDATE_CLIENTS);
									level.getBlockEntity(mutable, BlockEntityTypes.BRUSHABLE_BLOCK).ifPresent(block -> block.setLootTable(CortexLootTables.NETHER_FOSSIL_ARCHAEOLOGY, mutable.asLong()));
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}
