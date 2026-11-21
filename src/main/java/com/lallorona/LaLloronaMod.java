package com.lallorona;

import com.lallorona.entity.LaLloronaEntity;
import com.lallorona.events.StoryEventHandler;
import com.lallorona.registry.ModCreativeTabs;
import com.lallorona.registry.ModEntities;
import com.lallorona.registry.ModItems;
import com.lallorona.registry.ModSounds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(LaLloronaMod.MOD_ID)
public class LaLloronaMod {
    public static final String MOD_ID = "lallorona";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public LaLloronaMod() {
        GeckoLib.initialize();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        modEventBus.addListener(this::registerEntityAttributes);

        MinecraftForge.EVENT_BUS.register(new StoryEventHandler());
        LOGGER.info("La Llorona: El Último Lamento inicializado correctamente.");
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LA_LLORONA.get(), LaLloronaEntity.createAttributes().build());
    }
}
