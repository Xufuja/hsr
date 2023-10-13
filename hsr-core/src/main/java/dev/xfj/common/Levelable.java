package dev.xfj.common;

public interface Levelable {
    int expRequiredForLevel(int currentLevel, int expectedLevel);
    double getBaseStatAtLevel(Enums.BaseStatCategory stat, int ascension, int level);
    int[] addLevel(int ascension, int level, int exp);
    int getMaxAscension();
    int getMaxDupe();
    Stats getStatsByAscension(int ascension);
}
