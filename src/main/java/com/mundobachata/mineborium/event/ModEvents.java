package com.mundobachata.mineborium.event;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.abstinence.PlayerAbstinence;
import com.mundobachata.mineborium.abstinence.PlayerAbstinenceProvider;
import com.mundobachata.mineborium.block.ModBlocks;
import com.mundobachata.mineborium.client.AbstinenceHudOverlay;
import com.mundobachata.mineborium.client.ClientAbstinenceData;
import com.mundobachata.mineborium.item.ModItems;
import com.mundobachata.mineborium.item.custom.CigaretteItem;
import com.mundobachata.mineborium.networking.ModNetworking;
import com.mundobachata.mineborium.networking.packet.AbstinenceDataSyncS2CPacket;
import com.mundobachata.mineborium.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

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

                    if(abstinence.getTicksSinceLastCigarette() >= PlayerAbstinence.ABSTINENCE_MAX_TIME) { // After 3 days without smoking the abstinence is gone.
                        abstinence.setHasSmoked(false);
                        abstinence.setHasAbstinenceEffect(false);
                        event.player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    }

                    if(abstinence.getTicksSinceLastCigarette() % 200 == 0) { // This can be calculated to be sent only when the phase is changed, but for now it does the trick.
                        ModNetworking.sendToPlayer(new AbstinenceDataSyncS2CPacket(abstinence.getTicksSinceLastCigarette(), abstinence.hasSmoked()),
                                (ServerPlayer) event.player);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == ModVillagers.SMOKE_SHOP_ASSISTANT.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.MARLBORIUM_NUGGET.get(), 32),
                    new ItemStack(Items.EMERALD, 1), 16, 2, 0.05F));

            trades.get(2).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.CIGARETTE_STEW.get(), 1), 12, 15, 0.05F));

            trades.get(2).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3),
                    new ItemStack(ModItems.MARLBORIUM_HOE.get(), 1), 3, 10, 0.2F));

            trades.get(3).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4),
                    new ItemStack(Items.ARROW, 5),
                    new ItemStack(ModItems.MARLBORIUM_ARROW.get(), 5), 12, 15, 0.05F));

            trades.get(3).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModBlocks.ASHTRAY_BLOCK.get(), 1), 12, 15, 0.2F));

            trades.get(4).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 18),
                    new ItemStack(ModItems.MARLBORIUM_HORSE_ARMOR.get(), 1), 12, 15, 0.2F));

            trades.get(5).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(7, 21)),
                    new ItemStack(ModItems.MARLBORIUM_SWORD.get(), 1), 3, 30, 0.2F));

            trades.get(5).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(6, 20)),
                    new ItemStack(ModItems.MARLBORIUM_AXE.get(), 1), 3, 30, 0.2F));

            trades.get(5).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(8, 22)),
                    new ItemStack(ModItems.MARLBORIUM_PICKAXE.get(), 1), 3, 30, 0.2F));

            trades.get(5).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(7, 21)),
                    new ItemStack(ModItems.MARLBORIUM_SHOVEL.get(), 1), 3, 30, 0.2F));
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        /* This logic could go in CigaretteSword#hurtEnemy but then if an enemy with CanPickUpLoot hits a player
        * the effects is not applied to the player.*/
        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
        LivingEntity target = event.getEntity();
        if(attacker != null && target != null && attacker.getMainHandItem().getItem() == ModItems.CIGARETTE_SWORD.get()) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200), attacker);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerAbstinenceProvider.PLAYER_ABSTINENCE).ifPresent(abstinence -> {
                    ModNetworking.sendToPlayer(
                            new AbstinenceDataSyncS2CPacket(abstinence.getTicksSinceLastCigarette(), abstinence.hasSmoked()),
                            player);
                });
            }
        }
    }
}
