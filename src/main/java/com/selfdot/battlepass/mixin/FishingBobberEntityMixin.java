package com.selfdot.battlepass.mixin;

import com.selfdot.battlepass.event.FishedSuccessfullyCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {

    @Shadow @Nullable public abstract PlayerEntity getPlayerOwner();

    @Inject(method = "use", at = @At("RETURN"))
    private void fishEventInject(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValueI() == 1) FishedSuccessfullyCallback.EVENT.invoker().interact(getPlayerOwner());
    }

}
