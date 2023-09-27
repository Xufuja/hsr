package dev.xfj.database;

import dev.xfj.item.ItemCount;
import dev.xfj.jsonschema2pojo.equipmentatlas.EquipmentAtlasJson;
import dev.xfj.jsonschema2pojo.equipmentconfig.EquipmentConfigJson;
import dev.xfj.jsonschema2pojo.equipmentexpitemconfig.EquipmentExpItemConfigJson;
import dev.xfj.jsonschema2pojo.equipmentexptype.EquipmentExpTypeJson;
import dev.xfj.jsonschema2pojo.equipmentpromotionconfig.EquipmentPromotionConfigJson;
import dev.xfj.jsonschema2pojo.equipmentskillconfig.EquipmentSkillConfigJson;
import dev.xfj.jsonschema2pojo.equipmentskillconfig.Param;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConePassive;
import dev.xfj.lightcone.LightConeStats;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EquipmentData {
    private static Map<String, EquipmentAtlasJson> equipmentAtlas;
    private static Map<String, Map<String, EquipmentSkillConfigJson>> equipmentSkillConfigJson;
    private static Map<String, Map<String, EquipmentPromotionConfigJson>> equipmentPromotionConfigJson;
    private static Map<String, EquipmentConfigJson> equipmentConfig;
    private static Map<String, EquipmentExpItemConfigJson> equipmentExpItemConfigJson;
    private static Map<String, Map<String, EquipmentExpTypeJson>> equipmentExpTypeJson;

    private EquipmentData() {
    }

    public static void init() throws FileNotFoundException {
        equipmentAtlas = Loader.loadJSON(EquipmentAtlasJson.class);
        equipmentSkillConfigJson = Loader.loadNestedJSON(EquipmentSkillConfigJson.class);
        equipmentPromotionConfigJson = Loader.loadNestedJSON(EquipmentPromotionConfigJson.class);
        equipmentConfig = Loader.loadJSON(EquipmentConfigJson.class);
        equipmentExpItemConfigJson = Loader.loadJSON(EquipmentExpItemConfigJson.class);
        equipmentExpTypeJson = Loader.loadNestedJSON(EquipmentExpTypeJson.class);
    }

    protected static Map<Integer, LightCone> loadLightCones() {
        Map<Integer, LightCone> lightCones = new HashMap<>();

        for (Map.Entry<String, EquipmentConfigJson> entry : equipmentConfig.entrySet()) {
            LightCone lightCone = new LightCone(entry.getValue().getEquipmentID(),
                    TextMapData.getTranslation(entry.getValue().getEquipmentName().getHash()),
                    Database.lightConeItems.get(entry.getValue().getEquipmentID()).getBackgroundDescription(),
                    Database.lightConeItems.get(entry.getValue().getEquipmentID()).getDescription(),
                    getRarity(entry.getValue().getRarity()),
                    Database.getAvatarPaths().get(entry.getValue().getAvatarBaseType()).name(),
                    entry.getValue().getMaxPromotion(),
                    entry.getValue().getMaxRank(),
                    entry.getValue().getExpType(),
                    entry.getValue().getSkillID(),
                    entry.getValue().getExpProvide(),
                    entry.getValue().getCoinCost(),
                    equipmentAtlas.get(String.valueOf(entry.getValue().getEquipmentID())).isDefaultUnlock(),
                    Database.lightConePassives.get(entry.getValue().getEquipmentID()),
                    Database.lightConeStats.get(entry.getValue().getEquipmentID()));

            lightCones.put(entry.getValue().getEquipmentID(), lightCone);
        }

        return lightCones;
    }

    protected static Map<Integer, Map<Integer, Integer>> loadLightConeExp() {
        Map<Integer, Map<Integer, Integer>> exp = new HashMap<>();

        for (Map.Entry<String, Map<String, EquipmentExpTypeJson>> outerEntry : equipmentExpTypeJson.entrySet()) {
            Map<Integer, Integer> expPerExpType = new HashMap<>();

            for (Map.Entry<String, EquipmentExpTypeJson> innerEntry : outerEntry.getValue().entrySet()) {
                expPerExpType.put(innerEntry.getValue().getLevel(), innerEntry.getValue().getExp());
            }

            exp.put(Integer.valueOf(outerEntry.getKey()), expPerExpType);
        }

        return exp;
    }

    protected static Map<Integer, Map<Integer, LightConeStats>> loadLightConeStats() {
        Map<Integer, Map<Integer, LightConeStats>> stats = new HashMap<>();

        for (Map.Entry<String, Map<String, EquipmentPromotionConfigJson>> outerEntry : equipmentPromotionConfigJson.entrySet()) {
            Map<Integer, LightConeStats> statsPerAscension = new HashMap<>();

            for (Map.Entry<String, EquipmentPromotionConfigJson> innerEntry : outerEntry.getValue().entrySet()) {
                EquipmentPromotionConfigJson entry = innerEntry.getValue();
                LightConeStats lightConeStats = new LightConeStats(entry.getEquipmentID(),
                        entry.getPromotion(),
                        entry.getPromotionCostList().stream().map(cost -> new ItemCount(cost.getItemID(), cost.getItemNum())).collect(Collectors.toList()),
                        entry.getPlayerLevelRequire(),
                        entry.getWorldLevelRequire(),
                        entry.getMaxLevel(),
                        entry.getBaseHP().getValue(),
                        entry.getBaseHPAdd().getValue(),
                        entry.getBaseAttack().getValue(),
                        entry.getBaseAttackAdd().getValue(),
                        entry.getBaseDefence().getValue(),
                        entry.getBaseDefenceAdd().getValue());

                statsPerAscension.put(innerEntry.getValue().getPromotion(), lightConeStats);
            }

            stats.put(Integer.valueOf(outerEntry.getKey()), statsPerAscension);
        }

        return stats;
    }

    protected static Map<Integer, Map<Integer, LightConePassive>> loadLightConePassives() {
        Map<Integer, Map<Integer, LightConePassive>> passives = new HashMap<>();

        for (Map.Entry<String, Map<String, EquipmentSkillConfigJson>> outerEntry : equipmentSkillConfigJson.entrySet()) {
            Map<Integer, LightConePassive> passivePerSuperimpose = new HashMap<>();

            for (Map.Entry<String, EquipmentSkillConfigJson> innerEntry : outerEntry.getValue().entrySet()) {
                EquipmentSkillConfigJson entry = innerEntry.getValue();

                List<Map<String, Double>> properties = new ArrayList<>();
                for (Object ability : innerEntry.getValue().getAbilityProperty()) {
                    Map<String, Double> map = new HashMap<>();
                    String key = null;
                    double value = 0.0;

                    for (Map.Entry<String, Object> prop : ((Map<String, Object>) ability).entrySet()) {
                        if (key == null) {
                            key = (String) prop.getValue();
                        } else {
                            value = ((Map<String, Double>) prop.getValue()).get("Value");
                        }
                    }

                    map.put(key, value);
                    properties.add(map);
                }

                LightConePassive passive = new LightConePassive(entry.getSkillID(),
                        Database.getTranslation(entry.getSkillName().getHash()),
                        Database.getTranslation(entry.getSkillDesc().getHash()),
                        entry.getAbilityName(),
                        entry.getParamList().stream().map(Param::getValue).collect(Collectors.toList()),
                        properties);

                passivePerSuperimpose.put(innerEntry.getValue().getLevel(), passive);
            }

            passives.put(Integer.valueOf(outerEntry.getKey()), passivePerSuperimpose);
        }

        return passives;
    }

    private static int getRarity(String rarity) {
        return Integer.parseInt(rarity.replace("CombatPowerLightconeRarity", ""));
    }


    public static Map<String, EquipmentAtlasJson> getEquipmentAtlas() {
        return equipmentAtlas;
    }

    public static Map<String, EquipmentConfigJson> getEquipmentConfig() {
        return equipmentConfig;
    }

    public static Map<String, EquipmentExpItemConfigJson> getEquipmentExpItemConfigJson() {
        return equipmentExpItemConfigJson;
    }

    public static Map<String, Map<String, EquipmentExpTypeJson>> getEquipmentExpTypeJson() {
        return equipmentExpTypeJson;
    }

    public static Map<String, Map<String, EquipmentPromotionConfigJson>> getEquipmentPromotionConfigJson() {
        return equipmentPromotionConfigJson;
    }

    public static Map<String, Map<String, EquipmentSkillConfigJson>> getEquipmentSkillConfigJson() {
        return equipmentSkillConfigJson;
    }
}
