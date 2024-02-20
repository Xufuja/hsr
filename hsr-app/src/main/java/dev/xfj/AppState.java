package dev.xfj;

import imgui.type.ImString;

public class AppState {
    public ImString hashBuffer = new ImString("", 512);
    public ImString lastHash = new ImString("");
    public int frameItemIndex = 0;
    public int subFrameItemIndex = 0;
    public boolean add2Star = true;
    public boolean add3Star = true;
    public boolean add4Star = true;
    public boolean add5Star = true;
}
