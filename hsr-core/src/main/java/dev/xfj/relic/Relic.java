package dev.xfj.relic;

public record Relic(
        int relicId,
        int setId,
        String type,
        String rarity,
        int mainAffixGroup,
        int subAffixGroup,
        int maxLevel,
        int expType,
        int expProvide,
        int coinCost
) {

}
