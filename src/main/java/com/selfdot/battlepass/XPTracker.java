package com.selfdot.battlepass;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.JsonFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class XPTracker extends JsonFile {

    private final Map<UUID, Long> xpMap = new HashMap<>();

    public XPTracker(DisableableMod mod) {
        super(mod);
    }

    @Override
    protected String filename() {
        return "battlePass/xp.json";
    }

    @Override
    protected void setDefaults() {
        xpMap.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        jsonElement.getAsJsonObject().entrySet().forEach(
            entry -> xpMap.put(UUID.fromString(entry.getKey()), entry.getValue().getAsLong())
        );
    }

    @Override
    protected JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        xpMap.forEach((key, value) -> jsonObject.addProperty(key.toString(), value));
        return jsonObject;
    }

}
