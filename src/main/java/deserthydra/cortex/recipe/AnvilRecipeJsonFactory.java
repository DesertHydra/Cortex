/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe;

import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterionTrigger;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class AnvilRecipeJsonFactory {
	private final RecipeCategory category;
	private final Ingredient base;
	private final Ingredient addition;
	private final Item result;
	private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();

	public AnvilRecipeJsonFactory(RecipeCategory category, Ingredient base, Ingredient addition, Item result) {
		this.category = category;
		this.base = base;
		this.addition = addition;
		this.result = result;
	}

	public static AnvilRecipeJsonFactory create(RecipeCategory category, Ingredient base, Ingredient addition, Item result) {
		return new AnvilRecipeJsonFactory(category, base, addition, result);
	}

	public AnvilRecipeJsonFactory criterion(String name, AdvancementCriterion<?> criterion) {
		this.criteria.put(name, criterion);
		return this;
	}

	public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> registryKey)  {
		this.validate(registryKey);
		var builder = exporter.accept()
			.putCriteria("has_the_recipe", RecipeUnlockedCriterionTrigger.create(registryKey))
			.rewards(AdvancementRewards.Builder.recipe(registryKey))
			.merger(AdvancementRequirements.RequirementMerger.ANY);
		this.criteria.forEach(builder::putCriteria);
		var recipe = new AnvilRecipe(Optional.of(this.base), Optional.of(this.addition), new ItemStack(this.result));
		exporter.accept(registryKey, recipe, builder.build(registryKey.getValue().withPrefix("recipes/" + this.category.getName() + "/")));
	}

	private void validate(RegistryKey<Recipe<?>> registryKey) {
		if (this.criteria.isEmpty()) {
			throw new IllegalArgumentException("No way of obtaining recipe " + registryKey.getValue());
		}
	}
}
