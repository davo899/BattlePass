package com.selfdot.battlepass.util;

import net.fabricmc.api.ModInitializer;

public abstract class DisableableMod implements ModInitializer {

    private boolean disabled = false;

    public boolean isDisabled() {
        return disabled;
    }

    public void disable() {
        this.disabled = true;
    }

}
