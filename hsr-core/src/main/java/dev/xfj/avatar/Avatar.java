package dev.xfj.avatar;

import dev.xfj.common.Enums;
import dev.xfj.common.Levelable;
import dev.xfj.common.Upgradable;
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
) implements Levelable, Upgradable {

    @Override
    public int expRequiredForLevel(int currentLevel, int expectedLevel) {
        return Utils.expRequiredForLevel(currentLevel, expectedLevel, Database.getAvatarExp(), expGroup);
    }

    @Override
    public double getBaseStatAtLevel(Enums.BaseStatCategory stat, int ascension, int level) {
        AvatarStats avatarStats = stats.get(ascension);
        return switch (stat) {
            case HP -> avatarStats.baseHp() + ((level - 1) * avatarStats.hpPerLevel());
            case ATTACK -> avatarStats.baseAttack() + ((level - 1) * avatarStats.attackPerLevel());
            case DEFENSE -> avatarStats.baseDefense() + ((level - 1) * avatarStats.defensePerLevel());
        };
    }

    @Override
    public int[] addLevel(int ascension, int level, int exp) {
        int maxLevel = stats.get(ascension).maxLevel();
        return Utils.addLevel(maxLevel, level, exp, Database.getAvatarExp(), expGroup);
    }

    @Override
    public int getMaxAscension() {
        return maxAscension;
    }

    @Override
    public int getMaxDupe() {
        return maxEidolon;
    }

    @Override
    public AvatarStats getStatsByAscension(int ascension) {
        return stats.get(ascension);
    }

    @Override
    public int getMaxLevel(int ascension) {
        return getStatsByAscension(ascension).getMaxLevel();
    }

    public AvatarEidolon getEidolonByLevel(int eidolonLevel) {
        return eidolons.get(eidolonLevel);
    }

    public String getInterpolatedEidolonDescription(int eidolonLevel) {
        AvatarEidolon eidolon = getEidolonByLevel(eidolonLevel);
        return Utils.getInterpolatedString(eidolon.description(), eidolon.parameters());
    }

    public String getInterpolatedTraceDescription(AvatarTrace trace) {
        return Utils.getInterpolatedString(trace.traceDescription(), trace.parameters());
    }
}
