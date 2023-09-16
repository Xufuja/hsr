package dev.xfj.lightcone;

import java.util.List;
import java.util.Map;

public record LightConePassive(
        int skillId,
        String name,
        String description,
        String abilityName,
        List<Double> parameters,
        List<Map<String, Double>> abilityProperties) {
}