/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrushableBlock;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.TickSchedulerAccess;

public class SolidBrushableBlock extends BrushableBlock {
	public SolidBrushableBlock(Block block, SoundEvent brushingSound, SoundEvent brushingCompleteSound, Settings settings) {
		super(block, brushingSound, brushingCompleteSound, settings);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, TickSchedulerAccess tickSchedulerAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomGenerator random) {
		tickSchedulerAccess.scheduleBlockTick(pos, this, 2);
		return super.getStateForNeighborUpdate(state, world, tickSchedulerAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
		if (world.getBlockEntity(pos) instanceof BrushableBlockEntity brushableBlockEntity) {
			brushableBlockEntity.tickBrushCountReset(world);
		}
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random) {}
}
