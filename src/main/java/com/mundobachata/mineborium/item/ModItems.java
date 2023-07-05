package com.mundobachata.mineborium.item;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.item.custom.CigaretteItem;
import com.mundobachata.mineborium.item.custom.CigaretteStewItem;
import com.mundobachata.mineborium.item.custom.CigaretteSword;
import com.mundobachata.mineborium.item.custom.MarlboriumArrowItem;
import com.mundobachata.mineborium.item.custom.tier.MarlboriumTier;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Mineborium.MOD_ID);

    public static final RegistryObject<Item> MARLBORIUM_NUGGET = ITEMS.register("marlborium_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MARLBORIUM = ITEMS.register("marlborium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CIGARETTE_FILTER = ITEMS.register("cigarette_filter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CIGARETTE = ITEMS.register("cigarette",
            () -> new CigaretteItem(new Item.Properties()));
    public static final RegistryObject<Item> ROLLING_PAPER = ITEMS.register("rolling_paper",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MARLBORIUM_ARROW = ITEMS.register("marlborium_arrow",
            () -> new MarlboriumArrowItem(new Item.Properties()));
    public static final RegistryObject<Item> MARLBORIUM_SWORD = ITEMS.register("marlborium_sword",
            () -> new SwordItem(new MarlboriumTier(), 3, -2.0F, new Item.Properties()));
    public static final RegistryObject<Item> MARLBORIUM_PICKAXE = ITEMS.register("marlborium_pickaxe",
            () -> new PickaxeItem(new MarlboriumTier(), 1, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> MARLBORIUM_AXE = ITEMS.register("marlborium_axe",
            () -> new AxeItem(new MarlboriumTier(), 6, -2.9F, new Item.Properties()));
    public static final RegistryObject<Item> MARLBORIUM_SHOVEL = ITEMS.register("marlborium_shovel",
            () -> new ShovelItem(new MarlboriumTier(), 1.5F, -2.9F, new Item.Properties()));
    public static final RegistryObject<Item> MARLBORIUM_HOE = ITEMS.register("marlborium_hoe",
            () -> new HoeItem(new MarlboriumTier(), -2, 0.0F, new Item.Properties()));
    public static final RegistryObject<Item> CIGARETTE_STEW = ITEMS.register("cigarette_stew",
            () -> new CigaretteStewItem(new Item.Properties()));
    public static final RegistryObject<Item> CIGARETTE_SWORD = ITEMS.register("cigarette_sword",
            () -> new CigaretteSword(3, -2.0F, new Item.Properties().rarity(Rarity.EPIC).food(CigaretteItem.getCustomFoodProperties())));

    // This is probably bullshit
    public static final RegistryObject<Item> CIGARETTE_ENTITY_ITEM = ITEMS.register("cigarette_entity_item",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
