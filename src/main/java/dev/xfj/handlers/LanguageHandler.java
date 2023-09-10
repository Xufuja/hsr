package dev.xfj.handlers;

import java.io.FileNotFoundException;
import java.util.Map;

public class LanguageHandler implements Handler {
    private final Map<String, String> languageMap;

    public LanguageHandler(String languageCode) throws FileNotFoundException {
        this.languageMap = loadLanguage(languageCode);
    }

    private Map<String, String> loadLanguage(String code) throws FileNotFoundException {
        return loadJSON(String.class, "C:\\Dev\\StarRailData\\TextMap\\", String.format("TextMap%1$s.json", code));
    }

    public Map<String, String> getLanguageMap() {
        return languageMap;
    }

}
