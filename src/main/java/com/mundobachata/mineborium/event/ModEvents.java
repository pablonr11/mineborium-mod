package com.mundobachata.mineborium.event;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.abstinence.PlayerAbstinence;
import com.mundobachata.mineborium.abstinence.PlayerAbstinenceProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Mineborium.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerAbstinenceProvider.PLAYER_ABSTINENCE).isPresent()) {
                event.addCapability(new ResourceLocation(Mineborium.MOD_ID, "properties"), new PlayerAbstinenceProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerAbstinenceProvider.PLAYER_ABSTINENCE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerAbstinenceProvider.PLAYER_ABSTINENCE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerAbstinence.class);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(PlayerAbstinenceProvider.PLAYER_ABSTINENCE).ifPresent(abstinence -> {
                if(abstinence.hasSmoked()) {
                    abstinence.increaseTicksSinceLastCigarette();

                    if(abstinence.getCigarettesInARow() > 0 && abstinence.getTicksSinceLastCigarette() >= PlayerAbstinence.CIGARETTES_IN_A_ROW_TICK_RANGE) {
                        abstinence.resetCigarettesInARow();
                    }

                    if(!abstinence.hasAbstinenceEffect() && abstinence.getTicksSinceLastCigarette() >= PlayerAbstinence.APPLY_ABSTINENCE_EFFECT_TIME) {
                        abstinence.setHasAbstinenceEffect(true);
                        event.player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, -1));
                    }

                    if(abstinence.getTicksSinceLastCigarette() > PlayerAbstinence.ABSTINENCE_MAX_TIME) { // After 3 days without smoking the abstinence is gone.
                        abstinence.setHasSmoked(false);
                        abstinence.setHasAbstinenceEffect(false);
                        event.player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    }
                }
            });
        }
    }
}
