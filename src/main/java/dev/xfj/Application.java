package dev.xfj;

import dev.xfj.handlers.AvatarHandler;
import dev.xfj.handlers.EquipmentHandler;
import dev.xfj.handlers.LanguageHandler;

import java.io.FileNotFoundException;
import java.util.Map;

public class Application {
    private final LanguageHandler languageHandler;

    public Application(String languageCode) throws FileNotFoundException {
        languageHandler = new LanguageHandler(languageCode);
    }

    public void run() throws FileNotFoundException {
        AvatarHandler avatarHandler = new AvatarHandler();
        EquipmentHandler equipmentHandler = new EquipmentHandler();
        equipmentHandler.getEquipmentConfig().values().stream().map(equipmentConfigJson -> String.format("Light Cone ID: %1$s\r\n\t\tName: %2$s\r\n\t\tPath: %3$s",
                equipmentConfigJson.getEquipmentID(),
                getLanguage().get(equipmentConfigJson.getEquipmentName().getHash()),
                getLanguage().get(avatarHandler.getAvatarBaseType().get(equipmentConfigJson.getAvatarBaseType()).getBaseTypeText().getHash()))).forEach(System.out::println);
    }

    public Map<String, String> getLanguage() {
        return languageHandler.getLanguageMap();
    }
}
