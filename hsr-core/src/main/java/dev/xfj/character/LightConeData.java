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
        return String.format("%1$s\n%2$s\nAscension: %3$s\nLevel: %4$s/%5$s\n\nHP: %6$s\nAttack: %7$s\n Defense: %8$s\n\nSuperimposition: %9$s\n\n%10$s\n%11$s", lightCone.name(), lightCone.path(), getCurrentAscension(), getCurrentLevel(), lightCone.getStatsByAscension(getCurrentAscension()).getMaxLevel(), getMaxHP(), getMaxAttack(), getMaxDefense(), getCurrentDupe(), lightCone.getPassiveBySuperimposition(getCurrentDupe()).name(), lightCone.getInterpolatedPassive(getCurrentDupe()));
    }
}
