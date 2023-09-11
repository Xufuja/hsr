package dev.xfj.singletons;

import dev.xfj.handlers.TextMapHandler;

import java.io.FileNotFoundException;

public class Language {
    private static Language INSTANCE;
    private final TextMapHandler textMapHandler;

    private Language(String languageCode) throws FileNotFoundException {
        textMapHandler = new TextMapHandler(languageCode);
    }

    public static void init(String languageCode) throws FileNotFoundException {
        INSTANCE = new Language(languageCode);
    }

    public static String getTranslation(int id) {
        return INSTANCE.textMapHandler.getTranslation(id);
    }
}
