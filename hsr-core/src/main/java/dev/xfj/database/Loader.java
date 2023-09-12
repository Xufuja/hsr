package dev.xfj.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public interface Loader {
    static <T> Map<String, T> loadJSON(Class<T> clazz) throws FileNotFoundException {
        String file = clazz.getSimpleName().replace("Json", ".json");
        return loadJSON(clazz, "C:\\Dev\\StarRailData\\ExcelOutput\\", file);
    }

    static <T> Map<String, Map<String, T>> loadNestedJSON(Class<T> clazz) throws FileNotFoundException {
        String file = clazz.getSimpleName().replace("Json", ".json");
        return loadNestedJSON(clazz, "C:\\Dev\\StarRailData\\ExcelOutput\\", file);
    }

    static <T> Map<String, T> loadJSON(Class<T> clazz, String file) throws FileNotFoundException {
        return loadJSON(clazz, "C:\\Dev\\StarRailData\\ExcelOutput\\", file);
    }

    static <T> Map<String, T> loadJSON(Class<T> clazz, String baseDirectory, String file) throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(baseDirectory + file));
        JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Type type = clazz != String.class ? TypeToken.getParameterized(Map.class, String.class, clazz).getType() : TypeToken.getParameterized(Map.class, Integer.class, clazz).getType();

        Map<String, T> result = gson.fromJson(jsonObject, type);
        System.out.println(String.format("Loaded: %1$7d entries from %2$s", result.keySet().size(), file));
        return result;
    }

    static <T> Map<String, Map<String, T>> loadNestedJSON(Class<T> clazz, String baseDirectory, String file) throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(baseDirectory + file));
        JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Map<String, Map<String, T>>>() {
                        }.getType(),
                        new NestedMapTypeAdapter<>(clazz))
                .setPrettyPrinting()
                .create();

        Type type = new TypeToken<Map<String, Map<String, T>>>() {
        }.getType();

        Map<String, Map<String, T>> result = gson.fromJson(jsonObject, type);
        System.out.println(String.format("Loaded: %1$7d entries from %2$s", result.keySet().size(), file));
        return result;
    }
}
