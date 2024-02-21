package com.selfdot.battlepass.quest.type;

import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.ChatEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class ChatQuest extends Quest {

    private final String checkString;

    public ChatQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_CHAT);
        this.checkString = jsonObject.get(DataKeys.CHAT_MESSAGE).getAsString();
    }

    @Override
    public void registerListener() {
        ChatEvent.RECEIVED.register((player, component) -> {
            if (component.getString().equals(checkString)) incrementActiveQuests(player);
            return EventResult.pass();
        });
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(Items.PAPER);
        itemStack.setCustomName(Text.literal("Say \"" + checkString + "\""));
        return itemStack;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = super.toJson();
        jsonObject.addProperty(DataKeys.CHAT_MESSAGE, checkString);
        return jsonObject;
    }

}
