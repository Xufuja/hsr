package dev.xfj.character;

import dev.xfj.avatar.Avatar;
import dev.xfj.database.Database;

public class CharacterData extends Data {
    private final Avatar avatar;

    public CharacterData(int avatarId) {
        super();
        this.avatar = Database.getAvatars().get(avatarId);
    }

    public boolean ascend() {
        return super.ascend(avatar);
    }

    public void unlockEidolon() {
        super.unlockDupe(avatar);
    }

    public boolean levelUp(int exp) {
        return super.levelUp(avatar, exp);
    }

    public double getMaxHP() {
        return super.getMaxHP(avatar);
    }

    public double getMaxAttack() {
        return super.getMaxAttack(avatar);
    }

    public double getMaxDefense() {
        return super.getMaxDefense(avatar);
    }

    public Avatar getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "currentLevel=" + getCurrentLevel() +
                ", currentExp=" + getCurrentExp() +
                ", currentAscension=" + getCurrentAscension() +
                ", currentEidolon=" + getCurrentDupe() +
                '}';
    }
}
