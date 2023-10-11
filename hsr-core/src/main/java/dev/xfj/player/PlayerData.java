package dev.xfj.player;

import dev.xfj.avatar.Avatar;
import dev.xfj.common.Enums;
import dev.xfj.database.Database;

public class PlayerData {
    private final Avatar avatar;
    private int currentLevel;
    private boolean isMaxLevel;
    private int currentExp;
    private int currentAscension;
    private int currentEidolon;

    public PlayerData(int avatarId) {
        this.avatar = Database.getAvatars().get(avatarId);
        this.currentLevel = 1;
        this.isMaxLevel = false;
        this.currentExp = 0;
        this.currentAscension = 0;
        this.currentEidolon = 0;
    }

    public boolean ascend() {
        if (!isMaxLevel) {
            return false;
        }

        if (currentAscension < avatar.maxAscension()) {
            currentAscension++;
        }

        isMaxLevel = false;
        return true;
    }

    public void unlockEidolon() {
        if (currentEidolon < avatar.maxEidolon()) {
            currentEidolon++;
        }
    }

    public boolean levelUp(int exp) {
        if (isMaxLevel) {
            return false;
        }

        int[] data = avatar.addLevel(currentAscension, currentLevel, currentExp + exp);
        currentLevel = data[0];
        currentExp = data[1];

        if (currentLevel == avatar.getStatsByAscension(currentAscension).maxLevel()) {
            isMaxLevel = true;
        }

        return true;
    }

    public double getMaxHP() {
        return avatar.getBaseStatAtLevel(Enums.BaseStatCategory.HP, currentAscension, currentLevel);
    }

    public double getMaxAttack() {
        return avatar.getBaseStatAtLevel(Enums.BaseStatCategory.ATTACK, currentAscension, currentLevel);
    }

    public double getMaxDefense() {
        return avatar.getBaseStatAtLevel(Enums.BaseStatCategory.DEFENSE, currentAscension, currentLevel);
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "currentLevel=" + currentLevel +
                ", currentExp=" + currentExp +
                ", currentAscension=" + currentAscension +
                ", currentEidolon=" + currentEidolon +
                '}';
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
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

    public int getCurrentEidolon() {
        return currentEidolon;
    }

    public void setCurrentEidolon(int currentEidolon) {
        this.currentEidolon = currentEidolon;
    }
}
