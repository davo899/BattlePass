package com.selfdot.battlepass.quest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public abstract class Quest {

    private ActiveQuest active;
    private final int points;

    public Quest(int points) {
        this.points = points;
    }

    public Quest(JsonObject jsonObject) {
        this.points = jsonObject.get(DataKeys.QUEST_POINTS).getAsInt();
    }

    public int getPoints() {
        return points;
    }

    public void setActive(ActiveQuest active) {
        this.active = active;
        startListening();
    }

    protected abstract void startListening();

    protected void incrementActive(PlayerEntity player) {
        active.increment(player);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DataKeys.QUEST_POINTS, points);
        return jsonObject;
    }

    public static Quest fromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String questType = jsonObject.get(DataKeys.QUEST_TYPE).getAsString();
        return switch (questType) {
            case DataKeys.QUEST_TYPE_BREAK_BLOCK -> new BlockBreakQuest(jsonObject);
            default -> throw new IllegalStateException("Invalid quest type: " + questType);
        };
    }

    public abstract ItemStack getIconItem();

}
