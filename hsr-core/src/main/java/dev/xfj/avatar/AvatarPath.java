package dev.xfj.avatar;

import dev.xfj.Image;

public record AvatarPath(
        String id,
        String name,
        String description,
        Image pathIcon
) {

}
