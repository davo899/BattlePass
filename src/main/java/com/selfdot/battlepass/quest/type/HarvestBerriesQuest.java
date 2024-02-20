package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.berry.Berry;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.google.gson.JsonObject;
import kotlin.Unit;

public abstract class HarvestBerriesQuest extends Quest {

    public HarvestBerriesQuest(JsonObject jsonObject, String questType) {
        super(jsonObject, questType);
    }

    protected abstract boolean isValidBerry(Berry berry);

    @Override
    public void registerListener() {
        CobblemonEvents.BERRY_HARVEST.subscribe(Priority.NORMAL, event -> {
            if (isValidBerry(event.component1())) incrementActiveQuests(event.getPlayer());
            return Unit.INSTANCE;
        });
    }

}
