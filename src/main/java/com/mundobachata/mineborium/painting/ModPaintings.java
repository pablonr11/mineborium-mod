package com.mundobachata.mineborium.painting;

import com.mundobachata.mineborium.Mineborium;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTINGS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Mineborium.MOD_ID);

    public static final RegistryObject<PaintingVariant> LA_DANSE = PAINTINGS.register("la_danse",
            () -> new PaintingVariant(32, 16));

    public static final RegistryObject<PaintingVariant> LIGHT_RED_OVER_BLACK = PAINTINGS.register("light_red_over_black",
            () -> new PaintingVariant(16, 16));

    public static final RegistryObject<PaintingVariant> PHILIP_V_UPSIDE_DOWN = PAINTINGS.register("philip_v_upside_down",
            () -> new PaintingVariant(32, 32));

    public static final RegistryObject<PaintingVariant> SELF_PORTRAIT = PAINTINGS.register("self_portrait",
            () -> new PaintingVariant(16, 32));

    public static final RegistryObject<PaintingVariant> SKULL_OF_A_SKELETON_WITH_BURNING_CIGARETTE =
            PAINTINGS.register("skull_of_a_skeleton_with_burning_cigarette",
            () -> new PaintingVariant(64, 48));

    public static final RegistryObject<PaintingVariant> THE_SMOKER = PAINTINGS.register("the_smoker",
            () -> new PaintingVariant(64, 32));

    public static final RegistryObject<PaintingVariant> THE_SCREAM = PAINTINGS.register("the_scream",
            () -> new PaintingVariant(64, 64));


    public static void register(IEventBus eventBus) {
        PAINTINGS.register(eventBus);
    }

}
