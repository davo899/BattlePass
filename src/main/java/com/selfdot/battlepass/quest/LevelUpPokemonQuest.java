package com.selfdot.battlepass.quest;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import kotlin.Unit;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class LevelUpPokemonQuest extends Quest {

    public LevelUpPokemonQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_LEVEL_UP_POKEMON);
    }

    @Override
    public void registerListener() {
        CobblemonEvents.LEVEL_UP_EVENT.subscribe(Priority.NORMAL, event -> {
            incrementActiveQuests(event.getPokemon().getOwnerPlayer());
            return Unit.INSTANCE;
        });
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(CobblemonItems.RARE_CANDY);
        itemStack.setCustomName(Text.literal("Level up Pokémon"));
        return itemStack;
    }

}
