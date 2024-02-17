package com.selfdot.battlepass.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.BattlePassMod;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.JsonFile;
import net.minecraft.item.ItemStack;

import java.util.*;

public class DailyQuestTracker extends JsonFile {

    private static final int DAILY_QUEST_COUNT = 10;

    private long expiry;
    private final List<ActiveQuest> activeQuests = new ArrayList<>();

    public DailyQuestTracker(DisableableMod mod) {
        super(mod);
    }

    private void reroll() {
        activeQuests.forEach(ActiveQuest::remove);
        activeQuests.clear();
        for (int i = 0; i < DAILY_QUEST_COUNT; i++) {
            activeQuests.add(new ActiveQuest(BattlePassMod.getInstance().getQuestPoolConfig().getRandomQuest()));
        }
    }

    public void onTick() {
        if (System.currentTimeMillis() > expiry) {
            Calendar date = new GregorianCalendar();
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);
            date.add(Calendar.DAY_OF_MONTH, 1);
            expiry = date.getTimeInMillis();
            reroll();
        }
    }

    public List<ItemStack> getProgressItems(UUID playerID) {
        return activeQuests.stream().map(activeQuest -> activeQuest.getProgressItem(playerID)).toList();
    }

    @Override
    protected JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DataKeys.QUEST_TRACKER_EXPIRY, expiry);
        JsonArray questsJson = new JsonArray();
        activeQuests.forEach(activeQuest -> questsJson.add(activeQuest.toJson()));
        jsonObject.add(DataKeys.QUEST_TRACKER_QUESTS, questsJson);
        return jsonObject;
    }

    @Override
    protected String filename() {
        return DataKeys.BATTLE_PASS_NAMESPACE + "/activeDailyQuests.json";
    }

    @Override
    protected void setDefaults() {
        expiry = 0L;
        activeQuests.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        expiry = jsonObject.get(DataKeys.QUEST_TRACKER_EXPIRY).getAsLong();
        JsonArray jsonArray = jsonObject.getAsJsonArray(DataKeys.QUEST_TRACKER_QUESTS);
        jsonArray.forEach(questJson -> activeQuests.add(new ActiveQuest(questJson)));
    }

}
