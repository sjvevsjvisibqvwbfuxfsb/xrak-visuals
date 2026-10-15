package com.xrak.visuals.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Module {
    protected String name;
    protected String description;
    protected ModuleCategory category;
    protected boolean enabled;
    protected boolean visible;
    protected int keyBind;
    protected float[] settings;

    public Module(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
        this.visible = true;
        this.keyBind = -1;
        this.settings = new float[10];
    }

    public abstract void onEnable();
    public abstract void onDisable();
    public abstract void onTick();
    public abstract void renderHUD(DrawContext context, float tickDelta);
    public abstract void renderWorld(MatrixStack matrices, float tickDelta);

    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }

    public void enable() {
        if (!enabled) {
            enabled = true;
            onEnable();
        }
    }

    public void disable() {
        if (enabled) {
            enabled = false;
            onDisable();
        }
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ModuleCategory getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public boolean isVisible() { return visible; }
    public int getKeyBind() { return keyBind; }
    public void setKeyBind(int key) { this.keyBind = key; }
    public float getSetting(int index) { return index < settings.length ? settings[index] : 0f; }
    public void setSetting(int index, float value) { if (index < settings.length) settings[index] = value; }
}
