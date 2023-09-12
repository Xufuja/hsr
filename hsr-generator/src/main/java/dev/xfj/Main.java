package dev.xfj;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassGenerator classGenerator = new ClassGenerator("C:\\Dev\\StarRailData\\ExcelOutput\\", "C:\\Dev\\hsr\\hsr-core\\src\\generated\\");
        classGenerator.createClasses();
        //hashCheck();
    }

    public static void hashCheck() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println(ClassGenerator.getStableHash(scanner.nextLine()));
        }
    }
}