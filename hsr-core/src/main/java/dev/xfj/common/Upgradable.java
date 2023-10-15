package dev.xfj.common;

public interface Upgradable {
    double getBaseStatAtLevel(Enums.BaseStatCategory stat, int ascension, int level);
    int getMaxAscension();
    int getMaxDupe();
    Stats getStatsByAscension(int ascension);
}
