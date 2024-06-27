package de.abq.enhanced_lightsabers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.abq.enhanced_lightsabers.client.model.DynamicModel;
import de.abq.enhanced_lightsabers.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class ModelRenderLayer<T extends GeoAnimatable> extends AutoGlowingGeoLayer<T> {
    private ResourceLocation model;
    private final String joint_name;

    public ModelRenderLayer(GeoRenderer<T> renderer, String joint_name) {
        super(renderer);
        this.joint_name = joint_name;
    }

    @Override
    public void renderForBone(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (bone.getName().equals("joint_"+joint_name)){
            if (model != Util.EMPTY_RESOURCE_LOCATION) {
                poseStack.pushPose();
                //TODO: add translation based on `joint_name` pivot point
                this.getRenderer().reRender(new DynamicModel<T>(model).getBakedModel(model), poseStack, bufferSource, animatable, renderType, buffer, partialTick, packedLight, packedOverlay, 0xffffffff);
                poseStack.pushPose();
            }
        }
        super.renderForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
    }

    public ResourceLocation getModel() {
        return model;
    }

    public void setModel(ResourceLocation model) {
        this.model = model;
    }

    public String getJoint_name() {
        return joint_name;
    }
}
