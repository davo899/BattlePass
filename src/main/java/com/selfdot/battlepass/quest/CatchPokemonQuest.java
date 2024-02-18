package com.selfdot.battlepass.quest;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import kotlin.Unit;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public abstract class CatchPokemonQuest extends Quest {

    private final String questType;

    public CatchPokemonQuest(JsonObject jsonObject, String questType) {
        super(jsonObject);
        this.questType = questType;
        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, event -> {
            if (isValidPokemon(event.getPokemon())) incrementActiveQuests(event.getPlayer());
            return Unit.INSTANCE;
        });
    }

    protected abstract boolean isValidPokemon(Pokemon pokemon);
    protected abstract String iconItemName();

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = super.toJson();
        jsonObject.addProperty(DataKeys.QUEST_TYPE, questType);
        return jsonObject;
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(CobblemonItems.POKE_BALL);
        itemStack.setCustomName(Text.literal(iconItemName()));
        return itemStack;
    }

}
