package dev.xfj.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public interface Handler {
    default <T> Map<String, T> loadJSON(Class<T> clazz, String file) throws FileNotFoundException {
        return loadJSON(clazz, "C:\\Dev\\StarRailData\\ExcelOutput\\", file);
    }

    default  <T> Map<String, T> loadJSON(Class<T> clazz, String baseDirectory, String file) throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(baseDirectory + file));
        JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        Gson gson = new Gson();
        Type type = clazz != String.class ? TypeToken.getParameterized(Map.class, String.class, clazz).getType() : TypeToken.getParameterized(Map.class, Integer.class, clazz).getType();

        return gson.fromJson(jsonObject, type);
    }
}
