package dev.xfj.common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public class Utils {
    public static int expRequiredForLevel(int currentLevel, int expectedLevel, Map<Integer, Map<Integer, Integer>> expMap, int type) {
        int expNeeded = 0;
        for (int i = currentLevel; i < expectedLevel; i++) {
            expNeeded += expMap.get(type).get(i);
        }
        return expNeeded;
    }

    public static int[] addLevel(int maxLevel, int level, int exp, Map<Integer, Map<Integer, Integer>> expMap, int type) {

        if (level == maxLevel) {
            return new int[]{level, 0};
        }

        int expRemainder = exp - expMap.get(type).get(level);

        if (expRemainder < 0) {
            return new int[]{level, exp};
        }

        if (expRemainder > 0) {
            exp = expRemainder;
        }

        level++;

        if (exp >= expMap.get(type).get(level)) {
            int[] additional = addLevel(maxLevel, level, exp, expMap, type);
            level = additional[0];
            exp = additional[1];
        }

        return new int[]{level, exp};
    }

    public static String getInterpolatedString(String text, List<Double> parameters) {
        Document document = Jsoup.parse(text);
        String description = document.select("body").text();

        for (int i = 0; i < parameters.size(); i++) {
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
                replacer = String.valueOf(parameters.get(i).intValue());

                if (description.charAt(description.indexOf(toReplace) + toReplace.length()) == '%') {
                    replacer = String.format("%1$.0f", parameters.get(i) * 100);
                }

                if (description.charAt(currentIndex + 3) == 'f') {
                    String decimals = "%1$." + String.format("%1$sf", description.charAt(currentIndex + 4));
                    replacer = String.format(decimals, parameters.get(i) * 100);
                }
            }

            description = description.replace(toReplace, replacer);
        }
        return description;
    }
}
