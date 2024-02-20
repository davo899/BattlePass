package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class WinPlayerBattleQuest extends WinBattleQuest {

    public WinPlayerBattleQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_WIN_PLAYER_BATTLE);
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(CobblemonItems.LIFE_ORB);
        itemStack.setCustomName(Text.literal("Win player battles"));
        return itemStack;
    }

    @Override
    protected boolean isValidBattle(PokemonBattle pokemonBattle) {
        return pokemonBattle.isPvP();
    }

}
