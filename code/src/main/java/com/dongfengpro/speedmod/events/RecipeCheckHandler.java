package com.dongfengpro.speedmod.events;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class RecipeCheckHandler {

    @SubscribeEvent    //工作台相关
    public static void onPlayerCraftedItem(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();
        ItemStack craftedItem = event.getCrafting();
        RecipeManager recipeManager = player.level().getRecipeManager();
        ResourceLocation recipeKey = ForgeRegistries.ITEMS.getKey(craftedItem.getItem());

        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE) {
                // Allow crafting in creative mode without restrictions
                return;
            }
        }

        if (recipeKey != null) {
            Recipe<?> recipe = recipeManager.byKey(recipeKey).orElse(null);

            if (recipe != null) {
                String recipeId = recipe.getId().toString();
                if (!FoodConsumptionTracker.hasRecipe(player, recipeId)) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (serverPlayer.gameMode.getGameModeForPlayer() != GameType.CREATIVE) {
                            player.displayClientMessage(Component.literal("You have not unlocked the recipe to craft this item!"), true);
                        }
                    }
                    // Return the items to the crafting grid and clear the crafted item
                    for (int i = 0; i < event.getInventory().getContainerSize(); i++) {
                        ItemStack stackInSlot = event.getInventory().getItem(i);
                        if (!stackInSlot.isEmpty()) {
                            player.getInventory().add(stackInSlot.copy());
                            stackInSlot.setCount(0);
                        }
                    }
                    event.getCrafting().setCount(0);
                }
            }
        }
    }
    @SubscribeEvent//熔炉返还物品
    public static void onFurnaceItemPlaced(BlockEvent.EntityPlaceEvent event) {
        if (EffectiveSide.get().isServer()) {
            BlockPos pos = event.getPos();
            BlockEntity blockEntity = event.getLevel().getBlockEntity(pos);

            if (blockEntity instanceof AbstractFurnaceBlockEntity furnace) {
                // Check if the placed item is in the furnace input slot
                ItemStack inputStack = furnace.getItem(0);
                if (!inputStack.isEmpty()) {
                    // Return the input item to the player's inventory
                    if (event.getEntity() instanceof Player player) {
                        player.getInventory().add(inputStack.copy());
                        furnace.setItem(0, ItemStack.EMPTY); // Clear the input slot
                        player.displayClientMessage(Component.literal("The item has been returned to your inventory!"), true);
                    } else {
                        // If placed by hopper, drop the item in the world
                        furnace.getLevel().addFreshEntity(new ItemEntity(furnace.getLevel(), pos.getX(), pos.getY(), pos.getZ(), inputStack.copy()));
                        furnace.setItem(0, ItemStack.EMPTY); // Clear the input slot
                    }
                }

                // Check if the placed item is in the furnace fuel slot
                ItemStack fuelStack = furnace.getItem(1);
                if (!fuelStack.isEmpty()) {
                    // Return the fuel item to the player's inventory
                    if (event.getEntity() instanceof Player player) {
                        player.getInventory().add(fuelStack.copy());
                        furnace.setItem(1, ItemStack.EMPTY); // Clear the fuel slot
                        player.displayClientMessage(Component.literal("The fuel has been returned to your inventory!"), true);
                    } else {
                        // If placed by hopper, drop the fuel item in the world
                        furnace.getLevel().addFreshEntity(new ItemEntity(furnace.getLevel(), pos.getX(), pos.getY(), pos.getZ(), fuelStack.copy()));
                        furnace.setItem(1, ItemStack.EMPTY); // Clear the fuel slot
                    }
                }
            }
        }
    }

    @SubscribeEvent//漏斗连接熔炉
    public static void onFurnaceHopperInteraction(BlockEvent.NeighborNotifyEvent event) {
        if (EffectiveSide.get().isServer()) {
            BlockPos pos = event.getPos();
            BlockEntity blockEntity = event.getLevel().getBlockEntity(pos);

            if (blockEntity instanceof AbstractFurnaceBlockEntity furnace) {
                // Check if the placed item is in the furnace input slot
                ItemStack inputStack = furnace.getItem(0);
                if (!inputStack.isEmpty()) {
                    // Drop the item in the world if placed by hopper
                    furnace.getLevel().addFreshEntity(new ItemEntity(furnace.getLevel(), pos.getX(), pos.getY(), pos.getZ(), inputStack.copy()));
                    furnace.setItem(0, ItemStack.EMPTY); // Clear the input slot
                }

                // Check if the placed item is in the furnace fuel slot
                ItemStack fuelStack = furnace.getItem(1);
                if (!fuelStack.isEmpty()) {
                    // Drop the fuel item in the world if placed by hopper
                    furnace.getLevel().addFreshEntity(new ItemEntity(furnace.getLevel(), pos.getX(), pos.getY(), pos.getZ(), fuelStack.copy()));
                    furnace.setItem(1, ItemStack.EMPTY); // Clear the fuel slot
                }
            }
        }
    }

    @SubscribeEvent
    public static void onFurnaceTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (event.level instanceof ServerLevel serverLevel) {
                serverLevel.players().forEach(player -> {
                    BlockPos pos = player.blockPosition();
                    BlockEntity blockEntity = serverLevel.getBlockEntity(pos);

                    if (blockEntity instanceof AbstractFurnaceBlockEntity furnace) {
                        // Check if the placed item is in the furnace input slot
                        ItemStack inputStack = furnace.getItem(0);
                        if (!inputStack.isEmpty()) {
                            // Return the input item to the player's inventory
                            player.getInventory().add(inputStack.copy());
                            furnace.setItem(0, ItemStack.EMPTY); // Clear the input slot
                            player.displayClientMessage(Component.literal("The item has been returned to your inventory!"), true);
                        }

                        // Check if the placed item is in the furnace fuel slot
                        ItemStack fuelStack = furnace.getItem(1);
                        if (!fuelStack.isEmpty()) {
                            // Return the fuel item to the player's inventory
                            player.getInventory().add(fuelStack.copy());
                            furnace.setItem(1, ItemStack.EMPTY); // Clear the fuel slot
                            player.displayClientMessage(Component.literal("The fuel has been returned to your inventory!"), true);
                        }
                    }
                });
            }
        }
    }
}

