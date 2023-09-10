package dev.xfj.handlers;

import dev.xfj.avatarbasetype.AvatarBaseTypeJson;

import java.io.FileNotFoundException;
import java.util.Map;

public class AvatarHandler implements Handler{
    private final Map<String, AvatarBaseTypeJson> avatarBaseType;

    public AvatarHandler() throws FileNotFoundException {
        this.avatarBaseType = loadJSON(AvatarBaseTypeJson.class, "AvatarBaseType.json");
    }

    public Map<String, AvatarBaseTypeJson> getAvatarBaseType() {
        return avatarBaseType;
    }
}
