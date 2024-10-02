/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import deserthydra.cortex.item.CortexItems;
import net.minecraft.item.Item;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {
	@ModifyExpressionValue(method = "addVanillaRecipes", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;REDSTONE:Lnet/minecraft/item/Item;"))
	private static Item modifyRedstoneRecipe(Item original) {
		return CortexItems.REDSTONE;
	}
}
