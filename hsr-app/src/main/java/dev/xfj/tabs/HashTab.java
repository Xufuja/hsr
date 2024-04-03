package dev.xfj.tabs;

import dev.xfj.AppState;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

public class HashTab {
    private final AppState appState;

    public HashTab(AppState appState) {
        this.appState = appState;
    }

    public void onUIRender() {
        if (ImGui.beginTabItem("Hash")) {
            ImGui.inputText("##Hash", appState.hashBuffer, ImGuiInputTextFlags.None);

            if (ImGui.button("Calculate")) {
                appState.lastHash = new ImString(String.valueOf(getStableHash(appState.hashBuffer.get())));
            }

            ImGui.inputText("##Result", appState.lastHash, ImGuiInputTextFlags.ReadOnly);
            ImGui.endTabItem();
        }
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
