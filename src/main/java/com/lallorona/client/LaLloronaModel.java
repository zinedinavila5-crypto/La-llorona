package com.lallorona.client;

import com.lallorona.LaLloronaMod;
import com.lallorona.entity.LaLloronaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LaLloronaModel extends GeoModel<LaLloronaEntity> {
    @Override
    public ResourceLocation getModelResource(LaLloronaEntity animatable) {
        return new ResourceLocation(LaLloronaMod.MOD_ID, "geo/lallorona.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LaLloronaEntity animatable) {
        return new ResourceLocation(LaLloronaMod.MOD_ID, "textures/entity/lallorona.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LaLloronaEntity animatable) {
        return new ResourceLocation(LaLloronaMod.MOD_ID, "animations/lallorona.animation.json");
    }
}
