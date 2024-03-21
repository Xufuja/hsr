package dev.xfj.database;

import dev.xfj.Image;
import dev.xfj.avatar.*;
import dev.xfj.item.ItemCount;
import dev.xfj.jsonschema2pojo.avataratlas.AvatarAtlasJson;
import dev.xfj.jsonschema2pojo.avatarbasetype.AvatarBaseTypeJson;
import dev.xfj.jsonschema2pojo.avatarbreakdamage.AvatarBreakDamageJson;
import dev.xfj.jsonschema2pojo.avatarcamp.AvatarCampJson;
import dev.xfj.jsonschema2pojo.avatarconfig.AvatarConfigJson;
import dev.xfj.jsonschema2pojo.avatarconfig.Reward;
import dev.xfj.jsonschema2pojo.avatarconfig.RewardListMax;
import dev.xfj.jsonschema2pojo.avatarconfigtrial.AvatarConfigTrialJson;
import dev.xfj.jsonschema2pojo.avatardemoconfig.AvatarDemoConfigJson;
import dev.xfj.jsonschema2pojo.avatardemoguide.AvatarDemoGuideJson;
import dev.xfj.jsonschema2pojo.avatardetailtabconfig.AvatarDetailTabConfigJson;
import dev.xfj.jsonschema2pojo.avatarexpitemconfig.AvatarExpItemConfigJson;
import dev.xfj.jsonschema2pojo.avatarplayericon.AvatarPlayerIconJson;
import dev.xfj.jsonschema2pojo.avatarpromotionconfig.AvatarPromotionConfigJson;
import dev.xfj.jsonschema2pojo.avatarpromotionconfigtrial.AvatarPromotionConfigTrialJson;
import dev.xfj.jsonschema2pojo.avatarpromotionreward.AvatarPromotionRewardJson;
import dev.xfj.jsonschema2pojo.avatarpropertyconfig.AvatarPropertyConfigJson;
import dev.xfj.jsonschema2pojo.avatarrankconfig.AvatarRankConfigJson;
import dev.xfj.jsonschema2pojo.avatarrankconfigtrial.AvatarRankConfigTrialJson;
import dev.xfj.jsonschema2pojo.avatarrarity.AvatarRarityJson;
import dev.xfj.jsonschema2pojo.avatarrelicrecommend.AvatarRelicRecommendJson;
import dev.xfj.jsonschema2pojo.avatarskillconfig.AvatarSkillConfigJson;
import dev.xfj.jsonschema2pojo.avatarskillconfig.Param;
import dev.xfj.jsonschema2pojo.avatarskillconfig.ShowStance;
import dev.xfj.jsonschema2pojo.avatarskillconfig.SimpleParam;
import dev.xfj.jsonschema2pojo.avatarskillconfigtrial.AvatarSkillConfigTrialJson;
import dev.xfj.jsonschema2pojo.avatarskilltreeconfig.AvatarSkillTreeConfigJson;
import dev.xfj.jsonschema2pojo.avatarskilltreeconfigtrial.AvatarSkillTreeConfigTrialJson;
import dev.xfj.jsonschema2pojo.avatarvo.AvatarVOJson;
import dev.xfj.jsonschema2pojo.exptype.ExpTypeJson;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.xfj.database.Database.RESOURCE_PATH;

