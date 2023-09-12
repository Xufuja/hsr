package dev.xfj;

import dev.xfj.database.*;

import java.io.FileNotFoundException;

public class Application {

    public Application(String languageCode) throws FileNotFoundException {
        System.out.println("Loading Database...");
        TextMapData.languageCode = languageCode;
        TextMapData.init();
        AvatarData.init();
        ItemData.init();
        EquipmentData.init();
        System.out.println("Database loaded!");
    }

    public void run() {
        Database.getLightCones().forEach((key, value) -> System.out.println(String.format("Light Cone ID: %1$s\r\n\t\tName: %2$s\r\n\t\tPath: %3$s", value.getLightConeId(), value.getName(), value.getPath())));
        System.out.println(TextMapData.getTranslation(ItemData.getItemConfig().get("221").getItemName().getHash()));

    }


}
