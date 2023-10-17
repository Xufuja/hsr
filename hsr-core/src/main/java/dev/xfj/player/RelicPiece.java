package dev.xfj.player;

import dev.xfj.database.Database;
import dev.xfj.relic.Relic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelicPiece extends Data {
    private final Relic relic;
    private String mainStat;
    private Map<String, List<Double>> subStats;

    public RelicPiece(int relicId) {
        super();
        this.relic = Database.getRelics().get(relicId);
        subStats = new HashMap<>();
    }

    public boolean levelUp(int exp) {
        return super.levelUp(relic, exp);
    }

    public Relic getRelic() {
        return relic;
    }

    public String getMainStat() {
        return mainStat;
    }

    public void setMainStat(String mainStat) {
        this.mainStat = mainStat;
    }

    public Map<String, List<Double>> getSubStats() {
        return subStats;
    }

    public void setSubStats(Map<String, List<Double>> subStats) {
        this.subStats = subStats;
    }

    private String subStatsToString() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, List<Double>> entry : subStats.entrySet()) {
            if (!entry.getKey().contains("Delta")) {
                result.add(String.format("%1$s: %2$s", entry.getKey(), convertToPercentage(entry.getValue())));
            } else {
                result.add(String.format("%1$s: %2$s", entry.getKey(), sumUp(entry.getValue())));
            }
        }
        return String.join(", ", result);
    }

    private String convertToPercentage(List<Double> stats) {
        return String.format("%.2f", stats.stream().mapToDouble(Double::doubleValue).sum() * 100);
    }

    private int sumUp(List<Double> stats) {
        return ((Double) stats.stream().mapToDouble(Double::doubleValue).sum()).intValue();
    }

    @Override
    public String toString() {
        return "RelicPiece{" +
                "currentLevel=" + getCurrentLevel() +
                ", isMaxLevel=" + isMaxLevel() +
                ", currentExp=" + getCurrentExp() +
                ", mainStat='" + mainStat + "': " + (!mainStat.contains("Delta") ? String.format("%.2f", getRelic().getBaseMainStatAtLevel(mainStat, getCurrentLevel()) * 100) : getRelic().getBaseMainStatAtLevel(mainStat, getCurrentLevel())) +
                ", subStats=" + subStatsToString() +
                '}';
    }
}
