package com.lallorona.events;

import com.lallorona.entity.LaLloronaEntity;
import com.lallorona.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class StoryEventHandler {
    private static final Map<UUID, List<ItemStack>> SAVED_QUEST_ITEMS = new HashMap<>();

    private static boolean isQuestItem(ItemStack stack) {
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        return item == ModItems.MEDALLON_PLATA.get()
                || item == ModItems.DIARIO_ELENA.get()
                || item == ModItems.LLAVE_CAPILLA.get()
                || item == ModItems.VELA_BENDITA.get()
                || item == ModItems.FRASCO_AGUA_RIO.get()
                || item == ModItems.CARTA_PARROCO.get()
                || item == ModItems.LINTERNA_ACEITE.get()
                || item == ModItems.RELIQUIA_CUNA.get()
                || stack.getOrCreateTag().getBoolean("LaLloronaQuestItem");
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        List<ItemStack> kept = new ArrayList<>();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (isQuestItem(stack)) {
                kept.add(stack.copy());
                player.getInventory().setItem(i, ItemStack.EMPTY);
            }
        }
        SAVED_QUEST_ITEMS.put(player.getUUID(), kept);

        player.serverLevel().getEntitiesOfClass(LaLloronaEntity.class,
                player.getBoundingBox().inflate(64.0D)).forEach(llorona -> {
            if (!llorona.isLiberated()) {
                llorona.setTarget(null);
                llorona.getNavigation().stop();
                llorona.setAnimState(LaLloronaEntity.STATE_FLOAT_STALK, 40);
            }
        });
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        List<ItemStack> kept = SAVED_QUEST_ITEMS.remove(player.getUUID());
        if (kept != null) {
            for (ItemStack stack : kept) {
                player.getInventory().add(stack);
            }
        }

        player.displayClientMessage(Component.translatable("message.lallorona.checkpoint_retry"), false);
        player.server.getCommands().performPrefixedCommand(
                player.server.createCommandSourceStack().withSuppressedOutput(),
                "execute as " + player.getScoreboardName() + " at @s run function lallorona:checkpoints/on_death");
    }
}