public class AvatarData {
    private static Map<String, AvatarAtlasJson> avatarAtlas;
    private static Map<String, AvatarBaseTypeJson> avatarBaseType;
    private static Map<String, AvatarBreakDamageJson> avatarBreakDamage;
    private static Map<String, AvatarCampJson> avatarCamp;
    private static Map<String, AvatarConfigJson> avatarConfig;
    private static Map<String, AvatarConfigTrialJson> avatarConfigTrial;
    private static Map<String, AvatarDemoConfigJson> avatarDemoConfig;
    private static Map<String, AvatarDemoGuideJson> avatarDemoGuide;
    private static Map<String, AvatarDetailTabConfigJson> avatarDetailTabConfig;
    private static Map<String, AvatarExpItemConfigJson> avatarExpItemConfig;
    private static Map<String, Map<String, ExpTypeJson>> avatarExpTypeConfig;
    private static Map<String, AvatarPlayerIconJson> avatarPlayerIcon;
    private static Map<String, Map<String, AvatarPromotionConfigJson>> avatarPromotionConfig;
    private static Map<String, Map<String, AvatarPromotionConfigTrialJson>> avatarPromotionConfigTrial;
    private static Map<String, AvatarPromotionRewardJson> avatarPromotionReward;
    private static Map<String, AvatarPropertyConfigJson> avatarPropertyConfig;
    private static Map<String, AvatarRankConfigJson> avatarRankConfig;
    private static Map<String, AvatarRankConfigTrialJson> avatarRankConfigTrial;
    private static Map<String, AvatarRarityJson> avatarRarity;
    private static Map<String, AvatarRelicRecommendJson> avatarRelicRecommend;
    private static Map<String, Map<String, AvatarSkillConfigJson>> avatarSkillConfig;
    private static Map<String, Map<String, AvatarSkillConfigTrialJson>> avatarSkillConfigTrial;
    private static Map<String, Map<String, AvatarSkillTreeConfigJson>> avatarSkillTreeConfig;
    private static Map<String, Map<String, AvatarSkillTreeConfigTrialJson>> avatarSkillTreeConfigTrial;
    private static Map<String, AvatarVOJson> avatarVOJsonMap;

    private AvatarData() {
    }

    public static void init() throws FileNotFoundException {
        avatarAtlas = Loader.loadJSON(AvatarAtlasJson.class);
        avatarBaseType = Loader.loadJSON(AvatarBaseTypeJson.class);
        avatarBreakDamage = Loader.loadJSON(AvatarBreakDamageJson.class);
        avatarCamp = Loader.loadJSON(AvatarCampJson.class);
        avatarConfig = Loader.loadJSON(AvatarConfigJson.class);
        avatarConfigTrial = Loader.loadJSON(AvatarConfigTrialJson.class);
        avatarDemoConfig = Loader.loadJSON(AvatarDemoConfigJson.class);
        avatarDemoGuide = Loader.loadJSON(AvatarDemoGuideJson.class);
        avatarDetailTabConfig = Loader.loadJSON(AvatarDetailTabConfigJson.class);
        avatarExpItemConfig = Loader.loadJSON(AvatarExpItemConfigJson.class);
        avatarExpTypeConfig = Loader.loadNestedJSON(ExpTypeJson.class);
        avatarPlayerIcon = Loader.loadJSON(AvatarPlayerIconJson.class);
        avatarPromotionConfig = Loader.loadNestedJSON(AvatarPromotionConfigJson.class);
        avatarPromotionConfigTrial = Loader.loadNestedJSON(AvatarPromotionConfigTrialJson.class);
        avatarPromotionReward = Loader.loadJSON(AvatarPromotionRewardJson.class);
        avatarPropertyConfig = Loader.loadJSON(AvatarPropertyConfigJson.class);
        avatarRankConfig = Loader.loadJSON(AvatarRankConfigJson.class);
        avatarRankConfigTrial = Loader.loadJSON(AvatarRankConfigTrialJson.class);
        avatarRarity = Loader.loadJSON(AvatarRarityJson.class);
        avatarRelicRecommend = Loader.loadJSON(AvatarRelicRecommendJson.class);
        avatarSkillConfig = Loader.loadNestedJSON(AvatarSkillConfigJson.class);
        avatarSkillConfigTrial = Loader.loadNestedJSON(AvatarSkillConfigTrialJson.class);
        avatarSkillTreeConfig = Loader.loadNestedJSON(AvatarSkillTreeConfigJson.class);
        avatarSkillTreeConfigTrial = Loader.loadNestedJSON(AvatarSkillTreeConfigTrialJson.class);
        avatarVOJsonMap = Loader.loadJSON(AvatarVOJson.class);
    }

    protected static Map<String, AvatarPath> loadAvatarPaths() {
        Map<String, AvatarPath> paths = new HashMap<>();

        for (Map.Entry<String, AvatarBaseTypeJson> entry : avatarBaseType.entrySet()) {
            AvatarPath avatarPath = new AvatarPath(entry.getValue().getId(),
                    Database.getTranslation(entry.getValue().getBaseTypeText().getHash()),
                    Database.getTranslation(entry.getValue().getBaseTypeDesc().getHash())
            );
            paths.put(entry.getValue().getId(), avatarPath);
        }
        return paths;
    }

