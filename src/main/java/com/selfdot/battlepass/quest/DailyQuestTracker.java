package com.selfdot.battlepass.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.JsonFile;

import java.util.ArrayList;
import java.util.List;

public class DailyQuestTracker extends JsonFile {

    private final List<ActiveQuest> activeQuests = new ArrayList<>();

    public DailyQuestTracker(DisableableMod mod) {
        super(mod);
    }

    @Override
    protected JsonElement toJson() {
        JsonArray jsonArray = new JsonArray();
        activeQuests.forEach(activeQuest -> jsonArray.add(activeQuest.toJson()));
        return jsonArray;
    }

    @Override
    protected String filename() {
        return DataKeys.BATTLE_PASS_NAMESPACE + "/activeDailyQuests.json";
    }

    @Override
    protected void setDefaults() {
        activeQuests.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        jsonArray.forEach(questJson -> activeQuests.add(new ActiveQuest(questJson)));
    }

}
