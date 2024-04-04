package dev.xfj.tabs;

import dev.xfj.AppState;
import dev.xfj.avatar.Avatar;
import dev.xfj.avatar.AvatarTrace;
import dev.xfj.character.Character;
import dev.xfj.character.RelicData;
import dev.xfj.character.RelicPiece;
import dev.xfj.common.Enums;
import dev.xfj.database.Database;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConePassive;
import dev.xfj.lightcone.LightConeStats;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicSet;
import dev.xfj.relic.RelicSetEffect;
import dev.xfj.system.RelicGen;
import imgui.ImGui;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestTab {
    private final AppState appState;

    public TestTab(AppState appState) {
        this.appState = appState;
    }

    public void onUIRender() {
        if (ImGui.beginTabItem("Testing")) {
            if (ImGui.button("Execute")) {
                for (Map.Entry<Integer, LightCone> entry : Database.getLightCones().entrySet()) {
                    LightCone lc = entry.getValue();
                    System.out.println(String.format("Light Cone ID: %1$s\r\n\t\tName: %2$s\r\n\t\tPath: %3$s\r\n\t\tLore: %4$s", lc.lightConeId(), lc.name(), lc.path(), lc.backgroundDescription()));
                    int[] level = lc.addLevel(lc.maxAscension(), 70, 350000);
                    System.out.println(String.format("Level: %1$s, Exp: %2$s", level[0], level[1]));
                    LightConeStats stats = lc.getStatsByAscension(lc.maxAscension());
                    System.out.println(String.format("Max Level %1$s\r\nPlayer or Equilibrium Level Requirement: %2$s\r\nBase HP: %3$s\r\nBase Attack: %4$s\r\nBase Defense: %5$s\r\nCurrent HP: %6$s\r\nCurrent Attack: %7$s\r\nCurrent Defense %8$s", stats.maxLevel(), stats.levelRequirement() != 0 ? stats.levelRequirement() : stats.equilibriumLevelRequirement(), stats.baseHp(), stats.baseAttack(), stats.baseDefense(), lc.getBaseStatAtLevel(Enums.BaseStatCategory.HP, lc.maxAscension(), level[0]), lc.getBaseStatAtLevel(Enums.BaseStatCategory.ATTACK, lc.maxAscension(), level[0]), lc.getBaseStatAtLevel(Enums.BaseStatCategory.DEFENSE, lc.maxAscension(), level[0])));
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
                    for (Map.Entry<Integer, RelicSetEffect> effect : set.setEffects().entrySet()) {
                        System.out.println(String.format("\t\tBonus: %1$s piece\r\n\t\tEffect Description: %2$s", effect.getKey(), relic.getInterpolatedDescription(effect.getKey())));
                    }
                }
                Relic relic = Database.getRelics().get(61143);
                int[] rLev = relic.addLevel(-1, 1, 75440);
                System.out.println(String.format("Level: %1$s\r\nExp: %2$s\r\nMain: %3$s\r\nSub:%4$s", rLev[0], rLev[1], relic.mainAffixGroup(), relic.subAffixGroup()));
                System.out.println(relic.expRequiredForLevel(1, relic.maxLevel()));
                System.out.println(relic.type());
                relic.getPossibleMainStats().forEach(System.out::println);
                System.out.println(relic.getBaseMainStatAtLevel(relic.getPossibleMainStats().get(0), relic.maxLevel()));
                relic.getPossibleSubStats().forEach(System.out::println);
                System.out.println("High Roll: " + relic.rollSubStat(relic.getPossibleSubStats().get(7), 2));
                relic.getPossibleSubStats().forEach(s -> System.out.println(String.format("Stat: %1$S\r\n\t\tMax Value: %2$s", s, relic.getBaseStatAtRoll(s, relic.maxLevel() / 3))));
                System.out.println(relic.getBaseStatAtRoll(relic.getPossibleSubStats().get(7), 5));
                Avatar avatar = Database.getAvatars().get(1001);
                System.out.println(String.format("Name: %1$s\r\nPath: %2$s\r\nPath Description: %3$s\r\nType: %4$s", avatar.avatarName(), Database.getAvatarPaths().get(avatar.avatarBaseType()).name(), Database.getAvatarPaths().get(avatar.avatarBaseType()).description(), avatar.damageType()));

                int[] up = avatar.addLevel(0, 1, 112509);
                System.out.println(String.format("Level: %1$s\r\nExp: %2$s", up[0], up[1]));
                System.out.println(avatar.expRequiredForLevel(1, avatar.getStatsByAscension(0).maxLevel()));

                for (Map.Entry<Integer, Map<Integer, AvatarTrace>> trace : avatar.traces().entrySet()) {
                    for (Map.Entry<Integer, AvatarTrace> level : trace.getValue().entrySet()) {
                        StringBuilder adds = new StringBuilder();
                        level.getValue().statusAddList().forEach(additions -> additions.forEach((key, value) -> adds.append(key).append(" ").append(value).append("\r\n")));

                        System.out.println(String.format("Name: %1$s\r\n\t\tID: %2$s\r\n\t\tType: %3$s\r\n\t\tAnchor: %4$s\r\n\t\tRequirements: %5$s\r\n\t\tBonus: %6$s", level.getValue().traceName(), level.getValue().traceId(), level.getValue().pointType(), level.getValue().anchor(), level.getValue().prerequisites().toString(), level.getValue().pointType() == 1 ? adds : avatar.getInterpolatedTraceDescription(level.getValue())));
                    }
                }
                IntStream.rangeClosed(1, avatar.maxEidolon()).mapToObj(avatar::getInterpolatedEidolonDescription).forEach(System.out::println);
                Character character = new Character(1001, 21002);
                System.out.println(character.characterData);
                System.out.println(character.characterData.getAvatar().expGroup());
                System.out.println(character.characterData.getAvatar().expRequiredForLevel(1, 20));

                if (character.characterData.levelUp(112509)) {
                    System.out.println("Leveled up!");
                }
                System.out.println(character.characterData);
                if (character.characterData.levelUp(2)) {
                    System.out.println("Leveled up!");
                }
                System.out.println(character.characterData);

                if (character.characterData.levelUp(1)) {
                    System.out.println("Leveled up!");
                } else {
                    System.out.println("Max level!");
                }

                character.characterData.ascend();

                System.out.println(character.characterData.getAvatar().expRequiredForLevel(20, 30));

                if (character.characterData.levelUp(177910)) {
                    System.out.println("Leveled up!");
                }

                System.out.println(character.characterData);

                if (character.characterData.levelUp(1)) {
                    System.out.println("Leveled up!");
                } else {
                    System.out.println("Max level!");
                }

                System.out.println(character.lightConeData.getLightCone().name());
                System.out.println(character.lightConeData);
                character.lightConeData.levelUp(character.lightConeData.getLightCone().expRequiredForLevel(1, character.lightConeData.getLightCone().getStatsByAscension(character.lightConeData.getCurrentAscension()).getMaxLevel()));
                character.lightConeData.ascend();
                character.lightConeData.unlockSuperimpose();
                System.out.println(character.lightConeData);
                character.lightConeData.levelUp(character.lightConeData.getLightCone().expRequiredForLevel(1, character.lightConeData.getLightCone().getStatsByAscension(character.lightConeData.getCurrentAscension()).getMaxLevel()));
                System.out.println(character.lightConeData);

                //player.relicData.setHead(new RelicPiece(61021));
                //player.relicData.setHand(new RelicPiece(61022));
                //player.relicData.setBody(new RelicPiece(61023));
                //player.relicData.setFeet(new RelicPiece(61024));
                //player.relicData.setPlanarSphere(new RelicPiece(63015));
                //player.relicData.setLinkRope(new RelicPiece(63016));

                //System.out.println(player.relicData);

                //player.relicData.getBody().levelUp(player.relicData.getBody().getRelic().expRequiredForLevel(1, player.relicData.getBody().getRelic().maxLevel()));

                //System.out.println(player.relicData);

                RelicGen.getRelicsBySet("Musketeer of Wild Wheat");
                RelicPiece piece = RelicGen.createRelicFromSetWithType("Musketeer of Wild Wheat", "Head");
                System.out.println(piece);
                piece.levelUp(80000);
                System.out.println(piece);

                RelicGen.getRelicSets().stream().forEach(System.out::println);

                System.out.println(RelicGen.createRelicFromSet("Space Sealing Station"));

                character.relicData.getPiece(RelicData.PieceType.HEAD).ifPresentOrElse(System.out::println, () -> System.out.println("No Relic!"));

                character.relicData.setHead(piece);

                character.relicData.getPiece(RelicData.PieceType.HEAD).ifPresentOrElse(System.out::println, () -> System.out.println("No Relic!"));
            }
            ImGui.endTabItem();
        }
    }
}
