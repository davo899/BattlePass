package com.selfdot.battlepass.quest.type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.quest.ActiveQuest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public abstract class Quest {

    private final Set<ActiveQuest> activeQuests = new HashSet<>();
    private final String questType;
    private final int points;
    private final int required;

    public Quest(JsonObject jsonObject, String questType) {
        this.questType = questType;
        this.points = jsonObject.get(DataKeys.QUEST_POINTS).getAsInt();
        this.required = jsonObject.get(DataKeys.QUEST_REQUIRED).getAsInt();
        registerListener();
    }

    public abstract void registerListener();

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

    protected void incrementActiveQuests(PlayerEntity player, int amount) {
        activeQuests.forEach(activeQuest -> activeQuest.increment(player, amount));
    }

    protected void incrementActiveQuests(PlayerEntity player) {
        incrementActiveQuests(player, 1);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DataKeys.QUEST_TYPE, questType);
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
            case DataKeys.QUEST_TYPE_LEVEL_UP_POKEMON -> new LevelUpPokemonQuest(jsonObject);
            case DataKeys.QUEST_TYPE_EVOLVE_POKEMON -> new EvolvePokemonQuest(jsonObject);
            case DataKeys.QUEST_TYPE_WIN_WILD_BATTLE -> new WinWildBattleQuest(jsonObject);
            case DataKeys.QUEST_TYPE_WIN_PLAYER_BATTLE -> new WinPlayerBattleQuest(jsonObject);
            case DataKeys.QUEST_TYPE_WIN_NPC_BATTLE -> new WinNPCBattleQuest(jsonObject);
            case DataKeys.QUEST_TYPE_FISH -> new FishingQuest(jsonObject);
            case DataKeys.QUEST_TYPE_HARVEST_ANY_BERRIES -> new HarvestAnyBerriesQuest(jsonObject);
            case DataKeys.QUEST_TYPE_HARVEST_SPECIFIC_BERRIES -> new HarvestSpecificBerriesQuest(jsonObject);
            case DataKeys.QUEST_TYPE_SMELT_ANY_ITEM -> new SmeltAnyItemQuest(jsonObject);
            case DataKeys.QUEST_TYPE_SMELT_SPECIFIC_ITEM -> new SmeltSpecificItemQuest(jsonObject);
            case DataKeys.QUEST_TYPE_CRAFT_SPECIFIC_ITEM -> new CraftSpecificItemQuest(jsonObject);
            default -> throw new IllegalStateException("Invalid quest type: " + questType);
        };
    }

    public abstract ItemStack getIconItem();

}
