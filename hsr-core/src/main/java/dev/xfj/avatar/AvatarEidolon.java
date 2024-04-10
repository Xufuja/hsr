package dev.xfj.avatar;

import dev.xfj.Image;
import dev.xfj.item.ItemCount;

import java.util.List;

public record AvatarEidolon(
        int eidolonId,
        int eidolon,
        String name,
        String description,
        Image eidolonIcon,
        List<String> eidolonAbility,
        List<ItemCount> unlockCost,
        List<Double> parameters
) {
}
