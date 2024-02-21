package com.selfdot.battlepass.quest.type;

import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.event.SmeltedItemsCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class SmeltAnyItemQuest extends Quest {

    public SmeltAnyItemQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_SMELT_ANY_ITEM);
    }

    @Override
    public void registerListener() {
        SmeltedItemsCallback.EVENT.register((player, outputItem) -> {
            incrementActiveQuests(player, outputItem.getCount());
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
