package com.mundobachata.mineborium.loot;

import com.mojang.serialization.Codec;
import com.mundobachata.mineborium.Mineborium;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Mineborium.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_CIGARETTE_SWORD_TO_STRUCTURES =
            LOOT_MODIFIER_SERIALIZERS.register("add_cigarette_sword_to_structures", AddCigaretteSwordToStructuresModifier.CODEC);

    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }
}
