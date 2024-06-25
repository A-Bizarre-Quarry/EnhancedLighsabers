package de.abq.enhanced_lightsabers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.common.data_components.ELDataComponents;
import de.abq.enhanced_lightsabers.common.data_components.PartsComponents;
import de.abq.enhanced_lightsabers.common.item.TestLightsaberItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TestLightsaberRenderer extends GeoItemRenderer<TestLightsaberItem> {
    private TestLightsaberBladeRenderLayer bladeRenderLayer = new TestLightsaberBladeRenderLayer(this);

    public TestLightsaberRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "test/test_saber")));

        addRenderLayer(bladeRenderLayer); /*Blade RenderLayer*/
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        RenderType renderType = getRenderType(animatable, getTextureLocation(animatable), bufferSource, 1);
        PartsComponents parts = stack.getComponents().get(ELDataComponents.LIGHTSABER_PARTS);
        if (renderType == null) {
            Constants.LOG.warn("renderType == null");
            return;
        }
        if (parts == null) {
            Constants.LOG.warn("parts == null");
            return;
        }

        float bladeLength = (float) parts.value1();
        if (bladeLength != this.bladeRenderLayer.getBladeLength()) this.bladeRenderLayer.setBladeLength(bladeLength);

        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
