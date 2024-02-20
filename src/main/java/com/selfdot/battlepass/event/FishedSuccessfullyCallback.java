package com.selfdot.battlepass.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface FishedSuccessfullyCallback {

    Event<FishedSuccessfullyCallback> EVENT = EventFactory.createArrayBacked(FishedSuccessfullyCallback.class,
        (listeners) -> (player) -> {
            for (FishedSuccessfullyCallback listener : listeners) {
                ActionResult result = listener.interact(player);

                if(result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    ActionResult interact(PlayerEntity player);

}
