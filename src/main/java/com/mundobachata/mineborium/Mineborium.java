package com.mundobachata.mineborium;

import com.mojang.logging.LogUtils;
import com.mundobachata.mineborium.block.ModBlocks;
import com.mundobachata.mineborium.block.entity.ModBlockEntities;
import com.mundobachata.mineborium.block.entity.renderer.AshtrayBlockEntityRenderer;
import com.mundobachata.mineborium.block.entity.renderer.RollingMachineBlockEntityRenderer;
import com.mundobachata.mineborium.client.AbstinenceHudOverlay;
import com.mundobachata.mineborium.entity.ModEntityTypes;
import com.mundobachata.mineborium.entity.renderer.MarlboriumArrowRenderer;
import com.mundobachata.mineborium.entity.renderer.layer.ModModelLayers;
import com.mundobachata.mineborium.entity.renderer.layer.SheepCigaretteLayer;
import com.mundobachata.mineborium.entity.renderer.layer.model.SheepCigaretteModel;
import com.mundobachata.mineborium.item.ModCreativeModeTabs;
import com.mundobachata.mineborium.item.ModItems;
import com.mundobachata.mineborium.item.custom.properties.ModItemProperties;
import com.mundobachata.mineborium.loot.ModLootModifiers;
import com.mundobachata.mineborium.networking.ModNetworking;
import com.mundobachata.mineborium.painting.ModPaintings;
import com.mundobachata.mineborium.screen.ModMenuTypes;
import com.mundobachata.mineborium.screen.RollingMachineScreen;
import com.mundobachata.mineborium.sound.ModSounds;
import com.mundobachata.mineborium.trigger.ModTriggers;
import com.mundobachata.mineborium.villager.ModVillagers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModPaintings.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
//            ModNetworking.register();
            ModTriggers.register();
        });
        // This should work inside the event.enqueueWork Supplier, but it is not working in there so...
        ModNetworking.register();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //Blocks

        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.MARLBORIUM_ORE_BLOCK);
            event.accept(ModBlocks.DEEPSLATE_MARLBORIUM_ORE_BLOCK);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.MARLBORIUM_BLOCK);
            event.accept(ModBlocks.MARLBORIUM_DRIED_BLOCK);
            event.accept(ModBlocks.PACK_OF_CIGARETTES_BLOCK);
            event.accept(ModBlocks.END_MARLBORIUM_ROD_BLOCK);
            event.accept(ModBlocks.ASHTRAY_BLOCK);
        }

        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.ROLLING_MACHINE_BLOCK);
        }

        // Items

        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.MARLBORIUM_NUGGET);
            event.accept(ModItems.MARLBORIUM);
            event.accept(ModItems.DRY_MARLBORIUM);
            event.accept(ModItems.CIGARETTE_FILTER);
            event.accept(ModItems.ROLLING_PAPER);
        }

        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.CIGARETTE);
            event.accept(ModItems.DRY_CIGARETTE);
            event.accept(ModItems.CIGARETTE_STEW);
        }

        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.CIGARETTE_SWORD);
            event.accept(ModItems.MARLBORIUM_SWORD);
            event.accept(ModItems.MARLBORIUM_ARROW);
            event.accept(ModItems.MARLBORIUM_HORSE_ARMOR);
        }

        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.MARLBORIUM_SHOVEL);
            event.accept(ModItems.MARLBORIUM_PICKAXE);
            event.accept(ModItems.MARLBORIUM_AXE);
            event.accept(ModItems.MARLBORIUM_HOE);
        }

        //Mod specific tab

        if(event.getTab() == ModCreativeModeTabs.MINEBORIUM_TAB.get()) {
            // Blocks
            event.accept(ModBlocks.MARLBORIUM_ORE_BLOCK);
            event.accept(ModBlocks.DEEPSLATE_MARLBORIUM_ORE_BLOCK);
            event.accept(ModBlocks.MARLBORIUM_BLOCK);
            event.accept(ModBlocks.MARLBORIUM_DRIED_BLOCK);
            event.accept(ModBlocks.ROLLING_MACHINE_BLOCK);
            event.accept(ModBlocks.PACK_OF_CIGARETTES_BLOCK);
            event.accept(ModBlocks.END_MARLBORIUM_ROD_BLOCK);
            event.accept(ModBlocks.ASHTRAY_BLOCK);

            // Items
            event.accept(ModItems.CIGARETTE);
            event.accept(ModItems.DRY_CIGARETTE);
            event.accept(ModItems.CIGARETTE_STEW);
            event.accept(ModItems.MARLBORIUM_NUGGET);
            event.accept(ModItems.MARLBORIUM);
            event.accept(ModItems.DRY_MARLBORIUM);
            event.accept(ModItems.CIGARETTE_FILTER);
            event.accept(ModItems.ROLLING_PAPER);
            event.accept(ModItems.MARLBORIUM_ARROW);
            event.accept(ModItems.CIGARETTE_SWORD);
            event.accept(ModItems.MARLBORIUM_SWORD);
            event.accept(ModItems.MARLBORIUM_SHOVEL);
            event.accept(ModItems.MARLBORIUM_PICKAXE);
            event.accept(ModItems.MARLBORIUM_AXE);
            event.accept(ModItems.MARLBORIUM_HOE);
            event.accept(ModItems.MARLBORIUM_HORSE_ARMOR);
            event.accept(ModItems.MARLBORO_MAN_MUSIC_DISC);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ModItemProperties.registerSmokeProperty(ModItems.CIGARETTE.get());
                ModItemProperties.registerSmokeProperty(ModItems.DRY_CIGARETTE.get());
                ModItemProperties.registerSmokingProperty(ModItems.CIGARETTE.get());
                ModItemProperties.registerSmokingProperty(ModItems.DRY_CIGARETTE.get());
            });
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

            event.registerBlockEntityRenderer(ModBlockEntities.ASHTRAY_BLOCK_ENTITY.get(),
                    AshtrayBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("abstinence", AbstinenceHudOverlay.ABSTINENCE_HUD);
        }

        @SubscribeEvent
        public static void onAddLayer(EntityRenderersEvent.AddLayers event) {

            LivingEntityRenderer<Sheep, SheepModel<Sheep>> renderer = event.getRenderer(EntityType.SHEEP);
            SheepCigaretteLayer layer = new SheepCigaretteLayer(renderer, event.getContext().getModelSet());
            renderer.addLayer(layer);
        }

        @SubscribeEvent
        public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.SHEEP_CIGARETTE, () -> {
                return SheepCigaretteModel.createCigaretteLayer();
            });
        }
    }

}
