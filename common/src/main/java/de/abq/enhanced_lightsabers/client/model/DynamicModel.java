package de.abq.enhanced_lightsabers.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class DynamicModel<T extends GeoAnimatable> extends GeoModel<T> {
    public ResourceLocation resourceLocation;
    public DynamicModel(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    public ResourceLocation getModelResource(T t) {

        return resourceLocation.withPath("geo/item/" + resourceLocation.getPath() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T t) {
        return resourceLocation.withPath("textures/item/" + resourceLocation.getPath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T t) {
        return resourceLocation.withPath("animation/item/" + resourceLocation.getPath() + ".animation.json");
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public ResourceLocation getBasicResourceLocation(){
        return this.resourceLocation;
    }
}
