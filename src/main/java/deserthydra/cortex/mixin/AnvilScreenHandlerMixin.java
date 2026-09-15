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
import deserthydra.cortex.recipe.anvil.AnvilRecipeInput;
import deserthydra.cortex.recipe.CortexRecipeTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilScreenHandlerMixin extends ItemCombinerMenu {

	private AnvilScreenHandlerMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots) {
		super(menuType, containerId, inventory, access, itemInputSlots);
		throw new UnsupportedOperationException();
	}

	// FIXME - Add caching to recipe outputs..?
	@Inject(
		method = "createResult",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z",
			ordinal = 1
		)
	)
	private void updateResultWithRecipe(CallbackInfo ci, @Local(name = "price") LocalIntRef i, @Local(name = "result") LocalRef<ItemStack> outputStack, @Share("hasRecipe") LocalBooleanRef hasRecipe) {
		var additionStack = this.inputSlots.getItem(AnvilMenu.ADDITIONAL_SLOT);
		if (!additionStack.isEmpty()) {
			var recipeInput = new AnvilRecipeInput(this.inputSlots.getItem(AnvilMenu.INPUT_SLOT), additionStack);

			access.execute((level, pos) -> level.recipeAccess().getSynchronizedRecipes().getFirstMatch(CortexRecipeTypes.ANVIL, recipeInput, level)
				.ifPresentOrElse(holder -> {
					var output = holder.value().assemble(recipeInput);
					if(player instanceof ServerPlayer serverPlayer) {
						this.resultSlots.setRecipeUsed(serverPlayer, holder);
					}
					else {
						this.resultSlots.setRecipeUsed(holder);
					}
					this.resultSlots.setItem(0, output);
					hasRecipe.set(true);
					i.set(i.get() + 5);
					outputStack.set(this.resultSlots.getItem(0));
				}, () -> {
					this.resultSlots.setRecipeUsed(null);
					this.resultSlots.setItem(0, ItemStack.EMPTY);
					hasRecipe.set(false);
				}));
		}
	}

	@ModifyExpressionValue(
		method = "createResult",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z",
			ordinal = 1
		)
	)
	private boolean skipAntiRecipeCode(boolean original, @Share("hasRecipe") LocalBooleanRef hasRecipe) {
		return hasRecipe.get() || original;
	}
}
