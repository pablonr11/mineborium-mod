package com.mundobachata.mineborium;

import com.mojang.logging.LogUtils;
import com.mundobachata.mineborium.block.ModBlocks;
import com.mundobachata.mineborium.block.entity.ModBlockEntities;
import com.mundobachata.mineborium.entity.ModEntityTypes;
import com.mundobachata.mineborium.entity.renderer.MarlboriumArrowRenderer;
import com.mundobachata.mineborium.block.entity.renderer.RollingMachineBlockEntityRenderer;
import com.mundobachata.mineborium.item.ModCreativeModeTabs;
import com.mundobachata.mineborium.item.ModItems;
import com.mundobachata.mineborium.networking.ModNetworking;
import com.mundobachata.mineborium.screen.ModMenuTypes;
import com.mundobachata.mineborium.screen.RollingMachineScreen;
import com.mundobachata.mineborium.villager.ModVillagers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Mineborium.MOD_ID)
public class Mineborium {
    public static final String MOD_ID = "mineborium";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Mineborium() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
//            ModNetworking.register();
            ModVillagers.registerPOIs();
        });
        // This should work inside the event.enqueueWork Supplier, but it is not working in there so...
        ModNetworking.register();
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        //Blocks

        if(event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.MARLBORIUM_ORE_BLOCK);
            event.accept(ModBlocks.DEEPSLATE_MARLBORIUM_ORE_BLOCK);
        }

        if(event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.MARLBORIUM_BLOCK);
            event.accept(ModBlocks.MARLBORIUM_DRIED_BLOCK);
        }

        if(event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.ROLLING_MACHINE_BLOCK);
        }

        // Items

        if(event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.MARLBORIUM_NUGGET);
            event.accept(ModItems.MARLBORIUM);
            event.accept(ModItems.CIGARETTE_FILTER);
            event.accept(ModItems.ROLLING_PAPER);
        }

        if(event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.CIGARETTE);
        }

        if(event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.MARLBORIUM_SWORD);
            event.accept(ModItems.MARLBORIUM_ARROW);
        }

        if(event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.MARLBORIUM_SHOVEL);
            event.accept(ModItems.MARLBORIUM_PICKAXE);
            event.accept(ModItems.MARLBORIUM_AXE);
            event.accept(ModItems.MARLBORIUM_HOE);
        }

        //Mod specific tab

        if(event.getTab() == ModCreativeModeTabs.MINEBORIUM_TAB) {
            event.accept(ModBlocks.MARLBORIUM_ORE_BLOCK);
            event.accept(ModBlocks.DEEPSLATE_MARLBORIUM_ORE_BLOCK);
            event.accept(ModBlocks.MARLBORIUM_BLOCK);
            event.accept(ModBlocks.MARLBORIUM_DRIED_BLOCK);
            event.accept(ModBlocks.ROLLING_MACHINE_BLOCK);
            event.accept(ModItems.CIGARETTE);
            event.accept(ModItems.MARLBORIUM_NUGGET);
            event.accept(ModItems.MARLBORIUM);
            event.accept(ModItems.CIGARETTE_FILTER);
            event.accept(ModItems.ROLLING_PAPER);
            event.accept(ModItems.MARLBORIUM_ARROW);
            event.accept(ModItems.MARLBORIUM_SWORD);
            event.accept(ModItems.MARLBORIUM_SHOVEL);
            event.accept(ModItems.MARLBORIUM_PICKAXE);
            event.accept(ModItems.MARLBORIUM_AXE);
            event.accept(ModItems.MARLBORIUM_HOE);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.ROLLING_MACHINE_MENU.get(), RollingMachineScreen::new);
        }

        @SubscribeEvent
        public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntityTypes.MARLBORIUM_ARROW.get(),
                    MarlboriumArrowRenderer::new);
        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.ROLLING_MACHINE.get(),
                    RollingMachineBlockEntityRenderer::new);
        }
    }

}
