package dev.xfj.tabs;

import dev.xfj.AppState;
import dev.xfj.Image;
import dev.xfj.avatar.Avatar;
import dev.xfj.avatar.AvatarAbility;
import dev.xfj.avatar.AvatarEidolon;
import dev.xfj.database.Database;
import dev.xfj.relic.Relic;
import dev.xfj.relic.RelicSet;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.type.ImString;

import java.util.*;
import java.util.stream.Collectors;

public class CharacterTab {
    private final AppState appState;

    public CharacterTab(AppState appState) {
        this.appState = appState;
    }

    public void onUIRender() {
        if (ImGui.beginTabItem("Characters")) {
            Map<Integer, Integer> indexToId = new HashMap<>();
            int i = 0;

            Map<Integer, Avatar> sorted = Database.getAvatars().entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);

            for (Map.Entry<Integer, Avatar> entry : sorted.entrySet()) {
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

            ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0, 0, 0, 0);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0, 0, 0, 0);

            if (ImGui.imageButton(Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).avatarIcon().getRendererId(), 128, 128, 0, 1, 1, 0)) {
                ImGui.openPopup("FullSize");
            }

            ImGui.popStyleColor(3);

            Image avatar = Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).avatarImage();

            if (ImGui.beginPopup("FullSize")) {
                ImGui.image(avatar.getRendererId(), avatar.getWidth(), avatar.getHeight(), 0, 1, 1, 0);
                ImGui.endPopup();
            }

            ImGui.sameLine();

            ImGui.image(Database.getAvatarPaths().get(Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).avatarBaseType()).pathIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

            ImGui.sameLine();

            ImGui.image(Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).damageTypeIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

            ImGui.separator();

            ImGui.inputTextMultiline("##CharacterDetails", indexToId.size() > 0 ? new ImString(Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).toString()) : new ImString());

            ImGui.separator();

            List<AvatarAbility> abilities = new ArrayList<>();

            for (Map.Entry<Integer, Map<Integer, AvatarAbility>> entry : Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).abilities().entrySet()) {
                AvatarAbility ability = entry.getValue().get(entry.getValue().size());

                if (ability.abilityTypeDescription() != null) {
                    abilities.add(entry.getValue().get(entry.getValue().size()));
                }
            }

            if (ImGui.beginListBox("##Abilities")) {
                for (int n = 0; n < abilities.size(); n++) {
                    boolean isSelected = (appState.abilityItemIndex == n);
                    String name = String.format("%1$s - %2$s (%3$s)", abilities.get(n).abilityTypeDescription(), abilities.get(n).abilityName(), abilities.get(n).abilityId());

                    if (ImGui.selectable(name, isSelected)) {
                        appState.abilityItemIndex = n;

                        if (isSelected) {
                            ImGui.setItemDefaultFocus();
                        }
                    }
                }
                ImGui.endListBox();
            }

            ImGui.sameLine();

            ImGui.image(abilities.get(appState.abilityItemIndex).abilityIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

            ImGui.sameLine();

            ImGui.inputTextMultiline("##AbilityDetails", abilities.size() > 0 ? new ImString(abilities.get(appState.abilityItemIndex).toString()) : new ImString());

            ImGui.separator();

            List<AvatarEidolon> eidolons = new ArrayList<>();

            for (Map.Entry<Integer, AvatarEidolon> eidolon : Database.getAvatars().get(indexToId.get(appState.characterItemIndex)).eidolons().entrySet()) {
                eidolons.add(eidolon.getValue());
            }

            if (ImGui.beginListBox("##Eidolons")) {
                for (int n = 0; n < eidolons.size(); n++) {
                    boolean isSelected = (appState.eidolonItemIndex == n);
                    String name = String.format("%1$s - %2$s (%3$s)", eidolons.get(n).eidolon(), eidolons.get(n).name(), eidolons.get(n).eidolonId());

                    if (ImGui.selectable(name, isSelected)) {
                        appState.eidolonItemIndex = n;

                        if (isSelected) {
                            ImGui.setItemDefaultFocus();
                        }
                    }
                }
                ImGui.endListBox();
            }

            ImGui.sameLine();

            ImGui.image(eidolons.get(appState.eidolonItemIndex).eidolonIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

            ImGui.sameLine();

            ImGui.inputTextMultiline("##EidolonDetails", eidolons.size() > 0 ? new ImString(eidolons.get(appState.eidolonItemIndex).toString()) : new ImString());

            ImGui.endTabItem();
        }
    }
}
