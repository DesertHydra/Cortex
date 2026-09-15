/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import deserthydra.cortex.CortexMod;
import deserthydra.cortex.item.CortexItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionBrewing.class)
public abstract class PotionBrewingMixin {

	private PotionBrewingMixin() {
		throw new UnsupportedOperationException();
	}

	@ModifyExpressionValue(method = "addVanillaMixes", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;REDSTONE:Lnet/minecraft/world/item/Item;"))
	private static Item modifyRedstoneRecipe(Item original) {
		// mod compat check
		if(original != Items.REDSTONE) {
			CortexMod.LOGGER.error("Unable to inject {} into brewing registry, already replaced by {}!", BuiltInRegistries.ITEM.getKey(CortexItems.REDSTONE), BuiltInRegistries.ITEM.getKey(original));
			return original;
		}

		return CortexItems.REDSTONE;
	}
}
