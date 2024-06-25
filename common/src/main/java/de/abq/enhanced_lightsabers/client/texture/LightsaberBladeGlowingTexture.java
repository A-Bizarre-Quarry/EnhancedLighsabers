package de.abq.enhanced_lightsabers.client.texture;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import net.minecraft.client.renderer.RenderStateShard;

import java.io.IOException;

public class LightsaberBladeGlowingTexture extends AutoGlowingTexture {
    public LightsaberBladeGlowingTexture(ResourceLocation originalLocation, ResourceLocation location) {
        super(originalLocation, location);
    }

}
