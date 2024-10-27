/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.HolderLookup;
import net.minecraft.world.World;

public record AnvilRecipe(
	Ingredient base,
	Ingredient addition,
	ItemStack result
) implements Recipe<AnvilRecipeInput> {
	@Override
	public RecipeType<? extends Recipe<AnvilRecipeInput>> getType() {
		return CortexRecipeTypes.ANVIL;
	}

	@Override
	public IngredientPlacement getIngredientPlacement() {
		return null;
	}

	@Override
	public RecipeBookCategory getRecipeBookCategory() {
		return null;
	}

	@Override
	public boolean matches(AnvilRecipeInput input, World world) {
		return this.base.test(input.base()) && this.addition.test(input.addition());
	}

	@Override
	public ItemStack craft(AnvilRecipeInput input, HolderLookup.Provider provider) {
		var stack = input.base().copyComponentsToNewStack(this.result.getItem(), this.result.getCount());
		stack.applyPatch(this.result.getComponentPatch());
		return stack;
	}

	/*
	@Override
	public boolean fits(int width, int height) {
		return width >= 2 && height >= 1;
	}

	@Override
	public ItemStack getResult(HolderLookup.Provider provider) {
		return this.result;
	}
	 */

	@Override
	public RecipeSerializer<? extends Recipe<AnvilRecipeInput>> getSerializer() {
		return CortexRecipeSerializers.ANVIL;
	}

	public static class Serializer implements RecipeSerializer<AnvilRecipe> {
		private static final MapCodec<AnvilRecipe> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
				Ingredient.ALLOW_EMPTY_CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
				Ingredient.ALLOW_EMPTY_CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
				ItemStack.field_51397.fieldOf("result").forGetter(recipe -> recipe.result)
			).apply(instance, AnvilRecipe::new)
		);

		private static final PacketCodec<RegistryByteBuf, AnvilRecipe> PACKET_CODEC = PacketCodec.create(
			AnvilRecipe.Serializer::write, AnvilRecipe.Serializer::read
		);

		@Override
		public MapCodec<AnvilRecipe> getCodec() {
			return CODEC;
		}

		@Override
		public PacketCodec<RegistryByteBuf, AnvilRecipe> getPacketCodec() {
			return PACKET_CODEC;
		}

		private static AnvilRecipe read(RegistryByteBuf buf) {
			var base = Ingredient.PACKET_CODEC.decode(buf);
			var addition = Ingredient.PACKET_CODEC.decode(buf);
			var result = ItemStack.PACKET_CODEC.decode(buf);

			return new AnvilRecipe(base, addition, result);
		}

		private static void write(RegistryByteBuf buf, AnvilRecipe recipe) {
			Ingredient.PACKET_CODEC.encode(buf, recipe.base);
			Ingredient.PACKET_CODEC.encode(buf, recipe.addition);
			ItemStack.PACKET_CODEC.encode(buf, recipe.result);
		}
	}
}
