package deserthydra.cortex.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/// Overrides for vanilla's `en_us.json` file
public class CortexEnglishLanguageProviderOverride extends FabricLanguageProvider {

	public CortexEnglishLanguageProviderOverride(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(packOutput, registryLookup);
	}

	@Override
	public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
		translationBuilder.add(Items.NETHERITE_SCRAP, "Ancient Debris Scraps");
	}

	@Override
	public String getName() {
		return super.getName() + " (Override)";
	}

	/// Overriding vanilla language entries needs to happen in the same location,
	/// due to how language files are merged together
	@Override
	protected Path getLangFilePath(String code) {
		return packOutput
			.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang")
			.json(Identifier.withDefaultNamespace(code));
	}
}
