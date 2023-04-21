package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.auctionTracker.AuctionItem;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AuctionTracker {
    public AuctionTracker() {
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(FunctionManager.getStatus("AuctionTracker")){
            if(com.suyako.lazyQoL.core.auctionTracker.AuctionTracker.finish && System.currentTimeMillis() - com.suyako.lazyQoL.core.auctionTracker.AuctionTracker.lastGet > ((Integer) getConfigByFunctionName.get("AuctionTracker","cooldown") * 1000)){
                com.suyako.lazyQoL.core.auctionTracker.AuctionTracker.getAuctions();
            }
            if (com.suyako.lazyQoL.core.auctionTracker.AuctionTracker.finish && !com.suyako.lazyQoL.core.auctionTracker.AuctionTracker.auctions.isEmpty()) {
                new Thread(() -> {
                    for(AuctionItem item : com.suyako.lazyQoL.core.auctionTracker.AuctionTracker.auctions){
                        if(Minecraft.getMinecraft().thePlayer != null){
                            ChatComponentText text = new ChatComponentText(EnumChatFormatting.GOLD + "Item: " + item.item_name + EnumChatFormatting.GOLD + " | Coins: " + item.item_name);
                            ChatStyle style = text.getChatStyle();
                            String uuid = item.uuid.substring(0,8) + "-" + item.uuid.substring(8,12) + "-" + item.uuid.substring(12,16) + "-" + item.uuid.substring(16,20) + "-" + item.uuid.substring(20,32);
                            style.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/viewauction " + uuid));
                            Minecraft.getMinecraft().thePlayer.addChatMessage(text);
                        }
                    }
                    com.suyako.lazyQoL.core.auctionTracker.AuctionTracker.auctions.clear();
                });
            }
        }
    }
}
