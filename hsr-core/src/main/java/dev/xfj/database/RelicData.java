package dev.xfj.database;

import dev.xfj.jsonschema2pojo.equipmentexptype.EquipmentExpTypeJson;
import dev.xfj.jsonschema2pojo.equipmentskillconfig.EquipmentSkillConfigJson;
import dev.xfj.jsonschema2pojo.equipmentskillconfig.Param;
import dev.xfj.jsonschema2pojo.relicbasetype.RelicBaseTypeJson;
import dev.xfj.jsonschema2pojo.reliccomposeconfig.RelicComposeConfigJson;
import dev.xfj.jsonschema2pojo.relicconfig.RelicConfigJson;
import dev.xfj.jsonschema2pojo.relicdatainfo.RelicDataInfoJson;
import dev.xfj.jsonschema2pojo.relicexpitem.RelicExpItemJson;
import dev.xfj.jsonschema2pojo.relicexptype.RelicExpTypeJson;
import dev.xfj.jsonschema2pojo.relicmainaffixconfig.RelicMainAffixConfigJson;
import dev.xfj.jsonschema2pojo.relicsetconfig.RelicSetConfigJson;
import dev.xfj.jsonschema2pojo.relicsetskillconfig.AbilityParam;
import dev.xfj.jsonschema2pojo.relicsetskillconfig.Property;
import dev.xfj.jsonschema2pojo.relicsetskillconfig.RelicSetSkillConfigJson;
import dev.xfj.jsonschema2pojo.relicsubaffixconfig.RelicSubAffixConfigJson;
import dev.xfj.lightcone.LightConePassive;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicInfo;
import dev.xfj.relic.RelicSet;
import dev.xfj.relic.RelicSetEffect;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelicData {
    private static Map<String, RelicBaseTypeJson> relicBaseType;
    private static Map<String, RelicComposeConfigJson> relicComposeConfig;
    private static Map<String, RelicConfigJson> relicConfig;
    private static Map<String, RelicDataInfoJson> relicDataInfo;
    private static Map<String, RelicExpItemJson> relicExpItem;
    private static Map<String, Map<String, RelicExpTypeJson>> relicExpType;
    private static Map<String, Map<String, RelicMainAffixConfigJson>> relicMainAffixConfig;
    private static Map<String, RelicSetConfigJson> relicSetConfig;
    private static Map<String, Map<String, RelicSetSkillConfigJson>> relicSetSkillConfig;
    private static Map<String, Map<String, RelicSubAffixConfigJson>> relicSubAffixConfig;

    private RelicData() {

    }

    public static void init() throws FileNotFoundException {
        relicBaseType = Loader.loadJSON(RelicBaseTypeJson.class);
        relicComposeConfig = Loader.loadJSON(RelicComposeConfigJson.class);
        relicConfig = Loader.loadJSON(RelicConfigJson.class);
        relicDataInfo = Loader.loadJSON(RelicDataInfoJson.class);
        relicExpItem = Loader.loadJSON(RelicExpItemJson.class);
        relicExpType = Loader.loadNestedJSON(RelicExpTypeJson.class);
        relicMainAffixConfig = Loader.loadNestedJSON(RelicMainAffixConfigJson.class);
        relicSetConfig = Loader.loadJSON(RelicSetConfigJson.class);
        relicSetSkillConfig = Loader.loadNestedJSON(RelicSetSkillConfigJson.class);
        relicSubAffixConfig = Loader.loadNestedJSON(RelicSubAffixConfigJson.class);
    }

    protected static Map<Integer, Relic> loadRelics() {
        Map<Integer, Relic> relics = new HashMap<>();

        for (Map.Entry<String, RelicConfigJson> entry : relicConfig.entrySet()) {
            RelicInfo relicInfo = getRelicInfo(relicDataInfo.get(String.valueOf(entry.getValue().getSetID())), entry.getValue().getType());
            Relic relic = new Relic(entry.getValue().getId(),
                    relicInfo.name(),
                    relicInfo.backgroundDescription(),
                    relicInfo.backgroundStoryContent(),
                    entry.getValue().getSetID(),
                    entry.getValue().getType(),
                    entry.getValue().getRarity(),
                    entry.getValue().getMainAffixGroup(),
                    entry.getValue().getSubAffixGroup(),
                    entry.getValue().getMaxLevel(),
                    entry.getValue().getExpType(),
                    entry.getValue().getExpProvide(),
                    entry.getValue().getCoinCost(),
                    Database.relicSets.get(entry.getValue().getSetID()));

            relics.put(entry.getValue().getId(), relic);
        }

        return relics;
    }

    private static RelicInfo getRelicInfo(RelicDataInfoJson info, String type) {
        Object relic = null;
        RelicInfo relicInfo = null;

        switch (type) {
            case "HEAD" -> relic = info.getHead();
            case "HAND" -> relic = info.getHand();
            case "BODY" -> relic = info.getBody();
            case "FOOT" -> relic = info.getFoot();
            case "NECK" -> relic = info.getNeck();
            case "OBJECT" -> relic = info.getObject();
            default -> throw new RuntimeException("Unexpected value: " + type);
        }

        Class<?> clazz = relic.getClass();
        try {
            relicInfo = new RelicInfo((Integer) clazz.getMethod("getSetID").invoke(relic),
                    type,
                    Database.getTranslationNoHash((String) clazz.getMethod("getRelicName").invoke(relic)),
                    Database.getTranslationNoHash((String) clazz.getMethod("getItemBGDesc").invoke(relic)),
                    Database.getTranslationNoHash((String) clazz.getMethod("getBGStoryContent").invoke(relic)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return relicInfo;
    }

    protected static Map<Integer, RelicSet> loadRelicSets() {
        Map<Integer, RelicSet> relicSets = new HashMap<>();

        for (Map.Entry<String, RelicSetConfigJson> entry : relicSetConfig.entrySet()) {

            RelicSet relicSet = new RelicSet(entry.getValue().getSetID(),
                    Database.getTranslation(entry.getValue().getSetName().getHash()),
                    Database.relicSetEffects.get(entry.getValue().getSetID()));

            relicSets.put(entry.getValue().getSetID(), relicSet);
        }

        return relicSets;
    }

    private static String obtainGetter(String input) {
        char[] name = input.toCharArray();
        name[0] = Character.toUpperCase(name[0]);
        return "get".concat(new String(name));
    }

    protected static Map<Integer, Map<Integer, RelicSetEffect>> loadRelicSetEffects() {
        Map<Integer, Map<Integer, RelicSetEffect>> effects = new HashMap<>();

        for (Map.Entry<String, Map<String, RelicSetSkillConfigJson>> outerEntry : relicSetSkillConfig.entrySet()) {
            Map<Integer, RelicSetEffect> setCount = new HashMap<>();

            for (Map.Entry<String, RelicSetSkillConfigJson> innerEntry : outerEntry.getValue().entrySet()) {
                RelicSetSkillConfigJson entry = innerEntry.getValue();

                List<Map<String, Double>> properties = new ArrayList<>();
                for (Property ability : innerEntry.getValue().getPropertyList()) {
                    Map<String, Double> map = new HashMap<>();
                    String key = null;
                    double value = 0.0;

                    Field[] fields = Property.class.getDeclaredFields();
                    for (Field field : fields) {
                        try {
                            if (field.getType() == String.class) {
                                key = (String) Property.class.getMethod(obtainGetter(field.getName())).invoke(ability);
                            } else {
                                Object object = Property.class.getMethod(obtainGetter(field.getName())).invoke(ability);
                                value = (double) object.getClass().getMethod("getValue").invoke(object);;
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }

                    map.put(key, value);
                    properties.add(map);
                }

                RelicSetEffect setEffect = new RelicSetEffect(entry.getSetID(),
                        Database.getTranslationNoHash(entry.getSkillDesc()),
                        properties,
                        Database.getTranslationNoHash(entry.getAbilityName()),
                        entry.getAbilityParamList().stream().map(AbilityParam::getValue).collect(Collectors.toList())
                );

                setCount.put(innerEntry.getValue().getRequireNum(), setEffect);
            }

            effects.put(Integer.valueOf(outerEntry.getKey()), setCount);
        }

        return effects;
    }
    protected static Map<Integer, Map<Integer, Integer>> loadRelicExp() {
        Map<Integer, Map<Integer, Integer>> exp = new HashMap<>();

        for (Map.Entry<String, Map<String, RelicExpTypeJson>> outerEntry : relicExpType.entrySet()) {
            Map<Integer, Integer> expPerExpType = new HashMap<>();

            for (Map.Entry<String, RelicExpTypeJson> innerEntry : outerEntry.getValue().entrySet()) {
                expPerExpType.put(innerEntry.getValue().getLevel(), innerEntry.getValue().getExp());
            }

            exp.put(Integer.valueOf(outerEntry.getKey()), expPerExpType);
        }

        return exp;
    }
}