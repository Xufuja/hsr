package dev.xfj;

import imgui.type.ImString;

public class AppState {
    public ImString hashBuffer = new ImString("", 512);
    public ImString lastHash = new ImString("");
    public int relicItemIndex = 0;
    public int subRelicItemIndex = 0;
    public boolean add2Star = true;
    public boolean add3Star = true;
    public boolean add4Star = true;
    public boolean add5Star = true;
    public int characterItemIndex = 0;
}
