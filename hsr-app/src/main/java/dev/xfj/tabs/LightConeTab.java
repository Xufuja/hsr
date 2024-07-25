package dev.xfj.tabs;

import dev.xfj.AppState;
import dev.xfj.Image;
import dev.xfj.RarityFilter;
import dev.xfj.character.LightConeData;
import dev.xfj.database.Database;
import dev.xfj.lightcone.LightCone;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.type.ImString;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LightConeTab {
    private final AppState appState;

    public LightConeTab(AppState appState) {
        this.appState = appState;
    }

    public void onUIRender() {
        String tabName = "LightCones";

        if (ImGui.beginTabItem(tabName)) {
            appState.resetFilters(tabName);

            Map<Integer, Integer> indexToId = new HashMap<>();
            int i = 0;

            int temp = new RarityFilter(appState, false, true, true, true).filterRarity();

            Map<Integer, LightCone> sorted = Database.getLightCones().entrySet()
                    .stream()
                    .filter(entry -> !isNotSelected(temp, entry.getValue()))
                    .sorted(Map.Entry.comparingByKey())
                    .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);

            for (Map.Entry<Integer, LightCone> entry : sorted.entrySet()) {
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

            ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0, 0, 0, 0);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0, 0, 0, 0);

            if (ImGui.imageButton(Database.getLightCones().get(indexToId.get(appState.lightConeItemIndex)).lightConeIcon().getRendererId(), 128, 128, 0, 1, 1, 0)) {
                ImGui.openPopup("FullSize");
            }

            ImGui.popStyleColor(3);

            Image lightCone = Database.getLightCones().get(indexToId.get(appState.lightConeItemIndex)).lightConeImage();

            if (ImGui.beginPopup("FullSize")) {
                ImGui.image(lightCone.getRendererId(), lightCone.getWidth(), lightCone.getHeight(), 0, 1, 1, 0);
                ImGui.endPopup();
            }

            ImGui.sameLine();

            ImGui.image(Database.getLightCones().get(indexToId.get(appState.lightConeItemIndex)).pathIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

            ImGui.separator();

            LightConeData lightConeData = new LightConeData(indexToId.get(appState.lightConeItemIndex));

            ImGui.inputTextMultiline("##LightConeData", indexToId.size() > 0 ? new ImString(lightConeData.toString()) : new ImString());

            ImGui.separator();

            ImGui.pushItemWidth(ImGui.calcItemWidth() / 2);

            ImGui.inputTextMultiline("##LightConeDetails", indexToId.size() > 0 ? new ImString(Database.getLightCones().get(indexToId.get(appState.lightConeItemIndex)).toString()) : new ImString());

            ImGui.sameLine();

            ImGui.inputTextMultiline("##LightConeDescription", indexToId.size() > 0 ? new ImString(Database.getLightCones().get(indexToId.get(appState.lightConeItemIndex)).backgroundDescription()) : new ImString());

            ImGui.popItemWidth();

            ImGui.endTabItem();
        }
    }

    private boolean isNotSelected(int enabledRarity, LightCone entry) {
        return (enabledRarity & (1 << entry.rarity())) == 0;
    }

}
