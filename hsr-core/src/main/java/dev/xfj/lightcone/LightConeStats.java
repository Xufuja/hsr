package dev.xfj.lightcone;

import dev.xfj.item.ItemCount;

import java.util.List;

public class LightConeStats {
    private int lightConeId;
    private int ascension;
    private List<ItemCount> ascensionMaterials;
    private int levelRequirement;
    private int maxLevel;
    private double baseHp;
    private double hpPerLevel;
    private double baseAttack;
    private double attackPerLevel;
    private double baseDefense;
    private double DefensePerLevel;

    public int getLightConeId() {
        return lightConeId;
    }

    public void setLightConeId(int lightConeId) {
        this.lightConeId = lightConeId;
    }

    public int getAscension() {
        return ascension;
    }

    public void setAscension(int ascension) {
        this.ascension = ascension;
    }

    public List<ItemCount> getAscensionMaterials() {
        return ascensionMaterials;
    }

    public void setAscensionMaterials(List<ItemCount> ascensionMaterials) {
        this.ascensionMaterials = ascensionMaterials;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public double getBaseHp() {
        return baseHp;
    }

    public void setBaseHp(double baseHp) {
        this.baseHp = baseHp;
    }

    public double getHpPerLevel() {
        return hpPerLevel;
    }

    public void setHpPerLevel(double hpPerLevel) {
        this.hpPerLevel = hpPerLevel;
    }

    public double getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(double baseAttack) {
        this.baseAttack = baseAttack;
    }

    public double getAttackPerLevel() {
        return attackPerLevel;
    }

    public void setAttackPerLevel(double attackPerLevel) {
        this.attackPerLevel = attackPerLevel;
    }

    public double getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(double baseDefense) {
        this.baseDefense = baseDefense;
    }

    public double getDefensePerLevel() {
        return DefensePerLevel;
    }

    public void setDefensePerLevel(double defensePerLevel) {
        DefensePerLevel = defensePerLevel;
    }
}
