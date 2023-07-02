package com.mundobachata.mineborium.entity.custom;

import com.mundobachata.mineborium.entity.ModEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class MarlboriumArrow extends AbstractArrow {


    private int nauseaDuration = 200;
    private int witherDuration = 60;

    public MarlboriumArrow(EntityType<? extends MarlboriumArrow> entityType, Level level) {
        super(entityType, level);
    }

    public MarlboriumArrow(LivingEntity p_36718_, Level p_36719_) {
        super(ModEntityTypes.MARLBORIUM_ARROW.get(), p_36718_, p_36719_);
    }

    public MarlboriumArrow(Level level, double x, double y, double z) {
        super(ModEntityTypes.MARLBORIUM_ARROW.get(), x, y, z, level);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level.isClientSide && !this.inGround) {
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        MobEffectInstance nauseaMobEffectInstance = new MobEffectInstance(MobEffects.CONFUSION, this.nauseaDuration, 0);
        MobEffectInstance witherMobEffectInstance = new MobEffectInstance(MobEffects.WITHER, this.witherDuration, 1);
        livingEntity.addEffect(nauseaMobEffectInstance, this.getEffectSource());
        livingEntity.addEffect(witherMobEffectInstance, this.getEffectSource());
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if(nbt.contains("nausea_duration")) {
            this.nauseaDuration = nbt.getInt("nausea_duration");
        }
        if(nbt.contains("wither_duration")) {
            this.witherDuration = nbt.getInt("wither_duration");
        }
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        this.addAdditionalSaveData(nbt);
        nbt.putInt("nausea_duration", this.nauseaDuration);
        nbt.putInt("wither_duration", this.witherDuration);
    }
}
