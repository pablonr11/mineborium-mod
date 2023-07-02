package com.mundobachata.mineborium.item.custom.tier;

import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class MarlboriumTier implements Tier {
    private final int LEVEL = 2;
    private final int USES = 160;
    private final float SPEED = 6.0F;
    private final float DAMAGE = 2.0F;
    private final int ENCHANTMENT_VALUE = 14;
    private final Ingredient REPAIR_INGREDIENT = Ingredient.of(ModItems.MARLBORIUM.get());

    @Override
    public int getUses() {
        return this.USES;
    }

    @Override
    public float getSpeed() {
        return this.SPEED;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.DAMAGE;
    }

    @Override
    public int getLevel() {
        return this.LEVEL;
    }

    @Override
    public int getEnchantmentValue() {
        return this.ENCHANTMENT_VALUE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.REPAIR_INGREDIENT;
    }
}
