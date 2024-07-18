package dev.xfj.tabs;

import dev.xfj.AppState;
import dev.xfj.RarityFilter;
import dev.xfj.character.RelicPiece;
import dev.xfj.database.Database;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicSet;
import dev.xfj.system.RelicGen;
import imgui.ImGui;
import imgui.type.ImString;

import java.util.*;
import java.util.stream.Collectors;

public class RelicTab {
    private final AppState appState;
    private static RelicPiece generatedRelic = null;

    public RelicTab(AppState appState) {
        this.appState = appState;
    }

    public void onUIRender() {
        String tabName = "Relics";

        if (ImGui.beginTabItem(tabName)) {
            appState.resetFilters(tabName);

            Map<Integer, Integer> indexToId = new HashMap<>();
            int i = 0;

            Map<Integer, RelicSet> sorted = Database.getRelicSets().entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);

            for (Map.Entry<Integer, RelicSet> entry : sorted.entrySet()) {
                indexToId.put(i, entry.getKey());
                i++;
            }

            if (ImGui.beginListBox("##RelicSets")) {
                for (int n = 0; n < indexToId.size(); n++) {
                    boolean isSelected = (appState.relicItemIndex == n);
                    String name = String.format("%1$s (%2$s)", Database.getRelicSets().get(indexToId.get(n)).setName(), Database.getRelicSets().get(indexToId.get(n)).setId());

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

            int temp = new RarityFilter(appState, true, true, true, true).filterRarity();

            for (Relic entry : Database.getRelics().values()) {
                if (entry.setData().equals(relicSet)) {
                    if ((temp & (1 << Integer.parseInt(entry.rarity().substring(entry.rarity().length() - 1)))) == 0) {
                        continue;
                    }

                    relicsBySet.add(entry);
                }
            }

            ImGui.separator();

            if (ImGui.beginListBox("##Relics")) {
                for (int n = 0; n < relicsBySet.size(); n++) {
                    boolean isSelected = (appState.subRelicItemIndex == n);
                    String name = String.format("%1$s * | %2$s | %3$s (%4$s)", relicsBySet.get(n).rarity().substring(relicsBySet.get(n).rarity().length() - 1), relicsBySet.get(n).name(), relicsBySet.get(n).type(), relicsBySet.get(n).relicId());

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

            ImGui.pushItemWidth(ImGui.calcItemWidth() / 2);

            ImGui.inputTextMultiline("##RelicStats", relicsBySet.size() > 0 ? formatPossibleStats(relicsBySet.get(appState.subRelicItemIndex).getPossibleMainStats(), relicsBySet.get(appState.subRelicItemIndex).getPossibleSubStats()) : new ImString());

            ImGui.sameLine();

            ImGui.inputTextMultiline("##RelicExample", generatedRelic != null ? new ImString(generatedRelic.toString()) : new ImString());

            ImGui.popItemWidth();

            if (ImGui.button("Generate Example")) {
                generatedRelic = RelicGen.createRelic(relicsBySet.get(appState.subRelicItemIndex).relicId());
            }

            ImGui.sameLine();

            ImGui.beginDisabled(generatedRelic == null);
            if (ImGui.button("Next Roll")) {
                if (generatedRelic.getCurrentLevel() != generatedRelic.getRelic().maxLevel()) {
                    generatedRelic.levelUp(generatedRelic.getRelic().expRequiredForLevel(generatedRelic.getCurrentLevel(), generatedRelic.getCurrentLevel() % 3 == 0 ? generatedRelic.getCurrentLevel() + 3 : generatedRelic.getCurrentLevel() + 2));
                }
            }
            ImGui.endDisabled();

            ImGui.sameLine();

            ImGui.beginDisabled(generatedRelic == null);
            if (ImGui.button("Max Level")) {
                if (generatedRelic.getCurrentLevel() != generatedRelic.getRelic().maxLevel()) {
                    generatedRelic.levelUp(generatedRelic.getRelic().expRequiredForLevel(generatedRelic.getCurrentLevel(), generatedRelic.getRelic().maxLevel()));
                }
            }
            ImGui.endDisabled();

            ImGui.separator();

            ImGui.inputTextMultiline("##RelicDetails", relicsBySet.size() > 0 ? new ImString(relicsBySet.get(appState.subRelicItemIndex).toString()) : new ImString());

            ImGui.endTabItem();
        }
    }

    private ImString formatPossibleStats(List<String> mainStats, List<String> subStats) {
        String main = getPossibleStats(mainStats);
        String sub = getPossibleStats(subStats);

        return new ImString(String.format("Main Stats:\n%1$s\n\nSub Stats:\n%2$s", main, sub));
    }

    private String getPossibleStats(List<String> possibleStats) {
        return possibleStats.stream().map(stat -> Database.getAvatarStatTypes().get(stat).relicDescription()).collect(Collectors.joining("\n"));
    }

}
