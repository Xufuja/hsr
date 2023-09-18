package dev.xfj.lightcone;

import dev.xfj.database.Database;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

public record LightCone(
        int lightConeId,
        String name,
        String backgroundDescription,
        String description,
        int rarity,
        String path,
        int maxAscension,
        int maxSuperimpose,
        int expType,
        int skillId,
        int expProvide,
        int coinCost,
        boolean defaultUnlock,
        Map<Integer, LightConePassive> passives,
        Map<Integer, LightConeStats> stats) {

    public enum BaseStatCategory {
        HP,
        ATTACK,
        DEFENSE
    }

    public int expRequiredForLevel(int currentLevel, int expectedLevel) {
        int expNeeded = 0;
        for (int i = currentLevel; i < expectedLevel; i++) {
            expNeeded += Database.getLightConeExp().get(expType).get(i);
        }
        return expNeeded;
    }

    public double getBaseStatAtLevel(BaseStatCategory stat, int ascension, int level) {
        LightConeStats lc = stats.get(ascension);
        return switch (stat) {
            case HP -> lc.baseHp() + ((level - 1) * lc.hpPerLevel());
            case ATTACK -> lc.baseAttack() + ((level - 1) * lc.attackPerLevel());
            case DEFENSE -> lc.baseDefense() + ((level - 1) * lc.defensePerLevel());
        };
    }

    public LightConeStats getStatsByAscension(int ascension) {
        return stats.get(ascension);
    }

    public LightConePassive getPassiveBySuperimposition(int superimposition) {
        return passives.get(superimposition);
    }

    public String getInterpolatedPassive(int superimposition) {
        LightConePassive passive = getPassiveBySuperimposition(superimposition);
        Document document = Jsoup.parse(passive.description());
        String description = document.select("body").text();

        for (int i = 0; i < passive.parameters().size(); i++) {
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
                replacer = String.valueOf(passive.parameters().get(i).intValue());

                if (description.charAt(description.indexOf(toReplace) + toReplace.length()) == '%') {
                    replacer = String.format("%1$.0f", passive.parameters().get(i) * 100);
                }

                if (description.charAt(currentIndex + 3) == 'f') {
                    String decimals = "%1$." + String.format("%1$sf", description.charAt(currentIndex + 4));
                    replacer = String.format(decimals, passive.parameters().get(i) * 100);
                }
            }

            description = description.replace(toReplace, replacer);
        }
        return description;
    }

    public int[] addLevel(int ascension, int level, int exp) {
        int maxLevel = stats.get(ascension).maxLevel();

        if (level == maxLevel) {
            return new int[]{level, 0};
        }

        int expRemainder = exp - Database.getLightConeExp().get(expType).get(level);

        if (expRemainder < 0) {
            return new int[]{level, exp};
        }

        if (expRemainder > 0) {
            exp = expRemainder;
        }

        level++;

        if (exp >= Database.getLightConeExp().get(expType).get(level)) {
            int[] additional = addLevel(ascension, level, exp);
            level = additional[0];
            exp = additional[1];
        }

        return new int[]{level, exp};
    }
}
