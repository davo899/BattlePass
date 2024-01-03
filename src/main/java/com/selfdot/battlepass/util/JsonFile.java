package com.selfdot.battlepass.util;

import com.google.gson.*;
import com.mojang.logging.LogUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class JsonFile extends ReadOnlyJsonFile {

    protected abstract JsonElement toJson();

    public JsonFile(DisableableMod mod) {
        super(mod);
    }

    public void save() {
        try {
            Files.createDirectories(Paths.get(filename()).getParent());
            FileWriter writer = new FileWriter(filename());
            GSON.toJson(toJson(), writer);
            writer.close();

        } catch (IOException e) {
            mod.getLogger().error("Unable to store to " + filename());
        }
    }

}
