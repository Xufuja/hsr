package dev.xfj;

import dev.xfj.handlers.AvatarHandler;
import dev.xfj.handlers.EquipmentHandler;
import dev.xfj.handlers.ItemHandler;
import dev.xfj.singletons.Language;
import dev.xfj.singletons.Paths;

import java.io.FileNotFoundException;
import java.util.Map;

public class Application {
    private final ItemHandler itemHandler;

    public Application(String languageCode) throws FileNotFoundException {
        Language.init(languageCode);
        Paths.init();
        itemHandler = new ItemHandler();
    }

    public void run() throws FileNotFoundException {
        AvatarHandler avatarHandler = new AvatarHandler();
        EquipmentHandler equipmentHandler = new EquipmentHandler();
        equipmentHandler.getEquipmentConfig().values().stream().map(equipmentConfigJson -> String.format("Light Cone ID: %1$s\r\n\t\tName: %2$s\r\n\t\tPath: %3$s",
                equipmentConfigJson.getEquipmentID(),
                Language.getTranslation(equipmentConfigJson.getEquipmentName().getHash()),
                Language.getTranslation(avatarHandler.getAvatarBaseType().get(equipmentConfigJson.getAvatarBaseType()).getBaseTypeText().getHash()))).forEach(System.out::println);
        System.out.println(Language.getTranslation(itemHandler.getItemConfig().get("221").getItemName().getHash()));
        Map<Integer, LightCone> lightCones = equipmentHandler.getLightCones();
        System.out.println(lightCones.get(21011));
    }


}
