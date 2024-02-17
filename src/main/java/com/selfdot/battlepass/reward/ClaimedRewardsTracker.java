package com.selfdot.battlepass.reward;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.JsonFile;
import net.minecraft.entity.player.PlayerEntity;

import java.util.*;

public class ClaimedRewardsTracker extends JsonFile {

    private final Map<UUID, Set<Integer>> claimedRewardsMap = new HashMap<>();

    public ClaimedRewardsTracker(DisableableMod mod) {
        super(mod);
    }

    public void setClaimed(PlayerEntity player, int tier) {
        if (!claimedRewardsMap.containsKey(player.getUuid())) claimedRewardsMap.put(player.getUuid(), new HashSet<>());
        claimedRewardsMap.get(player.getUuid()).add(tier);
        save();
    }

    public boolean hasClaimed(PlayerEntity player, int tier) {
        if (!claimedRewardsMap.containsKey(player.getUuid())) return false;
        return claimedRewardsMap.get(player.getUuid()).contains(tier);
    }

    @Override
    protected JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        claimedRewardsMap.forEach((playerID, claimedTiers) -> {
            JsonArray claimedTiersJson = new JsonArray();
            claimedTiers.forEach(claimedTiersJson::add);
            jsonObject.add(playerID.toString(), claimedTiersJson);
        });
        return jsonObject;
    }

    @Override
    protected String filename() {
        return DataKeys.BATTLE_PASS_NAMESPACE + "/claimedRewards.json";
    }

    @Override
    protected void setDefaults() {
        claimedRewardsMap.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.entrySet().forEach(entry -> {
            Set<Integer> claimedTiers = new HashSet<>();
            entry.getValue().getAsJsonArray().forEach(tierJson -> claimedTiers.add(tierJson.getAsInt()));
            claimedRewardsMap.put(UUID.fromString(entry.getKey()), claimedTiers);
        });
    }

}
