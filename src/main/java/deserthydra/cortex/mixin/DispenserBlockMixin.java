package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import deserthydra.cortex.data.CortexTags;
import deserthydra.cortex.recipe.CortexRecipeTypes;
import deserthydra.cortex.recipe.grinding.GrindstoneGrindingRecipe;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {

	@Shadow
	@Final
	private static DefaultDispenseItemBehavior DEFAULT_BEHAVIOR;

	@WrapOperation(method = "dispenseFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/DispenserBlock;getDispenseMethod(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/core/dispenser/DispenseItemBehavior;"))
	private DispenseItemBehavior addDispenserCrafting(DispenserBlock instance, Level level, ItemStack itemStack, Operation<DispenseItemBehavior> original, @Local(name = "blockEntity") DispenserBlockEntity blockEntity, @Local(name = "source") BlockSource source) {
		var facingPos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
		var facingState = level.getBlockState(facingPos);
		
		if(facingState.is(CortexTags.Blocks.GRINDSTONE_GRINDING_TARGETS)) {
			// TODO store last used recipe on dispenser BE
			var recipe = level.recipeAccess().getSynchronizedRecipes().getFirstMatch(CortexRecipeTypes.GRINDSTONE_GRINDING, new SingleRecipeInput(itemStack), level, (RecipeHolder<GrindstoneGrindingRecipe>) null)
				.filter(holder -> holder.value().allowDispenserCrafting())
				.orElse(null);
			if(recipe != null) {
				return (source1, dispensed) -> {
					var facing = source1.state().getValue(DispenserBlock.FACING);
					var dispensePosition = DispenserBlock.getDispensePosition(source1);
					var dispenseLevel = source1.level();
					var random = dispenseLevel.getRandom();

					var input = new SingleRecipeInput(dispensed);
					var remainder = dispensed.getCraftingRemainder();
					var craftingResult = recipe.value().assemble(input);

					ItemStack dispenserReturn;
					if(remainder != null) {
						dispenserReturn = DEFAULT_BEHAVIOR.consumeWithRemainder(source1, dispensed, remainder.create());
					}
					else {
						dispensed.shrink(1);
						dispenserReturn = dispensed;
					}

					DefaultDispenseItemBehavior.spawnItem(dispenseLevel, craftingResult, 6, facing, dispensePosition);

					DefaultDispenseItemBehavior.playDefaultSound(source1);
					DefaultDispenseItemBehavior.playDefaultAnimation(source1, facing);

					dispenseLevel.playSound(null, facingPos, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 0.6F + random.nextFloat() * 0.4F, 0.6F + random.nextFloat() * 0.4F);
					dispenseLevel.gameEvent(GameEvent.ITEM_INTERACT_FINISH, facingPos, GameEvent.Context.of(facingState));

					return dispenserReturn;
				};
			}
		}

		return original.call(instance, level, itemStack);
	}
}
