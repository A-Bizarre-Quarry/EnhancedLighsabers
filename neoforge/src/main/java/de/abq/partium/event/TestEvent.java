package de.abq.partium.event;

import de.abq.partium.Partium;
import de.abq.partium.addon_system.ParseDataPacks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@EventBusSubscriber(modid = Partium.MOD_ID)
public class TestEvent {
    /*@SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        ParseDataPacks.printDatapacks(player.getServer());

        //checkResourcePacks(player);
    }*/
    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level().isClientSide() && entity instanceof LivingEntity living) {
            ParseDataPacks.printDatapacks(living.getServer());
        }
    }
}
