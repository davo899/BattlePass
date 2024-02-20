package com.selfdot.battlepass.quest.type;

import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BlockBreakQuest extends Quest {

    private final Identifier blockID;

    public BlockBreakQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_BREAK_BLOCK);
        this.blockID = Identifier.tryParse(jsonObject.get(DataKeys.BREAK_BLOCK_BLOCK).getAsString());
    }

    @Override
    public void registerListener() {
        PlayerBlockBreakEvents.AFTER.register(((world, player, pos, state, blockEntity) -> {
            if (Registries.BLOCK.getId(state.getBlock()).equals(blockID)) incrementActiveQuests(player);
        }));
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = super.toJson();
        jsonObject.addProperty(DataKeys.BREAK_BLOCK_BLOCK, blockID.toString());
        return jsonObject;
    }

    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(Registries.ITEM.get(blockID));
        itemStack.setCustomName(Text.literal("Break " + Registries.BLOCK.get(blockID).getName().getString()));
        return itemStack;
    }

}
