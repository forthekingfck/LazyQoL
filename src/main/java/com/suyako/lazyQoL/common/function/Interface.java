package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.core.gui.SettingsGUI;
import com.suyako.lazyQoL.core.HUDManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class Interface {
    public Interface(){
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void FunctionSwitchEvent(CustomEventHandler.FunctionSwitchEvent event){
        if(Minecraft.getMinecraft().theWorld != null){
            if(event.function.getName().equals("Interface")){
                event.setCanceled(true);
                Minecraft.getMinecraft().displayGuiScreen(new SettingsGUI(Minecraft.getMinecraft().currentScreen,"Interface", Objects.requireNonNull(FunctionManager.getFunctionByName("Interface")).getConfigurationList()));
            }
        }
    }
    @SubscribeEvent
    public void RenderGameOverlay(RenderGameOverlayEvent event){
        if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
            if(Minecraft.getMinecraft().theWorld != null) {
                if ((Boolean) getConfigByFunctionName.get("Interface", "fps")) {
                    HUDManager.Render("FPS", Minecraft.getDebugFPS(), (int) getConfigByFunctionName.get("Interface", "fpsX"), (int) getConfigByFunctionName.get("Interface", "fpsY"));
                }
                if ((Boolean) getConfigByFunctionName.get("Interface", "location")) {
                    HUDManager.Render("X", (int)Minecraft.getMinecraft().thePlayer.posX, (int) getConfigByFunctionName.get("Interface", "locationX"), (int) getConfigByFunctionName.get("Interface", "locationY"));
                    HUDManager.Render("Y", (int)Minecraft.getMinecraft().thePlayer.posY, (int) getConfigByFunctionName.get("Interface", "locationX"), (int) getConfigByFunctionName.get("Interface", "locationY") + 15);
                    HUDManager.Render("Z", (int)Minecraft.getMinecraft().thePlayer.posZ, (int) getConfigByFunctionName.get("Interface", "locationX"), (int) getConfigByFunctionName.get("Interface", "locationY") + 30);
                }
                if ((Boolean) getConfigByFunctionName.get("Interface", "day")) {
                    HUDManager.Render("Day", Minecraft.getMinecraft().theWorld.getWorldTime() / 24000L, (int) getConfigByFunctionName.get("Interface", "dayX"), (int) getConfigByFunctionName.get("Interface", "dayY"));
                }
            }
        }
    }
}
