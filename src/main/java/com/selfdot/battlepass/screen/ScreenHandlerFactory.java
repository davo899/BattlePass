package com.selfdot.battlepass.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class ScreenHandlerFactory implements NamedScreenHandlerFactory {

    private final int rows;
    private final int cols;

    private final Screen screen;

    public ScreenHandlerFactory(Screen screen) {
        this.screen = screen;
        if (screen.type().equals(ScreenHandlerType.GENERIC_9X3)) {
            this.rows = 3;
        } else {
            this.rows = 6;
        }
        this.cols = 9;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal(screen.getDisplayName());
    }

    public int rows() {
        return rows;
    }

    public int size() {
        return rows() * cols;
    }

    @Nullable
    @Override
    public net.minecraft.screen.ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        SimpleInventory inventory = new SimpleInventory(size());
        screen.initialize(inventory, rows, cols);
        return new ScreenHandler(screen.type(), syncId, inv, inventory, rows) {

            @Override
            public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
                screen.onSlotClick(slotIndex, button, actionType, player);
            }

        };
    }

}
