package com.mundobachata.mineborium.item.custom;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.entity.renderer.ModItemAnimations;
import com.mundobachata.mineborium.networking.ModNetworking;
import com.mundobachata.mineborium.networking.packet.SmokeC2SPacket;
import com.mundobachata.mineborium.trigger.ModTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.swing.text.AttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.util.function.Consumer;

public class CigaretteItem extends Item {

    private boolean isDried;

    public CigaretteItem(Properties properties, boolean isDried) {
        super(properties.food(getCustomFoodProperties(isDried)));
        this.isDried = isDried;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ModItemAnimations.smokingAnimation);
        super.initializeClient(consumer);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(!player.isCreative()) {
            if(!playerHasLighter(player, itemStack)) {
                if(level.isClientSide) {
                    player.displayClientMessage(Component.translatable("client.message.mineborium.lighter_needed"),
                            true);
                }
                return InteractionResultHolder.fail(itemStack);
            }

            if(!level.isClientSide) {
                damageLighter(player, itemStack);
            }

            player.getCooldowns().addCooldown(this, 84);
        }

        if(player.canEat(itemStack.getFoodProperties(player).canAlwaysEat())) {
            player.startUsingItem(hand);
            if (!level.isClientSide) {
                level.playSound((Player) null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if(level.isClientSide()) {
            ModNetworking.sendToServer(new SmokeC2SPacket());
        }

        return super.finishUsingItem(itemStack, level, livingEntity);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int p_41431_) {
        if (level.isClientSide() && entity.isUsingItem() && entity.getUseItemRemainingTicks() % 3 == 0) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();

            level.addParticle(ParticleTypes.SMOKE,
                    x + entity.getLookAngle().x,
                    y + 1.5D + entity.getLookAngle().y,
                    z + entity.getLookAngle().z,
                    0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.NONE;
    }

    public SoundEvent getDrinkingSound() { return SoundEvents.FIRE_AMBIENT; }

    public SoundEvent getEatingSound() { return SoundEvents.FIRE_EXTINGUISH; }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 64;
    }

    public static FoodProperties getCustomFoodProperties(boolean isDried) {
        FoodProperties.Builder foodPropertiesBuilder = new FoodProperties.Builder().alwaysEat().saturationMod(3.5F);

        if(isDried) {
            foodPropertiesBuilder.effect(() ->
                    new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 1.0F);
        }
        return foodPropertiesBuilder.build();
    }

    private boolean playerHasLighter(Player player, ItemStack usingItem) {
        ItemStack secondItem = getSecondaryItem(player, usingItem);
        return secondItem.getItem() instanceof FlintAndSteelItem || secondItem.getItem() instanceof FireChargeItem;
    }

    private ItemStack getSecondaryItem(Player player, ItemStack usingItem) {
        boolean cigaretteIsInMainHand = cigaretteIsInMainHand(player, usingItem);

        ItemStack secondItem;
        if(cigaretteIsInMainHand) {
            secondItem = player.getOffhandItem();
        } else {
            secondItem = player.getMainHandItem();
        }

        return secondItem;
    }

    private boolean cigaretteIsInMainHand(Player player, ItemStack usingItem) {
        return player.getMainHandItem() == usingItem;
    }

    private void damageLighter(Player player, ItemStack usingItem) {
        InteractionHand lighterHand;
        if (cigaretteIsInMainHand(player, usingItem)) {
            lighterHand = InteractionHand.OFF_HAND;
        } else {
            lighterHand = InteractionHand.MAIN_HAND;
        }

        ItemStack lighterItem = getSecondaryItem(player, usingItem);

        if(lighterItem.getItem() instanceof FlintAndSteelItem) {
            lighterItem.hurtAndBreak(1, player, (entity) -> {
                entity.broadcastBreakEvent(lighterHand);
            });
        } else if (lighterItem.getItem() instanceof FireChargeItem) {
            if(player instanceof ServerPlayer serverPlayer) {
                ModTriggers.SMOKE_WITH_FIRE_CHARGE.trigger(serverPlayer);
            }
            lighterItem.shrink(1);
        }
    }
}

