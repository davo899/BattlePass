package com.selfdot.battlepass;

import com.selfdot.battlepass.tier.TiersConfig;
import com.selfdot.battlepass.util.DisableableMod;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class BattlePass extends DisableableMod {

    private final PointsTracker pointsTracker = new PointsTracker(this);
    private final RewardsConfig rewardsConfig = new RewardsConfig(this);
    private final TiersConfig tiersConfig = new TiersConfig(this);

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
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
