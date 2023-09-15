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
    private int maxRefine;
    private int expType;
    private int skillId;
    private int expProvide;
    private int coinCost;
    private boolean defaultUnlock;
    private Map<Integer, LightConeSkill> skills;
    private Map<Integer, LightConeStats> stats;

    public int[] addLevel(int ascension, int level, int exp) {
        LightConeStats current = stats.get(ascension);
        int maxLevel = current.getMaxLevel();

        int currentLevel = level;
        int currentExp = 0;
        int leftOverExp = 0;

        if (currentLevel < maxLevel) {
            currentExp += exp;
            int expRemainder = currentExp - Database.getLightConeExp().get(expType).get(level);;

            if (expRemainder >= 0) {
                currentLevel++;
                if (expRemainder > 0) {
                    leftOverExp = expRemainder;
                }
            } else {
                leftOverExp = exp;
            }

            if (leftOverExp > 0 && leftOverExp >= Database.getLightConeExp().get(expType).get(level + 1)) {
                int[] additional = addLevel(ascension, currentLevel, leftOverExp);
                currentLevel = additional[0];
                leftOverExp = additional[1];
            }

            return new int[]{currentLevel, leftOverExp};
        } else {
            return new int[]{currentLevel, 0};
        }
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

    public int getMaxRefine() {
        return maxRefine;
    }

    public void setMaxRefine(int maxRefine) {
        this.maxRefine = maxRefine;
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

    public Map<Integer, LightConeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Map<Integer, LightConeSkill> skills) {
        this.skills = skills;
    }

    public Map<Integer, LightConeStats> getStats() {
        return stats;
    }

    public void setStats(Map<Integer, LightConeStats> stats) {
        this.stats = stats;
    }
}
