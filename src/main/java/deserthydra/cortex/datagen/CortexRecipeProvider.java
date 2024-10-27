/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen;

import deserthydra.cortex.util.CortexUtils;
import deserthydra.cortex.item.CortexItems;
import deserthydra.cortex.recipe.AnvilRecipeJsonFactory;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataPackOutput;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeCategory;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// Since we are using RecipesProvider instead of FabricRecipeProvider,
// The id will be "defaults to minecraft, but can be set to a mod id" instead of "always your mod id".
// Be aware!
public class CortexRecipeProvider extends RecipesProvider {
	public CortexRecipeProvider(HolderLookup.Provider provider, RecipeExporter exporter) {
		super(provider, exporter);
	}

	@Override
	public void generateRecipes() {
		// This will generate as a Minecraft recipe override because the Redstone Block is a vanilla block
		// 4 Redstone to Redstone Block
		offerTwoByTwoCompactingRecipe(RecipeCategory.REDSTONE, Blocks.REDSTONE_BLOCK, CortexItems.REDSTONE);

		method_47522(RecipeCategory.MISC, CortexItems.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP);


		// 4 Redstone Dust to Redstone
		method_62746(RecipeCategory.REDSTONE, CortexItems.REDSTONE)
			.ingredient('#', Items.REDSTONE)
			.pattern("##")
			.pattern("##")
			.group("cortex_redstone")
			.criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
			.offerTo(exporter);

		// Redstone Block to 4 Redstone
		this.method_62750(RecipeCategory.REDSTONE, CortexItems.REDSTONE, 4)
			.ingredient(Ingredient.ofItems(Blocks.REDSTONE_BLOCK))
			.group("cortex_redstone")
			.criterion(hasItem(Blocks.REDSTONE_BLOCK), this.conditionsFromItem(Blocks.REDSTONE_BLOCK))
			.offerTo(this.exporter, "minecraft:redstone");

		// debris smelting
		offerBlasting(List.of(CortexItems.ANCIENT_DEBRIS), RecipeCategory.MISC, CortexItems.MOLTEN_DEBRIS, 0, 600, getItemPath(CortexItems.MOLTEN_DEBRIS));

		// Netherite upgrades
		this.offerNetheriteUpgradeRecipe(RecipeCategory.COMBAT, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET);
		this.offerNetheriteUpgradeRecipe(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE);
		this.offerNetheriteUpgradeRecipe(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS);
		this.offerNetheriteUpgradeRecipe(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS);
		this.offerNetheriteUpgradeRecipe(RecipeCategory.COMBAT, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
		this.offerNetheriteUpgradeRecipe(RecipeCategory.TOOLS, Items.DIAMOND_AXE, Items.NETHERITE_AXE);
		this.offerNetheriteUpgradeRecipe(RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE);
		this.offerNetheriteUpgradeRecipe(RecipeCategory.TOOLS, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
		this.offerNetheriteUpgradeRecipe(RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL);
	}

	private void offerNetheriteUpgradeRecipe(RecipeCategory category, Item input, Item output) {
		AnvilRecipeJsonFactory.create(
				category,
				Ingredient.ofItems(input),
				Ingredient.ofItems(Items.NETHERITE_INGOT),
				output
			)
			.criterion(hasItem(Items.NETHERITE_INGOT), this.conditionsFromItem(Items.NETHERITE_INGOT))
			.offerTo(this.exporter, RegistryKey.of(RegistryKeys.RECIPE, CortexUtils.id(getItemPath(output) + "_smithing")));
	}

	public static class CortexRecipeGenerator extends RecipesProvider.C_ujfsvkmt {
		public CortexRecipeGenerator(DataPackOutput dataPackOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(dataPackOutput, completableFuture);
		}

		@Override
		protected RecipesProvider method_62766(HolderLookup.Provider provider, RecipeExporter exporter) {
			return new CortexRecipeProvider(provider, exporter);
		}

		@Override
		public String getDescription() {
			return "Cortex Recipes";
		}
	}
}
