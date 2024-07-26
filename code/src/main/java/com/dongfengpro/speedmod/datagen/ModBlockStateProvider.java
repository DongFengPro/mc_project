package com.dongfengpro.speedmod.datagen;

import com.dongfengpro.speedmod.SpeedMod;
import com.dongfengpro.speedmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, SpeedMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.WOODEN_CAT_BOX.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/wooden_cat_box")));

        simpleBlockWithItem(ModBlocks.STONE_CAT_BOX.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/stone_cat_box")));

        simpleBlockWithItem(ModBlocks.IRON_CAT_BOX.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/iron_cat_box")));

        simpleBlockWithItem(ModBlocks.COPPER_CAT_BOX.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/copper_cat_box")));

        simpleBlockWithItem(ModBlocks.GOLDEN_CAT_BOX.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/golden_cat_box")));

        simpleBlockWithItem(ModBlocks.DIAMOND_CAT_BOX.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/diamond_cat_box")));

        simpleBlockWithItem(ModBlocks.NETHERITE_CAT_BOX.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/netherite_cat_box")));

    }
}

