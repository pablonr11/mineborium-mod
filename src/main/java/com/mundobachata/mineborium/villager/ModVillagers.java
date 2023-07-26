package com.mundobachata.mineborium.villager;

import com.google.common.collect.ImmutableSet;
import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class ModVillagers {

    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, Mineborium.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Mineborium.MOD_ID);

    public static final RegistryObject<PoiType> ROLLING_MACHINE_BLOCK_POI = POI_TYPES.register("rolling_machine_block_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.ROLLING_MACHINE_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1 ,1));

    public static final RegistryObject<VillagerProfession> SMOKE_SHOP_ASSISTANT = VILLAGER_PROFESSIONS.register("smoke_shop_assistant",
            () -> new VillagerProfession("smoke_shop_assistant", x -> x.get() == ROLLING_MACHINE_BLOCK_POI.get(),
                    x -> x.get() == ROLLING_MACHINE_BLOCK_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_FARMER));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
