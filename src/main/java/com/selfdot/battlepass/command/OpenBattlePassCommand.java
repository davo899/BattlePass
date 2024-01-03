package com.selfdot.battlepass.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.selfdot.battlepass.screen.BattlePassScreen;
import com.selfdot.battlepass.screen.ScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class OpenBattlePassCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) return 0;
        player.openHandledScreen(new ScreenHandlerFactory(new BattlePassScreen()));
        return SINGLE_SUCCESS;
    }

}
