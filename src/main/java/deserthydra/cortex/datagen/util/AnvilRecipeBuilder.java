/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen.util;


import deserthydra.cortex.recipe.anvil.AnvilRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class AnvilRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private boolean showNotification = true;
	private final Ingredient base;
	@Nullable
	private final Ingredient addition;
	private final ItemStackTemplate result;
	private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();

	public AnvilRecipeBuilder(RecipeCategory category, Ingredient base, Ingredient addition, ItemStackTemplate result) {
		this.category = category;
		this.base = base;
		this.addition = addition;
		this.result = result;
	}

	public static AnvilRecipeBuilder create(RecipeCategory category, Ingredient base, Ingredient addition, ItemStackTemplate result) {
		return new AnvilRecipeBuilder(category, base, addition, result);
	}

	public static AnvilRecipeBuilder create(RecipeCategory category, Ingredient base, Ingredient addition, ItemLike result, int count) {
		return create(category, base, addition, new ItemStackTemplate(result.asItem(), count));
	}

	public static AnvilRecipeBuilder create(RecipeCategory category, Ingredient base, Ingredient addition, ItemLike result) {
		return create(category, base, addition, result, 1);
	}

	public AnvilRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		advancementBuilder.unlockedBy(name, criterion);
		return this;
	}

	@Override
	public AnvilRecipeBuilder group(@Nullable String group) {
		throw new UnsupportedOperationException("Groups are not allowed for anvil recipes!");
	}

	public AnvilRecipeBuilder showNotification(final boolean showNotification) {
		this.showNotification = showNotification;
		return this;
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return ResourceKey.create(Registries.RECIPE, this.result.typeHolder().unwrapKey().orElseThrow().identifier().withSuffix("_smithing"));
	}

	@Override
	public void save(RecipeOutput output, ResourceKey<Recipe<?>> location) {
		var recipe = new AnvilRecipe(RecipeBuilder.createCraftingCommonInfo(showNotification), this.base, Optional.ofNullable(this.addition), this.result);
		output.accept(location, recipe, advancementBuilder.build(output, location, this.category));
	}
}
