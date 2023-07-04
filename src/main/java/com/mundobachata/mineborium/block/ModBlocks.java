package com.mundobachata.mineborium.block;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.block.custom.MarlboriumOreBlock;
import com.mundobachata.mineborium.block.custom.RollingMachineBlock;
import com.mundobachata.mineborium.block.custom.WeatheringMarlborium;
import com.mundobachata.mineborium.block.custom.WeatheringMarlboriumFullBlock;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Mineborium.MOD_ID);

    public static final RegistryObject<Block> MARLBORIUM_ORE_BLOCK = registerBlock("marlborium_ore_block",
            () -> new MarlboriumOreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f), UniformInt.of(2, 6)));
    public static final RegistryObject<Block> DEEPSLATE_MARLBORIUM_ORE_BLOCK = registerBlock("deepslate_marlborium_ore_block",
            () -> new MarlboriumOreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(4.5f, 4.5f)
                    .sound(SoundType.DEEPSLATE), UniformInt.of(2, 6)));
    public static final RegistryObject<Block> MARLBORIUM_BLOCK = registerBlock("compact_marlborium_block",
            () -> new WeatheringMarlboriumFullBlock(WeatheringMarlborium.WeatherState.UNAFFECTED,
                    BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                            .instabreak()
                            .sound(SoundType.CROP)
            ));
    public static final RegistryObject<Block> MARLBORIUM_DRIED_BLOCK = registerBlock("dry_compact_marlborium_block",
            () -> new WeatheringMarlboriumFullBlock(WeatheringMarlborium.WeatherState.DRY,
                    BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                            .instabreak()
                            .sound(SoundType.CROP)
            ));

    public static final RegistryObject<Block> ROLLING_MACHINE_BLOCK = registerBlock("rolling_machine_block",
            () -> new RollingMachineBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()
                    .strength(1.0F)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
