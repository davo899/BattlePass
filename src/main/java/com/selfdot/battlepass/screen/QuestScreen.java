package com.selfdot.battlepass.screen;

import com.selfdot.battlepass.BattlePassMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class QuestScreen extends Screen {

    private static final int QUEST_LIST_X = 2;
    private static final int QUEST_LIST_Y = 2;
    private static final int QUEST_LIST_WIDTH = 5;
    private final PlayerEntity player;

    public QuestScreen(PlayerEntity player) {
        super(new BattlePassScreen(player));
        this.player = player;
    }

    @Override
    public void initialize(Inventory inventory) {
        List<ItemStack> questItems = BattlePassMod.getInstance()
            .getDailyQuestTracker().getProgressItems(player.getUuid());

        for (int i = 0; i < Math.min(questItems.size(), 10); i++) {
            inventory.setStack(
                slotIndex(QUEST_LIST_X + (i % QUEST_LIST_WIDTH), QUEST_LIST_Y + (i / QUEST_LIST_WIDTH)),
                questItems.get(i)
            );
        }
    }

    @Override
    public String getDisplayName() {
        return "Quests";
    }

    @Override
    public ScreenHandlerType<?> type() {
        return ScreenHandlerType.GENERIC_9X6;
    }

}
