package com.selfdot.battlepass.quest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public abstract class Quest {

    private final Set<ActiveQuest> activeQuests = new HashSet<>();
    private final int points;
    private final int required;

    public Quest(JsonObject jsonObject) {
        this.points = jsonObject.get(DataKeys.QUEST_POINTS).getAsInt();
        this.required = jsonObject.get(DataKeys.QUEST_REQUIRED).getAsInt();
    }

    public int getPoints() {
        return points;
    }

    public int getRequired() {
        return required;
    }

    public void addActiveQuest(ActiveQuest activeQuest) {
        activeQuests.add(activeQuest);
    }

    public void removeActiveQuest(ActiveQuest activeQuest) {
        activeQuests.remove(activeQuest);
    }

    protected void incrementActiveQuests(PlayerEntity player) {
        activeQuests.forEach(activeQuest -> activeQuest.increment(player));
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DataKeys.QUEST_POINTS, points);
        jsonObject.addProperty(DataKeys.QUEST_REQUIRED, required);
        return jsonObject;
    }

    public static Quest fromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String questType = jsonObject.get(DataKeys.QUEST_TYPE).getAsString();
        return switch (questType) {
            case DataKeys.QUEST_TYPE_BREAK_BLOCK -> new BlockBreakQuest(jsonObject);
            case DataKeys.QUEST_TYPE_CATCH_ANY_POKEMON -> new CatchAnyPokemonQuest(jsonObject);
            case DataKeys.QUEST_TYPE_CATCH_SPECIES -> new CatchSpeciesQuest(jsonObject);
            case DataKeys.QUEST_TYPE_CATCH_TYPE -> new CatchTypeQuest(jsonObject);
            case DataKeys.QUEST_TYPE_CATCH_REGION -> new CatchRegionQuest(jsonObject);
            default -> throw new IllegalStateException("Invalid quest type: " + questType);
        };
    }

    public abstract ItemStack getIconItem();

}
