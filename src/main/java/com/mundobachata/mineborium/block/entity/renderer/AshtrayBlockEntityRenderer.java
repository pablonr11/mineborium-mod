package com.mundobachata.mineborium.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.block.entity.AshtrayBlockEntity;
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

public class AshtrayBlockEntityRenderer implements BlockEntityRenderer<AshtrayBlockEntity> {
    public AshtrayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(AshtrayBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packetLight, int packedOverlay) {
        if(!blockEntity.hasCigarette()) {
            return;
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack itemStack = new ItemStack(ModItems.CIGARETTE_ENTITY_ITEM.get());

        for (int i = 0; i < blockEntity.getItemStack().getCount(); i++) {
            poseStack.pushPose();
            poseStack.scale(0.30F, 0.30F, 0.30F);

            AshtrayBlockRendererValues values = getValues(i);

            poseStack.mulPose(Axis.YP.rotationDegrees(values.ypRotation));
            poseStack.mulPose(Axis.ZP.rotationDegrees(values.zpRotation));

            poseStack.translate(values.xTranslation, values.yTranslation, values.zTranslation);

            itemRenderer.renderStatic(itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, poseStack, bufferSource,
                    blockEntity.getLevel(), 1);

            poseStack.popPose();
        }

    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);

        return LightTexture.pack(bLight, sLight);
    }

    private AshtrayBlockRendererValues getValues(int itemIndex) {
        switch (itemIndex) {
            case 0:
                return new AshtrayBlockRendererValues(-0.87f, 0.15f, -1.66f, 180,
                        5);
            case 1:
                return new AshtrayBlockRendererValues(-0.87f, 0.15f, 1.66f, 90,
                        5);
            case 2:
                return new AshtrayBlockRendererValues(2.45f, -0.13f, 1.66f, 0,
                        5);
            case 3:
                return new AshtrayBlockRendererValues(2.45f, -0.13f, -1.66f, 270,
                        5);
            default:
                return new AshtrayBlockRendererValues(0f, 0f, 0f, 0,
                        0);

        }
    }

    private class AshtrayBlockRendererValues {
        public float xTranslation;
        public float yTranslation;
        public float zTranslation;
        public int ypRotation;
        public int zpRotation;

        public AshtrayBlockRendererValues(float xTranslation, float yTranslation, float zTranslation, int ypRotation,
                                          int zpRotation) {
            this.xTranslation = xTranslation;
            this.yTranslation = yTranslation;
            this.zTranslation = zTranslation;
            this.ypRotation = ypRotation;
            this.zpRotation = zpRotation;
        }
    }
}
