package dev.xfj.avatar;

import dev.xfj.common.Enums;
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
        List<Integer> rankIds,
        List<ItemCount> rewards,
        List<ItemCount> maxRewards,
        List<Integer> skills,
        String avatarBaseType,
        String avatarDescription,
        Map<Integer, AvatarStats> stats
) {
    public int expRequiredForLevel(int currentLevel, int expectedLevel) {
        int expNeeded = 0;
        for (int i = currentLevel; i < expectedLevel; i++) {
            expNeeded += Database.getAvatarExp().get(expGroup).get(i);
        }
        return expNeeded;
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

    public int[] addLevel(int ascension, int level, int exp) {
        int maxLevel = stats.get(ascension).maxLevel();

        if (level == maxLevel) {
            return new int[]{level, 0};
        }

        int expRemainder = exp - Database.getAvatarExp().get(expGroup).get(level);

        if (expRemainder < 0) {
            return new int[]{level, exp};
        }

        if (expRemainder > 0) {
            exp = expRemainder;
        }

        level++;

        if (exp >= Database.getAvatarExp().get(expGroup).get(level)) {
            int[] additional = addLevel(ascension, level, exp);
            level = additional[0];
            exp = additional[1];
        }

        return new int[]{level, exp};
    }

}
