package com.mundobachata.mineborium.datagen;

import com.mundobachata.mineborium.block.ModBlocks;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
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
        add(ModBlocks.MALBORIUM_ORE_BLOCK.get(),
                (block) -> createMalboriumOreDrops(ModBlocks.MALBORIUM_ORE_BLOCK.get()));

        add(ModBlocks.DEEPSLATE_MALBORIUM_ORE_BLOCK.get(),
                (block) -> createMalboriumOreDrops(ModBlocks.DEEPSLATE_MALBORIUM_ORE_BLOCK.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected LootTable.Builder createMalboriumOreDrops(Block block) {
        return createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(ModItems.MALBORIUM.get())
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f)))
                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
}
