package dev.xfj.database;

import dev.xfj.lightcone.LightCone;

import java.util.Map;

public class Database {
    protected static Map<Integer, LightCone> lightCones;

    public static Map<Integer, LightCone> getLightCones() {
        return lightCones;
    }
}
