package com.mundobachata.mineborium.item.custom;

import com.mundobachata.mineborium.entity.custom.MarlboriumArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MarlboriumArrowItem extends ArrowItem {

    public MarlboriumArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity entity) {
        return new MarlboriumArrow(entity, level);
    }
}
