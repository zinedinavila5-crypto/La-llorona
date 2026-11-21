package com.lallorona.client;

import com.lallorona.LaLloronaMod;
import com.lallorona.entity.LaLloronaEntity;
import com.lallorona.registry.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = LaLloronaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModBusClient {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.LA_LLORONA.get(), LaLloronaRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = LaLloronaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeBusClient {
        @SubscribeEvent
        public static void onRenderFog(ViewportEvent.RenderFog event) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;

            AABB area = mc.player.getBoundingBox().inflate(38.0D);
            List<LaLloronaEntity> nearby = mc.level.getEntitiesOfClass(LaLloronaEntity.class, area);
            if (!nearby.isEmpty() && !nearby.get(0).isLiberated()) {
                float dist = (float) Math.max(4.0, Math.min(38.0, mc.player.distanceTo(nearby.get(0))));
                float factor = dist / 38.0F;
                event.setNearPlaneDistance(1.5F + factor * 4.5F);
                event.setFarPlaneDistance(14.0F + factor * 24.0F);
                event.setCanceled(true);
            }
        }

        @SubscribeEvent
        public static void onFogColor(ViewportEvent.ComputeFogColor event) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;

            AABB area = mc.player.getBoundingBox().inflate(38.0D);
            List<LaLloronaEntity> nearby = mc.level.getEntitiesOfClass(LaLloronaEntity.class, area);
            if (!nearby.isEmpty() && !nearby.get(0).isLiberated()) {
                event.setRed(0.07F);
                event.setGreen(0.11F);
                event.setBlue(0.15F);
            }
        }
    }
}
