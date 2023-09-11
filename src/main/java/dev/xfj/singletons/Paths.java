package dev.xfj.singletons;

import dev.xfj.handlers.AvatarHandler;

import java.io.FileNotFoundException;

public class Paths {
    private static Paths INSTANCE;
    private final AvatarHandler avatarHandler;

    private Paths() throws FileNotFoundException {
        avatarHandler = new AvatarHandler();
    }

    public static void init() throws FileNotFoundException {
        INSTANCE = new Paths();
    }

    public static String getTranslation(String id) {
        return Language.getTranslation(INSTANCE.avatarHandler.getAvatarBaseType().get(id).getBaseTypeText().getHash());
    }
}
