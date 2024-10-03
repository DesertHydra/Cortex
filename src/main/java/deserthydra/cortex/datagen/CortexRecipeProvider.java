/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.CortexUtils;
import deserthydra.cortex.item.CortexItems;
import deserthydra.cortex.recipe.AnvilRecipeJsonFactory;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeCategory;
import net.minecraft.registry.HolderLookup;
import net.minecraft.util.Identifier;

import java.util.List;
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

		FabricRecipeProvider.offerThreeByThreeCompactingRecipe(exporter, RecipeCategory.MISC, CortexItems.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP);


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

		// debris smelting
		FabricRecipeProvider.offerBlasting(exporter, List.of(CortexItems.ANCIENT_DEBRIS), RecipeCategory.MISC, CortexItems.MOLTEN_DEBRIS, 0, 600, getItemPath(CortexItems.MOLTEN_DEBRIS));

		// Netherite upgrades
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.COMBAT, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET);
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE);
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS);
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.COMBAT, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS);
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.COMBAT, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.TOOLS, Items.DIAMOND_AXE, Items.NETHERITE_AXE);
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE);
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.TOOLS, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
		offerNetheriteUpgradeRecipe(exporter, RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL);
	}

	private static void offerNetheriteUpgradeRecipe(RecipeExporter exporter, RecipeCategory category, Item input, Item output) {
		AnvilRecipeJsonFactory.create(
				category,
				Ingredient.ofItems(input),
				Ingredient.ofItems(Items.NETHERITE_INGOT),
				output
			)
			.criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
			.offerTo(exporter, CortexUtils.id(getItemPath(output) + "_smithing"));
	}
}
