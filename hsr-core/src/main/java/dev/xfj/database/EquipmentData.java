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
import dev.xfj.lightcone.LightConeSkill;
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
            LightCone lightCone = new LightCone();

            lightCone.setLightConeId(entry.getValue().getEquipmentID());
            lightCone.setName(TextMapData.getTranslation(entry.getValue().getEquipmentName().getHash()));
            lightCone.setBackgroundDescription(Database.lightConeItems.get(lightCone.getLightConeId()).getBackgroundDescription());
            lightCone.setDescription(Database.lightConeItems.get(lightCone.getLightConeId()).getDescription());
            lightCone.setRarity(getRarity(entry.getValue().getRarity()));
            lightCone.setPath(AvatarData.getPathName(entry.getValue().getAvatarBaseType()));
            lightCone.setMaxAscension(entry.getValue().getMaxPromotion());
            lightCone.setMaxRefine(entry.getValue().getMaxRank());
            lightCone.setExpType(entry.getValue().getExpType());
            lightCone.setSkillId(entry.getValue().getSkillID());
            lightCone.setExpProvide(entry.getValue().getExpProvide());
            lightCone.setCoinCost(entry.getValue().getCoinCost());
            lightCone.setDefaultUnlock(equipmentAtlas.get(String.valueOf(lightCone.getLightConeId())).isDefaultUnlock());
            lightCone.setSkills(Database.lightConeSkills.get(lightCone.getLightConeId()));
            lightCone.setStats(Database.lightConeStats.get(lightCone.getLightConeId()));
            lightCones.put(lightCone.getLightConeId(), lightCone);
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
                LightConeStats lightConeStats = new LightConeStats();
                lightConeStats.setLightConeId(innerEntry.getValue().getEquipmentID());
                lightConeStats.setAscension(innerEntry.getValue().getPromotion());
                lightConeStats.setAscensionMaterials(innerEntry.getValue().getPromotionCostList().stream().map(cost -> new ItemCount(cost.getItemID(), cost.getItemNum())).collect(Collectors.toList()));
                lightConeStats.setLevelRequirement(innerEntry.getValue().getPlayerLevelRequire());
                lightConeStats.setEquilibriumLevelRequirement(innerEntry.getValue().getWorldLevelRequire());
                lightConeStats.setMaxLevel(innerEntry.getValue().getMaxLevel());
                lightConeStats.setBaseHp(innerEntry.getValue().getBaseHP().getValue());
                lightConeStats.setHpPerLevel(innerEntry.getValue().getBaseHPAdd().getValue());
                lightConeStats.setBaseAttack(innerEntry.getValue().getBaseAttack().getValue());
                lightConeStats.setAttackPerLevel(innerEntry.getValue().getBaseAttackAdd().getValue());
                lightConeStats.setBaseDefense(innerEntry.getValue().getBaseDefence().getValue());
                lightConeStats.setDefensePerLevel(innerEntry.getValue().getBaseDefenceAdd().getValue());
                statsPerAscension.put(innerEntry.getValue().getPromotion(), lightConeStats);
            }

            stats.put(Integer.valueOf(outerEntry.getKey()), statsPerAscension);
        }

        return stats;
    }

    protected static Map<Integer, Map<Integer, LightConeSkill>> loadLightConeSkills() {
        Map<Integer, Map<Integer, LightConeSkill>> skills = new HashMap<>();

        for (Map.Entry<String, Map<String, EquipmentSkillConfigJson>> outerEntry : equipmentSkillConfigJson.entrySet()) {
            Map<Integer, LightConeSkill> skillsPerRefine = new HashMap<>();

            for (Map.Entry<String, EquipmentSkillConfigJson> innerEntry : outerEntry.getValue().entrySet()) {
                LightConeSkill skill = new LightConeSkill();
                skill.setSkillId(innerEntry.getValue().getSkillID());
                skill.setName(TextMapData.getTranslation(innerEntry.getValue().getSkillName().getHash()));
                skill.setDescription(TextMapData.getTranslation(innerEntry.getValue().getSkillDesc().getHash()));
                skill.setAbilityName(innerEntry.getValue().getAbilityName());
                skill.setParameters(innerEntry.getValue().getParamList().stream().map(Param::getValue).collect(Collectors.toList()));

                List<Map<String, Double>> properties = new ArrayList<>();
                for (Object ability : innerEntry.getValue().getAbilityProperty()) {
                    Map<String, Double> map = new HashMap<>();
                    String key = null;
                    double value = 0.0;

                    for (Map.Entry<String, Object> entry : ((Map<String, Object>) ability).entrySet()) {
                        if (key == null) {
                            key = (String) entry.getValue();
                        } else {
                            value = ((Map<String, Double>) entry.getValue()).get("Value");
                        }
                    }

                    map.put(key, value);
                    properties.add(map);
                }
                skill.setAbilityProperties(properties);
                skillsPerRefine.put(innerEntry.getValue().getLevel(), skill);
            }

            skills.put(Integer.valueOf(outerEntry.getKey()), skillsPerRefine);
        }

        return skills;
    }

    private static int getRarity(String rarity) {
        return Integer.parseInt(rarity.replace("CombatPowerLightconeRarity", ""));
    }

    private static EquipmentSkillConfigJson getSkill(int id, int refine) {
        return equipmentSkillConfigJson.get(String.valueOf(id)).get(String.valueOf(refine));
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
