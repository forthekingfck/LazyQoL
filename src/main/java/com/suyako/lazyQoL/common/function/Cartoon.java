package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.core.gui.ClickGUI;
import com.suyako.lazyQoL.core.gui.SettingsGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Cartoon {
    public Cartoon() {
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(FunctionManager.getStatus("Cartoon")){
            if(!Minecraft.getMinecraft().entityRenderer.isShaderActive()) {
                Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/art.json"));
            }
        }
    }
    @SubscribeEvent
    public void onFunctionDisable(CustomEventHandler.FunctionDisabledEvent event) {
        if(event.function.getName().equals("Cartoon")){
            if(!(Minecraft.getMinecraft().currentScreen instanceof ClickGUI) && !(Minecraft.getMinecraft().currentScreen instanceof SettingsGUI)) {
                Minecraft.getMinecraft().entityRenderer.stopUseShader();
            }
        }
    }
    @SubscribeEvent
    public void onFunctionSwitch(CustomEventHandler.FunctionSwitchEvent event) {
        if(event.function.getName().equals("Cartoon") && !event.status){
            if(!(Minecraft.getMinecraft().currentScreen instanceof ClickGUI) && !(Minecraft.getMinecraft().currentScreen instanceof SettingsGUI)) {
                Minecraft.getMinecraft().entityRenderer.stopUseShader();
            }
        }
    }
}
