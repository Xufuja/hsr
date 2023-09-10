package dev.xfj.handlers;

import dev.xfj.equipmentconfig.EquipmentConfigJson;

import java.io.FileNotFoundException;
import java.util.Map;

public class EquipmentHandler implements Handler{
    private final Map<String, EquipmentConfigJson> equipmentConfig;

    public EquipmentHandler() throws FileNotFoundException {
        this.equipmentConfig = loadJSON(EquipmentConfigJson.class, "EquipmentConfig.json");
    }

    public Map<String, EquipmentConfigJson> getEquipmentConfig() {
        return equipmentConfig;
    }
}
