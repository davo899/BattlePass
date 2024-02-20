package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.berry.Berry;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class HarvestAnyBerriesQuest extends HarvestBerriesQuest {

    public HarvestAnyBerriesQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_HARVEST_ANY_BERRIES);
    }

    @Override
    protected boolean isValidBerry(Berry berry) {
        return true;
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(CobblemonItems.ORAN_BERRY);
        itemStack.setCustomName(Text.literal("Harvest berries"));
        return itemStack;
    }

}
