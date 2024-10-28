/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import deserthydra.cortex.recipe.AnvilRecipe;
import deserthydra.cortex.recipe.AnvilRecipeInput;
import deserthydra.cortex.recipe.CortexRecipeTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeHolder;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ItemCombinationSlotManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	@Unique
	private World world;

	private AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ItemCombinationSlotManager itemCombinationSlotManager) {
		super(type, syncId, playerInventory, context, itemCombinationSlotManager);
	}

	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
	private void getWorldOnInit(int syncId, PlayerInventory inventory, ScreenHandlerContext context, CallbackInfo ci) {
		this.world = inventory.player.getWorld();
	}

	// FIXME - Add caching to recipe outputs..?
	@Inject(
		method = "updateResult",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;isEmpty()Z",
			ordinal = 1
		)
	)
	private void updateResultWithRecipe(CallbackInfo ci, @Local(ordinal = 0) LocalIntRef i, @Local(ordinal = 1) LocalRef<ItemStack> outputStack, @Share("hasRecipe") LocalBooleanRef hasRecipe) {
		var additionStack = this.ingredientInventory.getStack(1);
		if (!additionStack.isEmpty()) {
			var recipeInput = new AnvilRecipeInput(this.ingredientInventory.getStack(0), additionStack);
			Optional<RecipeHolder<AnvilRecipe>> recipeHolder;
			if (this.world instanceof ServerWorld serverWorld) {
				recipeHolder = serverWorld.m_mlvimbbc().getFirstMatch(CortexRecipeTypes.ANVIL, recipeInput, serverWorld);
			} else {
				recipeHolder = Optional.empty();
			}

			recipeHolder.ifPresentOrElse(holder -> {
				var output = holder.value().craft(recipeInput, this.world.getRegistryManager());
				this.result.onResultUpdate(holder);
				this.result.setStack(0, output);
				hasRecipe.set(true);
				i.set(i.get() + 5);
				outputStack.set(this.result.getStack(0));
			}, () -> {
				this.result.onResultUpdate(null);
				this.result.setStack(0, ItemStack.EMPTY);
			});

			hasRecipe.set(hasRecipe.get());
		}
	}

	@ModifyExpressionValue(
		method = "updateResult",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;isEmpty()Z",
			ordinal = 1
		)
	)
	private boolean skipAntiRecipeCode(boolean original, @Share("hasRecipe") LocalBooleanRef hasRecipe) {
		return hasRecipe.get() || original;
	}
}
