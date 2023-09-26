package dev.xfj.relic;

public record RelicSubStats(
        int groupId,
        int affixId,
        String property,
        double baseValue,
        double valuePerStep,
        int stepNumber
) {
}
