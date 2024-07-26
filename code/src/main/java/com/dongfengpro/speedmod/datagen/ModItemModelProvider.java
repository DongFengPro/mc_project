package com.dongfengpro.speedmod.datagen;

import com.dongfengpro.speedmod.SpeedMod;
import com.dongfengpro.speedmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SpeedMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.WOOD_CRAFT_RECIPES);
        simpleItem(ModItems.STONE_CRAFT_RECIPES);
        simpleItem(ModItems.IRON_CRAFT_RECIPES);
        simpleItem(ModItems.GOLD_CRAFT_RECIPES);
        simpleItem(ModItems.COPPER_CRAFT_RECIPES);
        simpleItem(ModItems.DIAMOND_CRAFT_RECIPES);
        simpleItem(ModItems.NETHERITE_CRAFT_RECIPES);

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(SpeedMod.MODID,"item/" + item.getId().getPath()));
    }
}


