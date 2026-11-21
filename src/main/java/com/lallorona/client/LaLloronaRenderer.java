package com.lallorona.client;

import com.lallorona.entity.LaLloronaEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class LaLloronaRenderer extends GeoEntityRenderer<LaLloronaEntity> {
    public LaLloronaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LaLloronaModel());
        this.shadowRadius = 0.65F;
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public RenderType getRenderType(LaLloronaEntity animatable, ResourceLocation texture,
                                    MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
