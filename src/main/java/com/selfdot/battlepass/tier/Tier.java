package com.selfdot.battlepass.tier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;

import java.util.List;

public class Tier {

    private final int points;
    private final List<String> rewards;

    public Tier(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        this.points = jsonObject.get(DataKeys.TIER_POINTS).getAsInt();
        JsonArray rewardsJson = jsonObject.getAsJsonArray(DataKeys.TIER_REWARDS);
        this.rewards = rewardsJson.asList().stream().map(JsonElement::getAsString).toList();
    }

}
