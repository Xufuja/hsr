package dev.xfj;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Application application = new Application("EN");
        application.run();
        //hashCheck();
    }
    public static void hashCheck() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println(Application.getStableHash(scanner.nextLine()));
        }
    }
}
