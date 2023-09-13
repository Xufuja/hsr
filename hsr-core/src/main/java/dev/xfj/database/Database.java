package dev.xfj.database;

import dev.xfj.item.Item;
import dev.xfj.item.ItemEquipment;
import dev.xfj.item.ItemExp;
import dev.xfj.lightcone.LightCone;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Database {
    protected static Map<Integer, LightCone> lightCones;
    protected static Map<Integer, Item> normalItems;
    protected static Map<Integer, ItemEquipment> lightConeItems;
    protected static Map<Integer, ItemExp> expItems;

    public static void init(String languageCode) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        TextMapData.languageCode = languageCode;
        TextMapData.init();
        AvatarData.init();
        ItemData.init();
        EquipmentData.init();

        Database.normalItems = ItemData.loadNormalItems();
        Database.lightConeItems = ItemData.loadEquipmentItems();
        Database.expItems = ItemData.loadExpItems();
        Database.lightCones = EquipmentData.loadLightCones();
    }

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
