package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import deserthydra.cortex.item.CortexItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemGroups.class)
public abstract class ItemGroupsMixin {
	@ModifyExpressionValue(
		method = "method_51321",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/item/Items;ANCIENT_DEBRIS:Lnet/minecraft/item/Item;")
	)
	private static Item replaceAncientDebris(Item original) {
		return CortexItems.MOLTEN_DEBRIS;
	}
}
