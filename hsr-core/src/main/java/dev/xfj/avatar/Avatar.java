package dev.xfj.avatar;

import dev.xfj.item.ItemCount;

import java.util.List;

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
        String avatarDescription
) {
}
