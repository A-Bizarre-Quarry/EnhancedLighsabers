package de.abq.enhanced_lightsabers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.client.model.DynamicModel;
import de.abq.enhanced_lightsabers.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.Color;

import java.io.Console;
import java.util.Objects;

public class ModelRenderLayer<T extends GeoAnimatable> extends AutoGlowingGeoLayer<T> {
    private ResourceLocation model;
    private float scale;
    private final String joint_name;
    private boolean retry = true;

    public ModelRenderLayer(GeoRenderer<T> renderer, String joint_name) {
        super(renderer);
        this.joint_name = joint_name;
    }

    @Override
    public void renderForBone(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (retry && bone.getName().equals("joint_"+joint_name) && model != Util.EMPTY_RESOURCE_LOCATION && !Objects.equals(model, ResourceLocation.fromNamespaceAndPath("minecraft", ""))) {
            DynamicModel<T> dynModel = new DynamicModel<>(model);
            try {
                BakedGeoModel bakedGeoModel= dynModel.getBakedModel(dynModel.getModelResource(animatable));
                poseStack.pushPose();
                //TODO: add translation based on `joint_name` pivot point

                poseStack.scale(scale, scale, scale);
                poseStack.translate(
                        (bone.getPivotX()-bakedGeoModel.getBone(joint_name).get().getPivotX())/16/scale,
                        (bone.getPivotY()-bakedGeoModel.getBone(joint_name).get().getPivotY())/16,
                        (bone.getPivotZ()-bakedGeoModel.getBone(joint_name).get().getPivotZ()+1)/16/scale
                );
                poseStack.rotateAround(new Quaternionf().rotationXYZ(-bone.getRotX(), -bone.getRotY(), -bone.getRotZ()), bone.getPivotX(), bone.getPivotY(), bone.getPivotZ());

                // Scale down
                this.getRenderer().reRender(bakedGeoModel, poseStack, bufferSource, animatable, renderType, bufferSource.getBuffer(renderType), partialTick, packedLight, packedOverlay, Color.WHITE.argbInt());
                poseStack.popPose();
            } catch (Exception e){
                Constants.LOG.error(e.getMessage());
                retry = false;
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
    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }

    private float scaleTransform(float scale){
        if (scale == 1) return 1;
        if (scale == 0) return 2;
        return 3f-scale;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
}
