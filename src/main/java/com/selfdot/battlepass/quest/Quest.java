package com.selfdot.battlepass.quest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.entity.player.PlayerEntity;

public abstract class Quest {

    private ActiveQuest active;

    public void setActive(ActiveQuest active) {
        this.active = active;
        startListening();
    }

    protected abstract void startListening();

    protected void incrementActive(PlayerEntity player) {
        active.increment(player);
    }

    public abstract JsonObject toJson();

    public static Quest fromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String questType = jsonObject.get(DataKeys.QUEST_TYPE).getAsString();
        return switch (questType) {
            case DataKeys.QUEST_TYPE_BREAK_BLOCK -> new BlockBreakQuest(jsonObject);
            default -> throw new IllegalStateException("Invalid quest type: " + questType);
        };
    }

}
