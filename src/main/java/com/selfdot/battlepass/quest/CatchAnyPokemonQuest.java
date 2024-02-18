package com.selfdot.battlepass.quest;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;

public class CatchAnyPokemonQuest extends CatchPokemonQuest {

    public CatchAnyPokemonQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_CATCH_ANY_POKEMON);
    }

    @Override
    protected boolean isValidPokemon(Pokemon pokemon) {
        return true;
    }

    @Override
    protected String iconItemName() {
        return "Catch Pokémon";
    }

}
