package com.selfdot.battlepass.quest;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class QuestEventListener {

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

    }

}
