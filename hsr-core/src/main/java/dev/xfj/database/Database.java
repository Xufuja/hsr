package dev.xfj.database;

import dev.xfj.item.Item;
import dev.xfj.item.ItemEquipment;
import dev.xfj.lightcone.LightCone;

import java.util.Map;

public class Database {
    protected static Map<Integer, LightCone> lightCones;
    protected static Map<Integer, Item> normalItems;
    protected static Map<Integer, ItemEquipment> lightConeItems;

    public static Map<Integer, LightCone> getLightCones() {
        return lightCones;
    }

    public static Map<Integer, Item> getNormalItems() {
        return normalItems;
    }

    public static Map<Integer, ItemEquipment> getLightConeItems() {
        return lightConeItems;
    }
}
