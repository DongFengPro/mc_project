package com.dongfengpro.speedmod.item;

import com.dongfengpro.speedmod.SpeedMod;
import com.dongfengpro.speedmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpeedMod.MODID);

    public static final RegistryObject<CreativeModeTab> SPEED_TAB = CREATIVE_MODE_TABS.register("speed_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WOODEN_CAT_BOX.get()))
                    .title(Component.translatable("creativetab.speed_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.WOOD_CRAFT_RECIPES.get());
                        pOutput.accept(ModItems.STONE_CRAFT_RECIPES.get());
                        pOutput.accept(ModItems.IRON_CRAFT_RECIPES.get());
                        pOutput.accept(ModItems.GOLD_CRAFT_RECIPES.get());
                        pOutput.accept(ModItems.COPPER_CRAFT_RECIPES.get());
                        pOutput.accept(ModItems.DIAMOND_CRAFT_RECIPES.get());
                        pOutput.accept(ModItems.NETHERITE_CRAFT_RECIPES.get());

                        pOutput.accept(ModBlocks.WOODEN_CAT_BOX.get());
                        pOutput.accept(ModBlocks.STONE_CAT_BOX.get());
                        pOutput.accept(ModBlocks.IRON_CAT_BOX.get());
                        pOutput.accept(ModBlocks.GOLDEN_CAT_BOX.get());
                        pOutput.accept(ModBlocks.COPPER_CAT_BOX.get());
                        pOutput.accept(ModBlocks.DIAMOND_CAT_BOX.get());
                        pOutput.accept(ModBlocks.NETHERITE_CAT_BOX.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

