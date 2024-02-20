package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.BattlePassMod;
import kotlin.Unit;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public abstract class WinBattleQuest extends Quest {

    public WinBattleQuest(JsonObject jsonObject, String questType) {
        super(jsonObject, questType);
    }

    protected abstract boolean isValidBattle(PokemonBattle pokemonBattle);

    @Override
    public void registerListener() {
        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.NORMAL, event -> {
            if (!isValidBattle(event.getBattle())) return Unit.INSTANCE;
            event.getWinners().forEach(
                battleActor -> battleActor.getPlayerUUIDs().forEach(
                    uuid -> incrementActiveQuests(BattlePassMod.getServer().getPlayerManager().getPlayer(uuid))
                )
            );
            return Unit.INSTANCE;
        });
    }

}
