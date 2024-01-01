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

    @Override
    protected String filename() {
        return "battlePass/xp.json";
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
