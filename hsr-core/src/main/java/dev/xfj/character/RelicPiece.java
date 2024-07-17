package dev.xfj.character;

import dev.xfj.database.Database;
import dev.xfj.relic.Relic;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class RelicPiece extends Data {
    private final Relic relic;
    private String mainStat;
    private Map<String, List<Double>> subStats;
    private boolean locked;

    public RelicPiece(int relicId) {
        super();
        this.relic = Database.getRelics().get(relicId);
        this.subStats = new HashMap<>();
        this.locked = false;
    }

    public boolean levelUp(int exp) {
        if (super.levelUp(relic, exp)) {
            if (super.getCurrentLevel() % 3 == 0) {
                if (subStats.keySet().size() < 4) {
                    List<String> possible = getRelic().getPossibleSubStats();

                    possible.removeAll(subStats.keySet());

                    String selected = possible.get(new Random().nextInt(possible.size()));
                    subStats.put(selected, Stream.of(doRoll(selected)).collect(toCollection(ArrayList::new)));
                } else {
                    Object[] possible = subStats.keySet().toArray();
                    String selected = (String) possible[(new Random().nextInt(possible.length))];
                    subStats.get(selected).add(doRoll(selected));
                }
            }
            return true;
        }
        return false;
    }

    public double doRoll(String selected) {
        int rollLevel = new Random().nextInt(1, getRelic().subStats().get(getRelic().getAffixIdBySubStat(selected)).stepNumber() + 1);
        return getRelic().rollSubStat(selected, rollLevel);
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
                result.add(String.format("%1$s: %2$s", Database.getAvatarStatTypes().get(entry.getKey()).relicDescription(), convertToPercentage(entry.getValue())));
            } else {
                result.add(String.format("%1$s: %2$s", Database.getAvatarStatTypes().get(entry.getKey()).relicDescription(), sumUp(entry.getValue())));
            }
        }
        return String.join("\n", result);
    }

    private String convertToPercentage(List<Double> stats) {
        return String.format("%.2f", stats.stream().mapToDouble(Double::doubleValue).sum() * 100);
    }

    private int sumUp(List<Double> stats) {
        return ((Double) stats.stream().mapToDouble(Double::doubleValue).sum()).intValue();
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return String.format("%1$s (%2$s)\n\nLevel: %3$s/%4$s\nExp: %5$s\n\n%6$s\n\n%7$s", getRelic().name(), isLocked() ? "Locked" : "Unlocked", getCurrentLevel(), getRelic().maxLevel(), getCurrentExp(), Database.getAvatarStatTypes().get(mainStat).relicDescription() + ": " + (!mainStat.contains("Delta") ? String.format("%.2f", getRelic().getBaseMainStatAtLevel(mainStat, getCurrentLevel()) * 100) : getRelic().getBaseMainStatAtLevel(mainStat, getCurrentLevel())), subStatsToString());
    }
}
