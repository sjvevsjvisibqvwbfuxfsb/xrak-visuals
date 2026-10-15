package com.xrak.visuals.module;

import java.util.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import com.xrak.visuals.XrakVisualsMod;
import com.xrak.visuals.module.impl.*;

public class ModuleManager {
    private final XrakVisualsMod mod;
    private final Map<String, Module> modules;
    private final Map<ModuleCategory, List<Module>> modulesByCategory;

    public ModuleManager(XrakVisualsMod mod) {
        this.mod = mod;
        this.modules = new LinkedHashMap<>();
        this.modulesByCategory = new EnumMap<>(ModuleCategory.class);
        
        for (ModuleCategory category : ModuleCategory.values()) {
            modulesByCategory.put(category, new ArrayList<>());
        }
    }

    public void registerAllModules() {
        registerModule(new TargetESPModule());
        registerModule(new HitFXModule());
        registerModule(new ParticleTrailModule());
        registerModule(new CustomHandModule());
        registerModule(new TotemFXModule());
        registerModule(new MultiHUDModule());
        registerModule(new AspectRatioModule());
        registerModule(new WaypointsModule());
    }

    public void registerModule(Module module) {
        modules.put(module.getName().toLowerCase(), module);
        modulesByCategory.get(module.getCategory()).add(module);
    }

    public Module getModule(String name) {
        return modules.get(name.toLowerCase());
    }

    public Collection<Module> getAllModules() {
        return modules.values();
    }

    public List<Module> getModulesByCategory(ModuleCategory category) {
        return new ArrayList<>(modulesByCategory.getOrDefault(category, new ArrayList<>()));
    }

    public void onTick() {
        for (Module module : modules.values()) {
            if (module.isEnabled()) {
                module.onTick();
            }
        }
    }

    public void renderHUD(DrawContext context, float tickDelta) {
        for (Module module : modules.values()) {
            if (module.isEnabled() && module.isVisible()) {
                module.renderHUD(context, tickDelta);
            }
        }
    }

    public void renderWorld(MatrixStack matrices, float tickDelta) {
        for (Module module : modules.values()) {
            if (module.isEnabled()) {
                module.renderWorld(matrices, tickDelta);
            }
        }
    }

    public boolean toggleModule(String name) {
        Module module = getModule(name);
        if (module != null) {
            module.toggle();
            return module.isEnabled();
        }
        return false;
    }
}
