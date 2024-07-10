package dev.xfj;

import imgui.ImGui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RarityFilter {
    private final AppState appState;
    private final boolean add2Star;
    private final boolean add3Star;
    private final boolean add4Star;
    private final boolean add5Star;

    public RarityFilter(AppState appState, boolean add2Star, boolean add3Star, boolean add4Star, boolean add5Star) {
        this.appState = appState;
        this.add2Star = add2Star;
        this.add3Star = add3Star;
        this.add4Star = add4Star;
        this.add5Star = add5Star;
    }

    private boolean raritiesDisabled(String skip) {
        Field[] fields = getClass().getDeclaredFields();
        boolean result = true;

        for (Field field : fields) {
            String name = field.getName();

            if (!name.contains(skip)) {
                try {
                    if (name.startsWith("add")) {
                        field.setAccessible(true);

                        if ((boolean) field.get(this)) {
                            if ((boolean) AppState.class.getMethod("is" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(appState)) {
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
        Field[] fields = getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = field.getName();

            try {
                if (name.startsWith("add")) {
                    field.setAccessible(true);
                    renderRarityCheckbox((boolean) field.get(this), rarityFromString(name), i + 1 == fields.length);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        int enabledRarity = 0;

        Method[] methods = AppState.class.getMethods();

        for (Method method : methods) {
            String name = method.getName();

            if (name.startsWith("is")) {
                try {
                    if ((boolean) method.invoke(appState)) {
                        enabledRarity |= 1 << Integer.parseInt(rarityFromString(name));
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        return enabledRarity;
    }

    private void renderRarityCheckbox(boolean render, String rarity, boolean last) {
        if (render) {
            try {
                boolean isStar = (boolean) AppState.class.getMethod(String.format("isAdd%sStar", rarity)).invoke(appState);

                ImGui.beginDisabled(raritiesDisabled(rarity));
                if (ImGui.checkbox(rarity + " Star", isStar)) {
                    AppState.class.getMethod(String.format("setAdd%sStar", rarity), boolean.class).invoke(appState, !isStar);
                }
                ImGui.endDisabled();

                if (!last) {
                    ImGui.sameLine();
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private String rarityFromString(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                return String.valueOf(text.charAt(i));
            }
        }

        return "";
    }
}
