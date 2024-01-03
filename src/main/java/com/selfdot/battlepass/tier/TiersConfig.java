package com.selfdot.battlepass.tier;

import com.google.gson.JsonElement;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.ReadOnlyJsonFile;

import java.util.HashMap;
import java.util.Map;

public class TiersConfig extends ReadOnlyJsonFile {

    private final Map<Integer, Tier> tierMap = new HashMap<>();

    public TiersConfig(DisableableMod mod) {
        super(mod);
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
