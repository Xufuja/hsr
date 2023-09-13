package dev.xfj;

import dev.xfj.database.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class Application {

    public Application(String languageCode) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        System.out.println("Loading Database...");
        Database.init(languageCode);
        System.out.println("Database loaded!");
    }

    public void run() {
        Database.getLightCones().forEach((key, value) -> System.out.println(String.format("Light Cone ID: %1$s\r\n\t\tName: %2$s\r\n\t\tPath: %3$s\r\n\t\tLore: %4$s", value.getLightConeId(), value.getName(), value.getPath(), value.getBackgroundDescription())));

    }


}
