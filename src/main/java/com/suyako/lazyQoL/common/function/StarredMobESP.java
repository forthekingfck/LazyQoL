package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class StarredMobESP {
    Utils utils = new Utils();
    public StarredMobESP() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void RenderESP(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (FunctionManager.getStatus("StarredMobESP")) {
            if(event.entity instanceof EntityArmorStand && event.entity.hasCustomName()) {
                EntityArmorStand entity = (EntityArmorStand) event.entity;
                if (entity.hasCustomName()) {
                    String nameString = StringUtils.stripControlCodes(entity.getCustomNameTag());
                    if (nameString.contains("✯") && entity.getCustomNameTag().contains("❤")) {
                        Utils.OutlinedBoxWithESP(new AxisAlignedBB(event.entity.posX - 0.5D, event.entity.posY - 2.0D, event.entity.posZ - 0.5D, event.entity.posX + 0.5D, event.entity.posY, event.entity.posZ + 0.5D), new Color(182, 255, 0), false,2.2F);
                    }
                }
            }
        }
    }
}
