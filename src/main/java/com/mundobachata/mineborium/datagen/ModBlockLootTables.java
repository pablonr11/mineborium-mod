package com.mundobachata.mineborium.datagen;

import com.mundobachata.mineborium.block.ModBlocks;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        add(ModBlocks.MARLBORIUM_ORE_BLOCK.get(),
                (block) -> createMarlboriumOreDrops(ModBlocks.MARLBORIUM_ORE_BLOCK.get()));

        add(ModBlocks.DEEPSLATE_MARLBORIUM_ORE_BLOCK.get(),
                (block) -> createMarlboriumOreDrops(ModBlocks.DEEPSLATE_MARLBORIUM_ORE_BLOCK.get()));

        dropSelf(ModBlocks.MARLBORIUM_BLOCK.get());
        dropSelf(ModBlocks.MARLBORIUM_DRIED_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected LootTable.Builder createMarlboriumOreDrops(Block block) {
        return createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ModItems.MARLBORIUM_NUGGET.get())
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f)))
                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
}
