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
import org.joml.Vector3d;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;

public class ModelRenderLayer<T extends GeoAnimatable> extends AutoGlowingGeoLayer<T> {
    private ResourceLocation model;
    private float scale;
    private float parentScale;
    private final String joint_name;
    private boolean retryWholeDraw = true;
    private boolean retryTextureDraw = true;

    public ModelRenderLayer(GeoRenderer<T> renderer, String joint_name) {
        super(renderer);
        this.joint_name = joint_name;
    }
    public ModelRenderLayer(GeoRenderer<T> renderer) {
        super(renderer);
        this.joint_name = "";
    }

    @Override
    public void renderForBone(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        renderModel(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        super.renderForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
    }

    public void renderModel(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay){
        if (retryWholeDraw && bone.getName().equals("joint_"+joint_name) && model != Util.EMPTY_RESOURCE_LOCATION && !Objects.equals(model, ResourceLocation.fromNamespaceAndPath("minecraft", ""))) {
            DynamicModel<T> dynModel = new DynamicModel<>(model);
            try {
                BakedGeoModel bakedGeoModel = dynModel.getBakedModel(dynModel.getModelResource(animatable));
                GeoBone additionalBone = bakedGeoModel.getBone(joint_name).get();
                poseStack.pushPose();
                //TODO: add translation based on `joint_name` pivot point

                poseStack.translate(
                        (bone.getPivotX() - additionalBone.getPivotX()*scale)/16,
                        (bone.getPivotY() - additionalBone.getPivotY()*scale)/16,
                        (bone.getPivotZ() - additionalBone.getPivotZ()*scale)/16
                );
                poseStack.scale(scale, scale, scale);

                poseStack.rotateAround(new Quaternionf().rotationXYZ(-bone.getRotX(), -bone.getRotY(), -bone.getRotZ()), bone.getPivotX(), bone.getPivotY(), bone.getPivotZ());

                //poseStack.scale(1/parentScale,1/parentScale,1/parentScale);

                if (retryTextureDraw) {
                    try {
                        renderType = RenderType.entityTranslucent(dynModel.getTextureResource(animatable));
                    } catch (Exception e) {
                        retryTextureDraw = false;
                        Constants.LOG.error(e.getMessage());
                    }
                }

                this.getRenderer().reRender(bakedGeoModel, poseStack, bufferSource, animatable, renderType, bufferSource.getBuffer(renderType), partialTick, packedLight, packedOverlay, Color.WHITE.argbInt());
                poseStack.popPose();

                if (joint_name.equals("emitter") && bone.getName().equals("joint_emitter")) sendBladeJoints(bone, bakedGeoModel.getBone("joint_blade").get());
            } catch (Exception e){
                Constants.LOG.error("Caught: {}", e.getMessage());
                retryWholeDraw = false;
            }
        }
    }

    private void sendBladeJoints(GeoBone bone, GeoBone additionJoint){
        SwordRenderer swordRenderer = ((SwordRenderer) this.getRenderer());

        Vector3f emitterLocation = new Vector3f(
                (bone.getPivotX() - additionJoint.getPivotX()),
                (bone.getPivotY() - additionJoint.getPivotY()),
                (bone.getPivotZ() - additionJoint.getPivotZ())
        );
        swordRenderer.setBladeEmitterLocation( emitterLocation );

        for (GeoBone emitterBone : additionJoint.getChildBones()) {
            swordRenderer.pushBladeJointsChecked(emitterBone);
        }
    }

    public ResourceLocation getModel() {
        return model;
    }
    public void setModel(ResourceLocation model) {
        this.model = model;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
    public void setRetryWholeDraw(boolean retryWholeDraw) {
        this.retryWholeDraw = retryWholeDraw;
    }

    public void setRetryTextureDraw(boolean retryTextureDraw) {
        this.retryTextureDraw = retryTextureDraw;
    }
    public void setRetry(boolean retry){
        this.setRetryTextureDraw(retry);
        this.setRetryWholeDraw(retry);
    }
    public void setParentScale(float scale) {
        this.parentScale = scale;
    }
}
