package com.suyako.lazyQoL.core;

import com.suyako.lazyQoL.utils.FontManager;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;

public class HUDManager {
    public HUDManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static void Render(String Name,Object Value,int x,int y){
        Utils.drawRoundRect2(x,y, (float) (FontManager.STXINWEIFont.getStringWidth(Name + ": " + Value) + 4),14,3, new Color(0,0,0, 60).getRGB());
        FontManager.STXINWEIFont.drawString(Name + ": " + Value,x + 2,y + 6, new Color(255, 255, 255, 255).getRGB());
    }
}
