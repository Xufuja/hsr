package dev.xfj.relic;

import dev.xfj.common.Utils;
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
        return Utils.expRequiredForLevel(currentLevel, expectedLevel, Database.getRelicExp(), expType);
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
        return Utils.getInterpolatedPassive(effect.setDescription(), effect.abilityParameters());
    }

    public int[] addLevel(int level, int exp) {
        return Utils.addLevel(maxLevel, level, exp, Database.getRelicExp(), expType);
    }
}
