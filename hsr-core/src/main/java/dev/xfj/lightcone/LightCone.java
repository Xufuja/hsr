package dev.xfj.lightcone;

import dev.xfj.database.Database;

import java.util.Map;

public record LightCone(
        int lightConeId,
        String name,
        String backgroundDescription,
        String description,
        int rarity,
        String path,
        int maxAscension,
        int maxSuperimpose,
        int expType,
        int skillId,
        int expProvide,
        int coinCost,
        boolean defaultUnlock,
        Map<Integer, LightConePassive> passives,
        Map<Integer, LightConeStats> stats) {

    public enum BaseStatCategory {
        HP,
        ATTACK,
        DEFENSE
    }

    public double getBaseStatAtLevel(BaseStatCategory stat, int ascension, int level) {
        LightConeStats lc = stats.get(ascension);
        return switch (stat) {
            case HP -> lc.baseHp() + ((level - 1) * lc.hpPerLevel());
            case ATTACK -> lc.baseAttack() + ((level - 1) * lc.attackPerLevel());
            case DEFENSE -> lc.baseDefense() + ((level - 1) * lc.defensePerLevel());
        };
    }

    public LightConeStats getStatsByAscension(int ascension) {
        return stats.get(ascension);
    }

    public LightConePassive getPassiveBySuperimposition(int superimposition) {
        return passives.get(superimposition);
    }

    public int[] addLevel(int ascension, int level, int exp) {
        int maxLevel = stats.get(ascension).maxLevel();

        if (level == maxLevel) {
            return new int[]{level, 0};
        }

        int expRemainder = exp - Database.getLightConeExp().get(expType).get(level);

        if (expRemainder < 0) {
            return new int[]{level, exp};
        }

        if (expRemainder > 0) {
            exp = expRemainder;
        }

        level++;

        if (exp >= Database.getLightConeExp().get(expType).get(level)) {
            int[] additional = addLevel(ascension, level, exp);
            level = additional[0];
            exp = additional[1];
        }

        return new int[]{level, exp};
    }
}
