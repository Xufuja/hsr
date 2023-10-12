package dev.xfj.player;

import dev.xfj.common.Enums;
import dev.xfj.database.Database;
import dev.xfj.lightcone.LightCone;

public class LightConeData {
    private final LightCone lightCone;
    private int currentLevel;
    private boolean isMaxLevel;
    private int currentExp;
    private int currentAscension;
    private int currentSuperimposition;

    public LightConeData(int lightConeId) {
        this.lightCone = Database.getLightCones().get(lightConeId);
        this.currentLevel = 1;
        this.isMaxLevel = false;
        this.currentExp = 0;
        this.currentAscension = 0;
        this.currentSuperimposition = 0;
    }

    public boolean ascend() {
        if (!isMaxLevel) {
            return false;
        }

        if (currentAscension < lightCone.maxAscension()) {
            currentAscension++;
        }

        isMaxLevel = false;
        return true;
    }

    public void superimpose() {
        if (currentSuperimposition < lightCone.maxSuperimpose()) {
            currentSuperimposition++;
        }
    }

    public boolean levelUp(int exp) {
        if (isMaxLevel) {
            return false;
        }

        int[] data = lightCone.addLevel(currentAscension, currentLevel, currentExp + exp);
        currentLevel = data[0];
        currentExp = data[1];

        if (currentLevel == lightCone.getStatsByAscension(currentAscension).maxLevel()) {
            isMaxLevel = true;
        }

        return true;
    }

    public double getMaxHP() {
        return lightCone.getBaseStatAtLevel(Enums.BaseStatCategory.HP, currentAscension, currentLevel);
    }

    public double getMaxAttack() {
        return lightCone.getBaseStatAtLevel(Enums.BaseStatCategory.ATTACK, currentAscension, currentLevel);
    }

    public double getMaxDefense() {
        return lightCone.getBaseStatAtLevel(Enums.BaseStatCategory.DEFENSE, currentAscension, currentLevel);
    }

    @Override
    public String toString() {
        return "LightConeData{" +
                "currentLevel=" + currentLevel +
                ", isMaxLevel=" + isMaxLevel +
                ", currentExp=" + currentExp +
                ", currentAscension=" + currentAscension +
                ", currentSuperimposition=" + currentSuperimposition +
                '}';
    }

    public LightCone getLightCone() {
        return lightCone;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public boolean isMaxLevel() {
        return isMaxLevel;
    }

    public void setMaxLevel(boolean maxLevel) {
        isMaxLevel = maxLevel;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public int getCurrentAscension() {
        return currentAscension;
    }

    public void setCurrentAscension(int currentAscension) {
        this.currentAscension = currentAscension;
    }

    public int getCurrentSuperimposition() {
        return currentSuperimposition;
    }

    public void setCurrentSuperimposition(int currentSuperimposition) {
        this.currentSuperimposition = currentSuperimposition;
    }
}
