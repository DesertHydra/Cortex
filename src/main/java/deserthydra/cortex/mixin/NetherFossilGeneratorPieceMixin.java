package deserthydra.cortex.mixin;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.loot.CortexLootTables;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.NetherFossilGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.piece.SimpleStructurePiece;
import net.minecraft.structure.piece.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetherFossilGenerator.Piece.class)
public abstract class NetherFossilGeneratorPieceMixin extends SimpleStructurePiece {
	private NetherFossilGeneratorPieceMixin(StructurePieceType type, int generationDepth, StructureTemplateManager structureTemplateManager, Identifier structureId, String identifier, StructurePlacementData placementData, BlockPos pos) {
		super(type, generationDepth, structureTemplateManager, structureId, identifier, placementData, pos);
	}

	@Inject(method = "generate", at = @At("TAIL"))
	private void a(StructureWorldAccess world, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomGenerator random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos, CallbackInfo ci) {
		System.out.println(this.boundingBox);
		for (int x = this.boundingBox.getMinX() + 1; x < this.boundingBox.getMaxX(); x++) {
			for (int z = this.boundingBox.getMinZ() + 1; z < this.boundingBox.getMaxZ(); z++) {
				var newPos = new BlockPos(x, this.boundingBox.getMinY(), z);
				if (world.getBlockState(newPos.down()).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
					for (int i = 3; i < 6; i++) {
						var downPos = newPos.down(i);
						if (random.nextFloat() < 0.05F) {
							if (world.getBlockState(downPos).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
								boolean noNearbyNeighbors = BlockPos.streamOutwards(downPos, 2, 2, 2).noneMatch(outPos -> world.getBlockState(outPos).isOf(CortexBlocks.SUSPICIOUS_NETHERRACK));
								if (noNearbyNeighbors) {
									world.setBlockState(downPos, CortexBlocks.SUSPICIOUS_NETHERRACK.getDefaultState(), Block.NOTIFY_ALL);
									world.getBlockEntity(downPos, BlockEntityType.BRUSHABLE_BLOCK).ifPresent(block -> block.setLootTable(CortexLootTables.NETHER_FOSSIL_ARCHAEOLOGY, pos.asLong()));
									break;
								}
							} else if (world.getBlockState(downPos).isOf(Blocks.NETHERRACK)) {
								boolean noNearbyNeighbors = BlockPos.streamOutwards(downPos, 2, 2, 2).noneMatch(outPos -> world.getBlockState(outPos).isOf(CortexBlocks.SUSPICIOUS_NETHERRACK));
								if (noNearbyNeighbors) {
									world.setBlockState(downPos, CortexBlocks.SUSPICIOUS_NETHERRACK.getDefaultState(), Block.NOTIFY_ALL);
									world.getBlockEntity(downPos, BlockEntityType.BRUSHABLE_BLOCK).ifPresent(block -> block.setLootTable(CortexLootTables.NETHER_FOSSIL_ARCHAEOLOGY, pos.asLong()));
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
