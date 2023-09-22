package dev.xfj.database;

import dev.xfj.Application;
import dev.xfj.jsonschema2pojo.equipmentconfig.EquipmentConfigJson;
import dev.xfj.jsonschema2pojo.relicbasetype.RelicBaseTypeJson;
import dev.xfj.jsonschema2pojo.reliccomposeconfig.RelicComposeConfigJson;
import dev.xfj.jsonschema2pojo.relicconfig.RelicConfigJson;
import dev.xfj.jsonschema2pojo.relicdatainfo.RelicDataInfoJson;
import dev.xfj.jsonschema2pojo.relicexpitem.RelicExpItemJson;
import dev.xfj.jsonschema2pojo.relicexptype.RelicExpTypeJson;
import dev.xfj.jsonschema2pojo.relicmainaffixconfig.RelicMainAffixConfigJson;
import dev.xfj.jsonschema2pojo.relicsetconfig.RelicSetConfigJson;
import dev.xfj.jsonschema2pojo.relicsetskillconfig.RelicSetSkillConfigJson;
import dev.xfj.jsonschema2pojo.relicsubaffixconfig.RelicSubAffixConfigJson;
import dev.xfj.lightcone.LightCone;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicInfo;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

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
                    entry.getValue().getCoinCost());

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
}