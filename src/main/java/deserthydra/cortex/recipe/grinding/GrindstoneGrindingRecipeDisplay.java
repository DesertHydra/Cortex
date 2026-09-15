package deserthydra.cortex.recipe.grinding;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import deserthydra.cortex.recipe.CortexRecipeDisplayTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record GrindstoneGrindingRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {

	public static final MapCodec<GrindstoneGrindingRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		SlotDisplay.CODEC.fieldOf("input").forGetter(GrindstoneGrindingRecipeDisplay::input),
		SlotDisplay.CODEC.fieldOf("result").forGetter(GrindstoneGrindingRecipeDisplay::result),
		SlotDisplay.CODEC.fieldOf("craftingStation").forGetter(GrindstoneGrindingRecipeDisplay::craftingStation)
	).apply(instance, GrindstoneGrindingRecipeDisplay::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, GrindstoneGrindingRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
		SlotDisplay.STREAM_CODEC,
		GrindstoneGrindingRecipeDisplay::input,

		SlotDisplay.STREAM_CODEC,
		GrindstoneGrindingRecipeDisplay::result,

		SlotDisplay.STREAM_CODEC,
		GrindstoneGrindingRecipeDisplay::craftingStation,

		GrindstoneGrindingRecipeDisplay::new
	);

	@Override
	public Type<? extends RecipeDisplay> type() {
		return CortexRecipeDisplayTypes.GRINDSTONE_GRINDING;
	}
}
