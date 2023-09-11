package dev.xfj;

public class LightCone {
    private int lightConeId;
    private String name;
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
}
