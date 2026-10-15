package com.xrak.visuals.module.impl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import com.xrak.visuals.module.Module;
import com.xrak.visuals.module.ModuleCategory;
import com.xrak.visuals.util.RenderUtils;

public class TargetESPModule extends Module {
    private LivingEntity target;
    private int ribbonCount;
    private float chromaFrequency;
    private float chromaDepth;
    private float aurahHeight;
    private float ringScale;
    private long startTime;

    public TargetESPModule() {
        super("Target ESP", "4-Ribbon orbiting aura with chroma gradient", ModuleCategory.VISUALS);
        this.ribbonCount = 4;
        this.chromaFrequency = 2.0f;
        this.chromaDepth = 1.0f;
        this.aurahHeight = 1.0f;
        this.ringScale = 1.0f;
    }

    @Override
    public void onEnable() {
        startTime = System.currentTimeMillis();
        target = null;
    }

    @Override
    public void onDisable() {
        target = null;
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        target = null;
        double maxDistance = 64.0;
        Vec3d playerPos = client.player.getEyePos();
        Vec3d lookDirection = client.player.getRotationVec(1.0f).normalize();

        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof LivingEntity && entity != client.player && !entity.isInvisible()) {
                Vec3d entityPos = entity.getE
