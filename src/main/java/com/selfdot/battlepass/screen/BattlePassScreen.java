package com.selfdot.battlepass.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandlerType;

public class BattlePassScreen extends Screen {

    @Override
    public void initialize(Inventory inventory) {

    }

    @Override
    public String getDisplayName() {
        return "Battle Pass";
    }

    @Override
    public ScreenHandlerType<?> type() {
        return ScreenHandlerType.GENERIC_9X3;
    }

}
