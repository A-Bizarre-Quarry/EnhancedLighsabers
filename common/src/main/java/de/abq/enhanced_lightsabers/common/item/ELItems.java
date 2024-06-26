package de.abq.enhanced_lightsabers.common.item;

import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.common.data_components.ELDataComponents;
import de.abq.enhanced_lightsabers.common.data_components.PartsComponents;
import de.abq.enhanced_lightsabers.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.BiConsumer;

public class ELItems {
    public static final Item SWORD = new SwordItem(Services.PLATFORM.defaultItemBuilder().rarity(Rarity.EPIC).component(ELDataComponents.SWORD_PARTS, PartsComponents.DEFAULT));

    public static void registerItems(BiConsumer<Item, ResourceLocation> register) {
        register.accept(SWORD, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sword"));
    }
}