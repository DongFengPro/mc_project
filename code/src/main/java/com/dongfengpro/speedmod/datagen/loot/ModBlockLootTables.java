package com.dongfengpro.speedmod.datagen.loot;

import com.dongfengpro.speedmod.block.ModBlocks;
import com.dongfengpro.speedmod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        add(ModBlocks.STONE_CAT_BOX.get(), createCatBoxLootTable(ModBlocks.STONE_CAT_BOX.get()));
        add(ModBlocks.WOODEN_CAT_BOX.get(), createCatBoxLootTable(ModBlocks.WOODEN_CAT_BOX.get()));
        add(ModBlocks.IRON_CAT_BOX.get(), createCatBoxLootTable(ModBlocks.IRON_CAT_BOX.get()));
        add(ModBlocks.GOLDEN_CAT_BOX.get(), createCatBoxLootTable(ModBlocks.GOLDEN_CAT_BOX.get()));
        add(ModBlocks.COPPER_CAT_BOX.get(), createCatBoxLootTable(ModBlocks.COPPER_CAT_BOX.get()));
        add(ModBlocks.DIAMOND_CAT_BOX.get(), createCatBoxLootTable(ModBlocks.DIAMOND_CAT_BOX.get()));
        add(ModBlocks.NETHERITE_CAT_BOX.get(), createCatBoxLootTable(ModBlocks.NETHERITE_CAT_BOX.get()));
    }

    private LootTable.Builder createCatBoxLootTable(Block block) {
        // Assuming that CAT_SPAWN_EGG is the identifier for the cat spawn egg item
        LootPool.Builder fixedItemPool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.CAT_SPAWN_EGG));  // 固定掉落猫刷怪蛋

        LootPool.Builder variableItemPool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1));  // 特定掉落根据方块类型

        if (block == ModBlocks.STONE_CAT_BOX.get()) {
            variableItemPool.add(LootItem.lootTableItem(ModItems.STONE_CRAFT_RECIPES.get()));
        } else if (block == ModBlocks.WOODEN_CAT_BOX.get()) {
            variableItemPool.add(LootItem.lootTableItem(ModItems.WOOD_CRAFT_RECIPES.get()));
        } else if (block == ModBlocks.IRON_CAT_BOX.get()) {
            variableItemPool.add(LootItem.lootTableItem(ModItems.IRON_CRAFT_RECIPES.get()));
        } else if (block == ModBlocks.GOLDEN_CAT_BOX.get()) {
            variableItemPool.add(LootItem.lootTableItem(ModItems.GOLD_CRAFT_RECIPES.get()));
        } else if (block == ModBlocks.COPPER_CAT_BOX.get()) {
            variableItemPool.add(LootItem.lootTableItem(ModItems.COPPER_CRAFT_RECIPES.get()));
        } else if (block == ModBlocks.DIAMOND_CAT_BOX.get()) {
            variableItemPool.add(LootItem.lootTableItem(ModItems.DIAMOND_CRAFT_RECIPES.get()));
        } else if (block == ModBlocks.NETHERITE_CAT_BOX.get()) {
            variableItemPool.add(LootItem.lootTableItem(ModItems.NETHERITE_CRAFT_RECIPES.get()));
        }

        return LootTable.lootTable()
                .withPool(fixedItemPool)
                .withPool(variableItemPool);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}



