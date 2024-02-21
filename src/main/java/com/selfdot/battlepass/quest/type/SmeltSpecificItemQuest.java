package com.selfdot.battlepass.quest.type;

import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.event.SmeltedItemsCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class SmeltSpecificItemQuest extends Quest {

    private final Identifier checkItemId;

    public SmeltSpecificItemQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_SMELT_SPECIFIC_ITEM);
        checkItemId = new Identifier(jsonObject.get(DataKeys.SMELT_ITEM_OUTPUT_ITEM).getAsString());
        if (Registries.ITEM.getOrEmpty(checkItemId).isEmpty()) {
            throw new IllegalStateException("Invalid item: " + checkItemId);
        }
    }

    @Override
    public void registerListener() {
        SmeltedItemsCallback.EVENT.register(((player, outputItem) -> {
            if (Registries.ITEM.getId(outputItem.getItem()).equals(checkItemId)) {
                incrementActiveQuests(player, outputItem.getCount());
            }
            return ActionResult.PASS;
        }));
    }

    @Override
    public ItemStack getIconItem() {
        Item item = Registries.ITEM.get(checkItemId);
        ItemStack itemStack = new ItemStack(item);
        itemStack.setCustomName(Text.literal("Smelt " + item.getName().getString()));
        return itemStack;
    }

}
