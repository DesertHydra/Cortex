/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe.anvil;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import deserthydra.cortex.recipe.CortexRecipeBookCategories;
import deserthydra.cortex.recipe.CortexRecipeSerializers;
import deserthydra.cortex.recipe.CortexRecipeTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AnvilRecipe implements Recipe<AnvilRecipeInput> {
	private final Recipe.CommonInfo commonInfo;
	private final Ingredient base;
	private final Optional<Ingredient> addition;
	private final ItemStackTemplate result;
	@Nullable
	private PlacementInfo placement;

	public AnvilRecipe(CommonInfo commonInfo, Ingredient base, Optional<Ingredient> addition, ItemStackTemplate result) {
		this.commonInfo = commonInfo;
		this.base = base;
		this.addition = addition;
		this.result = result;
	}

	@Override
	public RecipeType<? extends Recipe<AnvilRecipeInput>> getType() {
		return CortexRecipeTypes.ANVIL;
	}

	@Override
	public PlacementInfo placementInfo() {
		if (this.placement == null) {
			this.placement = PlacementInfo.createFromOptionals(List.of(Optional.of(this.base), this.addition));
		}

		return this.placement;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return CortexRecipeBookCategories.ANVIL;
	}

	@Override
	public boolean matches(AnvilRecipeInput input, Level level) {
		return this.base.test(input.base()) && Ingredient.testOptionalIngredient(this.addition, input.addition());
	}

	@Override
	public ItemStack assemble(AnvilRecipeInput input) {
		return TransmuteRecipe.createWithOriginalComponents(this.result, input.base());
	}

	@Override
	public RecipeSerializer<? extends Recipe<AnvilRecipeInput>> getSerializer() {
		return CortexRecipeSerializers.ANVIL;
	}

	@Override
	public List<RecipeDisplay> display() {
		return List.of(
			new AnvilRecipeDisplay(
				this.base.display(),
				Ingredient.optionalIngredientToDisplay(this.addition),
				new SlotDisplay.ItemStackSlotDisplay(this.result),
				new SlotDisplay.ItemSlotDisplay(Items.ANVIL)
			)
		);
	}

	@Override
	public boolean showNotification() {
		return this.commonInfo.showNotification();
	}

	@Override
	public String group() {
		return "";
	}

	public static final MapCodec<AnvilRecipe> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
			Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
			Ingredient.CODEC.optionalFieldOf("addition").forGetter(recipe -> recipe.addition),
			ItemStackTemplate.MAP_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
		).apply(instance, AnvilRecipe::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, AnvilRecipe> STREAM_CODEC = StreamCodec.composite(
		CommonInfo.STREAM_CODEC,
		recipe -> recipe.commonInfo,

		Ingredient.CONTENTS_STREAM_CODEC,
		recipe -> recipe.base,

		Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC,
		recipe -> recipe.addition,

		ItemStackTemplate.STREAM_CODEC,
		recipe -> recipe.result,

		AnvilRecipe::new
	);
}
