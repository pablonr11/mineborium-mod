package com.mundobachata.mineborium;

import com.mojang.logging.LogUtils;
import com.mundobachata.mineborium.block.ModBlocks;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Mineborium.MOD_ID)
public class Mineborium {
    public static final String MOD_ID = "mineborium";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Mineborium() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.MALBORIUM_ORE_BLOCK);
            event.accept(ModBlocks.DEEPSLATE_MALBORIUM_ORE_BLOCK);
        }

        if(event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.MALBORIUM);
            event.accept(ModItems.CIGARETTE_FILTER);
        }

        if(event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.CIGARETTE);
        }
    }
}
