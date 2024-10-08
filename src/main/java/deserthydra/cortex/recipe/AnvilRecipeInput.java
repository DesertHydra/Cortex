/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInput;

public record AnvilRecipeInput(ItemStack base, ItemStack addition) implements RecipeInput {
	@Override
	public ItemStack get(int i) {
		return switch (i) {
			case 0 -> this.base;
			case 1 -> this.addition;
			default -> throw new IllegalArgumentException("Recipe does not contain slot " + i);
		};
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		return this.base.isEmpty() && this.addition.isEmpty();
	}
}
