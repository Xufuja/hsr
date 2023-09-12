package dev.xfj.database;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class NestedMapTypeAdapter<T> implements JsonSerializer<Map<String, Map<String, T>>>,
        JsonDeserializer<Map<String, Map<String, T>>> {

    private final Class<T> clazz;

    public NestedMapTypeAdapter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public JsonElement serialize(Map<String, Map<String, T>> inputMap, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        inputMap.forEach((outerKey, outerValue) -> {
            JsonObject innerObject = new JsonObject();

            outerValue.forEach((innerKey, innerValue) -> {
                JsonElement value = context.serialize(innerValue, clazz);
                innerObject.add(innerKey, value);
            });

            jsonObject.add(outerKey, innerObject);
        });

        return jsonObject;
    }

    @Override
    public Map<String, Map<String, T>> deserialize(JsonElement inputJson, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<String, Map<String, T>> result = new HashMap<>();

        if (inputJson.isJsonObject()) {
            JsonObject jsonObject = inputJson.getAsJsonObject();

            jsonObject.entrySet().forEach(outerEntry -> {
                String outerKey = outerEntry.getKey();
                JsonObject innerObject = outerEntry.getValue().getAsJsonObject();
                Map<String, T> innerMap = new HashMap<>();

                innerObject.entrySet().forEach(innerEntry -> {
                    String innerKey = innerEntry.getKey();
                    T value = context.deserialize(innerEntry.getValue(), clazz);
                    innerMap.put(innerKey, value);
                });

                result.put(outerKey, innerMap);
            });
        }
        return result;
    }
}
