/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex;

import deserthydra.cortex.block.CortexBlocks;
import deserthydra.cortex.item.CortexItems;
import deserthydra.cortex.worldgen.CortexPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;

import static deserthydra.cortex.item.CortexItems.RAW_DIAMOND;

public class CortexMod implements ModInitializer {
	@Override
	public void onInitialize() {
		CortexBlocks.init();
		CortexItems.init();

		BiomeModifications.addFeature(
			context -> context.hasFeature(OreConfiguredFeatures.ORE_REDSTONE),
			GenerationStep.Feature.UNDERGROUND_DECORATION,
			CortexPlacedFeatures.REDSTONE_FORMATIONS
		);

		BiomeModifications.addFeature(
			context -> context.hasFeature(OreConfiguredFeatures.ORE_LAPIS),
			GenerationStep.Feature.UNDERGROUND_DECORATION,
			CortexPlacedFeatures.LAPIS_FORMATIONS
		);

		// stone diamond
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (player.getStackInHand(hand).getItem() == RAW_DIAMOND && !player.isSpectator() &&
				world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.GRINDSTONE)) {
				player.getInventory().offerOrDrop(Items.DIAMOND.getDefaultStack());
				// This wouldn't be needed if we were adding behavior directly to a grindstone
				if (!player.getAbilities().creativeMode) {
					player.getStackInHand(hand).decrement(1);
				}
				player.playSound(SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});
	}
}
