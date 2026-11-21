package com.lallorona.registry;

import com.lallorona.LaLloronaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LaLloronaMod.MOD_ID);

    public static final RegistryObject<SoundEvent> LAMENTO_LEJANO = registerSound("lamento_lejano");
    public static final RegistryObject<SoundEvent> GRITO_SOBRENATURAL = registerSound("grito_sobrenatural");
    public static final RegistryObject<SoundEvent> SUSURROS_HABITACION = registerSound("susurros_habitacion");
    public static final RegistryObject<SoundEvent> LLANTO_RIO = registerSound("llanto_rio");
    public static final RegistryObject<SoundEvent> PASOS_MADERA = registerSound("pasos_madera");
    public static final RegistryObject<SoundEvent> SONIDO_AGUA = registerSound("sonido_agua");
    public static final RegistryObject<SoundEvent> VIENTO_BOSQUE = registerSound("viento_bosque");
    public static final RegistryObject<SoundEvent> PUERTA_CHIRRIDO = registerSound("puerta_chirrido");
    public static final RegistryObject<SoundEvent> GOLPE_REPENTINO = registerSound("golpe_repentino");
    public static final RegistryObject<SoundEvent> MUSICA_AMBIENTAL = registerSound("musica_ambiental");
    public static final RegistryObject<SoundEvent> MUSICA_PERSECUCION = registerSound("musica_persecucion");
    public static final RegistryObject<SoundEvent> MUSICA_RITUAL_LIBERACION = registerSound("musica_ritual_liberacion");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name,
                () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(LaLloronaMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
