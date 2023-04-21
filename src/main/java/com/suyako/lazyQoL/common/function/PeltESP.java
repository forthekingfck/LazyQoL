package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.utils.Chroma;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class PeltESP {
    public PeltESP(){
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void RenderEvent(RenderWorldLastEvent event){
        if (FunctionManager.getStatus("PeltESP")) {
                if (Minecraft.getMinecraft().theWorld != null) {
                    Double x = Minecraft.getMinecraft().thePlayer.posX;
                    Double y = Minecraft.getMinecraft().thePlayer.posY;
                    Double z = Minecraft.getMinecraft().thePlayer.posZ;
                    List<EntityArmorStand> entityList = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, new AxisAlignedBB(x - (2000 / 2d), y - (256 / 2d), z - (2000 / 2d), x + (2000 / 2d), y + (256 / 2d), z + (2000 / 2d)), null);
                    for(Entity entity : entityList) {
                        if (entity instanceof EntityArmorStand) {
                            if (entity.hasCustomName()) {
                                if (isValid(entity.getCustomNameTag())) {
                                    Utils.OutlinedBoxWithESP(new AxisAlignedBB(entity.posX - 0.5D, entity.posY - 1.0D, entity.posZ - 0.5D, entity.posX + 0.5D, entity.posY, entity.posZ + 0.5D), Chroma.color, false, 3F);
                                    Utils.renderTrace(Minecraft.getMinecraft().thePlayer.getPosition(),entity.getPosition(),Chroma.color,2);
                                }
                            }
                        }
                    }
                }
        }
    }
    public boolean isValid(String name){
        String lowerName = name.toLowerCase();
        return (lowerName.contains("trackable") || lowerName.contains("untrackable") || lowerName.contains("undetected") || lowerName.contains("endangered") || lowerName.contains("elusive"));
    }
}
