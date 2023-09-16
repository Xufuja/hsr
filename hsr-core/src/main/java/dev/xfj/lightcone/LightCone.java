package dev.xfj.lightcone;

import dev.xfj.database.Database;

import java.util.Map;

public class LightCone {
    private int lightConeId;
    private String name;
    private String backgroundDescription;
    private String description;
    private int rarity;
    private String path;
    private int maxAscension;
    private int maxSuperimpose;
    private int expType;
    private int skillId;
    private int expProvide;
    private int coinCost;
    private boolean defaultUnlock;
    private Map<Integer, LightConePassive> passives;
    private Map<Integer, LightConeStats> stats;

    public enum BaseStatCategory {
        HP,
        ATTACK,
        DEFENSE
    }

    public double getBaseStatAtLevel(BaseStatCategory stat, int ascension, int level) {
        LightConeStats lc = stats.get(ascension);
        return switch (stat) {
            case HP -> lc.getBaseHp() + ((level - 1) * lc.getHpPerLevel());
            case ATTACK -> lc.getBaseAttack() + ((level - 1) * lc.getAttackPerLevel());
            case DEFENSE -> lc.getBaseDefense() + ((level - 1) * lc.getDefensePerLevel());
        };
    }

    public LightConeStats getStatsByAscension(int ascension) {
        return stats.get(ascension);
    }

    public LightConePassive getPassiveBySuperimposition(int superimposition) {
        return passives.get(superimposition);
    }

    public int[] addLevel(int ascension, int level, int exp) {
        int maxLevel = stats.get(ascension).getMaxLevel();

        if (level == maxLevel) {
            return new int[]{level, 0};
        }

        int expRemainder = exp - Database.getLightConeExp().get(expType).get(level);

        if (expRemainder < 0) {
            return new int[]{level, exp};
        }

        if (expRemainder > 0) {
            exp = expRemainder;
        }

        level++;

        if (exp >= Database.getLightConeExp().get(expType).get(level)) {
            int[] additional = addLevel(ascension, level, exp);
            level = additional[0];
            exp = additional[1];
        }

        return new int[]{level, exp};
    }

    public int getLightConeId() {
        return lightConeId;
    }

    public void setLightConeId(int lightConeId) {
        this.lightConeId = lightConeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundDescription() {
        return backgroundDescription;
    }

    public void setBackgroundDescription(String backgroundDescription) {
        this.backgroundDescription = backgroundDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getMaxAscension() {
        return maxAscension;
    }

    public void setMaxAscension(int maxAscension) {
        this.maxAscension = maxAscension;
    }

    public int getMaxSuperimpose() {
        return maxSuperimpose;
    }

    public void setMaxSuperimpose(int maxSuperimpose) {
        this.maxSuperimpose = maxSuperimpose;
    }

    public int getExpType() {
        return expType;
    }

    public void setExpType(int expType) {
        this.expType = expType;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getExpProvide() {
        return expProvide;
    }

    public void setExpProvide(int expProvide) {
        this.expProvide = expProvide;
    }

    public int getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(int coinCost) {
        this.coinCost = coinCost;
    }

    public boolean isDefaultUnlock() {
        return defaultUnlock;
    }

    public void setDefaultUnlock(boolean defaultUnlock) {
        this.defaultUnlock = defaultUnlock;
    }

    public Map<Integer, LightConePassive> getSkills() {
        return passives;
    }

    public void setSkills(Map<Integer, LightConePassive> skills) {
        this.passives = skills;
    }

    public Map<Integer, LightConeStats> getStats() {
        return stats;
    }

    public void setStats(Map<Integer, LightConeStats> stats) {
        this.stats = stats;
    }
}
