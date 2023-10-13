package dev.xfj.player;

import dev.xfj.database.Database;
import dev.xfj.lightcone.LightCone;

public class LightConeData extends Data {
    private final LightCone lightCone;

    public LightConeData(int lightConeId) {
        super();
        this.lightCone = Database.getLightCones().get(lightConeId);
    }

    public boolean ascend() {
        return super.ascend(lightCone);
    }

    public void unlockSuperimpose() {
        super.unlockDupe(lightCone);
    }

    public boolean levelUp(int exp) {
        return super.levelUp(lightCone, exp);
    }

    public double getMaxHP() {
        return super.getMaxHP(lightCone);
    }

    public double getMaxAttack() {
        return super.getMaxAttack(lightCone);
    }

    public double getMaxDefense() {
        return super.getMaxDefense(lightCone);
    }

    public LightCone getLightCone() {
        return lightCone;
    }

    @Override
    public String toString() {
        return "LightConeData{" +
                "currentLevel=" + getCurrentLevel() +
                ", isMaxLevel=" + isMaxLevel() +
                ", currentExp=" + getCurrentExp() +
                ", currentAscension=" + getCurrentAscension() +
                ", currentSuperimposition=" + getCurrentDupe() +
                '}';
    }
}
