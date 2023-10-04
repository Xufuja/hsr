package dev.xfj.avatar;

import dev.xfj.item.ItemCount;

import java.util.List;
import java.util.Map;

public record AvatarTrace(
        int traceId,
        int avatarId,
        int pointType,
        String anchor,
        int maxLevel,
        boolean defaultUnlock,
        List<Integer> prePoints,
        List<Map<String, Double>> statusAddList,
        List<ItemCount> materials,
        List<Integer> levelUpSkillIds,
        String traceName,
        String traceDescription,
        String abilityName,
        String traceTriggerKey,
        List<Double> parameters

) {
}
