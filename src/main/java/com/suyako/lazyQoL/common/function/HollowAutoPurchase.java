package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HollowAutoPurchase {
    public HollowAutoPurchase() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void AutoPurchase(ClientChatReceivedEvent event){
        if(FunctionManager.getStatus("HollowAutoPurchase")) {
            if (EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getFormattedText()).contains("purchase")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/purchasecrystallhollowspass");
            }
        }
    }
}
