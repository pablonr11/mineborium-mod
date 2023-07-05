package com.mundobachata.mineborium.item.custom;

import com.mundobachata.mineborium.item.custom.tier.CigaretteTier;
import com.mundobachata.mineborium.networking.ModNetworking;
import com.mundobachata.mineborium.networking.packet.SmokeC2SPacket;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CigaretteSword extends SwordItem {
    public CigaretteSword(int damage, float attackingSpeed, Properties properties) {
        super(new CigaretteTier(), damage, attackingSpeed, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if(!level.isClientSide()) {
            ModNetworking.sendToServer(new SmokeC2SPacket());
        } else {
            double x = livingEntity.getX();
            double y = livingEntity.getY();
            double z = livingEntity.getZ();

            Direction.Axis axis = livingEntity.getDirection().getAxis();
            Direction.AxisDirection axisDirection = livingEntity.getDirection().getAxisDirection();

            switch (axis) {
                case X -> x += getAxisDirectionValue(axisDirection);
                case Z -> z += getAxisDirectionValue(axisDirection);
            }

            level.addParticle(ParticleTypes.SMOKE, x, y + 1.5D,
                    z, 0.0D, 0.0D, 0.0D);
        }
        return super.finishUsingItem(itemStack, level, livingEntity);
    }

    private double getAxisDirectionValue(Direction.AxisDirection axisDirection) {
        if(axisDirection == Direction.AxisDirection.NEGATIVE) {
            return -0.5D;
        } else {
            return 0.5D;
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() { return SoundEvents.FIRE_AMBIENT; }

    public SoundEvent getEatingSound() { return SoundEvents.FIRE_EXTINGUISH; }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 64;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1), attacker);
        target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200), attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
