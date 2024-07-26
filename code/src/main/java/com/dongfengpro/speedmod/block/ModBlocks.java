package com.dongfengpro.speedmod.block;

import com.dongfengpro.speedmod.SpeedMod;
import com.dongfengpro.speedmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SpeedMod.MODID);

    public static final RegistryObject<Block> WOODEN_CAT_BOX = registerBlock("wooden_cat_box",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).noOcclusion()));

    public static final RegistryObject<Block> STONE_CAT_BOX = registerBlock("stone_cat_box",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(1.5f,6.0f).sound(SoundType.STONE).noOcclusion()));

    public static final RegistryObject<Block> IRON_CAT_BOX = registerBlock("iron_cat_box",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(5.0f,6.0f).sound(SoundType.METAL).noOcclusion()));

    public static final RegistryObject<Block> GOLDEN_CAT_BOX = registerBlock("golden_cat_box",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(3.0f,6.0f).sound(SoundType.METAL).noOcclusion()));

    public static final RegistryObject<Block> COPPER_CAT_BOX = registerBlock("copper_cat_box",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(3.0f,6.0f).sound(SoundType.METAL).noOcclusion()));

    public static final RegistryObject<Block> DIAMOND_CAT_BOX = registerBlock("diamond_cat_box",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(5.0f,1200f).sound(SoundType.METAL).noOcclusion()));

    public static final RegistryObject<Block> NETHERITE_CAT_BOX = registerBlock("netherite_cat_box",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)
                    .strength(15f,1200f).sound(SoundType.NETHERITE_BLOCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
