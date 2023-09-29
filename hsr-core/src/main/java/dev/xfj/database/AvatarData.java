package dev.xfj.database;

import dev.xfj.avatar.Avatar;
import dev.xfj.avatar.AvatarPath;
import dev.xfj.avatar.AvatarStats;
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

            Avatar avatar = new Avatar(entry.getValue().getAvatarID(),
                    Database.getTranslation(entry.getValue().getAvatarName().getHash()),
                    Database.getTranslation(entry.getValue().getAvatarFullName().getHash()),
                    entry.getValue().getDamageType(),
                    entry.getValue().getSPNeed().getValue(),
                    entry.getValue().getExpGroup(),
                    entry.getValue().getMaxPromotion(),
                    entry.getValue().getMaxRank(),
                    entry.getValue().getRankIDList(),
                    rewards,
                    rewardsMax,
                    entry.getValue().getSkillList(),
                    entry.getValue().getAvatarBaseType(),
                    Database.getTranslation(entry.getValue().getAvatarDesc().getHash())
            );

            avatars.put(entry.getValue().getAvatarID(), avatar);
        }

        return avatars;
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

}
