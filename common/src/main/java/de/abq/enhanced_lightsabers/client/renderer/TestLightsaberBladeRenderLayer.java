package de.abq.enhanced_lightsabers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.common.item.TestLightsaberItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TestLightsaberBladeRenderLayer extends GeoRenderLayer<TestLightsaberItem>{

    private float bladeLength = 0;
    private int outerColor = 0x88000000;
    private int innerColor = 0xffffffff;


    public TestLightsaberBladeRenderLayer(TestLightsaberRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void renderForBone(PoseStack poseStack, TestLightsaberItem animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (bone.getName().equals("joint_blade")) {

            //TODO: Add feature that searches for blade identifier in data component to make different colors

            for (GeoBone blade_joint : bone.getChildBones()) {
                poseStack.pushPose();

                poseStack.scale(0.05f, 0.05f, 0.05f);  // Scale down
                poseStack.rotateAround(new Quaternionf().rotationXYZ(-blade_joint.getRotX(),-blade_joint.getRotY(),-blade_joint.getRotZ()), blade_joint.getPivotX(), blade_joint.getPivotY(), blade_joint.getPivotZ());
                poseStack.translate((blade_joint.getPivotX()*1.3125), (blade_joint.getPivotY()*1.3125), (blade_joint.getPivotZ()*1.3125)); // Position it at the cube's location

                Matrix4f matrix = poseStack.last().pose();

                // Define the color and texture coordinates
                this.outerColor = 0x884444ff;
                this.innerColor = 0xffffffff;
                int maxLight = 0xF000F0; //Geckolib uses 15728640
                float u = 0.0f, v = 0.0f;
                float tip_length = 0.73f;
    
                //Outer Blade
                VertexConsumer outerBuffer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/misc/lightsaber_blade_glow.png")));
                // Front face
                outerBuffer.addVertex(matrix, -1f, (bladeLength + 0.01f), 1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, (bladeLength + 0.01f), 1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, -0.5f, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, -0.5f, 1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Back face
                outerBuffer.addVertex(matrix, -1f, -0.51f, -1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, -0.51f, -1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, (bladeLength + 0.01f), -1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, (bladeLength + 0.01f), -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Left face
                outerBuffer.addVertex(matrix, -1f, (bladeLength + 0.01f), -1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, (bladeLength + 0.01f), 1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, -0.51f, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, -0.51f, -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Right face
                outerBuffer.addVertex(matrix, 1f, (bladeLength + 0.01f), 1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, (bladeLength + 0.01f), -1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, -0.51f, -1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, -0.51f, 1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Top face
                outerBuffer.addVertex(matrix, -1f, (bladeLength + 0.01f), 1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, (bladeLength + 0.01f), 1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, (bladeLength + 0.01f), -1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, (bladeLength + 0.01f), -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Bottom face
                outerBuffer.addVertex(matrix, -1f, -0.51f, -1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, -0.51f, -1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, -0.51f, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, -0.5f, 1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

                //Tip front
                outerBuffer.addVertex(matrix, 0f, bladeLength + tip_length, 0f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, bladeLength, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, bladeLength, 1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

                outerBuffer.addVertex(matrix, 0f, bladeLength + tip_length, 0f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, bladeLength, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, bladeLength, -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

                outerBuffer.addVertex(matrix, 0f, bladeLength + tip_length, 0f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, bladeLength, -1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, bladeLength, -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

                outerBuffer.addVertex(matrix, 0f, bladeLength + tip_length, 0f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, 1f, bladeLength, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                outerBuffer.addVertex(matrix, -1f, bladeLength, -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);



                //Inner Blade
    
                VertexConsumer innerBuffer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/misc/lightsaber_blade.png"), false));
    
                // Bottom square
                innerBuffer.addVertex(matrix, -0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Front face
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Back face
                innerBuffer.addVertex(matrix, -0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Left face
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Right face
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                // Top face
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                //Tip front
                innerBuffer.addVertex(matrix, 0f, bladeLength + tip_length, 0f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                innerBuffer.addVertex(matrix, 0f, bladeLength + tip_length, 0f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                innerBuffer.addVertex(matrix, 0f, bladeLength + tip_length, 0f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
    
                innerBuffer.addVertex(matrix, 0f, bladeLength + tip_length, 0f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, 0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);
                innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1f, 1f, 1f);

                poseStack.popPose();
        }

        }
        super.renderForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, 15728640, OverlayTexture.NO_OVERLAY);
    }

    public void setBladeLength(float bladeLength){
        this.bladeLength = bladeLength * 2;
    }
    public float getBladeLength() {
        return bladeLength * 0.5f;
    }
}