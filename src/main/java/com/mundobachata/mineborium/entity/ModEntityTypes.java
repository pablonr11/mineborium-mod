package com.mundobachata.mineborium.entity;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.entity.custom.MarlboriumArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            Mineborium.MOD_ID);

    public static final RegistryObject<EntityType<MarlboriumArrow>> MARLBORIUM_ARROW = ENTITIES.register("marlborium_arrow",
            () -> EntityType.Builder.<MarlboriumArrow>of(MarlboriumArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(Mineborium.MOD_ID, "marlborium_arrow").toString()));

    public static void register(IEventBus event) {
        ENTITIES.register(event);
    }
}
