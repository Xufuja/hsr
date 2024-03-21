package dev.xfj.relic;

import dev.xfj.Image;

import java.util.Map;

public record RelicSet(
        int setId,
        String setName,
        Image relicSetIcon,
        Map<Integer, RelicSetEffect> setEffects) {
}
