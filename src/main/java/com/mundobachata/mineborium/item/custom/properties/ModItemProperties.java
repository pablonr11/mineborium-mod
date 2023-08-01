package com.mundobachata.mineborium.item.custom.properties;

import com.mundobachata.mineborium.Mineborium;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModItemProperties {
    private static final String SMOKE_PROPERTY_TAG = "smoke";
    private static final String SMOKING_PROPERTY_TAG = "smoking";

    public static void registerSmokeProperty(Item item) {
        ItemProperties.register(item, new ResourceLocation(Mineborium.MOD_ID,
                SMOKE_PROPERTY_TAG), (stack, level, living, id) -> {
            if(living == null) {
                return 0.0F;
            }

            return living.isUsingItem() && living.getUseItem() == stack ?
                    (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / (float)stack.getUseDuration() : 0.0F;
        });
    }

    public static void registerSmokingProperty(Item item) {
        ItemProperties.register(item, new ResourceLocation(Mineborium.MOD_ID,
                SMOKING_PROPERTY_TAG), (stack, level, living, id) -> {
            if(living == null) {
                return 0.0F;
            }

            return living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }

}
