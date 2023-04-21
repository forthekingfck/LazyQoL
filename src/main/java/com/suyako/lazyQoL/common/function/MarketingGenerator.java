package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MarketingGenerator {
    public MarketingGenerator() {
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onEnable(CustomEventHandler.FunctionEnableEvent event) {
        if(event.function.getName().equals("MarketingGenerator")){
            event.setCanceled(true);
            Minecraft.getMinecraft().thePlayer.sendChatMessage(Utils.MarketingAccountGenerator((String) getConfigByFunctionName.get("MarketingGenerator","name"),(String) getConfigByFunctionName.get("MarketingGenerator","event"),(String) getConfigByFunctionName.get("MarketingGenerator","explain")));
        }
    }
    @SubscribeEvent
    public void onSwitch(CustomEventHandler.FunctionSwitchEvent event) {
        if(event.function.getName().equals("MarketingGenerator")){
            if(event.status) {
                event.setCanceled(true);
                Minecraft.getMinecraft().thePlayer.sendChatMessage(Utils.MarketingAccountGenerator((String) getConfigByFunctionName.get("MarketingGenerator","name"),(String) getConfigByFunctionName.get("MarketingGenerator","event"),(String) getConfigByFunctionName.get("MarketingGenerator","explain")));
            }
        }
    }
}
