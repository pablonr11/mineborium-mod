package com.mundobachata.mineborium.block.entity;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Mineborium.MOD_ID);

    public static final RegistryObject<BlockEntityType<RollingMachineBlockEntity>> ROLLING_MACHINE =
            BLOCK_ENTITIES.register("rolling_machine", () ->
                    BlockEntityType.Builder.of(RollingMachineBlockEntity::new,
                            ModBlocks.ROLLING_MACHINE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<AshtrayBlockEntity>> ASHTRAY_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("ashtray_block_entity", () ->
                    BlockEntityType.Builder.of(AshtrayBlockEntity::new,
                            ModBlocks.ASHTRAY_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
