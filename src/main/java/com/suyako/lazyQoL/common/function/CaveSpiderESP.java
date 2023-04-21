package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.List;

public class CaveSpiderESP {
    public CaveSpiderESP() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void RenderESP(RenderWorldLastEvent event) {
        if (FunctionManager.getStatus("CaveSpiderESP")) {
            double x = Minecraft.getMinecraft().thePlayer.posX;
            double y = Minecraft.getMinecraft().thePlayer.posY;
            double z = Minecraft.getMinecraft().thePlayer.posZ;
            List<EntityCaveSpider> entityList = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityCaveSpider.class, new AxisAlignedBB(x - (2000 / 2d), y - (256 / 2d), z - (2000 / 2d), x + (2000 / 2d), y + (256 / 2d), z + (2000 / 2d)), null);
            for(EntityCaveSpider entity : entityList){
                Utils.OutlinedBoxWithESP(entity.getEntityBoundingBox(), new Color(0, 38, 255), false);
            }
        }
    }
}
