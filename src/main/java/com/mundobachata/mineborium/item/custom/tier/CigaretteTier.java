package com.mundobachata.mineborium.item.custom.tier;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class CigaretteTier implements Tier {
    private final int LEVEL = 3;
    private final int USES = 500;
    private final float SPEED = 8.0F;
    private final float DAMAGE = 3.0F;
    private final int ENCHANTMENT_VALUE = 10;
    private final Ingredient REPAIR_INGREDIENT = Ingredient.of();

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
