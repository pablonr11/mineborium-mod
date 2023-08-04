package com.mundobachata.mineborium.trigger;

import com.mundobachata.mineborium.trigger.custom.DeathByCigaretteTrigger;
import com.mundobachata.mineborium.trigger.custom.SmokeWithFireChargeTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    public static SmokeWithFireChargeTrigger SMOKE_WITH_FIRE_CHARGE;
    public static DeathByCigaretteTrigger DEATH_BY_CIGARETTE;

    public static void register() {
        SMOKE_WITH_FIRE_CHARGE =  CriteriaTriggers.register(new SmokeWithFireChargeTrigger());
        DEATH_BY_CIGARETTE = CriteriaTriggers.register(new DeathByCigaretteTrigger());
    }
}
