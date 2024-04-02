package dev.xfj.tabs;

import dev.xfj.AppState;
import dev.xfj.database.Database;
import dev.xfj.item.Item;
import imgui.ImGui;
import imgui.type.ImString;

import java.util.HashMap;
import java.util.Map;

public class ItemTab {
    private final AppState appState;

    public ItemTab(AppState appState) {
        this.appState = appState;
    }

    public void onUIRender() {
        if (ImGui.beginTabItem("Items")) {
            Map<Integer, Integer> indexToId = new HashMap<>();
            int i = 0;

            for (Map.Entry<Integer, Item> entry : Database.getNormalItems().entrySet()) {
                indexToId.put(i, entry.getKey());
                i++;
            }

            if (ImGui.beginListBox("##Items")) {
                for (int n = 0; n < indexToId.size(); n++) {
                    boolean isSelected = (appState.normalItemIndex == n);
                    String name = String.format("%1$s (%2$s)", Database.getNormalItems().get(indexToId.get(n)).getName(), Database.getNormalItems().get(indexToId.get(n)).getItemId());

                    if (ImGui.selectable(name, isSelected)) {
                        appState.normalItemIndex = n;

                        if (isSelected) {
                            ImGui.setItemDefaultFocus();
                        }
                    }
                }
                ImGui.endListBox();
            }

            ImGui.sameLine();

            ImGui.image(Database.getNormalItems().get(indexToId.get(appState.normalItemIndex)).getItemIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

            ImGui.separator();

            ImGui.inputTextMultiline("##ItemDetails", indexToId.size() > 0 ? new ImString(Database.getNormalItems().get(indexToId.get(appState.normalItemIndex)).toString()) : new ImString());

            ImGui.endTabItem();
        }
    }
}
