package com.dongfengpro.speedmod.item;

import com.dongfengpro.speedmod.SpeedMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SpeedMod.MODID);

    public static final RegistryObject<Item> WOOD_CRAFT_RECIPES = ITEMS.register("wood_craft_recipes",
            () -> new Item(new Item.Properties().food(ModFoods.WOOD_CRAFT_RECIPES)));

    public static final RegistryObject<Item> STONE_CRAFT_RECIPES = ITEMS.register("stone_craft_recipes",
            () -> new Item(new Item.Properties().food(ModFoods.STONE_CRAFT_RECIPES)));

    public static final RegistryObject<Item> IRON_CRAFT_RECIPES = ITEMS.register("iron_craft_recipes",
            () -> new Item(new Item.Properties().food(ModFoods.IRON_CRAFT_RECIPES)));

    public static final RegistryObject<Item> GOLD_CRAFT_RECIPES = ITEMS.register("gold_craft_recipes",
            () -> new Item(new Item.Properties().food(ModFoods.GOLD_CRAFT_RECIPES)));

    public static final RegistryObject<Item> COPPER_CRAFT_RECIPES = ITEMS.register("copper_craft_recipes",
            () -> new Item(new Item.Properties().food(ModFoods.COPPER_CRAFT_RECIPES)));

    public static final RegistryObject<Item> DIAMOND_CRAFT_RECIPES = ITEMS.register("diamond_craft_recipes",
            () -> new Item(new Item.Properties().food(ModFoods.DIAMOND_CRAFT_RECIPES)));

    public static final RegistryObject<Item> NETHERITE_CRAFT_RECIPES = ITEMS.register("netherite_craft_recipes",
            () -> new Item(new Item.Properties().food(ModFoods.NETHERITE_CRAFT_RECIPES)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

