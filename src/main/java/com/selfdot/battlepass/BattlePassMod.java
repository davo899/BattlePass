package com.selfdot.battlepass;

import com.mojang.brigadier.CommandDispatcher;
import com.selfdot.battlepass.command.BattlePassCommandTree;
import com.selfdot.battlepass.tier.TiersConfig;
import com.selfdot.battlepass.util.DisableableMod;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class BattlePassMod extends DisableableMod {

    private final PointsTracker pointsTracker = new PointsTracker(this);
    private final RewardsConfig rewardsConfig = new RewardsConfig(this);
    private final TiersConfig tiersConfig = new TiersConfig(this);

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        CommandRegistrationCallback.EVENT.register(this::registerCommands);
    }

    private void registerCommands(
        CommandDispatcher<ServerCommandSource> dispatcher,
        CommandRegistryAccess commandRegistryAccess,
        CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        new BattlePassCommandTree().register(this, dispatcher);
    }

    private void onServerStarting(MinecraftServer server) {
        pointsTracker.load();
        rewardsConfig.load();
        tiersConfig.load();
    }

    private void onServerStopping(MinecraftServer server) {
        if (!isDisabled()) pointsTracker.save();
    }

}
