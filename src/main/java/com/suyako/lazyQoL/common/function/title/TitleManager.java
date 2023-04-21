package com.suyako.lazyQoL.common.function.title;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.via.protocol.ProtocolCollection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TitleManager {
    public static String tips = "";
    int tick = 599;
    final int MaxTick = 600;
    List<String> TipsList = new ArrayList<String>();

    public TitleManager(){
        MinecraftForge.EVENT_BUS.register(this);

        TipsList.add("What do you wish");
    }
    @SubscribeEvent
    public void ClientTickEvent(TickEvent.ClientTickEvent event){
        if(tick == MaxTick) {
            tick = 0;
            tips = TipsList.get(new Random().nextInt(TipsList.size()));
            try {
                Display.setTitle("LazyQoL " + LazyQoL.VERSION + "." + new Random().nextInt(114514) + " | " + tips + " (Minecraft " + Objects.requireNonNull(ProtocolCollection.getProtocolById(LazyQoL.getVersion())).getName() + ")");
            } catch(Exception e){
                e.printStackTrace();
                Display.setTitle("LazyQoL " + LazyQoL.VERSION + "." + new Random().nextInt(114514) + " | " + tips + " (Minecraft " + "1.8.9" + ")");
            }
        } else {
            tick = tick + 1;
        }
    }
}
