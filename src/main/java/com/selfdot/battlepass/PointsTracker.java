package com.selfdot.battlepass;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.JsonFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PointsTracker extends JsonFile {

    private final Map<UUID, Long> pointsMap = new HashMap<>();

    public PointsTracker(DisableableMod mod) {
        super(mod);
    }

    public void addPoints(int points, UUID playerID) {
        pointsMap.put(playerID, pointsMap.containsKey(playerID) ? pointsMap.get(playerID) + points : points);
    }

    @Override
    protected String filename() {
        return DataKeys.BATTLE_PASS_NAMESPACE + "/points.json";
    }

    @Override
    protected void setDefaults() {
        pointsMap.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        jsonElement.getAsJsonObject().entrySet().forEach(
            entry -> pointsMap.put(UUID.fromString(entry.getKey()), entry.getValue().getAsLong())
        );
    }

    @Override
    protected JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        pointsMap.forEach((key, value) -> jsonObject.addProperty(key.toString(), value));
        return jsonObject;
    }

}
