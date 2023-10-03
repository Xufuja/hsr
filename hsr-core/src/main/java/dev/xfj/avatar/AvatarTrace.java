package dev.xfj.avatar;

import dev.xfj.item.ItemCount;

import java.util.List;

public record AvatarTrace(
        int traceId,
        int avatarId,
        int pointType,
        String anchor,
        int maxLevel,
        boolean defaultUnlock,
        List<Integer> prePoints,
        List<Integer> statusAddList,
        List<ItemCount> materials,
        List<Integer> levelUpSkillIds,
        String traceName,
        String traceDescription,
        String abilityName,
        String traceTriggerKey,
        List<Double> parameters

) {
}
