package dev.xfj.common;

public interface Levelable {
    int expRequiredForLevel(int currentLevel, int expectedLevel);
    int[] addLevel(int ascension, int level, int exp);
    int getMaxLevel(int ascension);
}
