package dev.xfj.character;

import dev.xfj.database.Database;
import dev.xfj.lightcone.LightCone;

public class LightConeData extends Data {
    private final LightCone lightCone;
    private boolean locked;

    public LightConeData(int lightConeId) {
        super();
        this.lightCone = Database.getLightCones().get(lightConeId);
        this.locked = false;
        unlockSuperimpose();
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return String.format("%1$s\n%2$s\n%3$s/%4$s\n\n%5$s\n%6$s\n%7$s\n\n%8$s", lightCone.name(), lightCone.path(), getCurrentLevel(), lightCone.getStatsByAscension(getCurrentAscension()).getMaxLevel(), getMaxHP(), getMaxAttack(), getMaxDefense(), lightCone.getInterpolatedPassive(getCurrentDupe()));
    }
}
