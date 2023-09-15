package dev.xfj.database;

import dev.xfj.item.Item;
import dev.xfj.item.ItemEquipment;
import dev.xfj.item.ItemExp;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConeSkill;
import dev.xfj.lightcone.LightConeStats;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Database {
    protected static Map<Integer, Item> normalItems;
    protected static Map<Integer, ItemEquipment> lightConeItems;
    protected static Map<Integer, ItemExp> expItems;
    protected static Map<Integer, Map<Integer, LightConeSkill>> lightConeSkills;
    protected static Map<Integer, Map<Integer, LightConeStats>> lightConeStats;
    protected static Map<Integer, LightCone> lightCones;
    protected static Map<Integer, Map<Integer, Integer>> lightConeExp;

    public static void init(String languageCode) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        TextMapData.languageCode = languageCode;
        TextMapData.init();
        AvatarData.init();
        ItemData.init();
        EquipmentData.init();

        Database.normalItems = ItemData.loadNormalItems();
        Database.lightConeItems = ItemData.loadEquipmentItems();
        Database.expItems = ItemData.loadExpItems();
        Database.lightConeSkills = EquipmentData.loadLightConeSkills();
        Database.lightConeStats = EquipmentData.loadLightConeStats();
        Database.lightCones = EquipmentData.loadLightCones();
        Database.lightConeExp = EquipmentData.loadLightConeExp();
    }

    public static String getTranslation(int key) {
        return TextMapData.getTranslation(key);
    }

    public static Map<Integer, Item> getNormalItems() {
        return normalItems;
    }

    public static Map<Integer, ItemEquipment> getLightConeItems() {
        return lightConeItems;
    }

    public static Map<Integer, ItemExp> getExpItems() {
        return expItems;
    }

    public static Map<Integer, LightCone> getLightCones() {
        return lightCones;
    }

    public static Map<Integer, Map<Integer, Integer>> getLightConeExp() {
        return lightConeExp;
    }
}
