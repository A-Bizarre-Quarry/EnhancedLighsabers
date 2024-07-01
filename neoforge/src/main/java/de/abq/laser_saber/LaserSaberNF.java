package de.abq.laser_saber;

import de.abq.laser_saber.addon_system.ParseDataPacks;
import de.abq.laser_saber.common.data_components.ELDataComponents;
import de.abq.laser_saber.common.item.LSItems;
import de.abq.laser_saber.event.ServerStartEvent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class LaserSaberNF {
    public LaserSaberNF(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        LaserSaber.init();

        eventBus.addListener((RegisterEvent event) -> {
            bindDataComponents(event, ELDataComponents::register);
            bindItems(event, LSItems::registerItems );
        });

        NeoForge.EVENT_BUS.register(new ServerStartEvent());
    }
    private void bindItems(RegisterEvent event, Consumer<BiConsumer<Item, ResourceLocation>> source){
        if (event.getRegistryKey().equals(Registries.ITEM)){
            source.accept( (t, rl) ->{
                //TODO: Add to inv
                event.register(Registries.ITEM, rl, () -> t);
            });
        }
    }

    private void bindDataComponents(RegisterEvent event, Consumer<BiConsumer<ResourceLocation, DataComponentType<?>>> source){
        if (event.getRegistryKey().equals(Registries.DATA_COMPONENT_TYPE)){
            source.accept( (rl, t) ->{
                event.register(Registries.DATA_COMPONENT_TYPE, rl, () -> t);
            });
        }
    }
}