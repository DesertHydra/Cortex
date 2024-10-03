/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class RedstoneFormationBlock extends HorizontalFacingBlock implements Waterloggable {
	public static final MapCodec<LapisFormationBlock> CODEC = createCodec(LapisFormationBlock::new);
	protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

	public RedstoneFormationBlock(Settings settings) {
		super(settings);
		this.setDefaultState(
			this.stateManager.getDefaultState()
				.with(Properties.WATERLOGGED, false)
		);
	}

	@Override
	protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		var offset = state.getModelOffset(world, pos);
		return SHAPE.offset(offset.x, offset.y, offset.z);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		var offset = state.getModelOffset(world, pos);
		return SHAPE.offset(offset.x, offset.y, offset.z);
	}

	@Override
	protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
		return !state.get(Properties.WATERLOGGED);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, Properties.WATERLOGGED);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		var fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		return this.getDefaultState()
			.with(FACING, ctx.getPlayerFacing().getOpposite())
			.with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
	}

	@Override
	protected float getMaxModelOffset() {
		// 0.125F is almost perfect, with neighboring blocks being able to touch each other
		// However, it causes a bit of Z-fighting, which is bad!
		//return 0.125F;
		return 0.0625F;
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(Properties.WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	protected boolean isRedstonePowerSource(BlockState state) {
		return true;
	}

	@Override
	protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return 15;
	}
}
