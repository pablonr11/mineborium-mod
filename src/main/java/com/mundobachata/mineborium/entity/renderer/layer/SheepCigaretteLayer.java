package com.mundobachata.mineborium.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class SheepCigaretteLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
    public SheepCigaretteLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> p_117346_) {
        super(p_117346_);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_117351_, Sheep sheep,
                       float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();



        ItemStack itemStack = new ItemStack(ModItems.CIGARETTE_ENTITY_ITEM.get());
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
//        poseStack.mulPose(Axis.YN.rotationDegrees(90));
        poseStack.rotateAround(Axis.YP.rotationDegrees(270 + sheep.yHeadRot),
                1.0F, 0.0F, 0.0F);
        poseStack.translate(-0.75F, 0.4F, 0.95F);

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                getLightLevel(sheep.level(), new BlockPos(sheep.getBlockX(),
                        sheep.getBlockY(), sheep.getBlockZ())), OverlayTexture.NO_OVERLAY,
                poseStack, bufferSource,
                sheep.level(), 1);

        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);

        return LightTexture.pack(bLight, sLight);
    }
}
