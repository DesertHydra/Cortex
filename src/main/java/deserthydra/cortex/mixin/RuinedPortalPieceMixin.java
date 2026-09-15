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
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RuinedPortalPiece.class)
public abstract class RuinedPortalPieceMixin {

	private RuinedPortalPieceMixin() {
		throw new UnsupportedOperationException();
	}

	@Shadow
	@Final
	private RuinedPortalPiece.Properties properties;

	@Unique
	private static final ThreadLocal<Boolean> UNDER_PORTAL = ThreadLocal.withInitial(() -> Boolean.FALSE);

	@Inject(
		method = "postProcess",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalPiece;addNetherrackDripColumnsBelowPortal(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/LevelAccessor;)V"
		)
	)
	private void setUnderPortalMarker(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBB, ChunkPos chunkPos, BlockPos referencePos, CallbackInfo ci) {
		UNDER_PORTAL.set(Boolean.TRUE);
	}

	@Inject(method = "postProcess", at = @At("RETURN"))
	private void resetUnderPortalMarker(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBB, ChunkPos chunkPos, BlockPos referencePos, CallbackInfo ci) {
		UNDER_PORTAL.set(Boolean.FALSE);
	}

	@WrapOperation(
		method = "placeNetherrackOrMagma",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	private boolean modifyNetherrackBottom(LevelAccessor instance, BlockPos pos, BlockState blockState, int flags, Operation<Boolean> original) {
		if (UNDER_PORTAL.get() && this.properties.replaceWithBlackstone && instance.getRandom().nextFloat() < 0.05F) {
			boolean noEmptyNeighbors = BlockPos.withinManhattanStream(pos, 0, 2, 0).allMatch(outPos -> instance.getBlockState(outPos).canOcclude());
			boolean noNearbyNeighbors = BlockPos.withinManhattanStream(pos, 1, 1, 1).noneMatch(outPos -> instance.getBlockState(outPos).is(CortexBlocks.SUSPICIOUS_NETHERRACK));
			if (noEmptyNeighbors && noNearbyNeighbors) {
				var value = original.call(instance, pos, CortexBlocks.SUSPICIOUS_NETHERRACK.defaultBlockState(), flags);
				instance.getBlockEntity(pos, BlockEntityType.BRUSHABLE_BLOCK).ifPresent(block -> block.setLootTable(CortexLootTables.NETHER_RUINED_PORTAL_ARCHAEOLOGY, pos.asLong()));
				return value;
			}
		}

		return original.call(instance, pos, blockState, flags);
	}
}
