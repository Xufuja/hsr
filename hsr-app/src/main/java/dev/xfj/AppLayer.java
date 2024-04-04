package dev.xfj;

import dev.xfj.database.Database;
import dev.xfj.events.Event;
import dev.xfj.tabs.*;
import imgui.ImGui;
import imgui.flag.ImGuiTabBarFlags;

public class AppLayer implements Layer {
    private AppState appState;
    private RelicTab relicTab;
    private CharacterTab characterTab;
    private LightConeTab lightConeTab;
    private ItemTab itemTab;
    private HashTab hashTab;
    private TestTab testTab;

    @Override
    public void onAttach() {
        this.appState = new AppState();
        this.relicTab = new RelicTab(appState);
        this.characterTab = new CharacterTab(appState);
        this.lightConeTab = new LightConeTab(appState);
        this.itemTab = new ItemTab(appState);
        this.hashTab = new HashTab(appState);
        this.testTab = new TestTab(appState);

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
            relicTab.onUIRender();
            characterTab.onUIRender();
            lightConeTab.onUIRender();
            itemTab.onUIRender();
            hashTab.onUIRender();
            testTab.onUIRender();
            ImGui.endTabBar();
        }

    }

    @Override
    public void onEvent(Event event) {
    }
}
