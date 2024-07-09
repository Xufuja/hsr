package dev.xfj;

import imgui.type.ImString;

public class AppState {
    public ImString hashBuffer = new ImString("", 512);
    public ImString lastHash = new ImString("");
    public int relicItemIndex = 0;
    public int subRelicItemIndex = 0;
    public int characterItemIndex = 0;
    public int lightConeItemIndex = 0;
    public int normalItemIndex = 0;
    public int abilityItemIndex = 0;
    public int eidolonItemIndex = 0;

    private boolean add2Star = true;
    private boolean add3Star = true;
    private boolean add4Star = true;
    private boolean add5Star = true;

    public boolean isAdd2Star() {
        return add2Star;
    }

    public void setAdd2Star(boolean add2Star) {
        this.add2Star = add2Star;
    }

    public boolean isAdd3Star() {
        return add3Star;
    }

    public void setAdd3Star(boolean add3Star) {
        this.add3Star = add3Star;
    }

    public boolean isAdd4Star() {
        return add4Star;
    }

    public void setAdd4Star(boolean add4Star) {
        this.add4Star = add4Star;
    }

    public boolean isAdd5Star() {
        return add5Star;
    }

    public void setAdd5Star(boolean add5Star) {
        this.add5Star = add5Star;
    }
}
