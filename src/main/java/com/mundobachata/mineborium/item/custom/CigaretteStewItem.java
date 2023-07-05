package com.mundobachata.mineborium.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CigaretteStewItem extends Item {

    public CigaretteStewItem(Properties properties) {
        super(properties.stacksTo(1)
                .food(new FoodProperties.Builder().nutrition(6)
                .saturationMod(1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.WITHER, 600, 1), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 1.0F)
                .build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack itemStack = super.finishUsingItem(stack, level, livingEntity);
        return livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild ? itemStack : new ItemStack(Items.BOWL);
    }
}
