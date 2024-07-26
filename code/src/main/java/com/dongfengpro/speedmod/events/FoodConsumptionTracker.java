package com.dongfengpro.speedmod.events;

import com.dongfengpro.speedmod.util.ModDataStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@Mod.EventBusSubscriber
public class FoodConsumptionTracker {

    private static Map<String, Map<UUID, Integer>> foodConsumptionCounts = new HashMap<>();
    private static Map<UUID, Set<String>> playerUnlockedRecipes = new HashMap<>();

    private static final Set<String> TRACKED_FOOD_ITEMS = new HashSet<>();
    private static final Map<String, Set<String>> SKILL_TO_RECIPES = new HashMap<>();

    static {
        TRACKED_FOOD_ITEMS.add("speedmod:wood_craft_recipes");
        TRACKED_FOOD_ITEMS.add("speedmod:stone_craft_recipes");
        TRACKED_FOOD_ITEMS.add("speedmod:iron_craft_recipes");
        TRACKED_FOOD_ITEMS.add("speedmod:copper_craft_recipes");
        TRACKED_FOOD_ITEMS.add("speedmod:gold_craft_recipes");
        TRACKED_FOOD_ITEMS.add("speedmod:diamond_craft_recipes");
        TRACKED_FOOD_ITEMS.add("speedmod:netherite_craft_recipes");

        SKILL_TO_RECIPES.put("speedmod:wood_craft_recipes", Set.of("minecraft:wooden_sword", "minecraft:wooden_pickaxe"));
        SKILL_TO_RECIPES.put("speedmod:stone_craft_recipes", Set.of("minecraft:stone_sword", "minecraft:stone_pickaxe"));
        // 添加更多技能书与配方的关联
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        // 初始化 ModDataStorage
        ModDataStorage.init();
        // 每次加载新世界时重置数据
        resetData();
    }

    @SubscribeEvent
    public static void onItemFinishedUsing(LivingEntityUseItemEvent.Finish event) {
        if (EffectiveSide.get() != LogicalSide.SERVER) {
            return;
        }

        if (event.getEntity() instanceof Player player) {
            UUID playerUUID = player.getUUID();
            loadCountsForPlayer(playerUUID); // 加载当前玩家的计数数据

            ItemStack itemStack = event.getItem();
            String itemId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemStack.getItem())).toString();

            if (TRACKED_FOOD_ITEMS.contains(itemId)) {
                // 检查是否已解锁技能
                if (playerUnlockedRecipes.containsKey(playerUUID) && playerUnlockedRecipes.get(playerUUID).contains(itemId)) {
                    return;
                }

                Map<UUID, Integer> itemConsumptionCounts = foodConsumptionCounts.getOrDefault(itemId, new HashMap<>());
                int playerCount = itemConsumptionCounts.getOrDefault(playerUUID, 0);

                int newCount = Math.min(playerCount + 1, 10);
                itemConsumptionCounts.put(playerUUID, newCount);
                foodConsumptionCounts.put(itemId, itemConsumptionCounts);

                saveCounts(playerUUID);

                String displayName = itemStack.getHoverName().getString();
                player.displayClientMessage(Component.literal(displayName + " " + newCount + "/10"), true);

                if (newCount == 10) {
                    unlockRecipes(playerUUID, itemId);
                }
            }
        }
    }

    private static void unlockRecipes(UUID playerUUID, String skillId) {
        Set<String> recipes = SKILL_TO_RECIPES.get(skillId);
        if (recipes != null && !recipes.isEmpty()) {
            Set<String> unlockedRecipes = playerUnlockedRecipes.getOrDefault(playerUUID, new HashSet<>());
            unlockedRecipes.add(skillId); // 标记技能书已使用10次并解锁
            unlockedRecipes.addAll(recipes);
            playerUnlockedRecipes.put(playerUUID, unlockedRecipes);
            saveUnlockedRecipes(playerUUID);
        }
    }

    private static void saveCounts(UUID playerUUID) {
        File playerDataFile = ModDataStorage.getPlayerDataFile(playerUUID);
        try (FileWriter writer = new FileWriter(playerDataFile)) {
            Gson gson = new Gson();
            Map<String, Object> data = new HashMap<>();
            data.put("foodConsumptionCounts", foodConsumptionCounts);
            data.put("playerUnlockedRecipes", playerUnlockedRecipes);
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCounts() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            for (UUID playerUUID : server.getPlayerList().getPlayers().stream().map(Player::getUUID).toList()) {
                loadCountsForPlayer(playerUUID);
            }
        }
    }

    private static void loadCountsForPlayer(UUID playerUUID) {
        File playerDataFile = ModDataStorage.getPlayerDataFile(playerUUID);
        if (playerDataFile.exists()) {
            try (FileReader reader = new FileReader(playerDataFile)) {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                Map<String, Object> data = gson.fromJson(reader, type);
                if (data != null) {
                    if (data.containsKey("foodConsumptionCounts")) {
                        Type countType = new TypeToken<Map<String, Map<UUID, Integer>>>() {}.getType();
                        Map<String, Map<UUID, Integer>> loadedCounts = gson.fromJson(gson.toJson(data.get("foodConsumptionCounts")), countType);
                        foodConsumptionCounts.putAll(loadedCounts);
                    }
                    if (data.containsKey("playerUnlockedRecipes")) {
                        Type recipeType = new TypeToken<Map<UUID, Set<String>>>() {}.getType();
                        Map<UUID, Set<String>> loadedRecipes = gson.fromJson(gson.toJson(data.get("playerUnlockedRecipes")), recipeType);
                        playerUnlockedRecipes.putAll(loadedRecipes);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveUnlockedRecipes(UUID playerUUID) {
        saveCounts(playerUUID);
    }

    private static void loadUnlockedRecipes() {
        loadCounts();
    }

    public static boolean hasRecipe(Player player, String recipeId) {
        Set<String> recipes = playerUnlockedRecipes.get(player.getUUID());
        return recipes != null && recipes.contains(recipeId);
    }

    private static void resetData() {
        foodConsumptionCounts = new HashMap<>();
        playerUnlockedRecipes = new HashMap<>();
        loadCounts();
        loadUnlockedRecipes();
    }
}
