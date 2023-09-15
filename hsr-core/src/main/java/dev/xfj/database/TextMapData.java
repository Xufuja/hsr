package dev.xfj.database;

import java.io.FileNotFoundException;
import java.util.Map;

public class TextMapData {
    public static String languageCode = "EN";
    private static Map<String, String> languageMap;

    private TextMapData() {
    }

    public static void init() throws FileNotFoundException {
        languageMap = loadLanguage(languageCode);
    }

    private static Map<String, String> loadLanguage(String code) throws FileNotFoundException {
        return Loader.loadJSON(String.class, "C:\\Dev\\StarRailData\\TextMap\\", String.format("TextMap%1$s.json", code));
    }

    protected static String getTranslation(int key) {
        return languageMap.get(key);
    }
}
