package de.abq.partium;

import de.abq.partium.common.data_components.ELDataComponents;
import de.abq.partium.common.item.ZItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class PartiumFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.

        Partium.init();


        bindItems( ZItems::registerItems );
        bindDataComponents( ELDataComponents::register );

    }

    private void bindItems(Consumer<BiConsumer<Item, ResourceLocation>> source){
        source.accept( (t, rl) ->{
            //TODO: Add to inv
            Registry.register(BuiltInRegistries.ITEM, rl, t);
        });
    }

    private void bindDataComponents(Consumer<BiConsumer<ResourceLocation, DataComponentType<?>>> source){
        source.accept( (rl, t) ->{
            Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, rl, t);
        });
    }
}
