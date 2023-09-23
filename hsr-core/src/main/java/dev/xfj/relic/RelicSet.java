package dev.xfj.relic;

import java.util.Map;

public record RelicSet(
        int setId,
        String setName,
        Map<Integer, RelicSetEffect> setEffects) {
}
