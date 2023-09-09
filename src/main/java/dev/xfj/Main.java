package dev.xfj;

import dev.xfj.avatarbasetype.AvatarBaseTypeJson;
import dev.xfj.equipmentconfig.EquipmentConfigJson;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        //ClassGenerator classGenerator = new ClassGenerator("C:\\Dev\\StarRailData\\ExcelOutput\\", "C:\\Dev\\hsr\\src\\generated\\");
        //classGenerator.createClasses();
        Application application = new Application("EN");
        Map<String, AvatarBaseTypeJson> avatarBaseTypeJsonMap = application.loadJSON(AvatarBaseTypeJson.class, "AvatarBaseType.json");
        Map<String, EquipmentConfigJson> equipmentConfigJsonMap = application.loadJSON(EquipmentConfigJson.class, "EquipmentConfig.json");
        equipmentConfigJsonMap.values().stream().map(equipmentConfigJson -> String.format("Light Cone ID: %1$s\r\n\t\tName: %2$s\r\n\t\tPath: %3$s",
                equipmentConfigJson.getEquipmentID(),
                application.getLanguageMap().get(equipmentConfigJson.getEquipmentName().getHash()),
                application.getLanguageMap().get(avatarBaseTypeJsonMap.get(equipmentConfigJson.getAvatarBaseType()).getBaseTypeText().getHash()))).forEach(System.out::println);
    }
}