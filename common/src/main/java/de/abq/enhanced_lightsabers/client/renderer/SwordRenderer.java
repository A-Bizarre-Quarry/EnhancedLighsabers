package de.abq.enhanced_lightsabers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.client.model.DynamicModel;
import de.abq.enhanced_lightsabers.common.data_components.ELDataComponents;
import de.abq.enhanced_lightsabers.common.data_components.PartsComponents;
import de.abq.enhanced_lightsabers.common.data_components.parts.BladesPart;
import de.abq.enhanced_lightsabers.common.data_components.parts.ModelPart;
import de.abq.enhanced_lightsabers.common.item.SwordItem;
import de.abq.enhanced_lightsabers.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.Color;

public class SwordRenderer extends GeoItemRenderer<SwordItem> {
    private BladeRenderLayer bladeRenderLayer = new BladeRenderLayer(this);
    private ModelRenderLayer<SwordItem> emitterRenderLayer = new ModelRenderLayer<>(this, "emitter");
    private ModelRenderLayer<SwordItem> pommelRenderLayer = new ModelRenderLayer<>(this, "pommel");
    private ModelRenderLayer<SwordItem> guardRenderLayer = new ModelRenderLayer<>(this, "guard");
    private DynamicModel<SwordItem> gripModel = new DynamicModel<>(Util.EMPTY_RESOURCE_LOCATION);

    public SwordRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sword")));

        addRenderLayer(bladeRenderLayer); /*Blade RenderLayer*/
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
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
            ModelPart emitter = parts.emitter();
            ModelPart guard = parts.guard();
            ModelPart grip = parts.grip();
            ModelPart pommel = parts.pommel();
            if (blades != this.bladeRenderLayer.getBlades())
                this.bladeRenderLayer.setBlades(blades);

            if (emitter.model() != this.emitterRenderLayer.getModel())
                this.emitterRenderLayer.setModel(emitter.model());
            if (guard.model() != this.guardRenderLayer.getModel())
                this.guardRenderLayer.setModel(guard.model());
            if (pommel.model() != this.pommelRenderLayer.getModel())
                this.pommelRenderLayer.setModel(pommel.model());
            if (grip.model() != this.gripModel.getBasicResourceLocation() && grip.model() != Util.EMPTY_RESOURCE_LOCATION) {
                System.out.println(grip.model());
                DynamicModel<SwordItem> gripModel = new DynamicModel<>(grip.model());
                this.reRender(gripModel.getBakedModel(gripModel.getModelResource(animatable)), poseStack, bufferSource, animatable, renderType, bufferSource.getBuffer(renderType), 0f, packedLight, packedOverlay, Color.WHITE.argbInt()); //TODO: fix animatiable and color
            }
        }
        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
