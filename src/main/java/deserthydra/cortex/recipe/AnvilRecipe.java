/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.registry.HolderLookup;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AnvilRecipe implements Recipe<AnvilRecipeInput> {
	private final Optional<Ingredient> base;
	private final Optional<Ingredient> addition;
	private final ItemStack result;
	@Nullable
	private IngredientPlacement placement;

	public AnvilRecipe(Optional<Ingredient> base, Optional<Ingredient> addition, ItemStack result) {
		this.base = base;
		this.addition = addition;
		this.result = result;
	}

	@Override
	public RecipeType<? extends Recipe<AnvilRecipeInput>> getType() {
		return CortexRecipeTypes.ANVIL;
	}

	@Override
	public IngredientPlacement getIngredientPlacement() {
		if (this.placement == null) {
			this.placement = IngredientPlacement.createFromOptions(List.of(this.base, this.addition));
		}

		return this.placement;
	}

	@Override
	public RecipeBookCategory getRecipeBookCategory() {
		return CortexRecipeBookCategories.ANVIL;
	}

	@Override
	public boolean matches(AnvilRecipeInput input, World world) {
		return Ingredient.testOptionalIngredient(this.base, input.base()) && Ingredient.testOptionalIngredient(this.addition, input.addition());
	}

	@Override
	public ItemStack craft(AnvilRecipeInput input, HolderLookup.Provider provider) {
		var stack = input.base().copyComponentsToNewStack(this.result.getItem(), this.result.getCount());
		stack.applyPatch(this.result.getComponentPatch());
		return stack;
	}

	@Override
	public RecipeSerializer<? extends Recipe<AnvilRecipeInput>> getSerializer() {
		return CortexRecipeSerializers.ANVIL;
	}

	@Override
	public List<RecipeDisplay> getDisplays() {
		return List.of(
			new AnvilRecipeDisplay(
				Ingredient.createOptionalSlotDisplay(this.base),
				Ingredient.createOptionalSlotDisplay(this.addition),
				new SlotDisplay.StackSlotDisplay(this.result),
				new SlotDisplay.ItemSlotDisplay(Items.ANVIL)
			)
		);
	}

	public static class Serializer implements RecipeSerializer<AnvilRecipe> {
		private static final MapCodec<AnvilRecipe> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
				Ingredient.ALLOW_EMPTY_CODEC.optionalFieldOf("base").forGetter(recipe -> recipe.base),
				Ingredient.ALLOW_EMPTY_CODEC.optionalFieldOf("addition").forGetter(recipe -> recipe.addition),
				ItemStack.field_51397.fieldOf("result").forGetter(recipe -> recipe.result)
			).apply(instance, AnvilRecipe::new)
		);

		private static final PacketCodec<RegistryByteBuf, AnvilRecipe> PACKET_CODEC = PacketCodec.tuple(
			Ingredient.OPTIONAL_PACKET_CODEC,
			recipe -> recipe.base,
			Ingredient.OPTIONAL_PACKET_CODEC,
			recipe -> recipe.addition,
			ItemStack.OPTIONAL_PACKET_CODEC,
			recipe -> recipe.result,
			AnvilRecipe::new
		);

		@Override
		public MapCodec<AnvilRecipe> getCodec() {
			return CODEC;
		}

		@Override
		public PacketCodec<RegistryByteBuf, AnvilRecipe> getPacketCodec() {
			return PACKET_CODEC;
		}
	}
}
