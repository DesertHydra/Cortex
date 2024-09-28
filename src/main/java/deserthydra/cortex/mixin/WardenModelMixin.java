package deserthydra.cortex.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.WardenModel;
import net.minecraft.entity.mob.warden.WardenEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenModel.class)
public abstract class WardenModelMixin<T extends WardenEntity> {
	@Shadow
	@Final
	@Mutable
	protected ModelPart leftTendril;

	@Shadow
	@Final
	@Mutable
	protected ModelPart rightTendril;

	@Inject(method = "animateTendrils", at = @At("HEAD"))
	private void storeTendrilPitch(T wardenEntity, float animationProgress, float delta, CallbackInfo ci, @Share("left") LocalFloatRef leftPitch, @Share("right") LocalFloatRef rightPitch) {
		leftPitch.set(this.leftTendril.pitch);
		rightPitch.set(this.rightTendril.pitch);
	}

	@Inject(method = "animateTendrils", at = @At("TAIL"))
	private void addTendrilPitch(T wardenEntity, float animationProgress, float delta, CallbackInfo ci, @Share("left") LocalFloatRef leftPitch, @Share("right") LocalFloatRef rightPitch) {
		this.leftTendril.pitch += leftPitch.get();
		this.rightTendril.pitch += rightPitch.get();
	}

	@Inject(method = "createBodyLayer", at = @At("TAIL"))
	private static void addEasyParts(CallbackInfoReturnable<TexturedModelData> cir, @Local(ordinal = 2) ModelPartData bodyModelPart, @Local(ordinal = 3) ModelPartData headPartData) {
		bodyModelPart.addChild(
			"upper_body",
			ModelPartBuilder.create(),
			ModelTransform.NONE
		);
		headPartData.addChild(
			"neck",
			ModelPartBuilder.create(),
			ModelTransform.NONE
		);
	}

	@ModifyReceiver(
		method = "createBodyLayer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/ModelPartData;addChild(Ljava/lang/String;Lnet/minecraft/client/model/ModelPartBuilder;Lnet/minecraft/client/model/ModelTransform;)Lnet/minecraft/client/model/ModelPartData;",
			ordinal = 8
		)
	)
	private static ModelPartData addRightShoulderPart(ModelPartData instance, String name, ModelPartBuilder builder, ModelTransform rotationData, @Local(ordinal = 2) ModelPartData bodyModelPart, @Local(ordinal = 3) ModelPartData headPartData) {
		instance.addChild(
			"right_shoulder",
			ModelPartBuilder.create(),
			ModelTransform.NONE
		);
		return instance;
	}

	@ModifyReceiver(
		method = "createBodyLayer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/ModelPartData;addChild(Ljava/lang/String;Lnet/minecraft/client/model/ModelPartBuilder;Lnet/minecraft/client/model/ModelTransform;)Lnet/minecraft/client/model/ModelPartData;",
			ordinal = 9
		)
	)
	private static ModelPartData addLeftShoulderPart(ModelPartData instance, String name, ModelPartBuilder builder, ModelTransform rotationData, @Local(ordinal = 2) ModelPartData bodyModelPart, @Local(ordinal = 3) ModelPartData headPartData) {
		instance.addChild(
			"left_shoulder",
			ModelPartBuilder.create(),
			ModelTransform.NONE
		);
		return instance;
	}
}
