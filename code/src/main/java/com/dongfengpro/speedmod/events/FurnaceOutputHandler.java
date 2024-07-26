package com.dongfengpro.speedmod.events;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
public class FurnaceOutputHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onFurnaceTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (event.level instanceof ServerLevel serverLevel) {
                for (Player player : serverLevel.players()) {
                    // 检查玩家周围的所有方块
                    for (BlockPos pos : BlockPos.betweenClosed(player.blockPosition().offset(-5, -5, -5), player.blockPosition().offset(5, 5, 5))) {
                        BlockEntity blockEntity = serverLevel.getBlockEntity(pos);

                        if (blockEntity instanceof AbstractFurnaceBlockEntity furnace) {
                            // 检查玩家是否处于创造模式
                            if (player instanceof ServerPlayer serverPlayer) {
                                if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE) {
                                    continue; // 允许创造模式下不受限制
                                }
                            }

                            // 检查输出槽
                            ItemStack outputStack = furnace.getItem(2);
                            if (!outputStack.isEmpty()) {
                                // 获取配方
                                RecipeManager recipeManager = player.level().getRecipeManager();
                                ResourceLocation recipeKey = ForgeRegistries.ITEMS.getKey(outputStack.getItem());

                                if (recipeKey != null) {
                                    Recipe<?> recipe = recipeManager.byKey(recipeKey).orElse(null);

                                    if (recipe != null) {
                                        String recipeId = recipe.getId().toString();
                                        if (!FoodConsumptionTracker.hasRecipe(player, recipeId)) {
                                            // 玩家未解锁该配方，将输出量设为0
                                            outputStack.setCount(0);
                                            furnace.setItem(2, outputStack); // 更新输出槽

                                            // 向玩家显示消息
                                            if (player instanceof ServerPlayer serverPlayer) {
                                                serverPlayer.displayClientMessage(Component.literal("The recipe was not obtained and the item smelting failed!"), true);
                                                LOGGER.info("Output amount set to zero for player: {}", serverPlayer.getName().getString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
