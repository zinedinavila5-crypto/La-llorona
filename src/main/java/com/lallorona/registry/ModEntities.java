package com.lallorona.registry;

import com.lallorona.LaLloronaMod;
import com.lallorona.entity.LaLloronaEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LaLloronaMod.MOD_ID);

    public static final RegistryObject<EntityType<LaLloronaEntity>> LA_LLORONA =
            ENTITY_TYPES.register("la_llorona",
                    () -> EntityType.Builder.of(LaLloronaEntity::new, MobCategory.MONSTER)
                            .sized(0.85F, 3.95F)
                            .fireImmune()
                            .clientTrackingRange(96)
                            .build(new ResourceLocation(LaLloronaMod.MOD_ID, "la_llorona").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
