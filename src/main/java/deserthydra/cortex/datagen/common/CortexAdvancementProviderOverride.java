package deserthydra.cortex.datagen.common;

import deserthydra.cortex.item.CortexItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CortexAdvancementProviderOverride extends FabricAdvancementProvider {
	public CortexAdvancementProviderOverride(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
		Advancement.Builder.advancement()
			.parent(Identifier.withDefaultNamespace("nether/root"))
			.display(CortexItems.ANCIENT_DEBRIS, Component.translatable("advancements.nether.obtain_ancient_debris.title"), Component.translatable("advancements.nether.obtain_ancient_debris.description"), null, AdvancementType.TASK, true, true, false)
			.addCriterion("ancient_debris", InventoryChangeTrigger.TriggerInstance.hasItems(CortexItems.ANCIENT_DEBRIS))
			.save(consumer, "nether/obtain_ancient_debris");
	}
}
