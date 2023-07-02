package com.mundobachata.mineborium.entity.renderer;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.entity.custom.MarlboriumArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MarlboriumArrowRenderer extends ArrowRenderer<MarlboriumArrow> {
    public static final ResourceLocation MARLBORIUM_ARROW_LOCATION = new ResourceLocation(Mineborium.MOD_ID,
            "textures/entity/projectile/marlborium_arrow.png");

    public MarlboriumArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(MarlboriumArrow p_114482_) {
        return MARLBORIUM_ARROW_LOCATION;
    }
}
