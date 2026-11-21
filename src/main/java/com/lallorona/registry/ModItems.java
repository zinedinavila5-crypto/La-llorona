package com.lallorona.registry;

import com.lallorona.LaLloronaMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LaLloronaMod.MOD_ID);

    public static final RegistryObject<Item> MEDALLON_PLATA = ITEMS.register("medallon_plata",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DIARIO_ELENA = ITEMS.register("diario_elena",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LLAVE_CAPILLA = ITEMS.register("llave_capilla",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> VELA_BENDITA = ITEMS.register("vela_bendita",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> FRASCO_AGUA_RIO = ITEMS.register("frasco_agua_rio",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CARTA_PARROCO = ITEMS.register("carta_parroco",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LINTERNA_ACEITE = ITEMS.register("linterna_aceite",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> RELIQUIA_CUNA = ITEMS.register("reliquia_cuna",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LA_LLORONA_SPAWN_EGG = ITEMS.register("la_llorona_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LA_LLORONA, 0xD8E2E6, 0x1A2E38, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
