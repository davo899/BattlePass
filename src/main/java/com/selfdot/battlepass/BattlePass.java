package com.selfdot.battlepass;

import com.selfdot.battlepass.util.DisableableMod;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class BattlePass extends DisableableMod {

    private final XPTracker xpTracker = new XPTracker(this);

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
    }

    private void onServerStarting(MinecraftServer server) {
        xpTracker.load();
    }

    private void onServerStopping(MinecraftServer server) {
        if (!isDisabled()) xpTracker.save();
    }

}
