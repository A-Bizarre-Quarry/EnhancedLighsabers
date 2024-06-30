package de.abq.laser_saber.common.item;

import de.abq.laser_saber.Constants;
import de.abq.laser_saber.common.data_components.ELDataComponents;
import de.abq.laser_saber.common.data_components.PartsComponents;
import de.abq.laser_saber.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.BiConsumer;

public class LSItems {
    public static final Item SWORD = new SwordItem(Services.PLATFORM.defaultItemBuilder().rarity(Rarity.EPIC).component(ELDataComponents.SWORD_PARTS, PartsComponents.DEFAULT));

    public static void registerItems(BiConsumer<Item, ResourceLocation> register) {
        register.accept(SWORD, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sword"));
    }
}