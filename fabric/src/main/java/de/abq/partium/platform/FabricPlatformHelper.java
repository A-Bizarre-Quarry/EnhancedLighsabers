package de.abq.partium.platform;

import de.abq.partium.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public String getEnvironmentName() {
        return IPlatformHelper.super.getEnvironmentName();
    }

    @Override
    public Item.Properties defaultItemBuilder() {
        return null;
    }

    @Override
    public Item.Properties defaultItemBuilderWithCustomDamageOnFabric() {
        return IPlatformHelper.super.defaultItemBuilderWithCustomDamageOnFabric();
    }

    @Override
    public <T extends DataComponentType<?>> T registerDataComponent(String name, Supplier<T> sup){
        return null;
    }
}
