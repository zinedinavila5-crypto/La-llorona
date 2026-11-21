package com.lallorona.registry;

import com.lallorona.LaLloronaMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LaLloronaMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LALLORONA_TAB =
            CREATIVE_MODE_TABS.register("lallorona_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.MEDALLON_PLATA.get()))
                    .title(Component.translatable("creativetab.lallorona.main"))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.MEDALLON_PLATA.get());
                        output.accept(ModItems.DIARIO_ELENA.get());
                        output.accept(ModItems.LLAVE_CAPILLA.get());
                        output.accept(ModItems.VELA_BENDITA.get());
                        output.accept(ModItems.FRASCO_AGUA_RIO.get());
                        output.accept(ModItems.CARTA_PARROCO.get());
                        output.accept(ModItems.LINTERNA_ACEITE.get());
                        output.accept(ModItems.RELIQUIA_CUNA.get());
                        output.accept(ModItems.LA_LLORONA_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
