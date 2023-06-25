package com.mundobachata.mineborium.item.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class CigaretteItem extends Item {

    public CigaretteItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder().alwaysEat().saturationMod(0.8F).alwaysEat().build()));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() { return SoundEvents.FIRE_AMBIENT; }

    public SoundEvent getEatingSound() { return SoundEvents.FIRE_EXTINGUISH; }
}

