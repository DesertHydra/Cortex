/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import deserthydra.cortex.recipe.AnvilRecipeInput;
import deserthydra.cortex.recipe.CortexRecipeTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	@Shadow
	@Final
	private Property levelCost;

	@Shadow
	public abstract void updateResult();

	@Unique
	private World world;

	public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
	private void getWorldOnInit(int syncId, PlayerInventory inventory, ScreenHandlerContext context, CallbackInfo ci) {
		this.world = inventory.player.getWorld();
	}

	@Inject(
		method = "updateResult",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/screen/Property;set(I)V",
			ordinal = 0,
			shift = At.Shift.AFTER
		),
		cancellable = true
	)
	private void updateResultWithRecipe(CallbackInfo ci, @Local ItemStack baseStack) {
		if (!baseStack.isEmpty()) {
			var recipeInput = new AnvilRecipeInput(baseStack, this.ingredientInventory.getStack(1));
			var matchingRecipes = this.world.getRecipeManager().getAllMatches(CortexRecipeTypes.ANVIL, recipeInput, this.world);
			if (!matchingRecipes.isEmpty()) {
				var recipeHolder = matchingRecipes.getFirst();
				var output = recipeHolder.value().craft(recipeInput, this.world.getRegistryManager());
				if (output.isEnabled(this.world.getEnabledFlags())) {
					this.levelCost.set(5);
					this.result.onResultUpdate(recipeHolder);
					this.result.setStack(0, output);
					ci.cancel();
				}
			}
		}
	}
}
