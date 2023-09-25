package dev.xfj.database;

import dev.xfj.Application;
import dev.xfj.item.Item;
import dev.xfj.item.ItemEquipment;
import dev.xfj.item.ItemExp;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConePassive;
import dev.xfj.lightcone.LightConeStats;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicMainStats;
import dev.xfj.relic.RelicSet;
import dev.xfj.relic.RelicSetEffect;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Database {
    protected static Map<Integer, Item> normalItems;
    protected static Map<Integer, ItemEquipment> lightConeItems;
    protected static Map<Integer, ItemExp> expItems;
    protected static Map<Integer, Map<Integer, LightConePassive>> lightConePassives;
    protected static Map<Integer, Map<Integer, LightConeStats>> lightConeStats;
    protected static Map<Integer, LightCone> lightCones;
    protected static Map<Integer, Map<Integer, Integer>> lightConeExp;
    protected static Map<Integer, Map<Integer, RelicSetEffect>> relicSetEffects;
    protected static Map<Integer, RelicSet> relicSets;
    protected static Map<Integer, Map<Integer, RelicMainStats>> relicMainStats;
    protected static Map<Integer, Relic> relics;
    protected static Map<Integer, Map<Integer, Integer>> relicExp;

    public static void init(String languageCode) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        TextMapData.languageCode = languageCode;
        TextMapData.init();
        AvatarData.init();
        ItemData.init();
        EquipmentData.init();
        RelicData.init();

        Database.normalItems = ItemData.loadNormalItems();
        Database.lightConeItems = ItemData.loadEquipmentItems();
        Database.expItems = ItemData.loadExpItems();
        Database.lightConePassives = EquipmentData.loadLightConePassives();
        Database.lightConeStats = EquipmentData.loadLightConeStats();
        Database.lightCones = EquipmentData.loadLightCones();
        Database.lightConeExp = EquipmentData.loadLightConeExp();
        Database.relicSetEffects = RelicData.loadRelicSetEffects();
        Database.relicSets = RelicData.loadRelicSets();
        Database.relicMainStats = RelicData.loadRelicMainStats();
        Database.relics = RelicData.loadRelics();
        Database.relicExp = RelicData.loadRelicExp();
    }

    public static String getTranslation(int key) {
        return TextMapData.getTranslation(key);
    }

    public static String getTranslationNoHash(String key) {
        return TextMapData.getTranslation(Application.getStableHash(key));
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

    public static Map<Integer, Relic> getRelics() {
        return relics;
    }

    public static Map<Integer, Map<Integer, Integer>> getRelicExp() {
        return relicExp;
    }
}
