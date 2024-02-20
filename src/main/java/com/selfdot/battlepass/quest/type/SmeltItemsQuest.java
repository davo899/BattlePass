package com.selfdot.battlepass.quest.type;

import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.event.SmeltedItemsCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class SmeltItemsQuest extends Quest {

    public SmeltItemsQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_SMELT_ITEMS);
    }

    @Override
    public void registerListener() {
        SmeltedItemsCallback.EVENT.register((player, count) -> {
            incrementActiveQuests(player, count);
            return ActionResult.PASS;
        });
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(Items.FURNACE);
        itemStack.setCustomName(Text.literal("Smelt items"));
        return itemStack;
    }

}
