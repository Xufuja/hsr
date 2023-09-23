package dev.xfj.relic;

import java.util.List;
import java.util.Map;

public record RelicSetEffect(
        int setId,
        String setDescription,
        List<Map<String, Double>> properties,
        String abilityName,
        List<Double> abilityParameters
) {
}
