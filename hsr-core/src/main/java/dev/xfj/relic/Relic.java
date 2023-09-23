package dev.xfj.relic;

public record Relic(
        int relicId,
        String name,
        String backgroundDescription,
        String backgroundStoryContent,
        int setId,
        String type,
        String rarity,
        int mainAffixGroup,
        int subAffixGroup,
        int maxLevel,
        int expType,
        int expProvide,
        int coinCost,
        RelicSet setData
) {
    public enum SetBonus {
        PIECES_2,
        PIECES_4
    }

    public RelicSetEffect getSetEffect(SetBonus pieceCount) {
        return switch (pieceCount) {
            case PIECES_2 -> setData.setEffects().get(2);
            case PIECES_4 -> setData.setEffects().get(4);
        };
    }
}
