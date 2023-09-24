package dev.xfj.relic;

import dev.xfj.database.Database;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

    public RelicSetEffect getSetEffect(int pieceCount) {
        return switch (pieceCount) {
            case 2 -> setData.setEffects().get(2);
            case 4 -> setData.setEffects().get(4);
            default -> throw new RuntimeException("Unexpected value: " + pieceCount);
        };
    }

    public int expRequiredForLevel(int currentLevel, int expectedLevel) {
        int expNeeded = 0;
        for (int i = currentLevel; i < expectedLevel; i++) {
            expNeeded += Database.getRelicExp().get(expType).get(i);
        }
        return expNeeded;
    }

    public String getInterpolatedPassive(int pieceCount) {
        RelicSetEffect effect = getSetEffect(pieceCount);
        Document document = Jsoup.parse(effect.setDescription());
        String description = document.select("body").text();

        for (int i = 0; i < effect.abilityParameters().size(); i++) {
            String toReplace = String.format("#%1$d", i + 1);
            String replacer = "";
            int currentIndex = description.indexOf(toReplace);

            if (currentIndex != -1) {
                boolean found = false;
                int nextIndex = -1;

                for (int j = currentIndex; !found; j++) {
                    if (description.charAt(j) == ']') {
                        found = true;
                        nextIndex = j;
                    }
                }

                toReplace = description.substring(currentIndex, nextIndex + 1);
                replacer = String.valueOf(effect.abilityParameters().get(i).intValue());

                if (description.charAt(description.indexOf(toReplace) + toReplace.length()) == '%') {
                    replacer = String.format("%1$.0f", effect.abilityParameters().get(i) * 100);
                }

                if (description.charAt(currentIndex + 3) == 'f') {
                    String decimals = "%1$." + String.format("%1$sf", description.charAt(currentIndex + 4));
                    replacer = String.format(decimals, effect.abilityParameters().get(i) * 100);
                }
            }

            description = description.replace(toReplace, replacer);
        }
        return description;
    }

    public int[] addLevel(int level, int exp) {

        if (level == maxLevel) {
            return new int[]{level, 0};
        }

        int expRemainder = exp - Database.getRelicExp().get(expType).get(level);

        if (expRemainder < 0) {
            return new int[]{level, exp};
        }

        if (expRemainder > 0) {
            exp = expRemainder;
        }

        level++;

        if (exp >= Database.getRelicExp().get(expType).get(level)) {
            int[] additional = addLevel(level, exp);
            level = additional[0];
            exp = additional[1];
        }

        return new int[]{level, exp};
    }
}
