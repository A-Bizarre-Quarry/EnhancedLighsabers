package de.abq.partium.event;

import de.abq.partium.Partium;
import de.abq.partium.addon_system.ParseDataPacks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

/*
@EventBusSubscriber(modid = Partium.MOD_ID)
public class ServerStartEvent {
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        ParseDataPacks.printDatapacks(server);
    }
}*/
