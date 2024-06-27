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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.util.Color;

import java.util.Optional;


public class SwordRenderer extends GeoItemRenderer<SwordItem> {
    private BladeRenderLayer bladeRenderLayer = new BladeRenderLayer(this);
    private ModelRenderLayer<SwordItem> emitterRenderLayer = new ModelRenderLayer<>(this, "emitter");
    private ModelRenderLayer<SwordItem> pommelRenderLayer = new ModelRenderLayer<>(this, "pommel");
    private ModelRenderLayer<SwordItem> guardRenderLayer = new ModelRenderLayer<>(this, "guard");

    private Optional<GeoBone> pommelBone = Optional.empty();
    private Optional<GeoBone> guardBone = Optional.empty();
    private Optional<GeoBone> emitterBone = Optional.empty();

    private BakedGeoModel gripModel = null;
    private double[] gripOffset = {};
    private boolean gripChange = true;

    public SwordRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sword")));

        addRenderLayer(bladeRenderLayer); /*Blade RenderLayer*/
        addRenderLayer(emitterRenderLayer);
        addRenderLayer(guardRenderLayer);
        addRenderLayer(pommelRenderLayer);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        this.animatable = (SwordItem) stack.getItem();
        this.currentItemStack = stack;
        this.renderPerspective = transformType;
        float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);

        RenderType renderType = getRenderType(animatable, getTextureLocation(animatable), bufferSource, partialTick);
        PartsComponents parts = stack.getComponents().get(ELDataComponents.SWORD_PARTS);

        if (renderType == null) {
            Constants.LOG.warn("renderType == null");
        }
        if (parts == null) {
            Constants.LOG.warn("parts == null");
        }
        else {
            BladesPart bladesData = parts.blades();
            ModelPart emitterData = parts.emitter();
            ModelPart guardData = parts.guard();
            ModelPart gripData = parts.grip();
            ModelPart pommelData = parts.pommel();
            if (bladesData != this.bladeRenderLayer.getBlades())
                this.bladeRenderLayer.setBlades(bladesData);

            if (shouldRender(emitterData.model(), this.emitterRenderLayer.getModel())) {
                this.emitterRenderLayer.setModel(emitterData.model());
                this.emitterRenderLayer.setScale(emitterData.scale());
                this.emitterRenderLayer.setRetry(true);
            }
            if (shouldRender(guardData.model(), this.guardRenderLayer.getModel())) {
                this.guardRenderLayer.setModel(guardData.model());
                this.guardRenderLayer.setScale(guardData.scale());
                this.guardRenderLayer.setRetry(true);
            }
            if (shouldRender(pommelData.model(), this.pommelRenderLayer.getModel())) {
                this.pommelRenderLayer.setModel(pommelData.model());
                this.pommelRenderLayer.setScale(pommelData.scale());
                this.pommelRenderLayer.setRetry(true);
            }

            if (this.gripChange){
                DynamicModel<SwordItem> localGripModel = new DynamicModel<>(gripData.model());
                this.gripModel = localGripModel.getBakedModel(localGripModel.getModelResource(animatable));
                BakedGeoModel rootModel = model.getBakedModel(this.model.getModelResource((SwordItem) stack.getItem()));
                this.gripChange = false;
                this.gripModel.getBone("bb_main").ifPresent( gripBone ->{
                    GeoBone rootBone = rootModel.getBone("root").get();
                    System.out.println("bb_main isPresent");

                    gripBone.setPivotX(-8);
                    gripBone.setPivotY(8);
                    gripBone.setPivotZ(8.5f);

                    gripBone.setPosX(-8);
                    gripBone.setPosY(8);
                    gripBone.setPosZ(9f);

                    gripBone.setRotX(rootBone.getRotX());
                    gripBone.setRotY(rootBone.getRotY());
                    gripBone.setRotZ(rootBone.getRotZ());

                    gripBone.setScaleX(gripData.scale());
                    gripBone.setScaleY(gripData.scale());
                    gripBone.setScaleZ(gripData.scale());
                });

                model.getBone("joint_emitter").get().setPivotX(this.gripModel.getBone("joint_emitter").get().getPivotX());
                model.getBone("joint_emitter").get().setPivotY(this.gripModel.getBone("joint_emitter").get().getPivotY());
                model.getBone("joint_emitter").get().setPivotZ(this.gripModel.getBone("joint_emitter").get().getPivotZ());

                model.getBone("joint_guard").get().setPivotX(this.gripModel.getBone("joint_guard").get().getPivotX());
                model.getBone("joint_guard").get().setPivotY(this.gripModel.getBone("joint_guard").get().getPivotY());
                model.getBone("joint_guard").get().setPivotZ(this.gripModel.getBone("joint_guard").get().getPivotZ());

                model.getBone("joint_pommel").get().setPivotX(this.gripModel.getBone("joint_pommel").get().getPivotX());
                model.getBone("joint_pommel").get().setPivotY(this.gripModel.getBone("joint_pommel").get().getPivotY());
                model.getBone("joint_pommel").get().setPivotZ(this.gripModel.getBone("joint_pommel").get().getPivotZ());

                model.getBone("joint_emitter").get().setRotX(this.gripModel.getBone("joint_emitter").get().getRotX());
                model.getBone("joint_emitter").get().setRotY(this.gripModel.getBone("joint_emitter").get().getRotY());
                model.getBone("joint_emitter").get().setRotZ(this.gripModel.getBone("joint_emitter").get().getRotZ());

                model.getBone("joint_guard").get().setRotX(this.gripModel.getBone("joint_guard").get().getRotX());
                model.getBone("joint_guard").get().setRotY(this.gripModel.getBone("joint_guard").get().getRotY());
                model.getBone("joint_guard").get().setRotZ(this.gripModel.getBone("joint_guard").get().getRotZ());

                model.getBone("joint_pommel").get().setRotX(this.gripModel.getBone("joint_pommel").get().getRotX());
                model.getBone("joint_pommel").get().setRotY(this.gripModel.getBone("joint_pommel").get().getRotY());
                model.getBone("joint_pommel").get().setRotZ(this.gripModel.getBone("joint_pommel").get().getRotZ());


            }
            this.reRender(this.gripModel, poseStack, bufferSource, animatable, renderType, bufferSource.getBuffer(renderType), partialTick, packedLight, packedOverlay, Color.WHITE.argbInt());
        }
        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }

    private boolean shouldRender(ResourceLocation input, ResourceLocation compare){
        return (
                (input != compare) &&
                (input != Util.EMPTY_RESOURCE_LOCATION)
        );
    }
}
