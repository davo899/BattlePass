package com.selfdot.battlepass.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.selfdot.battlepass.BattlePassMod;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReloadCommand implements Command<ServerCommandSource> {

    @Override
    public int run(CommandContext<ServerCommandSource> context) {
        BattlePassMod.getInstance().loadData();
        context.getSource().sendMessage(Text.literal("Reloaded Battle Pass config"));
        return SINGLE_SUCCESS;
    }

}
