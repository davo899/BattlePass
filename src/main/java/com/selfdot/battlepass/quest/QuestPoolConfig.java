package com.selfdot.battlepass.quest;

import com.google.gson.JsonElement;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.quest.type.Quest;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.ReadOnlyJsonFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestPoolConfig extends ReadOnlyJsonFile {

    private static final Random RANDOM = new Random();

    private final List<Quest> questPool = new ArrayList<>();

    public Quest getRandomQuest() {
        if (questPool.isEmpty()) return null;
        return questPool.get(RANDOM.nextInt(questPool.size()));
    }

    public QuestPoolConfig(DisableableMod mod) {
        super(mod);
    }

    @Override
    protected String filename() {
        return "config/" + DataKeys.BATTLE_PASS_NAMESPACE + "/quests.json";
    }

    @Override
    protected void setDefaults() {
        questPool.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        jsonElement.getAsJsonArray().forEach(questJson -> questPool.add(Quest.fromJson(questJson)));
    }

    @Override
    public void load() {
        super.load();
        if (questPool.isEmpty()) {
            mod.getLogger().error("Battle Pass has been disabled: Quest pool empty");
            mod.disable();
        }
    }

}