    protected static Map<String, AvatarStatType> loadAvatarStatTypes() {
        Map<String, AvatarStatType> types = new HashMap<>();

        for (Map.Entry<String, AvatarPropertyConfigJson> entry : avatarPropertyConfig.entrySet()) {
            AvatarStatType avatarStatType = new AvatarStatType(entry.getValue().getPropertyType(),
                    Database.getTranslation(entry.getValue().getPropertyName().getHash()),
                    Database.getTranslation(entry.getValue().getPropertyNameSkillTree().getHash()),
                    Database.getTranslation(entry.getValue().getPropertyNameRelic().getHash()),
                    Database.getTranslation(entry.getValue().getPropertyNameFilter().getHash())
            );
            types.put(entry.getValue().getPropertyType(), avatarStatType);
        }

        return types;
    }

    protected static Map<Integer, Avatar> loadAvatars() {
        Map<Integer, Avatar> avatars = new HashMap<>();

        for (Map.Entry<String, AvatarConfigJson> entry : avatarConfig.entrySet()) {
            List<ItemCount> rewards = new ArrayList<>();
            List<ItemCount> rewardsMax = new ArrayList<>();

            for (Reward itemEntry : entry.getValue().getRewardList()) {
                ItemCount itemReturn = new ItemCount(itemEntry.getItemID(), itemEntry.getItemNum());
                rewards.add(itemReturn);
            }

            for (RewardListMax itemEntry : entry.getValue().getRewardListMax()) {
                ItemCount itemReturn = new ItemCount(itemEntry.getItemID(), itemEntry.getItemNum());
                rewardsMax.add(itemReturn);
            }

            Map<Integer, Map<Integer, AvatarAbility>> abilities = new HashMap<>();

            for (Integer ability : entry.getValue().getSkillList()) {
                abilities.put(ability, Database.getAvatarAbilities().get(ability));
            }

            Map<Integer, Map<Integer, AvatarTrace>> traces = Database.getAvatarTraces().entrySet()
                    .stream()
                    .filter(trace -> Integer.parseInt(String.valueOf(trace.getKey()).substring(0, 4)) == entry.getValue().getAvatarID())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            Map<Integer, AvatarEidolon> eidolons = new HashMap<>();
            for (Integer eidolon : entry.getValue().getRankIDList()) {
                AvatarEidolon current = Database.avatarEidolons.get(eidolon);
                eidolons.put(current.eidolon(), current);
            }

            String icon = entry.getValue().getAvatarSideIconPath();

            Avatar avatar = new Avatar(entry.getValue().getAvatarID(),
                    Database.getTranslation(entry.getValue().getAvatarName().getHash()),
                    Database.getTranslation(entry.getValue().getAvatarFullName().getHash()),
                    new Image(RESOURCE_PATH + "\\icon\\avatar\\" + icon.substring(icon.lastIndexOf("/") + 1)),
                    entry.getValue().getDamageType(),
                    entry.getValue().getSPNeed().getValue(),
                    entry.getValue().getExpGroup(),
                    entry.getValue().getMaxPromotion(),
                    entry.getValue().getMaxRank(),
                    entry.getValue().getRarity(),
                    eidolons,
                    rewards,
                    rewardsMax,
                    abilities,
                    entry.getValue().getAvatarBaseType(),
                    Database.getTranslation(entry.getValue().getAvatarDesc().getHash()),
                    Database.getAvatarStats().get(entry.getValue().getAvatarID()),
                    traces
            );

            avatars.put(entry.getValue().getAvatarID(), avatar);
        }

        return avatars;
    }

