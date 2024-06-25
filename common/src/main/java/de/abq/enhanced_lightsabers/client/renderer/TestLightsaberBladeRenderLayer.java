package de.abq.enhanced_lightsabers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.abq.enhanced_lightsabers.Constants;
import de.abq.enhanced_lightsabers.common.item.TestLightsaberItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

import java.util.Objects;

public class TestLightsaberBladeRenderLayer extends GeoRenderLayer<TestLightsaberItem>{

    private float bladeLength = 0;


    public TestLightsaberBladeRenderLayer(TestLightsaberRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void renderForBone(PoseStack poseStack, TestLightsaberItem animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (bone.getName().contains("joint_blade")){

            //TODO: Add feature that searches for blade identifier in data component to make different colors

            poseStack.pushPose();

            // Transform the pose stack for the custom geometry
            poseStack.translate(bone.getPivotX()/16, (bone.getPivotY()/16), bone.getPivotZ()/16); // Position it at the bone's location
            poseStack.scale(0.05f, 0.1f, 0.05f);  // Scale down
            Matrix4f matrix = poseStack.last().pose();


            // Define the color and texture coordinates
            int outerColor = 0x444444ff;
            int innerColor = 0xffffffff;
            int maxLight = 0xF000F0; //Geckolib uses 15728640
            float u = 0.0f, v = 0.0f;

            //Outer Blade
            VertexConsumer outerBuffer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/misc/lightsaber_blade.png")));
            // Front face
            outerBuffer.addVertex(matrix, -1f, (bladeLength+0.01f), 1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, 1.0f);
            outerBuffer.addVertex(matrix, 1f, (bladeLength+0.01f), 1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, 1.0f);
            outerBuffer.addVertex(matrix, 1f, -0.5f, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, 1.0f);
            outerBuffer.addVertex(matrix, -1f, -0.5f, 1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, 1.0f);

            // Back face
            outerBuffer.addVertex(matrix, -1f, -0.51f, -1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, -1.0f);
            outerBuffer.addVertex(matrix, 1f, -0.51f, -1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, -1.0f);
            outerBuffer.addVertex(matrix, 1f, (bladeLength+0.01f), -1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, -1.0f);
            outerBuffer.addVertex(matrix, -1f, (bladeLength+0.01f), -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, -1.0f);

            // Left face
            outerBuffer.addVertex(matrix, -1f, (bladeLength+0.01f), -1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(-1.0f, 0.0f, 0.0f);
            outerBuffer.addVertex(matrix, -1f, (bladeLength+0.01f), 1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(-1.0f, 0.0f, 0.0f);
            outerBuffer.addVertex(matrix, -1f, -0.51f, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(-1.0f, 0.0f, 0.0f);
            outerBuffer.addVertex(matrix, -1f, -0.51f, -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(-1.0f, 0.0f, 0.0f);

            // Right face
            outerBuffer.addVertex(matrix, 1f, (bladeLength+0.01f), 1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1.0f, 0.0f, 0.0f);
            outerBuffer.addVertex(matrix, 1f, (bladeLength+0.01f), -1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1.0f, 0.0f, 0.0f);
            outerBuffer.addVertex(matrix, 1f, -0.51f, -1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1.0f, 0.0f, 0.0f);
            outerBuffer.addVertex(matrix, 1f, -0.51f, 1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1.0f, 0.0f, 0.0f);

            // Top face
            outerBuffer.addVertex(matrix, -1f, (bladeLength+0.01f), 1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 1.0f, 0.0f);
            outerBuffer.addVertex(matrix, 1f, (bladeLength+0.01f), 1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 1.0f, 0.0f);
            outerBuffer.addVertex(matrix, 1f, (bladeLength+0.01f), -1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 1.0f, 0.0f);
            outerBuffer.addVertex(matrix, -1f, (bladeLength+0.01f), -1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 1.0f, 0.0f);

            // Bottom face
            outerBuffer.addVertex(matrix, -1f, -0.51f, -1f).setColor(outerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, -1.0f, 0.0f);
            outerBuffer.addVertex(matrix, 1f, -0.51f, -1f).setColor(outerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, -1.0f, 0.0f);
            outerBuffer.addVertex(matrix, 1f, -0.51f, 1f).setColor(outerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, -1.0f, 0.0f);
            outerBuffer.addVertex(matrix, -1f, -0.5f, 1f).setColor(outerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, -1.0f, 0.0f);


            //Inner Blade

            VertexConsumer innerBuffer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/misc/lightsaber_blade.png"), false));

            // Bottom square
            innerBuffer.addVertex(matrix, -0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(poseStack.last(), 0.0f, 1.0f, 0.0f);
            innerBuffer.addVertex(matrix, 0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(poseStack.last(), 0.0f, 1.0f, 0.0f);
            innerBuffer.addVertex(matrix, 0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(poseStack.last(), 0.0f, 1.0f, 0.0f);
            innerBuffer.addVertex(matrix, -0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(poseStack.last(), 0.0f, 1.0f, 0.0f);

            // Front face
            innerBuffer.addVertex(matrix, -0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, 1.0f);
            innerBuffer.addVertex(matrix, 0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, 1.0f);
            innerBuffer.addVertex(matrix, 0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, 1.0f);
            innerBuffer.addVertex(matrix, -0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, 1.0f);

            // Back face
            innerBuffer.addVertex(matrix, -0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, -1.0f);
            innerBuffer.addVertex(matrix, 0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, -1.0f);
            innerBuffer.addVertex(matrix, 0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, -1.0f);
            innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 0.0f, -1.0f);

            // Left face
            innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(-1.0f, 0.0f, 0.0f);
            innerBuffer.addVertex(matrix, -0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(-1.0f, 0.0f, 0.0f);
            innerBuffer.addVertex(matrix, -0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(-1.0f, 0.0f, 0.0f);
            innerBuffer.addVertex(matrix, -0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(-1.0f, 0.0f, 0.0f);

            // Right face
            innerBuffer.addVertex(matrix, 0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1.0f, 0.0f, 0.0f);
            innerBuffer.addVertex(matrix, 0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1.0f, 0.0f, 0.0f);
            innerBuffer.addVertex(matrix, 0.25f, -0.5f, -0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1.0f, 0.0f, 0.0f);
            innerBuffer.addVertex(matrix, 0.25f, -0.5f, 0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(1.0f, 0.0f, 0.0f);

            // Top face
            innerBuffer.addVertex(matrix, -0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 1.0f, 0.0f);
            innerBuffer.addVertex(matrix, 0.25f, bladeLength, 0.25f).setColor(innerColor).setUv(u + 1.0f, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 1.0f, 0.0f);
            innerBuffer.addVertex(matrix, 0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u + 1.0f, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 1.0f, 0.0f);
            innerBuffer.addVertex(matrix, -0.25f, bladeLength, -0.25f).setColor(innerColor).setUv(u, v + 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setLight(maxLight).setNormal(0.0f, 1.0f, 0.0f);

            poseStack.popPose();

        }
        super.renderForBone(poseStack, animatable, bone, renderType, bufferSource, buffer, partialTick, 15728640, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public void render(PoseStack poseStack, TestLightsaberItem animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
       super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedLight);
    }

    public void setBladeLength(float bladeLength){
        this.bladeLength = bladeLength;
    }
    public float getBladeLength() {
        return bladeLength;
    }
}