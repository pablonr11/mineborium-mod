package com.mundobachata.mineborium.abstinence;

import net.minecraft.nbt.CompoundTag;

public class PlayerAbstinence {
    private boolean hasSmoked;
    private boolean hasAbstinenceEffect;
    private int ticksSinceLastCigarette;
    private int cigarettesInARow;

    // Theory: Those ticks are multiplied by 2 because the method that calls increaseTicksSinceLastCigarette() is calling it twice for some reason...
    public static final int LEVEL_1_TOXICITY_CIGARETTES = 10; // Nausea effect
    public static final int LEVEL_1_TOXICITY_TICKS = 1200; // Number of ticks for the effect
    public static final int LEVEL_2_TOXICITY_CIGARETTES = 20; // Wither effect
    public static final int LEVEL_2_TOXICITY_TICKS = 400; // Number of ticks for the effect
    public static final int LEVEL_3_TOXICITY_CIGARETTES = 40; // Death effect (lmfao)
    public static final int CIGARETTES_IN_A_ROW_TICK_RANGE = 400;
    public static final int ABSTINENCE_MAX_TIME = 1600;
    public static final int APPLY_ABSTINENCE_EFFECT_TIME = 1000;
    public PlayerAbstinence() {
        this.hasSmoked = false;
        this.hasAbstinenceEffect = false;
        this.ticksSinceLastCigarette = 0;
        this.cigarettesInARow = 0;
    }

    public boolean hasSmoked() {
        return hasSmoked;
    }

    public void setHasSmoked(boolean hasSmoked) {
        this.hasSmoked = hasSmoked;
    }

    public boolean hasAbstinenceEffect() {
        return hasAbstinenceEffect;
    }

    public void setHasAbstinenceEffect(boolean hasAbstinenceEffect) {
        this.hasAbstinenceEffect = hasAbstinenceEffect;
    }

    public int getTicksSinceLastCigarette() {
        return ticksSinceLastCigarette;
    }

    public void increaseTicksSinceLastCigarette() {
        ticksSinceLastCigarette++;
    }

    public void resetTicksSinceLastCigarette() {
        this.ticksSinceLastCigarette = 0;
    }

    public int getCigarettesInARow() {
        return cigarettesInARow;
    }

    public void increaseCigarettesInARow() {
        cigarettesInARow++;
    }

    public void resetCigarettesInARow() {
        this.cigarettesInARow = 0;
    }

    public void copyFrom(PlayerAbstinence source) {
        this.hasSmoked = source.hasSmoked;
        this.hasAbstinenceEffect = source.hasAbstinenceEffect;
        this.ticksSinceLastCigarette = source.ticksSinceLastCigarette;
        this.cigarettesInARow = source.cigarettesInARow;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("hasSmoked", hasSmoked);
        nbt.putBoolean("hasAbstinenceEffect", hasAbstinenceEffect);
        nbt.putInt("ticksSinceLastCigarette", ticksSinceLastCigarette);
        nbt.putInt("cigarettesInARow", cigarettesInARow);
    }

    public void loadNBTData(CompoundTag nbt) {
        hasSmoked = nbt.getBoolean("hasSmoked");
        hasAbstinenceEffect = nbt.getBoolean("hasAbstinenceEffect");
        ticksSinceLastCigarette = nbt.getInt("ticksSinceLastCigarette");
        cigarettesInARow = nbt.getInt("cigarettesInARow");
    }

}
