package dev.xfj.tabs;

import dev.xfj.AppState;
import dev.xfj.Image;
import dev.xfj.RarityFilter;
import dev.xfj.avatar.Avatar;
import dev.xfj.avatar.AvatarAbility;
import dev.xfj.avatar.AvatarEidolon;
import dev.xfj.character.CharacterData;
import dev.xfj.character.LightConeData;
import dev.xfj.database.Database;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.type.ImString;

import java.util.*;

public class CharacterTab {
    private final AppState appState;
    private CharacterData selectedCharacter;

    public CharacterTab(AppState appState) {
        this.appState = appState;
    }

    public void onUIRender() {
        String tabName = "Characters";

        if (ImGui.beginTabItem(tabName)) {
            appState.resetFilters(tabName);

            Map<Integer, Integer> indexToId = new HashMap<>();
            int i = 0;

            int temp = new RarityFilter(appState, false, false, true, true).filterRarity();
            Map<Integer, Avatar> sorted = Database.getAvatars().entrySet()
                    .stream()
                    .filter(entry -> !isNotSelected(temp, entry.getValue()))
                    .sorted(Map.Entry.comparingByKey())
                    .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);

            for (Map.Entry<Integer, Avatar> entry : sorted.entrySet()) {
                indexToId.put(i, entry.getKey());
                i++;
            }


            if (ImGui.beginListBox("##Characters")) {
                for (int n = 0; n < indexToId.size(); n++) {
                    boolean isSelected = (appState.characterItemIndex == n);
                    String name = String.format("%1$s * | %2$s (%3$s)", sorted.get(indexToId.get(n)).rarity().substring(sorted.get(indexToId.get(n)).rarity().length() - 1), sorted.get(indexToId.get(n)).avatarName(), sorted.get(indexToId.get(n)).avatarId());

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

            if (ImGui.imageButton(sorted.get(indexToId.get(appState.characterItemIndex)).avatarIcon().getRendererId(), 128, 128, 0, 1, 1, 0)) {
                ImGui.openPopup("FullSize");
            }

            ImGui.popStyleColor(3);

            Image avatar = sorted.get(indexToId.get(appState.characterItemIndex)).avatarImage();

            if (ImGui.beginPopup("FullSize")) {
                ImGui.image(avatar.getRendererId(), avatar.getWidth(), avatar.getHeight(), 0, 1, 1, 0);
                ImGui.endPopup();
            }

            ImGui.sameLine();

            ImGui.image(Database.getAvatarPaths().get(sorted.get(indexToId.get(appState.characterItemIndex)).avatarBaseType()).pathIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

            ImGui.sameLine();

            ImGui.image(sorted.get(indexToId.get(appState.characterItemIndex)).damageTypeIcon().getRendererId(), 128, 128, 0, 1, 1, 0);

            ImGui.separator();

            ImGui.inputTextMultiline("##CharacterDetails", selectedCharacter != null && indexToId.size() > 0 ? new ImString(selectedCharacter.toString()) : new ImString());

            if (ImGui.button("Show Stats")) {
                selectedCharacter = new CharacterData(indexToId.get(appState.characterItemIndex));
            }

            ImGui.sameLine();

            ImGui.beginDisabled(selectedCharacter == null || selectedCharacter.getCurrentLevel() == selectedCharacter.getAvatar().getStatsByAscension(selectedCharacter.getCurrentAscension()).getMaxLevel());
            if (ImGui.button("Level Up")) {
                selectedCharacter.levelUp(selectedCharacter.getAvatar().expRequiredForLevel(selectedCharacter.getCurrentLevel(), selectedCharacter.getCurrentLevel() + 1));
            }
            ImGui.endDisabled();

            ImGui.sameLine();

            ImGui.beginDisabled(selectedCharacter == null || selectedCharacter.getCurrentLevel() == selectedCharacter.getAvatar().getStatsByAscension(selectedCharacter.getCurrentAscension()).getMaxLevel());
            if (ImGui.button("Max Level")) {
                selectedCharacter.levelUp(selectedCharacter.getAvatar().expRequiredForLevel(selectedCharacter.getCurrentLevel(), selectedCharacter.getAvatar().getStatsByAscension(selectedCharacter.getCurrentAscension()).getMaxLevel()));
            }
            ImGui.endDisabled();

            ImGui.sameLine();

            ImGui.beginDisabled(selectedCharacter == null || selectedCharacter.getCurrentLevel() != selectedCharacter.getAvatar().getStatsByAscension(selectedCharacter.getCurrentAscension()).getMaxLevel() || selectedCharacter.getCurrentAscension() == selectedCharacter.getAvatar().getMaxAscension());
            if (ImGui.button("Ascend")) {
                selectedCharacter.ascend();
            }
            ImGui.endDisabled();

            ImGui.sameLine();

            ImGui.beginDisabled(selectedCharacter == null || selectedCharacter.getCurrentDupe() == selectedCharacter.getAvatar().maxEidolon());
            if (ImGui.button("Unlock Eidolon")) {
                selectedCharacter.unlockEidolon();
            }
            ImGui.endDisabled();

            ImGui.sameLine();

            ImGui.beginDisabled(selectedCharacter == null || selectedCharacter.getCurrentDupe() == selectedCharacter.getAvatar().maxEidolon());
            if (ImGui.button("Max Eidolons")) {
                for (int j = selectedCharacter.getCurrentDupe(); j < selectedCharacter.getAvatar().maxEidolon() + 1; j++) {
                    selectedCharacter.unlockEidolon();
                }
            }
            ImGui.endDisabled();

            ImGui.sameLine();

            ImGui.beginDisabled(selectedCharacter == null || selectedCharacter.getCurrentLevel() == selectedCharacter.getAvatar().getStatsByAscension(selectedCharacter.getAvatar().getMaxAscension()).getMaxLevel());
            if (ImGui.button("Max Out")) {
                for (int j = selectedCharacter.getCurrentAscension(); j < selectedCharacter.getAvatar().getMaxAscension() + 1; j++) {
                    selectedCharacter.levelUp(selectedCharacter.getAvatar().expRequiredForLevel(selectedCharacter.getCurrentLevel(), selectedCharacter.getAvatar().getStatsByAscension(selectedCharacter.getCurrentAscension()).getMaxLevel()));
                    selectedCharacter.ascend();
                    selectedCharacter.unlockEidolon();
                }
            }
            ImGui.endDisabled();

            ImGui.separator();

            List<AvatarAbility> abilities = new ArrayList<>();

            for (Map.Entry<Integer, Map<Integer, AvatarAbility>> entry : sorted.get(indexToId.get(appState.characterItemIndex)).abilities().entrySet()) {
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

            for (Map.Entry<Integer, AvatarEidolon> eidolon : sorted.get(indexToId.get(appState.characterItemIndex)).eidolons().entrySet()) {
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

    private boolean isNotSelected(int enabledRarity, Avatar entry) {
        return (enabledRarity & (1 << Integer.parseInt(entry.rarity().substring(entry.rarity().length() - 1)))) == 0;
    }
}
