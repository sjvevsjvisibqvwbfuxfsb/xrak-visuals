package com.xrak.visuals.module;

public enum ModuleCategory {
    VISUALS("Visuals", 0xFF00AAFF),
    COSMETICS("Cosmetics", 0xFFFF00AA),
    HUD("HUD", 0xFFFFAA00),
    NAVIGATION("Navigation", 0xFF00FF00),
    OPTIMIZATION("Optimization", 0xFF888888);

    private final String displayName;
    private final int color;

    ModuleCategory(String displayName, int color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() { return displayName; }
    public int getColor() { return color; }
}
