package de.abq.enhanced_lightsabers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.common.data_components.ELDataComponents;
import de.abq.enhanced_lightsabers.common.data_components.PartsComponents;
import de.abq.enhanced_lightsabers.common.data_components.parts.BladesPart;
import de.abq.enhanced_lightsabers.common.item.SwordItem;
import de.abq.enhanced_lightsabers.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SwordRenderer extends GeoItemRenderer<SwordItem> {
    private BladeRenderLayer bladeRenderLayer = new BladeRenderLayer(this);

    public SwordRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sword")));

        addRenderLayer(bladeRenderLayer); /*Blade RenderLayer*/
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        RenderType renderType = getRenderType(animatable, getTextureLocation(animatable), bufferSource, 1);
        PartsComponents parts = stack.getComponents().get(ELDataComponents.SWORD_PARTS);
        if (renderType == null) {
            Constants.LOG.warn("renderType == null");
        }
        if (parts == null) {
            Constants.LOG.warn("parts == null");
        } else {
            BladesPart blades = parts.blades();
            if (blades != this.bladeRenderLayer.getBlades())
                this.bladeRenderLayer.setBlades(blades);
        }
        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
