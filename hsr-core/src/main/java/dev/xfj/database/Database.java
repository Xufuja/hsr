package dev.xfj.database;

import dev.xfj.Application;
import dev.xfj.avatar.Avatar;
import dev.xfj.avatar.AvatarAbility;
import dev.xfj.avatar.AvatarPath;
import dev.xfj.avatar.AvatarStats;
import dev.xfj.item.Item;
import dev.xfj.item.ItemEquipment;
import dev.xfj.item.ItemExp;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConePassive;
import dev.xfj.lightcone.LightConeStats;
import dev.xfj.relic.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Database {
    protected static Map<Integer, Item> normalItems;
    protected static Map<Integer, ItemEquipment> lightConeItems;
    protected static Map<Integer, ItemExp> expItems;
    protected static Map<String, AvatarPath> avatarPaths;
    protected static Map<Integer, Map<Integer, AvatarStats>> avatarStats;
    protected static Map<Integer, Map<Integer, AvatarAbility>> avatarAbilities;
    protected static Map<Integer, Avatar> avatars;
    protected static Map<Integer, Map<Integer, Integer>> avatarExp;
    protected static Map<Integer, Map<Integer, LightConePassive>> lightConePassives;
    protected static Map<Integer, Map<Integer, LightConeStats>> lightConeStats;
    protected static Map<Integer, LightCone> lightCones;
    protected static Map<Integer, Map<Integer, Integer>> lightConeExp;
    protected static Map<Integer, Map<Integer, RelicSetEffect>> relicSetEffects;
    protected static Map<Integer, RelicSet> relicSets;
    protected static Map<Integer, Map<Integer, RelicMainStats>> relicMainStats;
    protected static Map<Integer, Map<Integer, RelicSubStats>> relicSubStats;
    protected static Map<Integer, Relic> relics;
    protected static Map<Integer, Map<Integer, Integer>> relicExp;

    public static void init(String languageCode) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        TextMapData.languageCode = languageCode;
        TextMapData.init();
        ItemData.init();
        AvatarData.init();
        EquipmentData.init();
        RelicData.init();

        Database.normalItems = ItemData.loadNormalItems();
        Database.lightConeItems = ItemData.loadEquipmentItems();
        Database.expItems = ItemData.loadExpItems();
        Database.avatarPaths = AvatarData.loadAvatarPaths();
        Database.avatarStats = AvatarData.loadAvatarStats();
        Database.avatarAbilities = AvatarData.loadAvatarAbilities();
        Database.avatars = AvatarData.loadAvatars();
        Database.avatarExp = AvatarData.loadAvatarExp();
        Database.lightConePassives = EquipmentData.loadLightConePassives();
        Database.lightConeStats = EquipmentData.loadLightConeStats();
        Database.lightCones = EquipmentData.loadLightCones();
        Database.lightConeExp = EquipmentData.loadLightConeExp();
        Database.relicSetEffects = RelicData.loadRelicSetEffects();
        Database.relicSets = RelicData.loadRelicSets();
        Database.relicMainStats = RelicData.loadRelicMainStats();
        Database.relicSubStats = RelicData.loadRelicSubStats();
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

    public static Map<String, AvatarPath> getAvatarPaths() {
        return avatarPaths;
    }

    public static Map<Integer, Map<Integer, AvatarStats>> getAvatarStats() {
        return avatarStats;
    }

    public static Map<Integer, Map<Integer, AvatarAbility>> getAvatarAbilities() {
        return avatarAbilities;
    }

    public static Map<Integer, Avatar> getAvatars() {
        return avatars;
    }

    public static Map<Integer, Map<Integer, Integer>> getAvatarExp() {
        return avatarExp;
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
