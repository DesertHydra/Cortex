/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex;

import com.mojang.logging.LogUtils;
import deserthydra.cortex.biome.CortexBiomes;
import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.data.CortexTags;
import deserthydra.cortex.item.CortexItems;
import deserthydra.cortex.loot.CortexLootTables;
import deserthydra.cortex.recipe.CortexRecipeBookCategories;
import deserthydra.cortex.recipe.CortexRecipeDisplayTypes;
import deserthydra.cortex.recipe.CortexRecipeSerializers;
import deserthydra.cortex.recipe.CortexRecipeTypes;
import deserthydra.cortex.recipe.grinding.GrindstoneGrindingRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.gameevent.GameEvent;
import org.slf4j.Logger;

public class CortexMod implements ModInitializer {

	public static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitialize() {
		CortexBlocks.init();
		CortexItems.init();
		CortexRecipeTypes.init();
		CortexRecipeSerializers.init();
		CortexRecipeDisplayTypes.init();
		CortexRecipeBookCategories.init();

		CortexBiomes.registerBiomeModifications();
		CortexLootTables.registerReplacements();

		UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
			if (!player.isSpectator() && !player.isShiftKeyDown() && level.getBlockState(hitResult.getBlockPos()).is(CortexTags.Blocks.GRINDSTONE_GRINDING_TARGETS)) {
				var stack = player.getItemInHand(hand);
				var input = new SingleRecipeInput(stack);

				// TODO cache last used recipe on player
				var recipe = level.recipeAccess().getSynchronizedRecipes().getFirstMatch(CortexRecipeTypes.GRINDSTONE_GRINDING, input, level, (RecipeHolder<GrindstoneGrindingRecipe>) null).orElse(null);
				if(recipe != null) {
					if(!level.isClientSide()) {
						var result = recipe.value().assemble(input);
						var remainder = stack.getCraftingRemainder();
						stack.consume(1, player);
						player.setItemInHand(hand, stack);

						player.getInventory().placeItemBackInInventory(result);
						if(remainder != null) {
							player.getInventory().placeItemBackInInventory(remainder.create());
						}
					}

					level.playSound(null, player, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 0.6F + player.getRandom().nextFloat() * 0.4F, 0.6F + player.getRandom().nextFloat() * 0.4F);
					return InteractionResult.SUCCESS;
				}
			}

			return InteractionResult.PASS;
		});

		//netherite
		UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
			if(hand != InteractionHand.MAIN_HAND) {
				return InteractionResult.PASS;
			}

			// TODO make this check mirrored too?
			// TODO make recipe
			var stack = player.getOffhandItem();
			var state = level.getBlockState(hitResult.getBlockPos());
			var pos = hitResult.getBlockPos();
			if (player.getItemInHand(hand).is(Items.DIAMOND_PICKAXE) && !player.isSpectator() &&
				player.getOffhandItem().is(CortexItems.MOLTEN_DEBRIS) && state.is(BlockTags.ANVIL)) {
				player.getInventory().placeItemBackInInventory(new ItemStack(Items.NETHERITE_INGOT));
				if (!player.hasInfiniteMaterials()) {
					if (!level.isClientSide()) {
						// Gets the new damaged anvil state
						var newState = AnvilBlock.damage(state);
						if (newState != null) {
							// If newState isn't null, it means we can safely set the block and make the anvil hitting noise
							level.setBlockAndUpdate(pos, newState);
							level.levelEvent(LevelEvent.SOUND_ANVIL_USED, pos, 0);
						} else {
							// Else, we can't, so nuke the block and make the destroy sound
							level.removeBlock(pos,  false);
							level.levelEvent(LevelEvent.SOUND_ANVIL_BROKEN, pos, 0);
						}
					}
					stack.consume(1, player);
				} else {
					if (!level.isClientSide()) {
						level.levelEvent(LevelEvent.SOUND_ANVIL_USED, pos, 0);
					}
				}

				return InteractionResult.SUCCESS;
			}

			return InteractionResult.PASS;
		});
	}
}
