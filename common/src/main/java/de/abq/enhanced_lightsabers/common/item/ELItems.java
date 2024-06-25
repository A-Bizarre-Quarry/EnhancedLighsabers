package de.abq.enhanced_lightsabers.common.item;

import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.BiConsumer;

public class ELItems {
    public static final Item LIGHTSABER = new TestLightsaberItem(Services.PLATFORM.defaultItemBuilder().rarity(Rarity.EPIC));

    public static void registerItems(BiConsumer<Item, ResourceLocation> register) {
        register.accept(LIGHTSABER, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "test_saber"));
    }
}