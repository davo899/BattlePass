package com.selfdot.battlepass.screen;

import com.selfdot.battlepass.BattlePassMod;
import com.selfdot.battlepass.tier.Tier;
import com.selfdot.battlepass.tier.TierProgress;
import com.selfdot.battlepass.util.CommandUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Map;

public class TierScreen extends Screen {

    private final List<Map.Entry<Integer, Tier>> tierList =
        BattlePassMod.getInstance().getTiersConfig().getTiersInOrder();
    protected int pageNumber;
    private final PlayerEntity player;
    private int maxPerPage = 0;
    private int prevPageSlot = 0;
    private int nextPageSlot = 0;

    public TierScreen(PlayerEntity player, int pageNumber) {
        super(new BattlePassScreen(player));
        this.pageNumber = pageNumber;
        this.player = player;
    }

    @Override
    public void initialize(Inventory inventory) {
        maxPerPage = columns;
        prevPageSlot = ((rows - 1) * columns) + (columns / 2) - 1;
        nextPageSlot = prevPageSlot + 2;

        ItemStack prevPageItem = new ItemStack(Items.SPECTRAL_ARROW);
        prevPageItem.setCustomName(Text.literal("Previous Page"));
        inventory.setStack(prevPageSlot, prevPageItem);

        ItemStack nextPageItem = new ItemStack(Items.SPECTRAL_ARROW);
        nextPageItem.setCustomName(Text.literal("Next Page"));
        inventory.setStack(nextPageSlot, nextPageItem);

        TierProgress tierProgress = BattlePassMod.getInstance().getTiersConfig().getTierProgress(
            BattlePassMod.getInstance().getPointsTracker().getPoints(player.getUuid())
        );
        for (int i = 0; i < Math.min(maxPerPage, tierList.size() - (maxPerPage * pageNumber)); i++) {
            Map.Entry<Integer, Tier> tier = tierList.get((maxPerPage * pageNumber) + i);
            boolean isCompletedTier = tierProgress.tier() >= tier.getKey();
            if (isCompletedTier) {
                setSlot(inventory, slotIndex(i, 2), Items.LIME_STAINED_GLASS_PANE,
                    Formatting.GOLD + "Tier " + tier.getKey()
                );
                if (BattlePassMod.getInstance().getClaimedRewardsTracker().hasClaimed(player, tier.getKey())) {
                    setSlot(inventory, slotIndex(i, 3), Items.MINECART, Formatting.GRAY + "Claimed");
                } else {
                    setSlot(inventory, slotIndex(i, 3), Items.CHEST_MINECART,
                        Formatting.GREEN + "Click to claim!"
                    );
                }
            } else {
                setSlot(inventory, slotIndex(i, 2), Items.RED_STAINED_GLASS_PANE,
                    Formatting.GOLD + "Tier " + tier.getKey()
                );
                setSlot(inventory, slotIndex(i, 3), Items.FURNACE_MINECART, Formatting.RED + "Locked");
            }
        }
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);

        if (slotIndex == prevPageSlot && pageNumber > 0) {
            pageNumber--;
            refresh(player);
            return;
        }
        if (slotIndex == nextPageSlot && maxPerPage * (pageNumber + 1) < tierList.size()) {
            pageNumber++;
            refresh(player);
            return;
        }

        int x = slotIndex % columns;
        int y = slotIndex / columns;
        if (y == 3) {
            int tierIndex = x + (maxPerPage * pageNumber);
            if (tierIndex < tierList.size()) {
                Map.Entry<Integer, Tier> tierEntry = tierList.get(tierIndex);
                BattlePassMod mod = BattlePassMod.getInstance();
                if (
                    mod.getTiersConfig().getTierProgress(
                        mod.getPointsTracker().getPoints(player.getUuid())
                    ).tier() < tierEntry.getKey() ||
                    mod.getClaimedRewardsTracker().hasClaimed(player, tierEntry.getKey())
                ) return;
                tierEntry.getValue().getRewards().forEach(
                    rewardID -> mod.getRewardsConfig().getReward(rewardID).forEach(
                        rewardCommand -> CommandUtils.executeCommandAsServer(rewardCommand, player.getServer(), player)
                    )
                );
                mod.getClaimedRewardsTracker().setClaimed(player, tierEntry.getKey());
                refresh(player);
            }
        }
    }

    @Override
    public String getDisplayName() {
        return "Tiers";
    }

    @Override
    public ScreenHandlerType<?> type() {
        return ScreenHandlerType.GENERIC_9X6;
    }

}
