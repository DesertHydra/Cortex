package deserthydra.cortex.block;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SuspiciousSoulSandBlock extends SolidBrushableBlock {
	protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

	public SuspiciousSoulSandBlock(Block block, SoundEvent brushingSound, SoundEvent brushingCompleteSound, Settings settings) {
		super(block, brushingSound, brushingCompleteSound, settings);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return COLLISION_SHAPE;
	}

	@Override
	protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.fullCube();
	}

	@Override
	protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.fullCube();
	}

	// Maybe a brittle soul sand with netherite inside shouldn't bubble

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType navigationType) {
		return false;
	}

	@Override
	protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 0.2F;
	}
}
