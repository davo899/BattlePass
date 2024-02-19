package com.selfdot.battlepass.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.selfdot.battlepass.util.CommandUtils;
import com.selfdot.battlepass.util.DisableableMod;
import net.minecraft.server.command.ServerCommandSource;

public class BattlePassCommandTree {

    public void register(DisableableMod mod, CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<ServerCommandSource>
            literal("bp")
            .requires(source ->
                !mod.isDisabled() &&
                source.isExecutedByPlayer() &&
                CommandUtils.hasPermission(source, "selfdot.bp")
            )
            .executes(new OpenBattlePassCommand())
        );

        dispatcher.register(LiteralArgumentBuilder.<ServerCommandSource>
            literal("bpa")
            .requires(source ->
                !mod.isDisabled() &&
                CommandUtils.hasPermission(source, "selfdot.bpa")
            )
            .then(LiteralArgumentBuilder.<ServerCommandSource>
                literal("reload")
                .executes(new ReloadCommand())
            )
        );
    }

}
