package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import kotlin.Unit;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public abstract class CatchPokemonQuest extends Quest {

    public CatchPokemonQuest(JsonObject jsonObject, String questType) {
        super(jsonObject, questType);
    }

    protected abstract boolean isValidPokemon(Pokemon pokemon);
    protected abstract String iconItemName();

    @Override
    public void registerListener() {
        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, event -> {
            if (isValidPokemon(event.getPokemon())) incrementActiveQuests(event.getPlayer());
            return Unit.INSTANCE;
        });
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(CobblemonItems.POKE_BALL);
        itemStack.setCustomName(Text.literal(iconItemName()));
        return itemStack;
    }

}
