package com.mundobachata.mineborium.item.custom;

import com.mundobachata.mineborium.networking.ModNetworking;
import com.mundobachata.mineborium.networking.packet.SmokeC2SPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CigaretteItem extends Item {

    public CigaretteItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder().alwaysEat().saturationMod(0.8F).alwaysEat().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if(!level.isClientSide()) {
            ModNetworking.sendToServer(new SmokeC2SPacket());
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() { return SoundEvents.FIRE_AMBIENT; }

    public SoundEvent getEatingSound() { return SoundEvents.FIRE_EXTINGUISH; }
}

