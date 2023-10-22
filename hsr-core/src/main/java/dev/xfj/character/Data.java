package dev.xfj.character;

import dev.xfj.common.Enums;
import dev.xfj.common.Levelable;
import dev.xfj.common.Upgradable;

public abstract class Data {
    private int currentLevel;
    private boolean isMaxLevel;
    private int currentExp;
    private int currentAscension;
    private int currentDupe;

    protected Data() {
        this.currentLevel = 1;
        this.isMaxLevel = false;
        this.currentExp = 0;
        this.currentAscension = 0;
        this.currentDupe = 0;
    }

    public boolean ascend(Upgradable upgradable) {
        if (!isMaxLevel) {
            return false;
        }

        if (currentAscension < upgradable.getMaxAscension()) {
            currentAscension++;
        }

        isMaxLevel = false;
        return true;
    }

    public void unlockDupe(Upgradable upgradable) {
        if (currentDupe < upgradable.getMaxDupe()) {
            currentDupe++;
        }
    }

    public boolean levelUp(Levelable levelable, int exp) {
        if (isMaxLevel) {
            return false;
        }

        int[] data = levelable.addLevel(currentAscension, currentLevel, currentExp + exp);
        currentLevel = data[0];
        currentExp = data[1];

        if (currentLevel == levelable.getMaxLevel(currentAscension)) {
            isMaxLevel = true;
        }

        return true;
    }

    public double getMaxHP(Upgradable upgradable) {
        return upgradable.getBaseStatAtLevel(Enums.BaseStatCategory.HP, currentAscension, currentLevel);
    }

    public double getMaxAttack(Upgradable upgradable) {
        return upgradable.getBaseStatAtLevel(Enums.BaseStatCategory.ATTACK, currentAscension, currentLevel);
    }

    public double getMaxDefense(Upgradable upgradable) {
        return upgradable.getBaseStatAtLevel(Enums.BaseStatCategory.DEFENSE, currentAscension, currentLevel);
    }


    public int getCurrentLevel() {
        return currentLevel;
    }

    public boolean isMaxLevel() {
        return isMaxLevel;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public int getCurrentAscension() {
        return currentAscension;
    }

    public int getCurrentDupe() {
        return currentDupe;
    }
}