    protected static Map<Integer, Map<Integer, Integer>> loadAvatarExp() {
        Map<Integer, Map<Integer, Integer>> exp = new HashMap<>();

        for (Map.Entry<String, Map<String, ExpTypeJson>> outerEntry : avatarExpTypeConfig.entrySet()) {
            Map<Integer, Integer> expPerExpType = new HashMap<>();

            for (Map.Entry<String, ExpTypeJson> innerEntry : outerEntry.getValue().entrySet()) {
                expPerExpType.put(innerEntry.getValue().getLevel(), innerEntry.getValue().getExp());
            }

            exp.put(Integer.valueOf(outerEntry.getKey()), expPerExpType);
        }

        return exp;
    }

    protected static Map<Integer, Map<Integer, AvatarStats>> loadAvatarStats() {
        Map<Integer, Map<Integer, AvatarStats>> stats = new HashMap<>();

        for (Map.Entry<String, Map<String, AvatarPromotionConfigJson>> outerEntry : avatarPromotionConfig.entrySet()) {
            Map<Integer, AvatarStats> statsPerAscension = new HashMap<>();

            for (Map.Entry<String, AvatarPromotionConfigJson> innerEntry : outerEntry.getValue().entrySet()) {
                AvatarPromotionConfigJson entry = innerEntry.getValue();
                AvatarStats avatarStats = new AvatarStats(entry.getAvatarID(),
                        entry.getPromotion(),
                        entry.getPromotionCostList().stream().map(cost -> new ItemCount(cost.getItemID(), cost.getItemNum())).collect(Collectors.toList()),
                        entry.getMaxLevel(),
                        entry.getPlayerLevelRequire(),
                        entry.getWorldLevelRequire(),
                        entry.getAttackBase().getValue(),
                        entry.getAttackAdd().getValue(),
                        entry.getDefenceBase().getValue(),
                        entry.getDefenceAdd().getValue(),
                        entry.getHPBase().getValue(),
                        entry.getHPAdd().getValue(),
                        entry.getSpeedBase().getValue(),
                        entry.getCriticalChance().getValue(),
                        entry.getCriticalDamage().getValue(),
                        entry.getBaseAggro().getValue()
                );

                statsPerAscension.put(innerEntry.getValue().getPromotion(), avatarStats);
            }

            stats.put(Integer.valueOf(outerEntry.getKey()), statsPerAscension);
        }

        return stats;
    }

    protected static Map<Integer, Map<Integer, AvatarTrace>> loadAvatarTraces() {
        Map<Integer, Map<Integer, AvatarTrace>> traces = new HashMap<>();

        for (Map.Entry<String, Map<String, AvatarSkillTreeConfigJson>> outerEntry : avatarSkillTreeConfig.entrySet()) {
            Map<Integer, AvatarTrace> tracesPerLevel = new HashMap<>();

            for (Map.Entry<String, AvatarSkillTreeConfigJson> innerEntry : outerEntry.getValue().entrySet()) {
                AvatarSkillTreeConfigJson entry = innerEntry.getValue();

                List<Map<String, Double>> statuses = new ArrayList<>();
                for (Object status : innerEntry.getValue().getStatusAddList()) {
                    Map<String, Double> map = new HashMap<>();
                    String key = null;
                    double value = 0.0;

                    for (Map.Entry<String, Object> prop : ((Map<String, Object>) status).entrySet()) {
                        if (key == null) {
                            key = (String) prop.getValue();
                        } else {
                            value = ((Map<String, Double>) prop.getValue()).get("Value");
                        }
                    }

                    map.put(key, value);
                    statuses.add(map);
                }

                List<ItemCount> materials = new ArrayList<>();
                for (Object material : entry.getMaterialList()) {
                    int id = 0;
                    int count = 0;

                    for (Map.Entry<String, Object> prop : ((Map<String, Object>) material).entrySet()) {
                        if (id == 0) {
                            id = ((Double) prop.getValue()).intValue();
                        } else {
                            count = ((Double) prop.getValue()).intValue();
                        }
                    }
                    materials.add(new ItemCount(id, count));
                }

                List<Double> parameters = new ArrayList<>();
                for (Object parameter : entry.getParamList()) {
                    for (Map.Entry<String, Object> prop : ((Map<String, Object>) parameter).entrySet()) {
                        parameters.add((Double) prop.getValue());
                    }
                }

                AvatarTrace avatarStats = new AvatarTrace(entry.getPointID(),
                        entry.getAvatarID(),
                        entry.getPointType(),
                        entry.getAnchor(),
                        entry.getMaxLevel(),
                        entry.isDefaultUnlock(),
                        entry.getPrePoint().stream().map(object -> ((Double) object).intValue()).collect(Collectors.toList()),
                        statuses,
                        materials,
                        entry.getLevelUpSkillID(),
                        Database.getTranslationNoHash(entry.getPointName()),
                        Database.getTranslationNoHash(entry.getPointDesc()),
                        Database.getTranslationNoHash(entry.getAbilityName()),
                        Database.getTranslation(entry.getPointTriggerKey().getHash()),
                        parameters
                );

                if (avatarStats.traceName() == null) {
                    continue;
                }

                tracesPerLevel.put(innerEntry.getValue().getLevel(), avatarStats);
            }

            traces.put(Integer.valueOf(outerEntry.getKey()), tracesPerLevel);
        }

        return traces;
    }

