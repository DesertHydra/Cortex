/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.datagen.common;

import deserthydra.cortex.datagen.util.AnvilRecipeBuilder;
import deserthydra.cortex.datagen.util.GrindstoneGrindingRecipeBuilder;
import deserthydra.cortex.item.CortexItems;
import deserthydra.cortex.util.CortexUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CortexRecipeProvider extends RecipeProvider {
	public CortexRecipeProvider(HolderLookup.Provider provider, RecipeOutput exporter) {
		super(provider, exporter);
	}

	@Override
	public void buildRecipes() {
		// This will generate as a Minecraft recipe override because the Redstone Block is a vanilla block
		// 4 Redstone to Redstone Block
		twoByTwoPacker(RecipeCategory.REDSTONE, Blocks.REDSTONE_BLOCK, CortexItems.REDSTONE);

		// 9 Netherite Scrap to Ancient Debris
		threeByThreePacker(RecipeCategory.MISC, CortexItems.ANCIENT_DEBRIS, Items.NETHERITE_SCRAP);

		// 4 Redstone Dust to Redstone
		twoByTwoPacker(RecipeCategory.REDSTONE, CortexItems.REDSTONE, Items.REDSTONE);

		// Redstone Block to 4 Redstone
		shapeless(RecipeCategory.REDSTONE, CortexItems.REDSTONE, 4)
			.requires(Blocks.REDSTONE_BLOCK)
			.group("cortex_redstone")
			.unlockedBy(getHasName(Blocks.REDSTONE_BLOCK), has(Blocks.REDSTONE_BLOCK))
			.save(this.output, "redstone_from_block");

		// debris smelting
		oreBlasting(List.of(CortexItems.ANCIENT_DEBRIS), RecipeCategory.MISC, CookingBookCategory.MISC, CortexItems.MOLTEN_DEBRIS, 10, 600, getItemName(CortexItems.MOLTEN_DEBRIS));

		// Netherite upgrades
		netheriteUpgradeSmithing(RecipeCategory.COMBAT, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET);
		netheriteUpgradeSmithing(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE);
		netheriteUpgradeSmithing(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS);
		netheriteUpgradeSmithing(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS);
		netheriteUpgradeSmithing(RecipeCategory.COMBAT, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
		netheriteUpgradeSmithing(RecipeCategory.COMBAT, Items.DIAMOND_SPEAR, Items.NETHERITE_SPEAR);
		netheriteUpgradeSmithing(RecipeCategory.TOOLS, Items.DIAMOND_AXE, Items.NETHERITE_AXE);
		netheriteUpgradeSmithing(RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE);
		netheriteUpgradeSmithing(RecipeCategory.TOOLS, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
		netheriteUpgradeSmithing(RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL);

		// grindstone in-world crafting and dispenser behavior
		grindstoneGrinding(RecipeCategory.MISC, CortexItems.RAW_DIAMOND, Items.DIAMOND).save(output, ResourceKey.create(Registries.RECIPE, CortexUtils.id("diamond_grindstone_grinding")));
		grindstoneGrinding(RecipeCategory.MISC, CortexItems.RAW_EMERALD, Items.EMERALD).save(output, ResourceKey.create(Registries.RECIPE, CortexUtils.id("emerald_grindstone_grinding")));
		grindstoneGrinding(RecipeCategory.MISC, CortexItems.REDSTONE, Items.REDSTONE, 4).save(output, ResourceKey.create(Registries.RECIPE, CortexUtils.id("redstone_grindstone_grinding")));
	}

	private void netheriteUpgradeSmithing(RecipeCategory category, ItemLike input, Item output) {
		AnvilRecipeBuilder.create(
				category,
				Ingredient.of(input),
				Ingredient.of(Items.NETHERITE_INGOT),
				output
			)
			.unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
			.save(this.output);
	}

	private GrindstoneGrindingRecipeBuilder grindstoneGrinding(RecipeCategory category, ItemLike input, Item result) {
		return GrindstoneGrindingRecipeBuilder.create(category, Ingredient.of(input), result)
			.unlockedBy(getHasName(input), has(input));
	}

	private GrindstoneGrindingRecipeBuilder grindstoneGrinding(RecipeCategory category, ItemLike input, Item result, int count) {
		return GrindstoneGrindingRecipeBuilder.create(category, Ingredient.of(input), result, count)
			.unlockedBy(getHasName(input), has(input));
	}

	public static class CortexRecipeGenerator extends FabricRecipeProvider {
		public CortexRecipeGenerator(FabricPackOutput dataPackOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
			super(dataPackOutput, completableFuture);
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput exporter) {
			return new CortexRecipeProvider(provider, exporter);
		}

		@Override
		public String getName() {
			return "Cortex Recipes";
		}
	}
}
