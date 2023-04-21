package com.suyako.lazyQoL.core.ui.transparent;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.storage.SelectGUIStorage;
import com.suyako.lazyQoL.core.type.SelectObject;
import com.suyako.lazyQoL.core.type.SelectTable;
import com.suyako.lazyQoL.utils.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class SelectGUI {
    Minecraft mc = Minecraft.getMinecraft();
    public void draw(){
        int height = (Integer) getConfigByFunctionName.get("HUD","HUDHeight") + 20;
        int width = 5;
        FontManager.GothamRoundedFont.drawString("",0,0,0xFFFFFF);
        ResourceLocation logo = new ResourceLocation(LazyQoL.MODID, "logo.png");
        Minecraft.getMinecraft().renderEngine.bindTexture(logo);
        Gui.drawModalRectWithCustomSizedTexture(width,(Integer)getConfigByFunctionName.get("HUD","HUDHeight"),0,0,20,20,20,20);
        FontManager.ExpressaSerialBigFont.drawString("lazyQoL",width + 22, (float) ((Integer)getConfigByFunctionName.get("HUD","HUDHeight") + ((FontManager.GothamRoundedFont.getHeight() * 1.0) / 2) - 5),0xFFFFFF);
        for(SelectTable table : SelectGUIStorage.TableStorage){
            if(table.getID().equals(LazyQoL.PresentGUI)){
                for(SelectObject object : table.getList()){
                    ResourceLocation resourceLocation = new ResourceLocation(LazyQoL.MODID, "hud2.png");
                    Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
                    if(object.getName().equals(LazyQoL.PresentFunction)) {
                        Gui.drawModalRectWithCustomSizedTexture(width,height,0,14,FunctionManager.getLongestTextWidthAdd20(),14,FunctionManager.getLongestTextWidthAdd20() - 15,28);
                        FontManager.GothamRoundedFont.drawString(object.getName(),width + 10,height + 4,0xFFFFFF);
                    } else {
                        Gui.drawModalRectWithCustomSizedTexture(width,height,0,0,FunctionManager.getLongestTextWidthAdd20(),14,FunctionManager.getLongestTextWidthAdd20() - 15,28);
                        FontManager.GothamRoundedFont.drawString(object.getName(),width + 3,height + 4,0xFFFFFF);
                    }
                    height = height + 14;
                }
            }
        }
    }
}
