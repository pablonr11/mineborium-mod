package com.mundobachata.mineborium.block;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.block.custom.*;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Mineborium.MOD_ID);

    public static final RegistryObject<Block> MARLBORIUM_ORE_BLOCK = registerBlock("marlborium_ore_block",
            () -> new MarlboriumOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f), UniformInt.of(2, 6)));
    public static final RegistryObject<Block> DEEPSLATE_MARLBORIUM_ORE_BLOCK = registerBlock("deepslate_marlborium_ore_block",
            () -> new MarlboriumOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(4.5f, 4.5f)
                    .sound(SoundType.DEEPSLATE), UniformInt.of(2, 6)));
    public static final RegistryObject<Block> MARLBORIUM_BLOCK = registerBlock("compact_marlborium_block",
            () -> new WeatheringMarlboriumFullBlock(WeatheringMarlborium.WeatherState.UNAFFECTED,
                    BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                            .instabreak()
                            .sound(SoundType.CROP)
            ));
    public static final RegistryObject<Block> MARLBORIUM_DRIED_BLOCK = registerBlockWithFuelItem("dry_compact_marlborium_block",
            () -> new WeatheringMarlboriumFullBlock(WeatheringMarlborium.WeatherState.DRY,
                    BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                            .instabreak()
                            .sound(SoundType.CROP)
            ), 2000);

    public static final RegistryObject<Block> ROLLING_MACHINE_BLOCK = registerBlock("rolling_machine_block",
            () -> new RollingMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()
                    .strength(1.0F)));

    public static final RegistryObject<Block> PACK_OF_CIGARETTES_BLOCK = registerBlock("pack_of_cigarettes_block",
            () -> new PackOfCigarettesBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(1.0F)
                    .sound(SoundType.CANDLE)));

    public static final RegistryObject<Block> END_MARLBORIUM_ROD_BLOCK = registerBlock("end_marlborium_rod_block",
            () -> new EndMarlboriumRodBlock(BlockBehaviour.Properties.copy(Blocks.END_ROD).lightLevel((p) -> 15)));

    public static final RegistryObject<Block> ASHTRAY_BLOCK = registerBlock("ashtray_block",
            () -> new AshtrayBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithFuelItem(String name, Supplier<T> block, int burnTime) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItemAsFuel(name, toReturn, burnTime);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItemAsFuel(String name, RegistryObject<T> block, int burnTime) {;

        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {
            @Override
            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                return burnTime;
            }
        });
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
