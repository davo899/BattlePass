package com.selfdot.battlepass.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.BattlePassMod;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.util.ScreenUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
            BattlePassMod.getInstance().getPointsTracker().addPoints(quest.getPoints(), playerID);
            player.sendMessage(Text.literal("Quest complete! +" + quest.getPoints() + " points"));
            progress.remove(playerID);
        } else {
            progress.put(playerID, next);
        }
    }

    public ItemStack getProgressItem(UUID playerID) {
        ItemStack itemStack = quest.getIconItem();

        Text progressText;
        if (completed.contains(playerID)) {
            progressText = Text.literal(Formatting.GREEN + "Completed!");
        } else {
            int playerProgress = progress.getOrDefault(playerID, 0);
            progressText = Text.literal(Formatting.WHITE + String.valueOf(playerProgress) + "/" + required);
        }

        ScreenUtils.addLore(itemStack, new Text[]{
            progressText,
            Text.literal(Formatting.GREEN + String.valueOf(quest.getPoints()) + " points")
        });
        return itemStack;
    }

}
