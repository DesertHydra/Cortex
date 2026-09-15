package deserthydra.cortex.recipe.grinding;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import deserthydra.cortex.recipe.CortexRecipeBookCategories;
import deserthydra.cortex.recipe.CortexRecipeSerializers;
import deserthydra.cortex.recipe.CortexRecipeTypes;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

import java.util.List;

public class GrindstoneGrindingRecipe extends SingleItemRecipe {

	private final boolean allowDispenser;

	public GrindstoneGrindingRecipe(CommonInfo commonInfo, Ingredient input, ItemStackTemplate result, boolean allowDispenser) {
		super(commonInfo, input, result);
		this.allowDispenser = allowDispenser;
	}

	@Override
	public String group() {
		return "";
	}

	@Override
	public RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
		return CortexRecipeSerializers.GRINDSTONE_GRINDING;
	}

	@Override
	public RecipeType<? extends SingleItemRecipe> getType() {
		return CortexRecipeTypes.GRINDSTONE_GRINDING;
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return CortexRecipeBookCategories.GRINDSTONE_GRINDING;
	}

	@Override
	public List<RecipeDisplay> display() {
		return List.of(new GrindstoneGrindingRecipeDisplay(this.input().display(), new SlotDisplay.ItemStackSlotDisplay(this.result()), new SlotDisplay.ItemSlotDisplay(Items.GRINDSTONE)));
	}

	/// @return whether to allow crafting via [DispenseItemBehavior] automation
	public boolean allowDispenserCrafting() {
		return allowDispenser;
	}

	public static final MapCodec<GrindstoneGrindingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		CommonInfo.MAP_CODEC.forGetter(r -> r.commonInfo),
		Ingredient.CODEC.fieldOf("ingredient").forGetter(GrindstoneGrindingRecipe::input),
		ItemStackTemplate.CODEC.fieldOf("result").forGetter(GrindstoneGrindingRecipe::result),
		Codec.BOOL.fieldOf("allow_dispenser_crafting").forGetter(GrindstoneGrindingRecipe::allowDispenserCrafting)
	).apply(instance, GrindstoneGrindingRecipe::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, GrindstoneGrindingRecipe> STREAM_CODEC = StreamCodec.composite(
		CommonInfo.STREAM_CODEC,
		r -> r.commonInfo,

		Ingredient.CONTENTS_STREAM_CODEC,
		GrindstoneGrindingRecipe::input,

		ItemStackTemplate.STREAM_CODEC,
		GrindstoneGrindingRecipe::result,

		ByteBufCodecs.BOOL,
		GrindstoneGrindingRecipe::allowDispenserCrafting,

		GrindstoneGrindingRecipe::new
	);
}
