package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class WinNPCBattleQuest extends WinBattleQuest {

    public WinNPCBattleQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_WIN_NPC_BATTLE);
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(CobblemonItems.BLACK_GLASSES);
        itemStack.setCustomName(Text.literal("Win NPC battles"));
        return itemStack;
    }

    @Override
    protected boolean isValidBattle(PokemonBattle pokemonBattle) {
        return pokemonBattle.isPvN();
    }

}
