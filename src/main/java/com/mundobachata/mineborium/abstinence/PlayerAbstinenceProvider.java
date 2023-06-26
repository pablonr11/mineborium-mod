package com.mundobachata.mineborium.abstinence;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerAbstinenceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerAbstinence> PLAYER_ABSTINENCE = CapabilityManager.get(new CapabilityToken<PlayerAbstinence>() {});

    private PlayerAbstinence abstinence = null;
    private final LazyOptional<PlayerAbstinence> optional = LazyOptional.of(this::createPlayerAbstinence);

    private PlayerAbstinence createPlayerAbstinence() {
        if(this.abstinence == null) {
            this.abstinence = new PlayerAbstinence();
        }

        return this.abstinence;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_ABSTINENCE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerAbstinence().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerAbstinence().loadNBTData(nbt);
    }
}
