package dev.xfj.avatar;

import dev.xfj.Image;

import java.util.List;

public record AvatarAbility(
        int abilityId,
        String abilityName,
        String abilityTag,
        String abilityTypeDescription,
        Image abilityIcon,
        int maxLevel,
        String triggerKey,
        String abilityDescription,
        String simpleDescription,
        List<Integer> ratedSkillTreeIds,
        List<Integer> ratedRankIds,
        List<Integer> extraEffectIds,
        List<Integer> simpleExtraEffectIds,
        List<Integer> stanceList,
        List<Double> parameters,
        List<Double> simpleParameters,
        String stanceDamageType,
        String attackType,
        String skillEffect
) {
}
