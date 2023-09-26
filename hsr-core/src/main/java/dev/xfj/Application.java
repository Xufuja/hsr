package dev.xfj;

import dev.xfj.database.Database;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConePassive;
import dev.xfj.lightcone.LightConeStats;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicSet;
import dev.xfj.relic.RelicSetEffect;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
    public Application(String languageCode) throws FileNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        System.out.println("Loading Database...");
        Database.init(languageCode);
        System.out.println("Database loaded!");
    }

    public void run() {
        for (Map.Entry<Integer, LightCone> entry : Database.getLightCones().entrySet()) {
            LightCone lc = entry.getValue();
            System.out.println(String.format("Light Cone ID: %1$s\r\n\t\tName: %2$s\r\n\t\tPath: %3$s\r\n\t\tLore: %4$s", lc.lightConeId(), lc.name(), lc.path(), lc.backgroundDescription()));
            int[] level = lc.addLevel(lc.maxAscension(), 70, 350000);
            System.out.println(String.format("Level: %1$s, Exp: %2$s", level[0], level[1]));
            LightConeStats stats = lc.getStatsByAscension(lc.maxAscension());
            System.out.println(String.format("Max Level %1$s\r\nPlayer or Equilibrium Level Requirement: %2$s\r\nBase HP: %3$s\r\nBase Attack: %4$s\r\nBase Defense: %5$s\r\nCurrent HP: %6$s\r\nCurrent Attack: %7$s\r\nCurrent Defense %8$s", stats.maxLevel(), stats.levelRequirement() != 0 ? stats.levelRequirement() : stats.equilibriumLevelRequirement(), stats.baseHp(), stats.baseAttack(), stats.baseDefense(), lc.getBaseStatAtLevel(LightCone.BaseStatCategory.HP, lc.maxAscension(), level[0]), lc.getBaseStatAtLevel(LightCone.BaseStatCategory.ATTACK, lc.maxAscension(), level[0]), lc.getBaseStatAtLevel(LightCone.BaseStatCategory.DEFENSE, lc.maxAscension(), level[0])));
            LightConePassive passive = lc.getPassiveBySuperimposition(3);

            System.out.println(String.format("Passive Name: %1$s\r\nDescription: %2$s\r\nParameters: %3$s\r\n", passive.name(), lc.getInterpolatedPassive(3), passive.parameters().stream().map(Object::toString).collect(Collectors.joining(", "))));
        }
        LightCone lightCone = Database.getLightCones().get(21000);
        int[] lev = lightCone.addLevel(0, 1, 21069);
        System.out.println(String.format("Level: %1$s\r\nExp: %2$s", lev[0], lev[1]));
        System.out.println(lightCone.expRequiredForLevel(1, lightCone.getStatsByAscension(0).maxLevel()));

        for (Map.Entry<Integer, Relic> entry : Database.getRelics().entrySet()) {
            Relic relic = entry.getValue();
            System.out.println(String.format("Relic ID: %1$s\r\n\t\tRelic Name: %2$s\r\n\t\tRelic Type: %3$s\r\n\t\tMax Level: %4$s", relic.relicId(), relic.name(), relic.type(), relic.maxLevel()));
            RelicSet set = entry.getValue().setData();
            System.out.println(String.format("\t\tSet ID: %1$s\r\n\t\tSet Name: %2$s", set.setId(), set.setName()));
            for (Map.Entry<Integer, RelicSetEffect> effect: set.setEffects().entrySet()) {
                System.out.println(String.format("\t\tBonus: %1$s piece\r\n\t\tEffect Description: %2$s", effect.getKey(), relic.getInterpolatedPassive(effect.getKey())));
            }
        }
        Relic relic = Database.getRelics().get(61143);
        int[] rLev = relic.addLevel(1, 75440);
        System.out.println(String.format("Level: %1$s\r\nExp: %2$s\r\nMain: %3$s\r\nSub:%4$s", rLev[0], rLev[1], relic.mainAffixGroup(), relic.subAffixGroup()));
        System.out.println(relic.expRequiredForLevel(1, relic.maxLevel()));
        System.out.println(relic.type());
        relic.getPossibleMainStats().forEach(System.out::println);
        System.out.println(relic.getBaseMainStatAtLevel(relic.getPossibleMainStats().get(0), relic.maxLevel()));
        relic.getPossibleSubStats().forEach(System.out::println);
        System.out.println("High Roll: " + relic.rollSubStat(relic.getPossibleSubStats().get(7), "HIGH"));
        relic.getPossibleSubStats().forEach(s -> System.out.println(String.format("Stat: %1$S\r\n\t\tMax Value: %2$s", s, relic.getBaseStatAtRoll(s,relic.maxLevel() / 3))));
        System.out.println(relic.getBaseStatAtRoll(relic.getPossibleSubStats().get(7), 5));
    }

    //For example, "EquipmentConfig_EquipmentName_21001" returns "1352234379" which is "Good Night and Sleep Well"
    public static int getStableHash(String str) {
        char[] chars = str.toCharArray();
        int hash1 = 5381;
        int hash2 = hash1;

        for (int i = 0; i < chars.length && chars[i] != '\0'; i += 2) {
            hash1 = ((hash1 << 5) + hash1) ^ chars[i];

            if (i == chars.length - 1 || chars[i + 1] == '\0') {
                break;
            }

            hash2 = ((hash2 << 5) + hash2) ^ chars[i + 1];
        }

        return (hash1 + (hash2 * 1566083941));
    }


}
