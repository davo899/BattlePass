package com.selfdot.battlepass.tier;

import com.google.gson.JsonElement;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.ReadOnlyJsonFile;

import java.util.*;
import java.util.stream.Collectors;

public class TiersConfig extends ReadOnlyJsonFile {

    private final Map<Integer, Tier> tierMap = new HashMap<>();

    public TiersConfig(DisableableMod mod) {
        super(mod);
    }

    public List<Map.Entry<Integer, Tier>> getTiersInOrder() {
        return tierMap.entrySet().stream()
            .sorted(Comparator.comparingInt(Map.Entry::getKey))
            .collect(Collectors.toList());
    }

    public TierProgress getTierProgress(long points) {
        long currentPoints = points;
        int tier = 0;
        for (Map.Entry<Integer, Tier> entry : getTiersInOrder()) {
            int pointsForNext = entry.getValue().getPoints();
            if (currentPoints < pointsForNext) {
                return new TierProgress(tier, currentPoints, pointsForNext);
            }
            currentPoints -= pointsForNext;
            tier = entry.getKey();
        }
        return new TierProgress(tier, points, 0);
    }

    @Override
    protected String filename() {
        return "config/" + DataKeys.BATTLE_PASS_NAMESPACE + "/tiers.json";
    }

    @Override
    protected void setDefaults() {
        tierMap.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        jsonElement.getAsJsonObject().entrySet().forEach(
            entry -> tierMap.put(Integer.parseInt(entry.getKey()), new Tier(entry.getValue()))
        );
    }

}
