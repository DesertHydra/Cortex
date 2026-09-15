package deserthydra.cortex.datagen.util;

import deserthydra.cortex.recipe.grinding.GrindstoneGrindingRecipe;
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

public class GrindstoneGrindingRecipeBuilder implements RecipeBuilder {

	private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
	private final RecipeCategory category;
	private final Ingredient input;
	private final ItemStackTemplate result;
	private boolean showNotification = true;
	private boolean allowDispenser = true;

	public GrindstoneGrindingRecipeBuilder(RecipeCategory category, Ingredient input, ItemStackTemplate result) {
		this.category = category;
		this.input = input;
		this.result = result;
	}

	@Override
	public GrindstoneGrindingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
		advancementBuilder.unlockedBy(name, criterion);
		return this;
	}

	@Override
	public GrindstoneGrindingRecipeBuilder group(@Nullable String group) {
		throw new UnsupportedOperationException("Groups are not allowed for grinding recipes!");
	}

	public GrindstoneGrindingRecipeBuilder showNotification(boolean showNotification) {
		this.showNotification = showNotification;
		return this;
	}

	public GrindstoneGrindingRecipeBuilder allowDispenser(boolean allowDispenser) {
		this.allowDispenser = allowDispenser;
		return this;
	}

	@Override
	public ResourceKey<Recipe<?>> defaultId() {
		return ResourceKey.create(Registries.RECIPE, this.result.typeHolder().unwrapKey().orElseThrow().identifier().withSuffix("_grindstone_grinding"));
	}

	@Override
	public void save(RecipeOutput output, ResourceKey<Recipe<?>> location) {
		var recipe = new GrindstoneGrindingRecipe(RecipeBuilder.createCraftingCommonInfo(showNotification), input, result, allowDispenser);
		output.accept(location, recipe, advancementBuilder.build(output, location, category));
	}

	public static GrindstoneGrindingRecipeBuilder create(RecipeCategory category, Ingredient input, ItemLike result, int count) {
		return new GrindstoneGrindingRecipeBuilder(category, input, new ItemStackTemplate(result.asItem(), count));
	}

	public static GrindstoneGrindingRecipeBuilder create(RecipeCategory category, Ingredient input, ItemLike result) {
		return create(category, input, result, 1);
	}
}
