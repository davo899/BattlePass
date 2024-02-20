package com.selfdot.battlepass.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface SmeltedItemsCallback {

    Event<SmeltedItemsCallback> EVENT = EventFactory.createArrayBacked(SmeltedItemsCallback.class,
        (listeners) -> (player, count) -> {
            for (SmeltedItemsCallback listener : listeners) {
                ActionResult result = listener.interact(player, count);

                if(result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    ActionResult interact(PlayerEntity player, int count);

}
