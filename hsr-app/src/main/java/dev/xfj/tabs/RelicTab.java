package dev.xfj.tabs;

import dev.xfj.AppState;
import dev.xfj.database.Database;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicSet;
import imgui.ImGui;
import imgui.type.ImString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelicTab {
    private final AppState appState;

    public RelicTab(AppState appState) {
        this.appState = appState;
    }

    public void onUIRender() {
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

            ImGui.inputTextMultiline("##RelicDetails", relicsBySet.size() > 0 ? new ImString(relicsBySet.get(appState.subRelicItemIndex).toString()) : new ImString());

            ImGui.endTabItem();
        }
    }
}
