package com.selfdot.battlepass.quest;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import kotlin.Unit;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class EvolvePokemonQuest extends Quest {

    public EvolvePokemonQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_EVOLVE_POKEMON);
    }

    @Override
    public void registerListener() {
        CobblemonEvents.EVOLUTION_COMPLETE.subscribe(Priority.NORMAL, event -> {
            incrementActiveQuests(event.getPokemon().getOwnerPlayer());
            return Unit.INSTANCE;
        });
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(CobblemonItems.WATER_STONE);
        itemStack.setCustomName(Text.literal("Evolve Pokémon"));
        return itemStack;
    }

}
