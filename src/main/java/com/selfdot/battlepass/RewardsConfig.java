package com.selfdot.battlepass;

import com.google.gson.JsonElement;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.ReadOnlyJsonFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RewardsConfig extends ReadOnlyJsonFile {

    private final Map<String, List<String>> rewards = new HashMap<>();

    public RewardsConfig(DisableableMod mod) {
        super(mod);
    }

    @Override
    protected String filename() {
        return "config/" + DataKeys.BATTLE_PASS_NAMESPACE + "/rewards.json";
    }

    @Override
    protected void setDefaults() {
        rewards.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        jsonElement.getAsJsonObject().entrySet().forEach(entry -> rewards.put(entry.getKey(),
            entry.getValue().getAsJsonArray().asList().stream()
                .map(JsonElement::getAsString).collect(Collectors.toList())
        ));
    }

}
