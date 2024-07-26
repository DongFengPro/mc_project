package com.dongfengpro.speedmod.datagen;

import com.dongfengpro.speedmod.SpeedMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, SpeedMod.MODID);
    }

    @Override
    protected void start() {

    }
}
