package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import deserthydra.cortex.item.CortexItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreativeModeTabs.class)
public abstract class CreativeModeTabsMixin {

	private CreativeModeTabsMixin() {
		throw new UnsupportedOperationException();
	}

	@ModifyExpressionValue(
		method = "lambda$bootstrap$27",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.GETSTATIC,
			target = "Lnet/minecraft/world/item/Items;ANCIENT_DEBRIS:Lnet/minecraft/world/item/Item;")
	)
	private static Item replaceAncientDebris(Item original) {
		return CortexItems.MOLTEN_DEBRIS;
	}
}
