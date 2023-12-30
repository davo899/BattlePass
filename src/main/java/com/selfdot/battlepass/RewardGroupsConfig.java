package com.selfdot.battlepass;

import com.google.gson.JsonElement;
import com.selfdot.battlepass.util.DisableableMod;
import com.selfdot.battlepass.util.ReadOnlyJsonFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RewardGroupsConfig extends ReadOnlyJsonFile {

    private final Map<String, List<String>> rewardGroups = new HashMap<>();

    public RewardGroupsConfig(DisableableMod mod) {
        super(mod);
    }

    @Override
    protected String filename() {
        return "config/battlePass/rewardGroups.json";
    }

    @Override
    protected void setDefaults() {
        rewardGroups.clear();
    }

    @Override
    protected void loadFromJson(JsonElement jsonElement) {
        jsonElement.getAsJsonObject().entrySet().forEach(entry -> rewardGroups.put(entry.getKey(),
            entry.getValue().getAsJsonArray().asList().stream()
                .map(JsonElement::getAsString).collect(Collectors.toList())
        ));
    }

}
