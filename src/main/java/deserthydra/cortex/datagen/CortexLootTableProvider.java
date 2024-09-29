package deserthydra.cortex.datagen;

import deserthydra.cortex.item.CortexItems;
import deserthydra.cortex.loot.CortexLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.RegistryKey;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class CortexLootTableProvider extends SimpleFabricLootTableProvider {
	public CortexLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup, LootContextTypes.ARCHAEOLOGY);
	}

	@Override
	public void generate(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> biConsumer) {
		biConsumer.accept(
			CortexLootTables.NETHER_RUINED_PORTAL_ARCHAEOLOGY,
			LootTable.builder()
				.pool(
					LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(ItemEntry.builder(Items.NETHERITE_SCRAP).weight(5))
						.with(ItemEntry.builder(Items.ANCIENT_DEBRIS).weight(1))
				)
		);

		biConsumer.accept(
			CortexLootTables.NETHER_FOSSIL_ARCHAEOLOGY,
			LootTable.builder()
				.pool(
					LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(ItemEntry.builder(Items.NETHERITE_SCRAP).weight(5))
						.with(ItemEntry.builder(Items.ANCIENT_DEBRIS).weight(1))
				)
		);
	}
}
