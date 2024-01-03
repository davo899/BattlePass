package com.selfdot.battlepass.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;

import java.util.*;

public class ActiveQuest {

    private final QuestType type;
    private final int required;
    private final Map<UUID, Integer> progress = new HashMap<>();
    private final Set<UUID> completed = new HashSet<>();

    public ActiveQuest(QuestType type, int required) {
        this.type = type;
        this.required = required;
    }

    public ActiveQuest(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String questType = jsonObject.get(DataKeys.QUEST_TYPE).getAsString();
        this.type = switch (questType) {
            case DataKeys.QUEST_TYPE_BREAK_BLOCK -> QuestType.BREAK_BLOCK;
            default -> throw new IllegalStateException("Invalid quest type: " + questType);
        };
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
        jsonObject.addProperty(DataKeys.QUEST_TYPE, switch (type) {
            case BREAK_BLOCK -> DataKeys.QUEST_TYPE_BREAK_BLOCK;
        });
        jsonObject.addProperty(DataKeys.QUEST_REQUIRED, required);
        JsonObject progressJson = new JsonObject();
        progress.forEach((key, value) -> progressJson.addProperty(key.toString(), value));
        jsonObject.add(DataKeys.QUEST_PROGRESS, progressJson);
        JsonArray completedJson = new JsonArray();
        completed.forEach(uuid -> completedJson.add(uuid.toString()));
        jsonObject.add(DataKeys.QUEST_COMPLETED, completedJson);
        return jsonObject;
    }

}
