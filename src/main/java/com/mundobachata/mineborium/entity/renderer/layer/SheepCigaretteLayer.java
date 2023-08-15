package com.mundobachata.mineborium.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.entity.renderer.layer.model.SheepCigaretteModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;

public class SheepCigaretteLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
    private static final ResourceLocation SHEEP_CIGARETTE_LOCATION =
            new ResourceLocation(Mineborium.MOD_ID, "textures/entity/sheep/sheep_cigarette.png");
    private final SheepCigaretteModel<Sheep> model;
    public SheepCigaretteLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> p_117346_, EntityModelSet entityModelSet) {
        super(p_117346_);
        this.model = new SheepCigaretteModel<>(entityModelSet.bakeLayer(ModModelLayers.SHEEP_CIGARETTE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_117351_, Sheep sheep,
                       float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {

        if(sheep.hasCustomName() && "Esplembliblembia".equals(sheep.getName().getString())) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(sheep, p_117353_, p_117354_, p_117355_);
            this.model.setupAnim(sheep, p_117353_, p_117354_, p_117356_, p_117357_,
                    p_117358_);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(SHEEP_CIGARETTE_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, p_117351_,
                    OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                    1.0F, 1.0F);
        }
    }
}
