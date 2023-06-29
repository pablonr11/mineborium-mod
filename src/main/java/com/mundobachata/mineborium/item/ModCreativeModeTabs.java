package com.mundobachata.mineborium.item;

import com.mundobachata.mineborium.Mineborium;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mineborium.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab MINEBORIUM_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        MINEBORIUM_TAB = event.registerCreativeModeTab(new ResourceLocation(Mineborium.MOD_ID, "mineborium_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.CIGARETTE.get()))
                        .title(Component.translatable("creativemodetab.mineborium_tab")));
    }
}
