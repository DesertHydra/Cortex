package deserthydra.cortex.datagen;

import deserthydra.cortex.item.CortexItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeCategory;
import net.minecraft.registry.HolderLookup;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

// Since we are using RecipesProvider instead of FabricRecipeProvider,
// The id will be "defaults to minecraft, but can be set to a mod id" instead of "always your mod id".
// Be aware!
public class CortexRecipeProvider extends RecipesProvider {
	public CortexRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generateRecipes(RecipeExporter exporter) {
		// This will generate as a Minecraft recipe override because the Redstone Block is a vanilla block
		// 4 Redstone to Redstone Block
		FabricRecipeProvider.offerTwoByTwoCompactingRecipe(exporter, RecipeCategory.REDSTONE, Blocks.REDSTONE_BLOCK, CortexItems.REDSTONE);

		// 4 Redstone Dust to Redstone
		ShapedRecipeJsonFactory.create(RecipeCategory.REDSTONE, CortexItems.REDSTONE, 1)
			.ingredient('#', Items.REDSTONE)
			.pattern("##")
			.pattern("##")
			.group("cortex_redstone")
			.criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
			.offerTo(exporter);

		// Redstone Block to 4 Redstone
		ShapelessRecipeJsonFactory.create(RecipeCategory.REDSTONE, CortexItems.REDSTONE, 4)
			.ingredient(Ingredient.ofItems(Blocks.REDSTONE_BLOCK))
			.group("cortex_redstone")
			.criterion(hasItem(Blocks.REDSTONE_BLOCK), conditionsFromItem(Blocks.REDSTONE_BLOCK))
			.offerTo(exporter, Identifier.ofDefault("redstone"));
	}
}
