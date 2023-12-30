package com.selfdot.battlepass.util;

import com.google.gson.*;
import com.mojang.logging.LogUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ReadOnlyJsonFile {

    protected abstract String filename();
    protected abstract void setDefaults();
    protected abstract void loadFromJson(JsonElement jsonElement);

    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final DisableableMod mod;

    public ReadOnlyJsonFile(DisableableMod mod) {
        this.mod = mod;
    }

    public void load() {
        setDefaults();
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(filename()));
            try {
                loadFromJson(jsonElement);
                LogUtils.getLogger().info(filename() + " loaded");

            } catch (Exception e) {
                mod.disable();
                LogUtils.getLogger().error("An exception occurred when loading " + filename() + ":");
                LogUtils.getLogger().error(e.getMessage());
            }

        } catch (FileNotFoundException e) {
            LogUtils.getLogger().warn(filename() + " not found, attempting to generate");
            try {
                Files.createDirectories(Paths.get(filename()).getParent());
                FileWriter writer = new FileWriter(filename());
                GSON.toJson(new JsonObject(), writer);
                writer.close();

            } catch (IOException ex) {
                mod.disable();
                LogUtils.getLogger().error("Unable to generate " + filename());
            }
        }
    }

}
