package com.selfdot.battlepass.mixin;

import com.selfdot.battlepass.event.SmeltedItemsCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceOutputSlot.class)
public abstract class FurnaceMixin {

    @Shadow @Final private PlayerEntity player;

    @Inject(method = "onCrafted(Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
    private void onCraftedInject(ItemStack itemStack, CallbackInfo callbackInfo) {
        if (!itemStack.isEmpty() && player instanceof ServerPlayerEntity) {
            SmeltedItemsCallback.EVENT.invoker().interact(player, itemStack);
        }
    }

}
