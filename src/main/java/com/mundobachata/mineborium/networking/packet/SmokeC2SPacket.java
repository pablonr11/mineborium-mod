package com.mundobachata.mineborium.networking.packet;

import com.mundobachata.mineborium.abstinence.PlayerAbstinence;
import com.mundobachata.mineborium.abstinence.PlayerAbstinenceProvider;
import com.mundobachata.mineborium.networking.ModNetworking;
import com.mundobachata.mineborium.trigger.ModTriggers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmokeC2SPacket {
    public SmokeC2SPacket() {

    }

    public SmokeC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // This is server side
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerAbstinenceProvider.PLAYER_ABSTINENCE).ifPresent(abstinence -> {
                abstinence.setHasSmoked(true);
                abstinence.resetTicksSinceLastCigarette();
                abstinence.increaseCigarettesInARow();

                if(abstinence.hasAbstinenceEffect()) {
                    abstinence.setHasAbstinenceEffect(false);
                    player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                }

                if(abstinence.getCigarettesInARow() == PlayerAbstinence.LEVEL_1_TOXICITY_CIGARETTES) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, PlayerAbstinence.LEVEL_1_TOXICITY_TICKS));
                }

                if(abstinence.getCigarettesInARow() == PlayerAbstinence.LEVEL_2_TOXICITY_CIGARETTES) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, PlayerAbstinence.LEVEL_2_TOXICITY_TICKS, 1));
                }

                if(abstinence.getCigarettesInARow() == PlayerAbstinence.LEVEL_3_TOXICITY_CIGARETTES) {
                    ModTriggers.DEATH_BY_CIGARETTE.trigger(player);
                    player.kill();
                }

                ModNetworking.sendToPlayer(
                        new AbstinenceDataSyncS2CPacket(abstinence.getTicksSinceLastCigarette(), abstinence.hasSmoked()),
                        player);
            });

        });

        return true;
    }

}
