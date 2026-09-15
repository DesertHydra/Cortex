/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package deserthydra.cortex.recipe.anvil;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import deserthydra.cortex.recipe.CortexRecipeDisplayTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record AnvilRecipeDisplay(SlotDisplay base, SlotDisplay addition, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
	public static final MapCodec<AnvilRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			SlotDisplay.CODEC.fieldOf("base").forGetter(AnvilRecipeDisplay::base),
			SlotDisplay.CODEC.fieldOf("addition").forGetter(AnvilRecipeDisplay::addition),
			SlotDisplay.CODEC.fieldOf("result").forGetter(AnvilRecipeDisplay::result),
			SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(AnvilRecipeDisplay::craftingStation)
		)
		.apply(instance, AnvilRecipeDisplay::new)
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, AnvilRecipeDisplay> PACKET_CODEC = StreamCodec.composite(
		SlotDisplay.STREAM_CODEC,
		AnvilRecipeDisplay::base,
		SlotDisplay.STREAM_CODEC,
		AnvilRecipeDisplay::addition,
		SlotDisplay.STREAM_CODEC,
		AnvilRecipeDisplay::result,
		SlotDisplay.STREAM_CODEC,
		AnvilRecipeDisplay::craftingStation,
		AnvilRecipeDisplay::new
	);

	@Override
	public RecipeDisplay.Type<AnvilRecipeDisplay> type() {
		return CortexRecipeDisplayTypes.ANVIL;
	}
}
