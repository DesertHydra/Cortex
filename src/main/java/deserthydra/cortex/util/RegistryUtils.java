/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class RegistryUtils {
	private static ResourceKey<Block> createBlockKey(String path) {
		return ResourceKey.create(Registries.BLOCK, CortexUtils.id(path));
	}

	private static Block register(String name, BlockBehaviour.Properties settings) {
		return register(name, Block::new, settings);
	}

	public static Block register(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties settings) {
		return Blocks.register(createBlockKey(name), function, settings);
	}

	// TODO - Add more helper functions here if necessary

	private static ResourceKey<Item> createItemKey(String path) {
		return ResourceKey.create(Registries.ITEM, CortexUtils.id(path));
	}

	public static Item register(String path, Function<Item.Properties, Item> factory, Item.Properties settings) {
		return Items.registerItem(createItemKey(path), factory, settings);
	}

	public static Item register(String path, Function<Item.Properties, Item> factory) {
		return register(path, factory, new Item.Properties());
	}

	public static Item register(String path, Item.Properties settings) {
		return register(path, Item::new, settings);
	}

	public static Item register(String path) {
		return register(path, Item::new, new Item.Properties());
	}

	public static Item registerBlock(Block block, Item.Properties settings) {
		return Items.registerBlock(block, settings);
	}

	public static Item registerBlock(Block block) {
		return Items.registerBlock(block, new Item.Properties());
	}

	public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String name) {
		var id = ResourceKey.create(Registries.RECIPE_TYPE, CortexUtils.id(name));
		return Registry.register(BuiltInRegistries.RECIPE_TYPE, id, new RecipeType<T>() {
			@Override
			public String toString() {
				return id.toString();
			}
		});
	}
}
