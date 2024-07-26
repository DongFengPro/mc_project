package com.dongfengpro.speedmod.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModEvents {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        // 检查加入的实体是否是 TNT
        if (event.getEntity() instanceof PrimedTnt) {
            PrimedTnt tnt = (PrimedTnt) event.getEntity();
            // 设置 TNT 的引爆时间为 40 游戏刻
            tnt.setFuse(40);
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            AttributeInstance attributeInstance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attributeInstance != null) {
                double baseSpeed = attributeInstance.getBaseValue();
                double maxSpeed = 0.6; // 设置最大速度为0.6
                double newSpeed = Math.min(baseSpeed * 2, maxSpeed);
                if (attributeInstance.getValue() != newSpeed) {
                    attributeInstance.setBaseValue(newSpeed);
                }
            }
        }
    }
}


