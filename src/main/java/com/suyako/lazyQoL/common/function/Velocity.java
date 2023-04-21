package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.event.CustomEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

public class Velocity {

    public static List<String> BootsIDList = new ArrayList<String>();
    public static List<String> ItemIDList = new ArrayList<String>();


    public Velocity(){
        BootsIDList.add("SPIDER_BOOTS");
        BootsIDList.add("TARANTULA_BOOTS");

        ItemIDList.add("BONZO_STAFF");
        ItemIDList.add("STARRED_BONZO_STAFF");
        ItemIDList.add("JERRY_STAFF");
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    public Boolean SkyBlockKB(){
        if(!Minecraft.getMinecraft().thePlayer.isInLava()) {
            ItemStack held = Minecraft.getMinecraft().thePlayer.getHeldItem();
            return held != null && (held.getDisplayName().contains("Bonzo's Staff") || held.getDisplayName().contains("Jerry-chine Gun"));
        }
        return true;
    }
}
