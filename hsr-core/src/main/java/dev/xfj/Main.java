package dev.xfj;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Application application = new Application("EN");
        application.run();
    }
}
