package dev.xfj;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public class Application {
    private final Map<String, String> languageMap;

    public Application(String languageCode) throws FileNotFoundException {
        this.languageMap = loadLanguage(languageCode);
    }

    public <T> Map<String, T> loadJSON(Class<T> clazz, String file) throws FileNotFoundException {
        return loadJSON(clazz, "C:\\Dev\\StarRailData\\ExcelOutput\\", file);
    }

    private <T> Map<String, T> loadJSON(Class<T> clazz, String baseDirectory, String file) throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(baseDirectory + file));
        JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        Gson gson = new Gson();
        Type type = clazz != String.class ? TypeToken.getParameterized(Map.class, String.class, clazz).getType() : TypeToken.getParameterized(Map.class, Integer.class, clazz).getType();

        return gson.fromJson(jsonObject, type);
    }

    private Map<String, String> loadLanguage(String code) throws FileNotFoundException {
        return loadJSON(String.class, "C:\\Dev\\StarRailData\\TextMap\\", String.format("TextMap%1$s.json", code));
    }

    public Map<String, String> getLanguageMap() {
        return languageMap;
    }
}
