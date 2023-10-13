package dev.xfj.player;

import dev.xfj.common.Enums;
import dev.xfj.common.Levelable;

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

    public boolean ascend(Levelable levelable) {
        if (!isMaxLevel) {
            return false;
        }

        if (currentAscension < levelable.getMaxAscension()) {
            currentAscension++;
        }

        isMaxLevel = false;
        return true;
    }

    public void unlockDupe(Levelable levelable) {
        if (currentDupe < levelable.getMaxDupe()) {
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

        if (currentLevel == levelable.getStatsByAscension(currentAscension).getMaxLevel()) {
            isMaxLevel = true;
        }

        return true;
    }

    public double getMaxHP(Levelable levelable) {
        return levelable.getBaseStatAtLevel(Enums.BaseStatCategory.HP, currentAscension, currentLevel);
    }

    public double getMaxAttack(Levelable levelable) {
        return levelable.getBaseStatAtLevel(Enums.BaseStatCategory.ATTACK, currentAscension, currentLevel);
    }

    public double getMaxDefense(Levelable levelable) {
        return levelable.getBaseStatAtLevel(Enums.BaseStatCategory.DEFENSE, currentAscension, currentLevel);
    }

    @Override
    public String toString() {
        return "Data{" +
                "currentLevel=" + currentLevel +
                ", isMaxLevel=" + isMaxLevel +
                ", currentExp=" + currentExp +
                ", currentAscension=" + currentAscension +
                ", currentDupe=" + currentDupe +
                '}';
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
