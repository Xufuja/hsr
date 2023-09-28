package dev.xfj.avatar;

import dev.xfj.item.ItemCount;

import java.util.List;

public record AvatarStats(
        int avatarId,
        int ascension,
        List<ItemCount> ascensionMaterials,
        int maxLevel,
        int levelRequirement,
        int equilibriumLevelRequirement,
        double baseAttack,
        double attackPerLevel,
        double baseDefense,
        double defensePerLevel,
        double baseHp,
        double hpPerLevel,
        int speed,
        double critRate,
        double critDamage,
        int taunt
) {
}
