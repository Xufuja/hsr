package dev.xfj.handlers;

import dev.xfj.singletons.Language;
import dev.xfj.LightCone;
import dev.xfj.equipmentatlas.EquipmentAtlasJson;
import dev.xfj.equipmentconfig.EquipmentConfigJson;
import dev.xfj.equipmentexpitemconfig.EquipmentExpItemConfigJson;
import dev.xfj.equipmentexptype.EquipmentExpTypeJson;
import dev.xfj.equipmentpromotionconfig.EquipmentPromotionConfigJson;
import dev.xfj.equipmentskillconfig.EquipmentSkillConfigJson;
import dev.xfj.singletons.Paths;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class EquipmentHandler implements Handler{
    private final Map<String, EquipmentAtlasJson> equipmentAtlas;
    private final Map<String, EquipmentConfigJson> equipmentConfig;
    private final Map<String, EquipmentExpItemConfigJson> equipmentExpItemConfigJson;
    private final Map<String, EquipmentExpTypeJson> equipmentExpTypeJson;
    private final Map<String, EquipmentPromotionConfigJson> equipmentPromotionConfigJson;
    private final Map<String, Map<String, EquipmentSkillConfigJson>> equipmentSkillConfigJson;

    public EquipmentHandler() throws FileNotFoundException {
        this.equipmentAtlas = loadJSON(EquipmentAtlasJson.class);
        this.equipmentConfig = loadJSON(EquipmentConfigJson.class);
        this.equipmentExpItemConfigJson = loadJSON(EquipmentExpItemConfigJson.class);
        this.equipmentExpTypeJson = loadJSON(EquipmentExpTypeJson.class);
        this.equipmentPromotionConfigJson = loadJSON(EquipmentPromotionConfigJson.class);
        this.equipmentSkillConfigJson = loadNestedJSON(EquipmentSkillConfigJson.class);
    }

    public Map<String, EquipmentConfigJson> getEquipmentConfig() {
        return equipmentConfig;
    }
    public Map<Integer, LightCone> getLightCones() {
        Map<Integer, LightCone> lightCones = new HashMap<>();

        for (Map.Entry<String, EquipmentConfigJson> entry : equipmentConfig.entrySet()) {
            LightCone lightCone = new LightCone();

            lightCone.setLightConeId(entry.getValue().getEquipmentID());
            lightCone.setName(Language.getTranslation(entry.getValue().getEquipmentName().getHash()));
            lightCone.setDescription(Language.getTranslation(entry.getValue().getEquipmentDesc().getHash()));
            lightCone.setRarity(getRarity(entry.getValue().getRarity()));
            lightCone.setPath(Paths.getTranslation(entry.getValue().getAvatarBaseType()));
            lightCone.setMaxAscension(entry.getValue().getMaxPromotion());
            lightCone.setMaxRefine(entry.getValue().getMaxRank());
            lightCone.setExpType(entry.getValue().getExpType());
            lightCone.setSkillId(entry.getValue().getSkillID());
            lightCone.setExpProvide(entry.getValue().getExpProvide());
            lightCone.setCoinCost(entry.getValue().getCoinCost());
            lightCone.setDefaultUnlock(equipmentAtlas.get(String.valueOf(lightCone.getLightConeId())).isDefaultUnlock());

            System.out.println(Language.getTranslation(equipmentSkillConfigJson.get("20000").get("1").getSkillName().getHash()));

            lightCones.put(lightCone.getLightConeId(), lightCone);
        }

        return lightCones;
    }
    private int getRarity(String rarity) {
        return Integer.parseInt(rarity.replace("CombatPowerLightconeRarity", ""));
    }
    //private getSkill(int id) {
    //    equipmentSkillConfigJson.get(id)
    //}
}
