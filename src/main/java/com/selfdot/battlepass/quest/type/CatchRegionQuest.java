package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.quest.PokemonRegion;

public class CatchRegionQuest extends CatchPokemonQuest {

    private final PokemonRegion checkRegion;

    public CatchRegionQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_CATCH_REGION);
        this.checkRegion = PokemonRegion.fromString(jsonObject.get(DataKeys.CATCH_POKEMON_REGION).getAsString());
    }

    @Override
    protected boolean isValidPokemon(Pokemon pokemon) {
        return PokemonRegion.fromDexNumber(pokemon.getSpecies().getNationalPokedexNumber()).equals(checkRegion);
    }

    @Override
    protected String iconItemName() {
        String regionName = PokemonRegion.toString(checkRegion);
        return "Catch Pokémon from the " +
            regionName.substring(0, 1).toUpperCase() +
            regionName.substring(1).toLowerCase() +
            " region";
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = super.toJson();
        jsonObject.addProperty(DataKeys.CATCH_POKEMON_REGION, PokemonRegion.toString(checkRegion));
        return jsonObject;
    }

}
