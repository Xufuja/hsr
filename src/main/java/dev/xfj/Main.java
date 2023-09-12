package dev.xfj;

import dev.xfj.avatarbasetype.AvatarBaseTypeJson;
import dev.xfj.equipmentconfig.EquipmentConfigJson;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //ClassGenerator classGenerator = new ClassGenerator("C:\\Dev\\StarRailData\\ExcelOutput\\", "C:\\Dev\\hsr\\src\\generated\\");
        //classGenerator.createClasses();
        Application application = new Application("EN");
        application.run();
        //hashCheck();

    }
    public static void hashCheck() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while(running) {
            System.out.println(ClassGenerator.getStableHash(scanner.nextLine()));
        }
    }
}