package com.selfdot.battlepass.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.*;

public class ActiveQuest {

    private final Quest quest;
    private final int required;
    private final Map<UUID, Integer> progress = new HashMap<>();
    private final Set<UUID> completed = new HashSet<>();

    public ActiveQuest(Quest quest, int required) {
        this.quest = quest;
        this.quest.setActive(this);
        this.required = required;
    }

    public ActiveQuest(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        this.quest = Quest.fromJson(jsonObject.getAsJsonObject(DataKeys.QUEST));
        this.quest.setActive(this);
        this.required = jsonObject.get(DataKeys.QUEST_REQUIRED).getAsInt();
        jsonObject.getAsJsonObject(DataKeys.QUEST_PROGRESS).entrySet().forEach(
            entry -> progress.put(UUID.fromString(entry.getKey()), entry.getValue().getAsInt())
        );
        jsonObject.getAsJsonArray(DataKeys.QUEST_COMPLETED).forEach(
            uuidJson -> completed.add(UUID.fromString(uuidJson.getAsString()))
        );
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(DataKeys.QUEST, quest.toJson());
        jsonObject.addProperty(DataKeys.QUEST_REQUIRED, required);
        JsonObject progressJson = new JsonObject();
        progress.forEach((key, value) -> progressJson.addProperty(key.toString(), value));
        jsonObject.add(DataKeys.QUEST_PROGRESS, progressJson);
        JsonArray completedJson = new JsonArray();
        completed.forEach(uuid -> completedJson.add(uuid.toString()));
        jsonObject.add(DataKeys.QUEST_COMPLETED, completedJson);
        return jsonObject;
    }

    public void increment(PlayerEntity player) {
        UUID playerID = player.getUuid();
        if (completed.contains(playerID)) return;
        if (!progress.containsKey(playerID)) progress.put(playerID, 0);
        int next = progress.get(playerID) + 1;
        if (next >= required) {
            completed.add(playerID);
            player.sendMessage(Text.literal("Completed quest!"));
            progress.remove(playerID);
        } else {
            progress.put(playerID, next);
        }
    }

}
