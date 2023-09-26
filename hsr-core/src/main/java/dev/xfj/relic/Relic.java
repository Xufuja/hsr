package dev.xfj.relic;

import dev.xfj.database.Database;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        RelicSet setData,
        Map<Integer, RelicMainStats> mainStats,
        Map<Integer, RelicSubStats> subStats
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

    public List<String> getPossibleMainStats() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<Integer, RelicMainStats> entry : mainStats.entrySet()) {
            result.add(entry.getValue().property());
        }
        return result;
    }

    public List<String> getPossibleSubStats() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<Integer, RelicSubStats> entry : subStats.entrySet()) {
            result.add(entry.getValue().property());
        }
        return result;
    }

    public int getAffixIdByMainStat(String mainStat) {
        int result = -1;
        for (Map.Entry<Integer, RelicMainStats> entry : mainStats.entrySet()) {
            if (entry.getValue().property().equals(mainStat)) {
                result = entry.getKey();
                break;
            }
        }
        return result;
    }

    public int getAffixIdBySubStat(String subStat) {
        int result = -1;
        for (Map.Entry<Integer, RelicSubStats> entry : subStats.entrySet()) {
            if (entry.getValue().property().equals(subStat)) {
                result = entry.getKey();
                break;
            }
        }
        return result;
    }

    public double getBaseMainStatAtLevel(String mainStat, int level) {
        RelicMainStats relic = mainStats.get(getAffixIdByMainStat(mainStat));
        return relic.baseValue() + (level * relic.valuePerLevel());
    }

    public double getBaseStatAtRoll(String subStat, int roll) {
        if (roll > maxLevel() / 3) {
            throw new RuntimeException("Maximum amount of rolls: " + maxLevel() / 3);
        }
        double result = rollSubStat(subStat, "HIGH");
        for (int i = 0; i < roll; i++) {
            result += rollSubStat(subStat, "HIGH");
        }
        return result;
    }

    public double rollSubStat(String subStat, String step) {
        RelicSubStats relic = subStats.get(getAffixIdBySubStat(subStat));
        return switch (step) {
            case "LOW" -> relic.baseValue();
            case "MED" -> relic.baseValue() + relic.valuePerStep();
            case "HIGH" -> relic.baseValue() + (relic.valuePerStep() * 2);
            default -> throw new RuntimeException("Invalid roll!");
        };
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
