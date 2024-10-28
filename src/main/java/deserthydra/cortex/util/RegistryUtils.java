package deserthydra.cortex.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class RegistryUtils {
	private static RegistryKey<Block> createBlockKey(String path) {
		return RegistryKey.of(RegistryKeys.BLOCK, CortexUtils.id(path));
	}

	private static Block register(String path, AbstractBlock.Settings settings) {
		return Blocks.register(createBlockKey(path), Block::new, settings);
	}

	public static Block register(String path, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
		return Blocks.register(createBlockKey(path), function, settings);
	}

	// TODO - Add more helper functions here if necessary

	private static RegistryKey<Item> createItemKey(String path) {
		return RegistryKey.of(RegistryKeys.ITEM, CortexUtils.id(path));
	}

	public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
		return Items.register(createItemKey(path), factory, settings);
	}

	public static Item register(String path, Function<Item.Settings, Item> factory) {
		return Items.register(createItemKey(path), factory);
	}

	public static Item register(String path, Item.Settings settings) {
		return Items.register(createItemKey(path), Item::new, settings);
	}

	public static Item register(String path) {
		return Items.register(createItemKey(path), Item::new, new Item.Settings());
	}

	public static Item register(Block block, Item.Settings settings) {
		return Items.register(block, settings);
	}
}
