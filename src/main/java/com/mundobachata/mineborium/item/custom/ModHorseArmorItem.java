package com.mundobachata.mineborium.item.custom;

import com.mundobachata.mineborium.Mineborium;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;

public class ModHorseArmorItem extends HorseArmorItem {
    public ModHorseArmorItem(int protection, String material, Properties properties) {
        super(protection, new ResourceLocation(Mineborium.MOD_ID,
                "textures/entity/horse/armor/horse_armor_" + material + ".png"), properties);
    }
}
