/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.loot.CortexLootTables;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.piece.RuinedPortalStructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RuinedPortalStructurePiece.class)
public abstract class RuinedPortalStructurePieceMixin {
	@Shadow
	@Final
	private RuinedPortalStructurePiece.Properties properties;

	@Unique
	private boolean underPortal;

	@Inject(
		method = "generate",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/structure/piece/RuinedPortalStructurePiece;updateNetherracksInBound(Lnet/minecraft/util/random/RandomGenerator;Lnet/minecraft/world/WorldAccess;)V"
		)
	)
	private void setUnderPortalMarker(StructureWorldAccess world, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomGenerator random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos, CallbackInfo ci) {
		this.underPortal = true;
	}

	@WrapOperation(
		method = "placeNetherrackBottom",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
		)
	)
	private boolean modifyNetherrackBottom(WorldAccess instance, BlockPos originalPos, BlockState originalState, int flags, Operation<Boolean> original, RandomGenerator random, WorldAccess world, BlockPos pos) {
		if (this.underPortal && this.properties.replaceWithBlackstone && random.nextFloat() < 0.05F) {
			boolean noEmptyNeighbors = BlockPos.streamOutwards(pos, 0, 2, 0).allMatch(outPos -> world.getBlockState(outPos).isOpaque());
			boolean noNearbyNeighbors = BlockPos.streamOutwards(pos, 1, 1, 1).noneMatch(outPos -> world.getBlockState(outPos).isOf(CortexBlocks.SUSPICIOUS_NETHERRACK));
			if (noEmptyNeighbors && noNearbyNeighbors) {
				instance.setBlockState(pos, CortexBlocks.SUSPICIOUS_NETHERRACK.getDefaultState(), flags);
				instance.getBlockEntity(pos, BlockEntityType.BRUSHABLE_BLOCK).ifPresent(block -> block.setLootTable(CortexLootTables.NETHER_RUINED_PORTAL_ARCHAEOLOGY, pos.asLong()));
				return true;
			}
		}

		return instance.setBlockState(originalPos, originalState, flags);
	}
}
