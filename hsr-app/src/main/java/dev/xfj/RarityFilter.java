package dev.xfj;

import imgui.ImGui;

import java.lang.reflect.Field;

public class RarityFilter {
    private AppState appState;
    private boolean add2Star;
    private boolean add3Star;
    private boolean add4Star;
    private boolean add5Star;

    public RarityFilter(AppState appState, boolean add2Star, boolean add3Star, boolean add4Star, boolean add5Star) {
        this.appState = appState;
        this.add2Star = add2Star;
        this.add3Star = add3Star;
        this.add4Star = add4Star;
        this.add5Star = add5Star;
    }

    public boolean raritiesDisabled(String skip) {
        Field[] fields = getClass().getDeclaredFields();
        boolean result = false;

        for (Field field : fields) {
            String name = field.getName();

            if (!name.contains(skip)) {
                try {
                    if (name.startsWith("add")) {
                        String getter = "is" + name.substring(0, 1).toUpperCase() + name.substring(1);
                        boolean item = (boolean) getClass().getMethod(getter).invoke(this);

                        if (item) {
                            boolean state = (boolean) AppState.class.getMethod(getter).invoke(appState);
                            
                            if (!state) {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        return result;
    }

    public int filterRarity() {
        if (add2Star) {
            ImGui.beginDisabled(raritiesDisabled("2"));
            if (ImGui.checkbox("2 Star", appState.add2Star)) {
                appState.add2Star = !appState.add2Star;
            }
            ImGui.endDisabled();

            ImGui.sameLine();
        }

        if (add3Star) {
            ImGui.beginDisabled(raritiesDisabled("3"));
            if (ImGui.checkbox("3 Star", appState.add3Star)) {
                appState.add3Star = !appState.add3Star;
            }
            ImGui.endDisabled();

            ImGui.sameLine();
        }

        if (add4Star) {
            ImGui.beginDisabled(raritiesDisabled("4"));
            if (ImGui.checkbox("4 Star", appState.add4Star)) {
                appState.add4Star = !appState.add4Star;
            }
            ImGui.endDisabled();

            ImGui.sameLine();
        }

        if (add5Star) {
            ImGui.beginDisabled(raritiesDisabled("5"));
            if (ImGui.checkbox("5 Star", appState.add5Star)) {
                appState.add5Star = !appState.add5Star;
            }
            ImGui.endDisabled();
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

        return enabledRarity;
    }

    public boolean isAdd2Star() {
        return add2Star;
    }

    public boolean isAdd3Star() {
        return add3Star;
    }

    public boolean isAdd4Star() {
        return add4Star;
    }

    public boolean isAdd5Star() {
        return add5Star;
    }
}
