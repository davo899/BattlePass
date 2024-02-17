package com.selfdot.battlepass.screen;

import com.selfdot.battlepass.BattlePassMod;
import com.selfdot.battlepass.tier.TierProgress;
import com.selfdot.battlepass.util.ScreenUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BattlePassScreen extends Screen {

    private int rewardsButtonSlotIndex;
    private int questsButtonSlotIndex;
    private final PlayerEntity player;

    public BattlePassScreen(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void initialize(Inventory inventory) {
        rewardsButtonSlotIndex = slotIndex(2, 1);
        questsButtonSlotIndex = slotIndex(6, 1);
        setSlot(inventory, rewardsButtonSlotIndex, Items.CHEST_MINECART, "Rewards");
        setSlot(inventory, questsButtonSlotIndex, Items.WRITABLE_BOOK, "Quests");

        ItemStack playerInfo = new ItemStack(Items.PLAYER_HEAD);
        NbtCompound playerInfoNbt = playerInfo.getNbt();
        if (playerInfoNbt == null) playerInfoNbt = new NbtCompound();
        playerInfoNbt.putString("SkullOwner", player.getGameProfile().getName());
        playerInfo.setNbt(playerInfoNbt);
        playerInfo.setCustomName(Text.literal(player.getGameProfile().getName()));
        TierProgress tierProgress = BattlePassMod.getInstance().getTiersConfig().getTierProgress(
            BattlePassMod.getInstance().getPointsTracker().getPoints(player.getUuid())
        );
        ScreenUtils.addLore(playerInfo, new Text[]{
            Text.literal(Formatting.GOLD + "Tier " + tierProgress.tier()),
            Text.literal(
                Formatting.AQUA + String.valueOf(tierProgress.pointsInCurrent()) +
                (tierProgress.pointsForNext() == 0 ? "" : "/" + tierProgress.pointsForNext()) +
                " points"
            )
        });
        inventory.setStack(slotIndex(4, 1), playerInfo);
    }

    @Override
    public String getDisplayName() {
        return "Battle Pass";
    }

    @Override
    public ScreenHandlerType<?> type() {
        return ScreenHandlerType.GENERIC_9X3;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);

        if (slotIndex == questsButtonSlotIndex) {
            player.openHandledScreen(new ScreenHandlerFactory(new QuestScreen(player)));
        }
        if (slotIndex == rewardsButtonSlotIndex) {
            player.openHandledScreen(new ScreenHandlerFactory(new TierScreen(player, 0)));
        }
    }
}
