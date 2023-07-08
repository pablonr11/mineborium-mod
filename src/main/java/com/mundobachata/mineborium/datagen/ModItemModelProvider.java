package com.mundobachata.mineborium.datagen;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Mineborium.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.CIGARETTE);
        simpleItem(ModItems.DRY_CIGARETTE);
        simpleItem(ModItems.CIGARETTE_FILTER);
        simpleItem(ModItems.MARLBORIUM_NUGGET);
        simpleItem(ModItems.MARLBORIUM);
        simpleItem(ModItems.ROLLING_PAPER);
        simpleItem(ModItems.MARLBORIUM_ARROW);
        simpleItem(ModItems.CIGARETTE_STEW);

        handheldItem(ModItems.MARLBORIUM_SWORD);
        handheldItem(ModItems.MARLBORIUM_PICKAXE);
        handheldItem(ModItems.MARLBORIUM_AXE);
        handheldItem(ModItems.MARLBORIUM_SHOVEL);
        handheldItem(ModItems.MARLBORIUM_HOE);
        handheldItem(ModItems.CIGARETTE_SWORD);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Mineborium.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Mineborium.MOD_ID,"item/" + item.getId().getPath()));
    }
}
