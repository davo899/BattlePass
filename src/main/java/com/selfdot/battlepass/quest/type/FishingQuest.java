package com.selfdot.battlepass.quest.type;

import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.event.FishedSuccessfullyCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class FishingQuest extends Quest {

    public FishingQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_FISH);
    }

    @Override
    public void registerListener() {
        FishedSuccessfullyCallback.EVENT.register(player -> {
            incrementActiveQuests(player);
            return ActionResult.PASS;
        });
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(Items.FISHING_ROD);
        itemStack.setCustomName(Text.literal("Go fishing"));
        return itemStack;
    }

}
