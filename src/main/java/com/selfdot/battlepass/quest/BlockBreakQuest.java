package com.selfdot.battlepass.quest;

import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import com.selfdot.battlepass.quest.listener.BlockBreakQuestListener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class BlockBreakQuest extends Quest {

    private final Identifier blockID;

    public BlockBreakQuest(int points, Block block) {
        super(points);
        this.blockID = Registries.BLOCK.getId(block);
    }

    public BlockBreakQuest(JsonObject jsonObject) {
        super(jsonObject);
        this.blockID = Identifier.tryParse(jsonObject.get(DataKeys.BREAK_BLOCK_BLOCK).getAsString());
    }

    public void test(Block broken, PlayerEntity player) {
        if (Registries.BLOCK.getId(broken).equals(blockID)) incrementActive(player);
    }

    @Override
    protected void startListening() {
        BlockBreakQuestListener.getInstance().add(this);
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(DataKeys.QUEST_TYPE, DataKeys.QUEST_TYPE_BREAK_BLOCK);
        jsonObject.addProperty(DataKeys.BREAK_BLOCK_BLOCK, blockID.toString());
        return jsonObject;
    }

}
