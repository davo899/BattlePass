package com.selfdot.battlepass.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.selfdot.battlepass.screen.ScreenHandlerFactory;
import com.selfdot.battlepass.screen.TierScreen;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class OpenRewardsCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) return 0;
        player.openHandledScreen(new ScreenHandlerFactory(new TierScreen(player, 0)));
        return SINGLE_SUCCESS;
    }

}
