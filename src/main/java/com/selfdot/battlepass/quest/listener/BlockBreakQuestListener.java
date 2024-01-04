package com.selfdot.battlepass.quest.listener;

import com.selfdot.battlepass.BattlePassMod;
import com.selfdot.battlepass.quest.BlockBreakQuest;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockBreakQuestListener extends QuestEventListener<BlockBreakQuest> {

    private static final BlockBreakQuestListener INSTANCE = new BlockBreakQuestListener();
    private BlockBreakQuestListener() { }
    public static BlockBreakQuestListener getInstance() {
        return INSTANCE;
    }

    @Override
    public void register() {
        PlayerBlockBreakEvents.AFTER.register(this::onPlayerBreakBlock);
    }

    private void onPlayerBreakBlock(
        World world,
        PlayerEntity player,
        BlockPos blockPos,
        BlockState blockState,
        @Nullable BlockEntity blockEntity
    ) {
        if (!BattlePassMod.getInstance().isDisabled()) {
            listeners.forEach(blockBreakQuest -> blockBreakQuest.test(blockState.getBlock(), player));
        }
    }

}
