package com.selfdot.battlepass.quest;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;

public class CatchSpeciesQuest extends CatchPokemonQuest {

    private final Species checkSpecies;

    public CatchSpeciesQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_CATCH_SPECIES);
        checkSpecies = PokemonSpecies.INSTANCE.getByName(jsonObject.get(DataKeys.CATCH_POKEMON_SPECIES).getAsString());
    }

    @Override
    protected boolean isValidPokemon(Pokemon pokemon) {
        return pokemon.getSpecies().equals(checkSpecies);
    }

    @Override
    protected String iconItemName() {
        return "Catch " + checkSpecies.getTranslatedName().getString();
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = super.toJson();
        jsonObject.addProperty(DataKeys.CATCH_POKEMON_SPECIES, checkSpecies.getName().toLowerCase());
        return jsonObject;
    }

}
