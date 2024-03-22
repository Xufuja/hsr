package dev.xfj;

import dev.xfj.avatar.Avatar;
import dev.xfj.avatar.AvatarTrace;
import dev.xfj.character.Character;
import dev.xfj.character.RelicData;
import dev.xfj.character.RelicPiece;
import dev.xfj.common.Enums;
import dev.xfj.database.Database;
import dev.xfj.events.Event;
import dev.xfj.lightcone.LightCone;
import dev.xfj.lightcone.LightConePassive;
import dev.xfj.lightcone.LightConeStats;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicSet;
import dev.xfj.relic.RelicSetEffect;
import dev.xfj.system.RelicGen;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTabBarFlags;
import imgui.type.ImString;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppLayer implements Layer {
    private AppState appState;

    @Override
    public void onAttach() {
        this.appState = new AppState();

        System.out.println("Loading Database...");
        try {
            Database.init("EN");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("Database loaded!");
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onUpdate(float ts) {

    }

    @Override
    public void onUIRender() {
        if (ImGui.beginTabBar("##TabBar", ImGuiTabBarFlags.None)) {
            if (ImGui.beginTabItem("Relics")) {
                Map<Integer, Integer> indexToId = new HashMap<>();
                int i = 0;

                for (Map.Entry<Integer, RelicSet> entry : Database.getRelicSets().entrySet()) {
                    indexToId.put(i, entry.getKey());
                    i++;
                }

                if (ImGui.beginListBox("##RelicSets")) {
                    for (int n = 0; n < indexToId.size(); n++) {
                        boolean isSelected = (appState.relicItemIndex == n);
                        String name = Database.getRelicSets().get(indexToId.get(n)).setName();

                        if (ImGui.selectable(name, isSelected)) {
                            appState.relicItemIndex = n;

                            if (isSelected) {
                                ImGui.setItemDefaultFocus();
                            }
                        }
                    }
                    ImGui.endListBox();
                }

                ImGui.sameLine();

                ImGui.image(Database.getRelicSets().get(indexToId.get(appState.relicItemIndex)).relicSetIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

                RelicSet relicSet = Database.getRelicSets().get(indexToId.get(appState.relicItemIndex));

                List<Relic> relicsBySet = new ArrayList<>();

                if (ImGui.checkbox("2 Star", appState.add2Star)) {
                    appState.add2Star = !appState.add2Star;
                }
                ImGui.sameLine();
                if (ImGui.checkbox("3 Star", appState.add3Star)) {
                    appState.add3Star = !appState.add3Star;
                }
                ImGui.sameLine();
                if (ImGui.checkbox("4 Star", appState.add4Star)) {
                    appState.add4Star = !appState.add4Star;
                }
                ImGui.sameLine();
                if (ImGui.checkbox("5 Star", appState.add5Star)) {
                    appState.add5Star = !appState.add5Star;
                }

                int enabledRarity = 0;
                if (appState.add2Star) {
                    enabledRarity |= 1 << 2;
                }
                if (appState.add3Star) {
                    enabledRarity |= 1 << 3;
                }
                if (appState.add4Star) {
                    enabledRarity |= 1 << 4;
                }
                if (appState.add5Star) {
                    enabledRarity |= 1 << 5;
                }


                for (Relic entry : Database.getRelics().values()) {
                    if (entry.setData().equals(relicSet)) {
                        if ((enabledRarity & (1 << Integer.parseInt(entry.rarity().substring(entry.rarity().length() - 1)))) == 0) {
                            continue;
                        }

                        relicsBySet.add(entry);
                    }
                }

                ImGui.separator();

                if (ImGui.beginListBox("##Relics")) {
                    for (int n = 0; n < relicsBySet.size(); n++) {
                        boolean isSelected = (appState.subRelicItemIndex == n);
                        String name = String.format("%1$s * | %2$s (%3$s)", relicsBySet.get(n).rarity().substring(relicsBySet.get(n).rarity().length() - 1), relicsBySet.get(n).name(), relicsBySet.get(n).type());

                        if (ImGui.selectable(name, isSelected)) {
                            appState.subRelicItemIndex = n;

                            if (isSelected) {
                                ImGui.setItemDefaultFocus();
                            }
                        }
                    }
                    ImGui.endListBox();
                }

                ImGui.sameLine();

                ImGui.image(relicsBySet.get(appState.subRelicItemIndex).relicIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

                ImGui.separator();

                ImGui.inputTextMultiline("##RelicDetails", relicsBySet.size() > 0 ? new ImString(relicsBySet.get(appState.subRelicItemIndex).toString()) : new ImString());

                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("Characters")) {
                Map<Integer, Integer> indexToId = new HashMap<>();
                int i = 0;

                for (Map.Entry<Integer, Avatar> entry : Database.getAvatars().entrySet()) {
                    indexToId.put(i, entry.getKey());
                    i++;
                }

                if (ImGui.beginListBox("##Characters")) {
                    for (int n = 0; n < indexToId.size(); n++) {
                        boolean isSelected = (appState.characterItemIndex == n);
                        String name = String.format("%1$s * | %2$s (%3$s)", Database.getAvatars().get(indexToId.get(n)).rarity().substring(Database.getAvatars().get(indexToId.get(n)).rarity().length() - 1), Database.getAvatars().get(indexToId.get(n)).avatarName(), Database.getAvatars().get(indexToId.get(n)).avatarId());

                        if (ImGui.selectable(name, isSelected)) {
                            appState.characterItemIndex = n;

                            if (isSelected) {
                                ImGui.setItemDefaultFocus();
                            }
                        }
                    }
                    ImGui.endListBox();
                }

                ImGui.sameLine();

                ImGui.image(Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).avatarIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

                ImGui.separator();

                ImGui.inputTextMultiline("##CharacterDetails", indexToId.size() > 0 ? new ImString(Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).toString()) : new ImString());

                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("LightCones")) {
                Map<Integer, Integer> indexToId = new HashMap<>();
                int i = 0;

                for (Map.Entry<Integer, LightCone> entry : Database.getLightCones().entrySet()) {
                    indexToId.put(i, entry.getKey());
                    i++;
                }

                if (ImGui.beginListBox("##LightCones")) {
                    for (int n = 0; n < indexToId.size(); n++) {
                        boolean isSelected = (appState.lightConeItemIndex == n);
                        String name = String.format("%1$s * | %2$s (%3$s)", Database.getLightCones().get(indexToId.get(n)).rarity(), Database.getLightCones().get(indexToId.get(n)).name(), Database.getLightCones().get(indexToId.get(n)).lightConeId());

                        if (ImGui.selectable(name, isSelected)) {
                            appState.lightConeItemIndex = n;

                            if (isSelected) {
                                ImGui.setItemDefaultFocus();
                            }
                        }
                    }
                    ImGui.endListBox();
                }

                ImGui.sameLine();

                ImGui.image(Database.getLightCones().get(indexToId.get(appState.lightConeItemIndex)).lightConeIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

                ImGui.separator();

                ImGui.inputTextMultiline("##LightConeDetails", indexToId.size() > 0 ? new ImString(Database.getLightCones().get(indexToId.get(appState.lightConeItemIndex)).toString()) : new ImString());

                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("Hash")) {
                ImGui.inputText("##Hash", appState.hashBuffer, ImGuiInputTextFlags.None);

                if (ImGui.button("Calculate")) {
                    appState.lastHash = new ImString(String.valueOf(getStableHash(appState.hashBuffer.get())));
                }

                ImGui.inputText("##Result", appState.lastHash, ImGuiInputTextFlags.ReadOnly);
                ImGui.endTabItem();
            }

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
            ImGui.endTabBar();
        }

    }

    @Override
    public void onEvent(Event event) {
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
