package de.abq.partium.common.item;

import de.abq.partium.Partium;
import de.abq.partium.common.data_components.ELDataComponents;
import de.abq.partium.common.data_components.PartsComponents;
import de.abq.partium.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.function.BiConsumer;

public class ZItems {

    private static final Item.Properties DEFAULT_PROPERTIES = Services.PLATFORM.defaultItemBuilder().rarity(Rarity.EPIC).stacksTo(1).component(ELDataComponents.SWORD_PARTS, PartsComponents.DEFAULT);

    public static final Item SWORD = new PartiumSwordItem(Tiers.NETHERITE, DEFAULT_PROPERTIES);
    public static final Item PICKAXE = new PartiumPickaxeItem(Tiers.NETHERITE, DEFAULT_PROPERTIES);
    public static final Item AXE = new PartiumAxeItem(Tiers.NETHERITE, DEFAULT_PROPERTIES);
    public static final Item HOE = new PartiumHoeItem(Tiers.NETHERITE, DEFAULT_PROPERTIES);
    public static final Item SHOVEL = new PartiumShovelItem(Tiers.NETHERITE, DEFAULT_PROPERTIES);

    public static final Item TRIDENT = new PartiumTridentItem(DEFAULT_PROPERTIES);
    public static final Item MACE = new PartiumMaceItem(DEFAULT_PROPERTIES);

    public static final Item BOW = new PartiumBowItem(DEFAULT_PROPERTIES);
    public static final Item CROSSBOW = new PartiumCrossbowItem(DEFAULT_PROPERTIES);

    public static void registerItems(BiConsumer<Item, ResourceLocation> register) {
        register.accept(SWORD, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "sword"));

        //NEED WORK
        register.accept(PICKAXE, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "pickaxe"));
        register.accept(AXE, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "axe"));
        register.accept(HOE, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "hoe"));
        register.accept(SHOVEL, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "shovel"));
        register.accept(TRIDENT, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "trident"));
        register.accept(MACE, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "mace"));
        register.accept(BOW, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "bow"));
        register.accept(CROSSBOW, ResourceLocation.fromNamespaceAndPath(Partium.MOD_ID, "crossbow"));
    }
}