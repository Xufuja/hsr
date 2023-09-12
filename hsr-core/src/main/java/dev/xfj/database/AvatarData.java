package dev.xfj.database;

import dev.xfj.avatarbasetype.AvatarBaseTypeJson;

import java.io.FileNotFoundException;
import java.util.Map;

public class AvatarData {
    private static Map<String, AvatarBaseTypeJson> avatarBaseType;

    private AvatarData() {
    }

    public static void init() throws FileNotFoundException {
        avatarBaseType = Loader.loadJSON(AvatarBaseTypeJson.class);
    }

    public static Map<String, AvatarBaseTypeJson> getAvatarBaseType() {
        return avatarBaseType;
    }

    public static String getPathName(String id) {
        return TextMapData.getTranslation(getAvatarBaseType().get(id).getBaseTypeText().getHash());
    }
}
