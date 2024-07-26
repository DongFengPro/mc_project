package com.dongfengpro.speedmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties WOOD_CRAFT_RECIPES = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0f).effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f).build();

    public static final FoodProperties STONE_CRAFT_RECIPES = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0f).effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f).build();

    public static final FoodProperties IRON_CRAFT_RECIPES = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0f).effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f).build();

    public static final FoodProperties GOLD_CRAFT_RECIPES = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0f).effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f).build();

    public static final FoodProperties COPPER_CRAFT_RECIPES = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0f).effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f).build();

    public static final FoodProperties DIAMOND_CRAFT_RECIPES = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0f).effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f).build();

    public static final FoodProperties NETHERITE_CRAFT_RECIPES = new FoodProperties.Builder().nutrition(1).fast()
            .saturationMod(0f).effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f).build();

}
