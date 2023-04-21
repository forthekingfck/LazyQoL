package com.suyako.lazyQoL.core.ui.QSF;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.storage.SelectGUIStorage;
import com.suyako.lazyQoL.core.type.SelectObject;
import com.suyako.lazyQoL.core.type.SelectTable;
import com.suyako.lazyQoL.utils.FontManager;
import com.suyako.lazyQoL.utils.GaussianBlur;
import com.suyako.lazyQoL.utils.render.AnimationEngine;
import com.suyako.lazyQoL.utils.render.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.Color;
import java.util.List;

public class QSFSelectGUI {
    Minecraft mc = Minecraft.getMinecraft();
    static int current = -1;
    static int lastHeight = -1;
    static String currentName;
    AnimationEngine animation = new AnimationEngine(5,0);
    public void draw(){
        int configHeight = (Integer) getConfigByFunctionName.get("HUD","HUDHeight");
        int height = configHeight + 30;
        int width = 10;
        FontManager.QuicksandFont47.drawString("LazyQoL",width,configHeight + 5,0xFFFFFF);
        if(lastHeight > 0) {
            GaussianBlur.drawBlurRect(width, height, FunctionManager.getLongestTextWidthAdd20(), lastHeight, 10);
        }
        for(SelectTable table : SelectGUIStorage.TableStorage){
            if(table.getID().equals(LazyQoL.PresentGUI)){
                for(SelectObject object : table.getList()){
                    if(object.getName().equals(LazyQoL.PresentFunction)) {
                        current = height;
                        currentName = object.getName();
                    } else {
                        FontManager.QuicksandBoldFont.drawString(object.getName(),width + 3,height + 5,0xFFFFFF);
                    }
                    height = height + 13;
                }
                counter(table.getList());
                if(current > 0 && currentName != null){
                    animation.moveTo(width,current,0.25);
                    Gui.drawRect(width, (int) animation.yCoord,width + FunctionManager.getLongestTextWidthAdd20(), (int) (animation.yCoord + 13),new Color(154, 154, 154, 77).getRGB());
                    FontManager.QuicksandBoldFont.drawString(currentName,width + 10,current + 5,0xFFFFFF);
                }
                break;
            }
        }
        Shadow.drawShadow(width,configHeight + 30,FunctionManager.getLongestTextWidthAdd20(),lastHeight);
    }
    private void counter(List<SelectObject> object){
        lastHeight = object.size() * 13;
    }
}
