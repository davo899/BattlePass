package com.selfdot.battlepass;

import com.mojang.brigadier.CommandDispatcher;
import com.selfdot.battlepass.command.BattlePassCommandTree;
import com.selfdot.battlepass.quest.DailyQuestTracker;
import com.selfdot.battlepass.quest.QuestPoolConfig;
import com.selfdot.battlepass.reward.ClaimedRewardsTracker;
import com.selfdot.battlepass.reward.RewardsConfig;
import com.selfdot.battlepass.tier.TiersConfig;
import com.selfdot.battlepass.util.DisableableMod;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class BattlePassMod extends DisableableMod {

    private static BattlePassMod INSTANCE;

    private final RewardsConfig rewardsConfig = new RewardsConfig(this);
    private final TiersConfig tiersConfig = new TiersConfig(this);
    private final QuestPoolConfig questPoolConfig = new QuestPoolConfig(this);

    private final PointsTracker pointsTracker = new PointsTracker(this);
    private final DailyQuestTracker dailyQuestTracker = new DailyQuestTracker(this);
    private final ClaimedRewardsTracker claimedRewardsTracker = new ClaimedRewardsTracker(this);

    @Override
    public void onInitialize() {
        INSTANCE = this;

        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
        CommandRegistrationCallback.EVENT.register(this::registerCommands);

        ServerTickEvents.START_SERVER_TICK.register(this::onTick);
    }

    public static BattlePassMod getInstance() {
        return INSTANCE;
    }

    public PointsTracker getPointsTracker() {
        return pointsTracker;
    }

    public TiersConfig getTiersConfig() {
        return tiersConfig;
    }

    public DailyQuestTracker getDailyQuestTracker() {
        return dailyQuestTracker;
    }

    public ClaimedRewardsTracker getClaimedRewardsTracker() {
        return claimedRewardsTracker;
    }

    public RewardsConfig getRewardsConfig() {
        return rewardsConfig;
    }

    public QuestPoolConfig getQuestPoolConfig() {
        return questPoolConfig;
    }

    private void registerCommands(
        CommandDispatcher<ServerCommandSource> dispatcher,
        CommandRegistryAccess commandRegistryAccess,
        CommandManager.RegistrationEnvironment registrationEnvironment
    ) {
        new BattlePassCommandTree().register(this, dispatcher);
    }

    private void onServerStarting(MinecraftServer server) {
        rewardsConfig.load();
        tiersConfig.load();
        questPoolConfig.load();

        pointsTracker.load();
        dailyQuestTracker.load();
        claimedRewardsTracker.load();
    }

    private void onServerStopping(MinecraftServer server) {
        pointsTracker.save();
        dailyQuestTracker.save();
        claimedRewardsTracker.save();
    }

    private void onTick(MinecraftServer server) {
        if (!isDisabled()) {
            dailyQuestTracker.onTick();
        }
    }

}
