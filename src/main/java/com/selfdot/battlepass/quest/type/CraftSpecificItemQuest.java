package com.selfdot.battlepass.quest.type;

import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.event.CraftedItemsCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class CraftSpecificItemQuest extends Quest {

    private final Identifier checkItemId;

    public CraftSpecificItemQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_CRAFT_SPECIFIC_ITEM);
        checkItemId = new Identifier(jsonObject.get(DataKeys.SMELT_ITEM_OUTPUT_ITEM).getAsString());
        if (Registries.ITEM.getOrEmpty(checkItemId).isEmpty()) {
            throw new IllegalStateException("Invalid item: " + checkItemId);
        }
    }

    @Override
    public void registerListener() {
        CraftedItemsCallback.EVENT.register((player, outputItem) -> {
            if (Registries.ITEM.getId(outputItem.getItem()).equals(checkItemId)) {
                incrementActiveQuests(player, outputItem.getCount());
            }
            return ActionResult.PASS;
        });
    }

    @Override
    public ItemStack getIconItem() {
        Item item = Registries.ITEM.get(checkItemId);
        ItemStack itemStack = new ItemStack(item);
        itemStack.setCustomName(Text.literal("Craft " + item.getName().getString()));
        return itemStack;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = super.toJson();
        jsonObject.addProperty(DataKeys.CRAFT_ITEM_OUTPUT_ITEM, checkItemId.toString());
        return jsonObject;
    }

}
