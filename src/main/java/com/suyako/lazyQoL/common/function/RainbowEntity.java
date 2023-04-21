package com.suyako.lazyQoL.common.function;


import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.utils.Chroma;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RainbowEntity {
    public RainbowEntity() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onRenderScreen(RenderLivingEvent.Pre<EntityLivingBase> event){
        if(FunctionManager.getStatus("RainbowEntity")){
            GlStateManager.color(Chroma.color.getRed(),Chroma.color.getGreen(),Chroma.color.getBlue());
        }
    }
    @SubscribeEvent
    public void onRenderScreen(RenderLivingEvent.Post<EntityLivingBase> event){
        if(FunctionManager.getStatus("RainbowEntity")){
            GlStateManager.color(1.0F,1.0F,1.0F);
        }
    }
}
