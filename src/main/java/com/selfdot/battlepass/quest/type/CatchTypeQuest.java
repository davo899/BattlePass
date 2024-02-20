package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;

public class CatchTypeQuest extends CatchPokemonQuest {

    private final ElementalType checkType;

    public CatchTypeQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_CATCH_TYPE);
        this.checkType = ElementalTypes.INSTANCE.get(jsonObject.get(DataKeys.CATCH_POKEMON_TYPE).getAsString());
    }

    @Override
    protected boolean isValidPokemon(Pokemon pokemon) {
        for (ElementalType type : pokemon.getTypes()) {
            if (type.equals(checkType)) return true;
        }
        return false;
    }

    @Override
    protected String iconItemName() {
        return "Catch " + checkType.getDisplayName().getString() + " type Pokémon";
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = super.toJson();
        jsonObject.addProperty(DataKeys.CATCH_POKEMON_TYPE, checkType.getName());
        return jsonObject;
    }

}
