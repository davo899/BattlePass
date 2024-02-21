package com.selfdot.battlepass.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface CraftedItemsCallback {

    Event<CraftedItemsCallback> EVENT = EventFactory.createArrayBacked(CraftedItemsCallback.class,
        (listeners) -> (player, outputItem) -> {
            for (CraftedItemsCallback listener : listeners) {
                ActionResult result = listener.interact(player, outputItem);

                if(result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    ActionResult interact(PlayerEntity player, ItemStack outputItem);

}
