package dev.xfj.handlers;

import dev.xfj.itemconfig.ItemConfigJson;

import java.io.FileNotFoundException;
import java.util.Map;

public class ItemHandler implements Handler{
    private final Map<String, ItemConfigJson> itemConfig;

    public ItemHandler() throws FileNotFoundException {
        this.itemConfig = loadJSON(ItemConfigJson.class, "ItemConfig.json");
    }

    public Map<String, ItemConfigJson> getItemConfig() {
        return itemConfig;
    }
}
