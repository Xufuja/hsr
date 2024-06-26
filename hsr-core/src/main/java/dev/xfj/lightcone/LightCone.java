package dev.xfj.lightcone;

import dev.xfj.Image;
import dev.xfj.common.Enums;
import dev.xfj.common.Levelable;
import dev.xfj.common.Upgradable;
import dev.xfj.common.Utils;
import dev.xfj.database.Database;

import java.util.Map;

public record LightCone(
        int lightConeId,
        String name,
        String backgroundDescription,
        String description,
        Image lightConeIcon,
        Image lightConeImage,
        int rarity,
        String path,
        Image pathIcon,
        int maxAscension,
        int maxSuperimpose,
        int expType,
        int skillId,
        int expProvide,
        int coinCost,
        boolean defaultUnlock,
        Map<Integer, LightConePassive> passives,
        Map<Integer, LightConeStats> stats)
        implements Levelable, Upgradable {

    @Override
    public int expRequiredForLevel(int currentLevel, int expectedLevel) {
        return Utils.expRequiredForLevel(currentLevel, expectedLevel, Database.getLightConeExp(), expType);
    }

    @Override
    public double getBaseStatAtLevel(Enums.BaseStatCategory stat, int ascension, int level) {
        LightConeStats lc = stats.get(ascension);
        return switch (stat) {
            case HP -> lc.baseHp() + ((level - 1) * lc.hpPerLevel());
            case ATTACK -> lc.baseAttack() + ((level - 1) * lc.attackPerLevel());
            case DEFENSE -> lc.baseDefense() + ((level - 1) * lc.defensePerLevel());
        };
    }

    @Override
    public int[] addLevel(int ascension, int level, int exp) {
        int maxLevel = stats.get(ascension).maxLevel();
        return Utils.addLevel(maxLevel, level, exp, Database.getLightConeExp(), expType);
    }

    @Override
    public int getMaxAscension() {
        return maxAscension;
    }

    @Override
    public int getMaxDupe() {
        return maxSuperimpose;
    }

    @Override
    public LightConeStats getStatsByAscension(int ascension) {
        return stats.get(ascension);
    }

    @Override
    public int getMaxLevel(int ascension) {
        return getStatsByAscension(ascension).getMaxLevel();
    }

    public LightConePassive getPassiveBySuperimposition(int superimposition) {
        return passives.get(superimposition);
    }

    public String getInterpolatedPassive(int superimposition) {
        LightConePassive passive = getPassiveBySuperimposition(superimposition);
        return Utils.getInterpolatedString(passive.description(), passive.parameters());
    }
}
