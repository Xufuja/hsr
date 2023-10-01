package dev.xfj.avatar;

public record AvatarAbility(
        int abilityId,
        String abilityName,
        String abilityTag,
        String abilityTypeDescription,
        int maxLevel,
        String triggerKey,
        String abilityDescription,
        String simpleDescription
) {
}
