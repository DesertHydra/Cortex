/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.block;


import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SuspiciousSoulSandBlock extends SolidBrushableBlock {

	public SuspiciousSoulSandBlock(Block block, SoundEvent brushingSound, SoundEvent brushingCompleteSound, BlockBehaviour.Properties properties) {
		super(block, brushingSound, brushingCompleteSound, properties);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Blocks.SOUL_SAND.defaultBlockState().getCollisionShape(level, pos, context);
	}

	@Override
	protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Blocks.SOUL_SAND.defaultBlockState().getBlockSupportShape(level, pos);
	}

	@Override
	protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Blocks.SOUL_SAND.defaultBlockState().getVisualShape(level, pos, context);
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return Blocks.SOUL_SAND.defaultBlockState().isPathfindable(type);
	}

	@Override
	protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
		return Blocks.SOUL_SAND.defaultBlockState().getShadeBrightness(level, pos);
	}

	// Maybe a brittle soul sand with netherite inside shouldn't bubble
}
