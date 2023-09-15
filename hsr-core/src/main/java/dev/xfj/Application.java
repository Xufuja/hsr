package dev.xfj;

import dev.xfj.database.Database;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConeStats;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class Application {

    public Application(String languageCode) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        System.out.println("Loading Database...");
        Database.init(languageCode);
        System.out.println("Database loaded!");
    }

    public void run() {
        for (Map.Entry<Integer, LightCone> entry : Database.getLightCones().entrySet()) {
            LightCone lc = entry.getValue();
            System.out.println(String.format("Light Cone ID: %1$s\r\n\t\tName: %2$s\r\n\t\tPath: %3$s\r\n\t\tLore: %4$s", lc.getLightConeId(), lc.getName(), lc.getPath(), lc.getBackgroundDescription()));
            int[] level = lc.addLevel(lc.getMaxAscension(), 70, 350000);
            System.out.println(String.format("Level: %1$s, Exp: %2$s", level[0], level[1]));
            LightConeStats stats = lc.getStats().get(lc.getMaxAscension());
            System.out.println(String.format("Max Level %1$s\r\nPlayer or Equilibrium Level Requirement: %2$s\r\nBase HP: %3$s\r\nBase Attack: %4$s\r\nBase Defense: %5$s\r\nCurrent HP: %6$s\r\nCurrent Attack: %7$s\r\nCurrent Defense %8$s\r\n", stats.getMaxLevel(), stats.getLevelRequirement() != 0 ? stats.getLevelRequirement() : stats.getEquilibriumLevelRequirement(), stats.getBaseHp(), stats.getBaseAttack(), stats.getBaseDefense(), stats.getBaseHp() + ((level[0] - 1) * stats.getHpPerLevel()), stats.getBaseAttack() + ((level[0] - 1) * stats.getAttackPerLevel()), stats.getBaseDefense() + ((level[0] - 1) * stats.getDefensePerLevel())));
        }
    }


}
