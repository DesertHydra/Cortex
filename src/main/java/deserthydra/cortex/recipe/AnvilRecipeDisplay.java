package deserthydra.cortex.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;

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

	public static final PacketCodec<RegistryByteBuf, AnvilRecipeDisplay> PACKET_CODEC = PacketCodec.tuple(
		SlotDisplay.PACKET_CODEC,
		AnvilRecipeDisplay::base,
		SlotDisplay.PACKET_CODEC,
		AnvilRecipeDisplay::addition,
		SlotDisplay.PACKET_CODEC,
		AnvilRecipeDisplay::result,
		SlotDisplay.PACKET_CODEC,
		AnvilRecipeDisplay::craftingStation,
		AnvilRecipeDisplay::new
	);

	public static final RecipeDisplay.Type<AnvilRecipeDisplay> TYPE = new RecipeDisplay.Type<>(CODEC, PACKET_CODEC);

	@Override
	public RecipeDisplay.Type<AnvilRecipeDisplay> type() {
		return TYPE;
	}
}
