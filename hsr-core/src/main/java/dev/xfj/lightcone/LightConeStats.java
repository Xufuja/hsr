package dev.xfj.lightcone;

import dev.xfj.common.Stats;
import dev.xfj.item.ItemCount;

import java.util.List;

public record LightConeStats(
        int lightConeId,
        int ascension,
        List<ItemCount> ascensionMaterials,
        int levelRequirement,
        int equilibriumLevelRequirement,
        int maxLevel,
        double baseHp,
        double hpPerLevel,
        double baseAttack,
        double attackPerLevel,
        double baseDefense,
        double defensePerLevel) implements Stats {
    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
}

