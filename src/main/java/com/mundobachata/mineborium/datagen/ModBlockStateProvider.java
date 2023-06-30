package com.mundobachata.mineborium.datagen;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Mineborium.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.MARLBORIUM_ORE_BLOCK);
        blockWithItem(ModBlocks.DEEPSLATE_MARLBORIUM_ORE_BLOCK);
        blockWithItem(ModBlocks.MARLBORIUM_BLOCK);
        blockWithItem(ModBlocks.MARLBORIUM_DRIED_BLOCK);
        blockWithItem(ModBlocks.ROLLING_MACHINE_BLOCK);

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
