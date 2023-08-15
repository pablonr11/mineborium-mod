package com.mundobachata.mineborium.entity.renderer.layer.model;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.animal.Sheep;

public class SheepCigaretteModel<T extends Sheep> extends QuadrupedModel<T> {
    private float headXRot;

    public SheepCigaretteModel(ModelPart modelPart) {
        super(modelPart, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    public static LayerDefinition createCigaretteLayer() {
        MeshDefinition meshDefinition = QuadrupedModel.createBodyMesh(12, CubeDeformation.NONE);
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-0.5F, 0.5F, -9.0F, 1.0F, 1.0F, 3.0F,
                        new CubeDeformation(0.0F, 0.0F, 0.0F)),
                PartPose.offset(0.0F, 6.0F, -8.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 0);
        partDefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-3.0F, 12.0F, 7.0F));
        partDefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(3.0F, 12.0F, 7.0F));
        partDefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-3.0F, 12.0F, -5.0F));
        partDefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(3.0F, 12.0F, -5.0F));


        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    public void prepareMobModel(T p_103661_, float p_103662_, float p_103663_, float p_103664_) {
        super.prepareMobModel(p_103661_, p_103662_, p_103663_, p_103664_);
        this.head.y = 6.0F + p_103661_.getHeadEatPositionScale(p_103664_) * 9.0F;
        this.headXRot = p_103661_.getHeadEatAngleScale(p_103664_);
    }

    public void setupAnim(T p_103666_, float p_103667_, float p_103668_, float p_103669_, float p_103670_, float p_103671_) {
        super.setupAnim(p_103666_, p_103667_, p_103668_, p_103669_, p_103670_, p_103671_);
        this.head.xRot = this.headXRot;
    }
}
