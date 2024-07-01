package de.abq.laser_saber.event;

import de.abq.laser_saber.addon_system.ParseDataPacks;
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
