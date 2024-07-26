package com.dongfengpro.speedmod.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class ModDataStorage {

    private static File modDataFolder;

    public static void init() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            Path worldPath = server.getWorldPath(LevelResource.ROOT); // 获取世界保存路径
            modDataFolder = new File(worldPath.toFile(), "moddata/speedmod");
            if (!modDataFolder.exists() && !modDataFolder.mkdirs()) {
                throw new RuntimeException("Failed to create mod data folder: " + modDataFolder.getAbsolutePath());
            }
        }
    }

    public static File getPlayerDataFile(UUID playerUUID) {
        if (modDataFolder == null) {
            throw new IllegalStateException("ModDataStorage not initialized");
        }
        return new File(modDataFolder, playerUUID.toString() + ".json");
    }
}