    protected static Map<Integer, Map<Integer, AvatarAbility>> loadAvatarAbilities() {
        Map<Integer, Map<Integer, AvatarAbility>> abilities = new HashMap<>();

        for (Map.Entry<String, Map<String, AvatarSkillConfigJson>> outerEntry : avatarSkillConfig.entrySet()) {
            Map<Integer, AvatarAbility> abilityPerLevel = new HashMap<>();

            for (Map.Entry<String, AvatarSkillConfigJson> innerEntry : outerEntry.getValue().entrySet()) {
                AvatarSkillConfigJson entry = innerEntry.getValue();

                AvatarAbility ability = new AvatarAbility(entry.getSkillID(),
                        Database.getTranslation(entry.getSkillName().getHash()),
                        Database.getTranslation(entry.getSkillTag().getHash()),
                        Database.getTranslation(entry.getSkillTypeDesc().getHash()),
                        entry.getMaxLevel(),
                        entry.getSkillTriggerKey(),
                        Database.getTranslation(entry.getSkillDesc().getHash()),
                        Database.getTranslation(entry.getSimpleSkillDesc().getHash()),
                        (List<Integer>) (List<?>) entry.getRatedSkillTreeID(),
                        (List<Integer>) (List<?>) entry.getRatedRankID(),
                        (List<Integer>) (List<?>) entry.getExtraEffectIDList(),
                        (List<Integer>) (List<?>) entry.getSimpleExtraEffectIDList(),
                        entry.getShowStanceList().stream().map(ShowStance::getValue).collect(Collectors.toList()),
                        entry.getParamList().stream().map(Param::getValue).collect(Collectors.toList()),
                        entry.getSimpleParamList().stream().map(SimpleParam::getValue).collect(Collectors.toList()),
                        entry.getStanceDamageType(),
                        entry.getAttackType(),
                        entry.getSkillEffect()
                );

                abilityPerLevel.put(innerEntry.getValue().getLevel(), ability);
            }

            abilities.put(Integer.valueOf(outerEntry.getKey()), abilityPerLevel);
        }

        return abilities;
    }

    protected static Map<Integer, AvatarEidolon> loadAvatarEidolons() {
        Map<Integer, AvatarEidolon> paths = new HashMap<>();

        for (Map.Entry<String, AvatarRankConfigJson> entry : avatarRankConfig.entrySet()) {
            AvatarEidolon avatarEidolon = new AvatarEidolon(entry.getValue().getRankID(),
                    entry.getValue().getRank(),
                    Database.getTranslationNoHash(entry.getValue().getName()),
                    Database.getTranslationNoHash(entry.getValue().getDesc()),
                    entry.getValue().getRankAbility().stream().map(ability -> (String) ability).collect(Collectors.toList()),
                    entry.getValue().getUnlockCost().stream().map(cost -> new ItemCount(cost.getItemID(), cost.getItemNum())).collect(Collectors.toList()),
                    entry.getValue().getParam().stream().map(dev.xfj.jsonschema2pojo.avatarrankconfig.Param::getValue).collect(Collectors.toList())

            );
            paths.put(entry.getValue().getRankID(), avatarEidolon);
        }
        return paths;
    }
}
