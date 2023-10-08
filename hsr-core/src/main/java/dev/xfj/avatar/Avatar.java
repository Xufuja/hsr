package dev.xfj.avatar;

import dev.xfj.common.Enums;
import dev.xfj.common.Utils;
import dev.xfj.database.Database;
import dev.xfj.item.ItemCount;

import java.util.List;
import java.util.Map;

public record Avatar(
        int avatarId,
        String avatarName,
        String avatarFullName,
        String damageType,
        int ultimateCost,
        int expGroup,
        int maxAscension,
        int maxEidolon,
        Map<Integer, AvatarEidolon> eidolons,
        List<ItemCount> rewards,
        List<ItemCount> maxRewards,
        Map<Integer, Map<Integer, AvatarAbility>> abilities,
        String avatarBaseType,
        String avatarDescription,
        Map<Integer, AvatarStats> stats,
        Map<Integer, Map<Integer, AvatarTrace>> traces
) {
    public int expRequiredForLevel(int currentLevel, int expectedLevel) {
        return Utils.expRequiredForLevel(currentLevel, expectedLevel, Database.getAvatarExp(), expGroup);
    }

    public double getBaseStatAtLevel(Enums.BaseStatCategory stat, int ascension, int level) {
        AvatarStats lc = stats.get(ascension);
        return switch (stat) {
            case HP -> lc.baseHp() + ((level - 1) * lc.hpPerLevel());
            case ATTACK -> lc.baseAttack() + ((level - 1) * lc.attackPerLevel());
            case DEFENSE -> lc.baseDefense() + ((level - 1) * lc.defensePerLevel());
        };
    }

    public AvatarStats getStatsByAscension(int ascension) {
        return stats.get(ascension);
    }
    public AvatarEidolon getEidolonByLevel(int eidolonLevel){
        return eidolons.get(eidolonLevel);
    }

    public String getInterpolatedDescription(int eidolonLevel) {
        AvatarEidolon eidolon = getEidolonByLevel(eidolonLevel);
        return Utils.getInterpolatedString(eidolon.description(), eidolon.parameters());
    }
    public int[] addLevel(int ascension, int level, int exp) {
        int maxLevel = stats.get(ascension).maxLevel();
        return Utils.addLevel(maxLevel, level, exp, Database.getAvatarExp(), expGroup);
    }
}
