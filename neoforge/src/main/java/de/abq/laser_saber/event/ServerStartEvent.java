package de.abq.laser_saber.event;

import de.abq.laser_saber.addon_system.ParseDataPacks;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

public class ServerStartEvent {
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        ParseDataPacks.printDatapacks(server);
    }
}
