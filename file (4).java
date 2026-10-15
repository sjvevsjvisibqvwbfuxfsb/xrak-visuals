package com.xrak.visuals;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import com.xrak.visuals.config.ConfigManager;
import com.xrak.visuals.gui.ClickGuiScreen;
import com.xrak.visuals.module.ModuleManager;
import com.xrak.visuals.particle.ParticleRenderEngine;

public class XrakVisualsMod implements ClientModInitializer {
    public static final String MOD_ID = "xrakvisuals";
    public static final String MOD_NAME = "Xrak Visuals";
    public static final String MOD_VERSION = "1.0";

    private static XrakVisualsMod instance;
    private ModuleManager moduleManager;
    private ParticleRenderEngine particleRenderEngine;
    private ConfigManager configManager;

    private KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        instance = this;
        
        configManager = new ConfigManager();
        configManager.loadConfig();
        
        particleRenderEngine = new ParticleRenderEngine();
        moduleManager = new ModuleManager(this);
        moduleManager.registerAllModules();
        
        setupKeyBindings();
        registerEventHandlers();
    }

    private void setupKeyBindings() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.xrakvisuals.gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.xrakvisuals"
        ));
    }

    private void registerEventHandlers() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ClickGuiScreen());
                }
            }
            moduleManager.onTick();
        });

        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> {
            moduleManager.renderHUD(guiGraphics, tickDelta);
        });

        WorldRenderEvents.END.register(context -> {
            moduleManager.renderWorld(context.matrixStack(), context.tickDelta());
        });
    }

    public static XrakVisualsMod getInstance() {
        return instance;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ParticleRenderEngine getParticleRenderEngine() {
        return particleRenderEngine;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
