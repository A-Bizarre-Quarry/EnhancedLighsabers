package de.abq.enhanced_lightsabers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.common.data_components.parts.BladesPart;
import de.abq.enhanced_lightsabers.common.item.SwordItem;
import de.abq.enhanced_lightsabers.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.Optional;
import java.util.function.Consumer;

public class BladeRenderLayer extends GeoRenderLayer<SwordItem>{

    private BladesPart blades = null;


    public BladeRenderLayer(SwordRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void renderForBone(PoseStack poseStack, SwordItem animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (bone.getName().equals("joint_blade")) {
            for (GeoBone blade_joint : bone.getChildBones()) {
                Optional<BladesPart.Blade> bladeDataOpt = blades.getByString(blade_joint.getName());
                if (bladeDataOpt.isEmpty()) continue;
                BladesPart.Blade bladeData = bladeDataOpt.get();

                if (bladeData.model().getPath().isBlank() || bladeData.model().getNamespace().isBlank()){
                    Tuple<MultiBufferSource, PoseStack> blade = renderLightsaberBlade(bufferSource, poseStack, blade_joint, bladeData.length() * 2, Util.HexStringToIntARGB(bladeData.outerColor(), 0x66), Util.HexStringToIntARGB(bladeData.innerColor(), 0xff));
                    bufferSource = blade.getA();
                    poseStack = blade.getB();
                } else {
                    renderModel(animatable, bladeData.model());
                }
            }

        }
        super.renderForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, 15728640, OverlayTexture.NO_OVERLAY);
    }

    private void renderModel(SwordItem animatable, ResourceLocation model){
    }

    private Tuple<MultiBufferSource, PoseStack> renderLightsaberBlade(MultiBufferSource bufferSource, PoseStack poseStack, GeoBone blade_joint, float completeBladeLength, int outerColor, int innerColor){
        poseStack.pushPose();

        poseStack.scale(0.05f, 0.05f, 0.05f);  // Scale down
        poseStack.rotateAround(new Quaternionf().rotationXYZ(-blade_joint.getRotX(), -blade_joint.getRotY(), -blade_joint.getRotZ()), blade_joint.getPivotX(), blade_joint.getPivotY(), blade_joint.getPivotZ());
        poseStack.translate((blade_joint.getPivotX() * 1.3125), (blade_joint.getPivotY() * 1.3125)-0.05, (blade_joint.getPivotZ() * 1.3125)); // Position it at the cube's location

        Matrix4f matrix = poseStack.last().pose();

        // Define the color and texture coordinates
        //this.outerColor = 0x884444ff;
        //this.innerColor = 0xffffffff;
        int maxLight = 0xF000F0; //Geckolib uses 15728640
        float u = 0.0f, v = 0.0f;
        float tip_length = 0.73f;
        float bladeHeight = completeBladeLength - tip_length;

        float inner_blade_thickness = .25f, inner_blade_length = .25f;
        float outer_blade_thickness = inner_blade_thickness * 3.5f , outer_blade_length = inner_blade_length * 3.5f;

        //Outer Blade
        VertexConsumer outerBuffer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/misc/lightsaber_blade_glow.png")));
        // Front face
        outerBuffer.addVertex(matrix, -outer_blade_thickness, (bladeHeight + 0.01f), outer_blade_length).setColor(outerColor).setUv(u + 0.5f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, (bladeHeight + 0.01f), outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, -0.5f, outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        outerBuffer.addVertex(matrix, -outer_blade_thickness, -0.5f, outer_blade_length).setColor(outerColor).setUv(u + 0.5f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        // Back face
        outerBuffer.addVertex(matrix, -outer_blade_thickness, -0.51f, -outer_blade_length).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, -0.51f, -outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, (bladeHeight + 0.01f), -outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        outerBuffer.addVertex(matrix, -outer_blade_thickness, (bladeHeight + 0.01f), -outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        // Left face
        outerBuffer.addVertex(matrix, -outer_blade_thickness, (bladeHeight + 0.01f), -outer_blade_length).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_thickness, (bladeHeight + 0.01f), outer_blade_length).setColor(outerColor).setUv(u + 0.5f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_thickness, -0.51f, outer_blade_length).setColor(outerColor).setUv(u + 0.5f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_thickness, -0.51f, -outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);

        // Right face
        outerBuffer.addVertex(matrix, outer_blade_thickness, (bladeHeight + 0.01f), outer_blade_length).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, (bladeHeight + 0.01f), -outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, -0.51f, -outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, -0.51f, outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);

        /*// Top face
        outerBuffer.addVertex(matrix, -outer_blade_length, (bladeHeight + 0.01f), outer_blade_length).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, (bladeHeight + 0.01f), outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, (bladeHeight + 0.01f), -outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_length, (bladeHeight + 0.01f), -outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);

        // Bottom face
        outerBuffer.addVertex(matrix, -outer_blade_length, -0.51f, -outer_blade_length).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, -0.51f, -outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, -0.51f, outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_length, -0.5f, outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
*/
        //Tip front
        outerBuffer.addVertex(matrix, 0f, bladeHeight + tip_length+0.75f, 0f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, bladeHeight, outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_length, bladeHeight, outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);

        outerBuffer.addVertex(matrix, 0f, bladeHeight + tip_length+0.75f, 0f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_length, bladeHeight, outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_length, bladeHeight, -outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);

        outerBuffer.addVertex(matrix, 0f, bladeHeight + tip_length+0.75f, 0f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, bladeHeight, -outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_length, bladeHeight, -outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);

        outerBuffer.addVertex(matrix, 0f, bladeHeight + tip_length+0.75f, 0f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, outer_blade_thickness, bladeHeight, outer_blade_length).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);
        outerBuffer.addVertex(matrix, -outer_blade_length, bladeHeight, -outer_blade_length).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f,1f,1f);


        //Inner Blade

        VertexConsumer innerBuffer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/misc/lightsaber_blade.png"), false));
        // Bottom square
        innerBuffer.addVertex(matrix, -inner_blade_thickness, -0.5f, -inner_blade_length).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, -0.5f, -inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, -0.5f, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, -0.5f, inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        // Front face
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, -0.5f, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, -0.5f, inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        // Back face
        innerBuffer.addVertex(matrix, -inner_blade_thickness, -0.5f, -inner_blade_length).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, -0.5f, -inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        // Left face
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, -0.5f, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, -0.5f, -inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
/*
        // Right face
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, -0.5f, -inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, -0.5f, inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        // Top face
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
*/
        //Tip front
        innerBuffer.addVertex(matrix, 0f, bladeHeight + tip_length, 0f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        innerBuffer.addVertex(matrix, 0f, bladeHeight + tip_length, 0f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        innerBuffer.addVertex(matrix, 0f, bladeHeight + tip_length, 0f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        innerBuffer.addVertex(matrix, 0f, bladeHeight + tip_length, 0f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, inner_blade_thickness, bladeHeight, inner_blade_length).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
        innerBuffer.addVertex(matrix, -inner_blade_thickness, bladeHeight, -inner_blade_length).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

        poseStack.popPose();
        return new Tuple<>(bufferSource, poseStack);
    }

    public BladesPart getBlades() {
        return blades;
    }
    public void setBlades(BladesPart blades) {
        this.blades = blades;
    }
}