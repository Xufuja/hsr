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
        return String.format("%1$s\n%2$s\n%3$s\n%4$s\nLevel: %5$s/%6$s\n\nHP: %7$s\nAttack: %8$s\nDefense: %9$s\nEidolons: %10$s", avatar.avatarName(), avatar.damageType(), Database.getAvatarPaths().get(avatar.avatarBaseType()).name(), getCurrentAscension(), getCurrentLevel(), avatar.getStatsByAscension(getCurrentAscension()).maxLevel(), getMaxHP(), getMaxAttack(), getMaxDefense(), getCurrentDupe());
    }
}
