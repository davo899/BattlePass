package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class WinWildBattleQuest extends WinBattleQuest {

    public WinWildBattleQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_WIN_WILD_BATTLE);
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(Items.TALL_GRASS);
        itemStack.setCustomName(Text.literal("Defeat Wild Pokémon"));
        return itemStack;
    }

    @Override
    protected boolean isValidBattle(PokemonBattle pokemonBattle) {
        return pokemonBattle.isPvW();
    }

}
