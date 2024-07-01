package de.abq.partium.event;

import de.abq.partium.addon_system.ParseDataPacks;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class ClientJoinServerEvent {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        ParseDataPacks.printDatapacks(player.getServer());

        //checkResourcePacks(player);
    }
}
