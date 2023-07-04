package com.mundobachata.mineborium.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mundobachata.mineborium.block.custom.RollingMachineBlock;
import com.mundobachata.mineborium.block.entity.RollingMachineBlockEntity;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class RollingMachineBlockEntityRenderer implements BlockEntityRenderer<RollingMachineBlockEntity> {
    public RollingMachineBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(RollingMachineBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packetLight, int packedOverlay) {
        if(!blockEntity.hasCigaretteInOutput()) {
            return;
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack itemStack = new ItemStack(ModItems.CIGARETTE_ENTITY_ITEM.get());
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);

        float xTranslation = 0.0f;
        float yTranslation = -0.075f;
        float zTranslation = 0.0f;

        switch(blockEntity.getBlockState().getValue(RollingMachineBlock.FACING)) {
            case NORTH:
                xTranslation = 0.45f;
                zTranslation = 0.5f;
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                break;
            case EAST:
                xTranslation = 1.45f;
                zTranslation = 0.5f;
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
                break;
            case SOUTH:
                xTranslation = 1.45f;
                zTranslation = -0.5f;
                poseStack.mulPose(Axis.YP.rotationDegrees(270));
                break;
            case WEST:
                xTranslation = 0.45f;
                zTranslation = -0.5f;
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                break;
        }
        poseStack.translate(xTranslation, yTranslation, zTranslation);


        itemRenderer.renderStatic(itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, poseStack, bufferSource,
                blockEntity.getLevel(), 1);
        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);

        return LightTexture.pack(bLight, sLight);
    }
}
