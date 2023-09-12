package dev.xfj.database;

import dev.xfj.itemconfig.ItemConfigJson;
import dev.xfj.itemconfigequipment.ItemConfigEquipmentJson;

import java.io.FileNotFoundException;
import java.util.Map;

public class ItemData {
    private static Map<String, ItemConfigJson> itemConfig;
    private static Map<String, ItemConfigEquipmentJson> itemConfigEquipment;

    private ItemData() {
    }

    public static void init() throws FileNotFoundException {
        itemConfig = Loader.loadJSON(ItemConfigJson.class);
        itemConfigEquipment = Loader.loadJSON(ItemConfigEquipmentJson.class);
    }

    public static Map<String, ItemConfigJson> getItemConfig() {
        return itemConfig;
    }
}
