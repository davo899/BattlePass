package com.selfdot.battlepass.quest.type;

import com.cobblemon.mod.common.api.berry.Berries;
import com.cobblemon.mod.common.api.berry.Berry;
import com.google.gson.JsonObject;
import com.selfdot.battlepass.DataKeys;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HarvestSpecificBerriesQuest extends HarvestBerriesQuest {

    private final Berry checkBerry;

    public HarvestSpecificBerriesQuest(JsonObject jsonObject) {
        super(jsonObject, DataKeys.QUEST_TYPE_HARVEST_SPECIFIC_BERRIES);
        String berryString = jsonObject.get(DataKeys.HARVEST_BERRY_BERRY).getAsString();
        this.checkBerry = Berries.INSTANCE.getByIdentifier(new Identifier(berryString));
        if (checkBerry == null || checkBerry.item() == null) {
            throw new IllegalStateException("Invalid berry: " + berryString);
        }
    }

    @Override
    protected boolean isValidBerry(Berry berry) {
        return berry.getIdentifier().equals(checkBerry.getIdentifier());
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public ItemStack getIconItem() {
        ItemStack itemStack = new ItemStack(checkBerry.item());
        itemStack.setCustomName(Text.literal("Harvest " + checkBerry.item().getName().getString()));
        return itemStack;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = super.toJson();
        jsonObject.addProperty(DataKeys.HARVEST_BERRY_BERRY, checkBerry.getIdentifier().toString());
        return jsonObject;
    }

}
