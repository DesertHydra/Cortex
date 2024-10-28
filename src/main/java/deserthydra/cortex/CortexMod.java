/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.item.CortexItems;
import deserthydra.cortex.recipe.CortexRecipeBookCategories;
import deserthydra.cortex.recipe.CortexRecipeDisplayTypes;
import deserthydra.cortex.recipe.CortexRecipeSerializers;
import deserthydra.cortex.recipe.CortexRecipeTypes;
import deserthydra.cortex.util.CortexUtils;
import deserthydra.cortex.worldgen.CortexPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.dispenser.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OrePlacedFeatures;

public class CortexMod implements ModInitializer {
	@Override
	public void onInitialize() {
		CortexBlocks.init();
		CortexItems.init();
		CortexRecipeTypes.init();
		CortexRecipeSerializers.init();
		CortexRecipeDisplayTypes.init();
		CortexRecipeBookCategories.init();

		BiomeModifications.addFeature(
			context -> context.hasFeature(OreConfiguredFeatures.ORE_REDSTONE),
			GenerationStep.Feature.UNDERGROUND_DECORATION,
			CortexPlacedFeatures.REDSTONE_FORMATIONS
		);

		BiomeModifications.addFeature(
			context -> context.hasFeature(OreConfiguredFeatures.ORE_LAPIS),
			GenerationStep.Feature.UNDERGROUND_DECORATION,
			CortexPlacedFeatures.LAPIS_FORMATIONS
		);

		BiomeModifications.create(CortexUtils.id("remove_large_ancient_debris")).add(
			ModificationPhase.REMOVALS,
			selectionContext -> selectionContext.hasPlacedFeature(OrePlacedFeatures.ORE_ANCIENT_DEBRIS_LARGE),
			modificationContext -> modificationContext.getGenerationSettings().removeFeature(OrePlacedFeatures.ORE_ANCIENT_DEBRIS_LARGE)
		);

		BiomeModifications.create(CortexUtils.id("remove_small_ancient_debris")).add(
			ModificationPhase.REMOVALS,
			selectionContext -> selectionContext.hasPlacedFeature(OrePlacedFeatures.ORE_DEBRIS_SMALL),
			modificationContext -> modificationContext.getGenerationSettings().removeFeature(OrePlacedFeatures.ORE_DEBRIS_SMALL)
		);

		// raw diamond
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			var stack = player.getStackInHand(hand);
			if (stack.isOf(CortexItems.RAW_DIAMOND) && !player.isSpectator() &&
				world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.GRINDSTONE)) {
				player.getInventory().offerOrDrop(new ItemStack(Items.DIAMOND));
				// This wouldn't be needed if we were adding behavior directly to a grindstone
				if (!player.getAbilities().creativeMode) {
					stack.decrement(1);
				}
				player.playSound(SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});

		// raw emerald
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			var stack = player.getStackInHand(hand);
			if (stack.isOf(CortexItems.RAW_EMERALD) && !player.isSpectator() &&
				world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.GRINDSTONE)) {
				player.getInventory().offerOrDrop(new ItemStack(Items.EMERALD));
				// This wouldn't be needed if we were adding behavior directly to a grindstone
				if (!player.getAbilities().creativeMode) {
					stack.decrement(1);
				}
				player.playSound(SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});

		// red
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			var stack = player.getStackInHand(hand);
			if (stack.isOf(CortexItems.REDSTONE) && !player.isSpectator() &&
				world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.GRINDSTONE)) {
				player.getInventory().offerOrDrop(new ItemStack(Items.REDSTONE, 4));
				// This wouldn't be needed if we were adding behavior directly to a grindstone
				if (!player.getAbilities().creativeMode) {
					stack.decrement(1);
				}
				player.playSound(SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});

		//netherite
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			var stack = player.getOffHandStack();
			var state = world.getBlockState(hitResult.getBlockPos());
			var pos = hitResult.getBlockPos();
			if (player.getStackInHand(hand).isOf(Items.DIAMOND_PICKAXE) && !player.isSpectator() &&
				player.getOffHandStack().isOf(CortexItems.MOLTEN_DEBRIS) && state.isIn(BlockTags.ANVILS)) {
				player.getInventory().offerOrDrop(new ItemStack(Items.NETHERITE_INGOT));
				// This wouldn't be needed if we were adding behavior directly to a grindstone
				if (!player.getAbilities().creativeMode) {
					// Gets the new damaged anvil state
					var newState = AnvilBlock.getLandingState(state);
					if (newState != null) {
						// If newState isn't null, it means we can safely set the block and make the anvil hitting noise
						world.setBlockState(pos, AnvilBlock.getLandingState(state), Block.NOTIFY_LISTENERS);
						world.syncWorldEvent(WorldEvents.ANVIL_USED, pos, 0);
					} else {
						// Else, we can't, so nuke the block and make the destroy sound
						world.removeBlock(pos,  false);
						world.syncWorldEvent(WorldEvents.ANVIL_DESTROYED, pos, 0);
					}
					stack.decrement(1);
				}
				player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});

		// dispenser
		DispenserBlock.registerBehavior(CortexItems.RAW_DIAMOND, new FallibleItemDispenserBehavior() {
			@Override
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				var world = pointer.world();
				var blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				if (world.getBlockState(blockPos).getBlock() == Blocks.GRINDSTONE) {
					this.setSuccess(true);
					world.emitGameEvent(null, GameEvent.ITEM_INTERACT_FINISH, pointer.pos());
					return this.consumeWithRemainder(pointer, stack, new ItemStack(Items.DIAMOND));
				} else {
					return super.dispenseSilently(pointer, stack);
				}
			}
		});

		DispenserBlock.registerBehavior(CortexItems.RAW_EMERALD, new FallibleItemDispenserBehavior() {
			@Override
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				var world = pointer.world();
				var blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				if (world.getBlockState(blockPos).getBlock() == Blocks.GRINDSTONE) {
					this.setSuccess(true);
					world.emitGameEvent(null, GameEvent.ITEM_INTERACT_FINISH, pointer.pos());
					return this.consumeWithRemainder(pointer, stack, new ItemStack(Items.EMERALD));
				} else {
					return super.dispenseSilently(pointer, stack);
				}
			}
		});

		DispenserBlock.registerBehavior(CortexItems.REDSTONE, new FallibleItemDispenserBehavior() {
			@Override
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				var world = pointer.world();
				var blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				if (world.getBlockState(blockPos).getBlock() == Blocks.GRINDSTONE) {
					this.setSuccess(true);
					world.emitGameEvent(null, GameEvent.ITEM_INTERACT_FINISH, pointer.pos());
					return this.consumeWithRemainder(pointer, stack, new ItemStack(Items.REDSTONE, 4));
				} else {
					return super.dispenseSilently(pointer, stack);
				}
			}
		});
	}
}
