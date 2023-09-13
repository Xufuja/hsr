package dev.xfj.database;

import dev.xfj.jsonschema2pojo.equipmentatlas.EquipmentAtlasJson;
import dev.xfj.jsonschema2pojo.equipmentconfig.EquipmentConfigJson;
import dev.xfj.jsonschema2pojo.equipmentexpitemconfig.EquipmentExpItemConfigJson;
import dev.xfj.jsonschema2pojo.equipmentexptype.EquipmentExpTypeJson;
import dev.xfj.jsonschema2pojo.equipmentpromotionconfig.EquipmentPromotionConfigJson;
import dev.xfj.jsonschema2pojo.equipmentskillconfig.EquipmentSkillConfigJson;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConeSkill;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentData {
    private static Map<String, EquipmentAtlasJson> equipmentAtlas;
    private static Map<String, EquipmentConfigJson> equipmentConfig;
    private static Map<String, EquipmentExpItemConfigJson> equipmentExpItemConfigJson;
    private static Map<String, EquipmentExpTypeJson> equipmentExpTypeJson;
    private static Map<String, EquipmentPromotionConfigJson> equipmentPromotionConfigJson;
    private static Map<String, Map<String, EquipmentSkillConfigJson>> equipmentSkillConfigJson;

    private EquipmentData() {
    }

    public static void init() throws FileNotFoundException {
        equipmentAtlas = Loader.loadJSON(EquipmentAtlasJson.class);
        equipmentConfig = Loader.loadJSON(EquipmentConfigJson.class);
        equipmentExpItemConfigJson = Loader.loadJSON(EquipmentExpItemConfigJson.class);
        equipmentExpTypeJson = Loader.loadJSON(EquipmentExpTypeJson.class);
        equipmentPromotionConfigJson = Loader.loadJSON(EquipmentPromotionConfigJson.class);
        equipmentSkillConfigJson = Loader.loadNestedJSON(EquipmentSkillConfigJson.class);
    }

    protected static Map<Integer, LightCone> loadLightCones() {
        Map<Integer, LightCone> lightCones = new HashMap<>();

        for (Map.Entry<String, EquipmentConfigJson> entry : equipmentConfig.entrySet()) {
            LightCone lightCone = new LightCone();

            lightCone.setLightConeId(entry.getValue().getEquipmentID());
            lightCone.setName(TextMapData.getTranslation(entry.getValue().getEquipmentName().getHash()));
            lightCone.setBackgroundDescription(Database.getLightConeItems().get(lightCone.getLightConeId()).getBackgroundDescription());
            lightCone.setDescription(Database.getLightConeItems().get(lightCone.getLightConeId()).getDescription());
            lightCone.setRarity(getRarity(entry.getValue().getRarity()));
            lightCone.setPath(AvatarData.getPathName(entry.getValue().getAvatarBaseType()));
            lightCone.setMaxAscension(entry.getValue().getMaxPromotion());
            lightCone.setMaxRefine(entry.getValue().getMaxRank());
            lightCone.setExpType(entry.getValue().getExpType());
            lightCone.setSkillId(entry.getValue().getSkillID());
            lightCone.setExpProvide(entry.getValue().getExpProvide());
            lightCone.setCoinCost(entry.getValue().getCoinCost());
            lightCone.setDefaultUnlock(equipmentAtlas.get(String.valueOf(lightCone.getLightConeId())).isDefaultUnlock());
            lightCone.setSkills(getSkills(lightCone.getSkillId(), lightCone.getMaxRefine()));

            lightCones.put(lightCone.getLightConeId(), lightCone);
        }

        return lightCones;
    }

    private static int getRarity(String rarity) {
        return Integer.parseInt(rarity.replace("CombatPowerLightconeRarity", ""));
    }

    private static EquipmentSkillConfigJson getSkill(int id, int refine) {
        return equipmentSkillConfigJson.get(String.valueOf(id)).get(String.valueOf(refine));
    }

    private static Map<Integer, LightConeSkill> getSkills(int id, int maxRefine) {
        Map<Integer, LightConeSkill> skills = new HashMap<>();

        for (int i = 0; i < maxRefine; i++) {
            int current = i + 1;
            EquipmentSkillConfigJson temp = getSkill(id, current);
            LightConeSkill skill = new LightConeSkill();
            skill.setSkillId(id);
            skill.setName(TextMapData.getTranslation(temp.getSkillName().getHash()));
            skill.setDescription(TextMapData.getTranslation(temp.getSkillDesc().getHash()));
            skill.setAbilityName(temp.getAbilityName());
            List<Double> params = new ArrayList<>();

            for (var param : temp.getParamList()) {
                params.add(param.getValue());
            }

            skill.setParameters(params);
            skills.put(current, skill);

            List<Map<String, Double>> properties = new ArrayList<>();
            for (Object ability : temp.getAbilityProperty()) {
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

        }
        return skills;
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

    public static Map<String, EquipmentExpTypeJson> getEquipmentExpTypeJson() {
        return equipmentExpTypeJson;
    }

    public static Map<String, EquipmentPromotionConfigJson> getEquipmentPromotionConfigJson() {
        return equipmentPromotionConfigJson;
    }

    public static Map<String, Map<String, EquipmentSkillConfigJson>> getEquipmentSkillConfigJson() {
        return equipmentSkillConfigJson;
    }
}
